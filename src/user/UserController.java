package user;

import java.util.List;

public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public boolean addUser(int id, String name, String email, String password, String type) {
    User createUser;
    switch (type.toLowerCase()) {
      case "client":
        createUser = new user.client.Client(id, name, email, password, type);
        break;
      case "manager":
        createUser = new user.manager.Manager(id, name, email, password);
        break;
      case "mechanic":
        createUser = new user.mechanic.Mechanic(id, name, email, password);
        break;
      case "admin":
        createUser = new user.admin.Admin(id, name, email, password);
        break;
      default:
        return false;
    }
    try {
      return userService.createUser(createUser);
    } catch (SecurityException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  public User findUserById(int id) {
    return userService.findUserById(id);
  }

  public boolean deleteUserByEmail(String email) {
    return userService.deleteUserByEmail(email);
  }

  public boolean updateUserById(User user) {
    try {
      return userService.updateUserById(user);
    } catch (SecurityException e) {
      System.out.println("Error: " + e.getMessage());
      return false;
    }
  }

  public List<User> getEligibleForPremium() {
    return userService.getEligibleForPremium();
  }
}