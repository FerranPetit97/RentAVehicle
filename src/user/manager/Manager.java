package user.manager;

import user.User;

public class Manager extends User {

  public Manager(int id, String name, String email, String password) {
    super(id, name, email, password, "manager");
  }

  @Override
  public String toString() {
    return "Manager{" + super.toString() + "}";
  }
}