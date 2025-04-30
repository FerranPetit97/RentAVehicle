package user.mechanic;

import user.User;

public class Mechanic extends User {

  private int baseId;
  private int vehicleId;

  public Mechanic(int id, String name, String email, String password, int baseId, int vehicleId) {
    super(id, name, email, password, "mechanic");
    this.baseId = baseId;
    this.vehicleId = vehicleId;
  }

  public int getBaseId() {
    return baseId;
  }

  public void setBaseId(int baseId) {
    this.baseId = baseId;
  }

  public int getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  @Override
  public String toString() {
    return "Mechanic{" + super.toString() + "}";
  }
}