package user.admin;

import java.util.ArrayList;

import user.User;
import user.UserController;

public class AdminService {

  private UserController userController = new UserController();

  public String addUser(String name, String email, String password, String role) {

    if (name == null || email == null || password == null || name.isEmpty() || email.isEmpty()
        || password.isEmpty()) {
      // TODO: hacer gestor de errores para este tipos de casos.
      return null;
    }

    return userController.register(name, email, password, role);
  }

  public ArrayList<User> getAllUsers() {
    return userController.getAllUsers();
  }

}
