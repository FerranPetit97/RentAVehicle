package user.client;

import user.User;

public class Client extends User {

  private double balance;
  private boolean isPremium;

  public Client(int id, String name, String email, String password, double balance) {
    super(id, name, email, password, "client");
    this.balance = balance;
    this.isPremium = false;
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

  public boolean isPremium() {
    return isPremium;
  }

  public void setPremium(boolean isPremium) {
    this.isPremium = isPremium;
  }

  public String toString() {
    return "Client" + super.toString() + ", balance=" + balance + '}';
  }
}