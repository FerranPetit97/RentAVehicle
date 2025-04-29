package user.client;

import user.User;

public class Client extends User {

  public Client(int id, String name, String email, String password, String role) {
    super(id, name, email, password, role);
  }

  @Override
  public String toString() {
    return "Client{" + super.toString() + "}";
  }
}