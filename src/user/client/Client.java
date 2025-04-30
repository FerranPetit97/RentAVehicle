package user.client;

import user.User;

public class Client extends User {

  public Client(int id, String name, String email, String password) {
    super(id, name, email, password, "client");
  }

  @Override
  public String toString() {
      return super.toString();
  }
}