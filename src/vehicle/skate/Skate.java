package vehicle.skate;

import vehicle.Vehicle;

public class Skate extends Vehicle {

  public Skate(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    super(id, "skate", isAvailable, hasNoDamage, batteryLevel);
  }
  
  public void consumeBattery(int minutes) {
    super.consumeBattery(0.5, minutes);
  }

  @Override
  public String toString() {
    return "Skate{" + super.toString() + "}";
  }
}