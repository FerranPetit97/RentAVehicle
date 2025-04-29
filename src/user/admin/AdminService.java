package user.admin;

import java.util.ArrayList;
import java.util.List;

import user.User;
import user.UserFactory;
import user.UserController;
import user.client.premium.Premium;

public class AdminService {
  private final List<Admin> admins = new ArrayList<>();
  private final UserController userController;

  public AdminService(User authenticatedUser) {
    this.userController = UserFactory.getInstance(authenticatedUser);
  }

  public boolean addAdmin(Admin admin) {
    return admins.add(admin);
  }

  public List<Admin> getAllAdmins() {
    return admins;
  }

  public List<User> getEligibleForPremium() {
    List<User> eligibleUsers = new ArrayList<>();
    for (User user : userController.getAllUsers()) {
      if (!(user instanceof Premium) && meetsPremiumConditions(user)) {
        eligibleUsers.add(user);
      }
    }
    return eligibleUsers;
  }

  private boolean meetsPremiumConditions(User user) {
    // TODO: Aquí puedes implementar la lógica real para verificar las condiciones:
    // - Al menos 15 usos en el último mes
    // - 10 usos al mes durante 3 meses consecutivos
    // - Uso de los tres tipos de vehículos durante 6 meses consecutivos
    // Por ahora, asumimos que todos los usuarios cumplen las condiciones.
    return true; // Placeholder
  }

  public Admin findAdminByEmail(String email) {
    return admins.stream()
        .filter(admin -> admin.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
  }

  public boolean deleteAdminByEmail(String email) {
    return admins.removeIf(admin -> admin.getEmail().equalsIgnoreCase(email));
  }

  public boolean createUser(int id, String name, String email, String password, String type) {
    return userController.addUser(id, name, email, password, type);
  }
}