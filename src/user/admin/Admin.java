package user.admin;

import user.User;

public class Admin extends User {

  public Admin(int id, String name, String email, String password) {
    super(id, name, email, password, "admin");
  }

  public String toString() {
    return super.toString() + '}';
  }
}