package vehicle;

import java.util.List;

public class VehicleController {
  private final VehicleService vehicleService;

  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  public boolean addVehicle(int id, String type, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    return vehicleService.addVehicle(id, type, isAvailable, hasNoDamage, batteryLevel);
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

  public Vehicle findNearestVehicle(String vehicleType, int userX, int userY) {
    return vehicleService.findNearestVehicle(vehicleType, userX, userY);
  }

  public List<String> getVehiclesAndBatteryLevels() {
    return vehicleService.getVehiclesAndBatteryLevels();
  }
}