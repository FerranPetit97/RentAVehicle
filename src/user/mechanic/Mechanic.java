package user.mechanic;

import user.User;

public class Mechanic extends User {

  private int baseId;
  private int vehicleId;

  public Mechanic(int id, String name, String email, String password) {
    super(id, name, email, password, "mechanic");
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

  public String toString() {
    return super.toString() + '}';
  }
}