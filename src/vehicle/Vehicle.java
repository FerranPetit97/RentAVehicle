package vehicle;

import user.User;

public abstract class Vehicle {
  private int id;
  private String type;
  private boolean isAvailable;
  private boolean hasNoDamage;
  private int batteryLevel;
  private int x;
  private int y;
  private boolean isBroken;
  private User user;

  public Vehicle(int id, String type, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    this.id = id;
    this.type = type;
    this.isAvailable = isAvailable;
    this.hasNoDamage = hasNoDamage;
    this.batteryLevel = batteryLevel;
    this.x = 0;
    this.y = 0;
    this.isBroken = false;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public boolean isBroken() {
    return isBroken;
  }

  public void setBroken(boolean broken) {
    isBroken = broken;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public boolean hasNoDamage() {
    return hasNoDamage;
  }

  public void setHasNoDamage(boolean hasNoDamage) {
    this.hasNoDamage = hasNoDamage;
  }

  public int getBatteryLevel() {
    return batteryLevel;
  }

  public void setBatteryLevel(int batteryLevel) {
    this.batteryLevel = batteryLevel;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public boolean canStartTrip(boolean isPremium) {
    if (isPremium) {
      return batteryLevel > 10;
    }
    return batteryLevel >= 20;
  }

  public void consumeBattery(double percentagePerMinute, int minutes) {
    int consumption = (int) Math.ceil(percentagePerMinute * minutes);
    batteryLevel = Math.max(0, batteryLevel - consumption);
  }

  public boolean isBatteryDepleted() {
    return batteryLevel <= 0;
  }

  @Override
  public String toString() {
    return "Vehicle{id=" + id + ", type='" + type + "', isAvailable=" + isAvailable +
        ", hasNoDamage=" + hasNoDamage + ", batteryLevel=" + batteryLevel + "}";
  }
}