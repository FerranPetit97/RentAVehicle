package vehicle.motorbike;

import vehicle.Vehicle;

public class Motorbike extends Vehicle {

  public Motorbike(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    super(id, "motorbike", isAvailable, hasNoDamage, batteryLevel);
  }

  public void consumeBattery(int minutes) {
    super.consumeBattery(0.4, minutes); // 0.4% por minuto
  }

  @Override
  public String toString() {
    return "Motorbike{" + super.toString() + "}";
  }
}