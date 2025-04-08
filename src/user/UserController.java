package user;

import java.util.ArrayList;

public class UserController {
  private UserService userService;

  public UserController() {
    this.userService = new UserService();
  }

  public String register(String name, String email, String password, String role) {

    User registeredUser = userService.addUser(name, email, password, role);

    return registeredUser != null ? "User registered successfully" : "Registration failed";
  }

  public ArrayList<User> getAllUsers() {
    return userService.getAllUsers();
  }
}