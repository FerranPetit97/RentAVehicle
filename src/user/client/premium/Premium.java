package user.client.premium;

import user.client.Client;

public class Premium extends Client {
  private double discountPercentage; // Discount for premium users
  private boolean canReserveVehicles; // Premium reservation feature

  public Premium(int id, String name, String email, String password, double discountPercentage) {
    super(id, name, email, password, "premium");
    this.discountPercentage = discountPercentage;
    this.canReserveVehicles = true; // Premium users can reserve vehicles
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

  @Override
  public String toString() {
    return "Premium{" + super.toString() + ", discountPercentage=" + discountPercentage +
           ", canReserveVehicles=" + canReserveVehicles + "}";
  }
}