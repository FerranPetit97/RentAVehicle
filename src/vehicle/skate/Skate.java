package vehicle.skate;

import vehicle.Vehicle;

public class Skate extends Vehicle {

  public Skate(int id) {
    super(id, "skate", true, true, 100);
  }
  
  public void consumeBattery(int minutes) {
    super.consumeBattery(0.5, minutes);
  }

  @Override
  public String toString() {
    return "Skate" + super.toString() + "}";
  }
}