package user.admin;

import java.util.ArrayList;

import user.User;

public class AdminController {
  private AdminService adminService;

  public AdminController() {
    this.adminService = new AdminService();
  }

  public String registerUser(String name, String email, String password, String role) {
    return adminService.addUser(name, email, password, role);
  }

  public ArrayList<User> getAllUsers() {
    return adminService.getAllUsers();
  }

}
