package tariff;

public class Tariff {
  private String vehicleType; // "bicycle", "skate", "motorbike"
  private double baseRate; // Base rate per minute or per kilometer
  private double premiumDiscount; // Discount percentage for premium users

  public Tariff(String vehicleType, double baseRate, double premiumDiscount) {
    this.vehicleType = vehicleType;
    this.baseRate = baseRate;
    this.premiumDiscount = premiumDiscount;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public double getBaseRate() {
    return baseRate;
  }

  public void setBaseRate(double baseRate) {
    this.baseRate = baseRate;
  }

  public double getPremiumDiscount() {
    return premiumDiscount;
  }

  public void setPremiumDiscount(double premiumDiscount) {
    this.premiumDiscount = premiumDiscount;
  }

  public double calculateRate(boolean isPremium) {
    if (isPremium) {
      return baseRate * (1 - premiumDiscount / 100);
    }
    return baseRate;
  }

  @Override
  public String toString() {
    return "Tariff{vehicleType='" + vehicleType + "', baseRate=" + baseRate +
           ", premiumDiscount=" + premiumDiscount + "}";
  }
}