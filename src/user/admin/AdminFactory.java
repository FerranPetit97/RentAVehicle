package user.admin;

import vehicle.VehicleController;
import vehicle.VehicleFactory;

import notify.NotifyFactory;
import notify.NotifyController;

import user.User;
import user.UserController;
import user.UserFactory;

public class AdminFactory {
  private static AdminController instance;

  public static synchronized AdminController getInstance(User authenticatedUser) {
    if (instance == null) {
      synchronized (AdminFactory.class) {
        if (instance == null) {
          AdminService adminService = new AdminService(authenticatedUser);
          NotifyController notifyController = NotifyFactory.getInstance();
          VehicleController vehicleController = VehicleFactory.getInstance();
          UserController userController = UserFactory.getInstance(authenticatedUser);

          instance = new AdminController(userController, adminService, vehicleController, notifyController);
        }
      }
    }
    return instance;
  }
}