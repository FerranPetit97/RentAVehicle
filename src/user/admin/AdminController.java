package user.admin;

import java.util.List;

import user.User;
import user.UserController;
import user.client.premium.Premium;

import vehicle.VehicleController;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;

public class AdminController {
  private final AdminService adminService;
  private final NotifyController notifyController;
  private final VehicleController vehicleController;
  private final UserController userController;

  public AdminController(UserController userController, AdminService adminService, VehicleController vehicleController,
      NotifyController notifyController) {
    this.adminService = adminService;
    this.vehicleController = vehicleController;
    this.userController = userController;
    this.notifyController = notifyController;
  }

  public boolean registerUser(int id, String name, String email, String password, String type) {
    try {
      return adminService.createUser(id, name, email, password, type);
    } catch (SecurityException e) {
      this.notifyController.log(NotifyCodeEnum.UNAUTHORIZED, "Permission denied: " + e.getMessage());
      return false;
    }
  }

  public List<User> getAllUsers() {
    return userController.getAllUsers();
  }

  public List<User> getEligibleForPremium() {
    return userController.getEligibleForPremium();
  }

  public boolean promoteToPremium(int userId, double discountPercentage) {
    User user = userController.getAllUsers().stream()
        .filter(u -> u.getId() == userId)
        .findFirst()
        .orElse(null);
    if (user != null) {
      Premium premiumUser = new Premium(user.getId(), user.getName(), user.getEmail(),
          user.getPassword(), discountPercentage);
      userController.deleteUserByEmail(user.getEmail());
      return userController.addUser(premiumUser.getId(), premiumUser.getName(), premiumUser.getEmail(), premiumUser.getPassword(), premiumUser.getRole());
    }
    return false;
  }

  public List<String> getBatteriesLevel() {
    return vehicleController.getVehiclesAndBatteryLevels();
  }
}