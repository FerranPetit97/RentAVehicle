package user;

import java.util.List;

import user.client.Client;
import user.manager.Manager;
import user.mechanic.Mechanic;

public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public boolean createClient(Client user) {
    return userService.createClient(user);
  }

  public boolean createMechanic(Mechanic user) {
    return userService.createMechanic(user);
  }

  public boolean createManager(Manager user) {
    return userService.createManager(user);
  }

  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  public User findUserById(int id) {
    return userService.findUserById(id);
  }

  public boolean updateUser(int id, String name, String email, String password, String role, int vehicleId) {
    return userService.updateUser(id, name, email, password, role, vehicleId);
  }

  public boolean deleteUserById(int id) {
    return userService.deleteUserById(id);
  }

  // public boolean promoteToPremium(int userId, double discountPercentage) {
  //   return userService.promoteToPremium(userId, discountPercentage);
  // }

  // public List<User> getEligibleForPremium() {
  //   return userService.getEligibleForPremium();
  // }
}