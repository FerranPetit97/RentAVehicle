package task.maintenance;

import task.Task;
import vehicle.Vehicle;

public class Maintenance extends Task {
  private Vehicle vehicle;
  private String pickupLocation;
  private String dropOffLocation;

  public Maintenance(int id, String description, Vehicle vehicle, String pickupLocation, String dropOffLocation) {
    super(id, description);
    this.vehicle = vehicle;
    this.pickupLocation = pickupLocation;
    this.dropOffLocation = dropOffLocation;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public String getPickupLocation() {
    return pickupLocation;
  }

  public String getDropOffLocation() {
    return dropOffLocation;
  }

  @Override
  public String toString() {
    return "Maintenance{" + super.toString() + ", vehicle=" + vehicle + ", pickupLocation='" + pickupLocation + "', dropOffLocation='" + dropOffLocation + "'}";
  }
}