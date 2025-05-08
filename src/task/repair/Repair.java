package task.repair;

import task.Task;
import vehicle.Vehicle;
import vehicle.base.Base;

public class Repair extends Task {
  private Vehicle vehicle;
  private Base base;

  public Repair(int id, String description, Vehicle vehicle, Base base) {
    super(id, description);
    this.vehicle = vehicle;
    this.base = base;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public Base getBase() {
    return base;
  }
}