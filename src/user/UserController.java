package user;

import java.util.List;

public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public boolean createUser(int id, String name, String email, String password, String role) {
    return userService.createUser(id, name, email, password, role);
  }

  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  public User findUserById(int id) {
    return userService.findUserById(id);
  }

  public boolean deleteUserById(int id) {
    return userService.deleteUserById(id);
  }

  public boolean updateUserById(User user) {
    try {
      return userService.updateUser(user);
    } catch (SecurityException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  public boolean promoteToPremium(int userId, double discountPercentage) {
    return userService.promoteToPremium(userId, discountPercentage);
  }

  public List<User> getEligibleForPremium() {
    return userService.getEligibleForPremium();
  }
}