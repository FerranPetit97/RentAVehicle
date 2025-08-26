package user;

import java.util.ArrayList;
import java.util.List;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import user.mechanic.Mechanic;

public class UserService {
  private final List<User> users = new ArrayList<>();

  private final NotifyController notifyController;

  private int nextId = 1;

  public UserService(NotifyController notifyController) {
    this.notifyController = notifyController;
  }

  public boolean createUser(User user) {
    if (user == null) {
      notifyController.log(NotifyCodeEnum.BAD_REQUEST, "User cannot be null.");
      return false;
    }

    user.setId(nextId++);

    if (isUserDuplicate(user.getId(), user.getEmail())) {
      notifyController.log(NotifyCodeEnum.BAD_REQUEST, "User with the same ID or email already exists.");
      return false;
    }

    users.add(user);
    return true;
  }

  public List<User> getAllUsers() {
    if (users.isEmpty()) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "No users found in the system.");
    }
    return users;
  }

  public User findUserById(int id) {
    return getAllUsers().stream()
        .filter(u -> u.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean updateUser(int id, String name, String email, String password, String role, int vehicleId) {
    User user = findUserById(id);
    if (user == null) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "User with ID " + id + " not found.");
      return false;
    }

    if (user instanceof Mechanic) {
      Mechanic mechanic = (Mechanic) user;
      mechanic.setVehicleId(vehicleId);
    }

    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    user.setRole(role);
    return true;
  }

  public boolean deleteUserById(int id) {
    User user = findUserById(id);
    if (user == null) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "User with ID " + id + " not found.");
      return false;
    }
    users.remove(user);
    return true;
  }

  private boolean isUserDuplicate(int id, String email) {
    return users.stream().anyMatch(user -> user.getId() == id || user.getEmail().equalsIgnoreCase(email));
  }

  // public boolean promoteToPremium(int clientId, double discountPercentage) {
  // Client client = (Client) findUserById(clientId);
  // if (client == null || !(client instanceof Client)) {
  // notifyController.log(NotifyCodeEnum.NOT_FOUND, "Client with ID " + clientId +
  // " not found.");
  // return false;
  // }

  // Premium premiumUser = new Premium(client.getId(), client.getName(),
  // client.getEmail(),
  // client.getPassword(), discountPercentage);
  // premiumController.addPremiumUser(premiumUser);
  // clientController.deleteClientByEmail(client.getEmail());
  // return true;
  // }

}