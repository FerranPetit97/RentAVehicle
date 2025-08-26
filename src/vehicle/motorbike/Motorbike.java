package vehicle.motorbike;

import vehicle.Vehicle;

public class Motorbike extends Vehicle {

  public Motorbike(int id) {
    super(id, "motorbike", true, true, 100);
  }

  public void consumeBattery(int minutes) {
    super.consumeBattery(0.4, minutes); // 0.4% por minuto
  }

  @Override
  public String toString() {
    return "Motorbike" + super.toString() + "}";
  }
}