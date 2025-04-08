package user.admin;

import user.User;

public class Admin extends User {

  public Admin(int id, String name, String email, String password, String role) {
    super(id, name, email, password, role);
  }

  @Override
  public String toString() {
    return "Administrator{" + super.toString() + "}";
  }

}
