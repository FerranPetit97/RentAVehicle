package task.maintenance;

import task.Task;
import vehicle.base.Base;

public class Maintenance extends Task {
  private Base base;

  public Maintenance(int id, String description, Base base) {
    super(id, description);
    this.base = base;
  }

  public Base getBase() {
    return base;
  }

  @Override
  public String toString() {
    return "Maintenance{" + super.toString() + ", base=" + base + "'}";
  }
}