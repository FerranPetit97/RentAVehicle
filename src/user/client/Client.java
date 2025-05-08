package user.client;

import user.User;

public class Client extends User {

  private double balance;

  public Client(int id, String name, String email, String password, double balance) {
    super(id, name, email, password, "standard");
    this.balance = balance;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public boolean hasPositiveBalance() {
    return getBalance() > 0;
  }

  public String toString() {
    return super.toString() + ", balance=" + balance + '}';
  }
}