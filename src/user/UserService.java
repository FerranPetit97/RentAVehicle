package user;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import user.client.Client;
import user.client.ClientController;
import user.client.premium.Premium;
import user.client.premium.PremiumController;
import user.manager.Manager;
import user.manager.ManagerController;
import user.mechanic.Mechanic;
import user.mechanic.MechanicController;
import vehicle.usage.UsageController;

public class UserService {
  private final ClientController clientController;
  private final PremiumController premiumController;
  private final MechanicController mechanicController;
  private final ManagerController managerController;
  private final NotifyController notifyController;
  private final UsageController usageController;

  public UserService(ClientController clientController, PremiumController premiumController,
      MechanicController mechanicController, ManagerController managerController,
      NotifyController notifyController, UsageController usageController) {
    this.clientController = clientController;
    this.premiumController = premiumController;
    this.mechanicController = mechanicController;
    this.managerController = managerController;
    this.notifyController = notifyController;
    this.usageController = usageController;
  }

  public boolean createUser(int id, String name, String email, String password, String role) {
    switch (role.toLowerCase()) {
      case "client":
        return clientController.createClient(new Client(id, name, email, password));
      case "premium":
        notifyController.log(NotifyCodeEnum.BAD_REQUEST, "Cannot create premium user directly.");
        return false;
      case "mechanic":
        return mechanicController.createMechanic(new Mechanic(id, name, email, password));
      case "manager":
        return managerController.createManager(new Manager(id, name, email, password));
      default:
        notifyController.log(NotifyCodeEnum.BAD_REQUEST, "Invalid user role: " + role);
        return false;
    }
  }

  public List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<>();
    allUsers.addAll(clientController.getAllClients());
    allUsers.addAll(premiumController.getAllPremiumUsers());
    allUsers.addAll(mechanicController.getAllMechanics());
    allUsers.addAll(managerController.getAllManagers());
    return allUsers;
  }

  public User findUserById(int id) {
    return getAllUsers().stream()
        .filter(user -> user.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean deleteUserById(int id) {
    User user = findUserById(id);
    if (user == null) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "User with ID " + id + " not found.");
      return false;
    }

    if (user instanceof Client) {
      return clientController.deleteClientByEmail(user.getEmail());
    } else if (user instanceof Premium) {
      return premiumController.removePremiumUserById(id);
    } else if (user instanceof Mechanic) {
      return mechanicController.deleteMechanicById(id);
    } else if (user instanceof Manager) {
      return managerController.deleteManagerByEmail(user.getEmail());
    }

    return false;
  }

  public List<User> getEligibleForPremium() {
    List<User> eligibleUsers = new ArrayList<>();
    for (Client client : clientController.getAllClients()) {
      if (isEligibleForPremium(client)) {
        eligibleUsers.add(client);
      }
    }
    return eligibleUsers;
  }

  public boolean promoteToPremium(int clientId, double discountPercentage) {
    Client client = (Client) findUserById(clientId);
    if (client == null || !(client instanceof Client)) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "Client with ID " + clientId + " not found.");
      return false;
    }

    Premium premiumUser = new Premium(client.getId(), client.getName(), client.getEmail(),
        client.getPassword(), discountPercentage);
    premiumController.addPremiumUser(premiumUser);
    clientController.deleteClientByEmail(client.getEmail());
    return true;
  }

  public User getUserByEmail(String email) {
    return getAllUsers().stream()
        .filter(user -> user.getEmail().equalsIgnoreCase(user.getEmail()))
        .findFirst()
        .orElse(null);
  }

  public updateUser(User user) {
    if (user instanceof Client) {
      return clientController.updateClient((Client) user);
    } else if (user instanceof Premium) {
      return premiumController.updatePremiumUser((Premium) user);
    } else if (user instanceof Mechanic) {
      return mechanicController.updateMechanic((Mechanic) user);
    } else if (user instanceof Manager) {
      return managerController.updateManager((Manager) user);
    }
    return false;
  }

  private boolean isEligibleForPremium(Client client) {
    List<vehicle.usage.Usage> usages = usageController.getUsagesByUser(client);

    // Regla 1: Al menos 15 usos en el último mes
    long lastMonthUsages = usages.stream()
        .filter(usage -> usage.getStartTime().isAfter(LocalDate.now().minus(1, ChronoUnit.MONTHS).atStartOfDay()))
        .count();
    if (lastMonthUsages >= 15) {
      return true;
    }

    // Regla 2: Al menos 10 usos al mes durante 3 meses consecutivos
    long threeMonthsUsages = usages.stream()
        .filter(usage -> usage.getStartTime().isAfter(LocalDate.now().minus(3, ChronoUnit.MONTHS).atStartOfDay()))
        .count();
    if (threeMonthsUsages >= 30) {
      return true;
    }

    // Regla 3: Uso de los tres tipos de vehículos durante 6 meses consecutivos
    boolean usedAllVehicleTypes = usages.stream()
        .filter(usage -> usage.getStartTime().isAfter(LocalDate.now().minus(6, ChronoUnit.MONTHS).atStartOfDay()))
        .map(usage -> usage.getVehicle().getType())
        .distinct()
        .count() == 3;
    return usedAllVehicleTypes;
  }
}