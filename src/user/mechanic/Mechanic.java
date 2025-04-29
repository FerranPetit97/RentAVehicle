package user.mechanic;

import user.User;

public class Mechanic extends User {

  public Mechanic(int id, String name, String email, String password) {
    super(id, name, email, password, "mechanic");
  }

  @Override
  public String toString() {
    return "Mechanic{" + super.toString() + "}";
  }
}