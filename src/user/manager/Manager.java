package user.manager;

import user.User;

public class Manager extends User {

  private int vehicleId;

  public Manager(int id, String name, String email, String password) {
    super(id, name, email, password, "manager");
  }

  public int getVehicleId() {
    return vehicleId;
  }

  public void setVehicleId(int vehicleId) {
    this.vehicleId = vehicleId;
  }

  @Override
  public String toString() {
      return super.toString(); // Llama al método toString() de User
  }
}