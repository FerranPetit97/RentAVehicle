package user.client.premium;

import user.client.Client;

public class Premium extends Client {
  private double discountPercentage;
  private boolean canReserveVehicles;

  public Premium(int id, String name, String email, String password, double balance, double discountPercentage) {
    super(id, name, email, password, balance);
    this.discountPercentage = discountPercentage;
    this.canReserveVehicles = true;
  }

  public double getDiscountPercentage() {
    return discountPercentage;
  }

  public void setDiscountPercentage(double discountPercentage) {
    this.discountPercentage = discountPercentage;
  }

  public boolean canReserveVehicles() {
    return canReserveVehicles;
  }

  public void setCanReserveVehicles(boolean canReserveVehicles) {
    this.canReserveVehicles = canReserveVehicles;
  }

}