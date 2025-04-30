package vehicle;

import java.util.List;

public class VehicleController {
  private final VehicleService vehicleService;

  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  public boolean addVehicle(Vehicle vehicle) {
    return vehicleService.addVehicle(vehicle);
  }

  public List<Vehicle> getAllVehicles() {
    return vehicleService.getAllVehicles();
  }

  public Vehicle findVehicleById(int id) {
    return vehicleService.findVehicleById(id);
  }

  public boolean updateVehicleAvailability(int id, boolean isAvailable) {
    return vehicleService.updateVehicleAvailability(id, isAvailable);
  }

  public boolean removeVehicle(int id) {
    return vehicleService.removeVehicle(id);
  }

  public boolean updateVehicleLocation(int id, int x, int y) {
    return vehicleService.updateVehicleLocation(id, x, y);
  }

  public boolean startTrip(int vehicleId, int userId, boolean isPremium) {
    return vehicleService.startTrip(vehicleId, userId, isPremium);
  }

  public void endTrip(int vehicleId, int minutes, String dropOffLocation) {
    vehicleService.endTrip(vehicleId, minutes, dropOffLocation);
  }

  public Vehicle findNearestVehicle(String vehicleType, int userX, int userY) {
    return vehicleService.findNearestVehicle(vehicleType, userX, userY);
  }

  public List<String> getVehiclesAndBatteryLevels() {
    return vehicleService.getVehiclesAndBatteryLevels();
  }
}