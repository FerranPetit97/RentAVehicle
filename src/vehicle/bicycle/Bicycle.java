package vehicle.bicycle;

import vehicle.Vehicle;

public class Bicycle extends Vehicle {

  public Bicycle(int id, boolean isAvailable, boolean hasNoDamage) {
    super(id, "bicycle", isAvailable, hasNoDamage, 100);
  }

  public void consumeBattery(int minutes) {
    super.consumeBattery(1.0, minutes);
  }

  @Override
  public String toString() {
    return "Bicycle{" + super.toString() + "}";
  }
}