package vehicle;

import java.util.List;

public class VehicleController {
  private final VehicleService vehicleService;

  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  public boolean addVehicle(String type) {
    return vehicleService.addVehicle(type);
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

  public int calculateBatteryAfterHours(Vehicle vehicle, int hours) {
    return vehicleService.calculateBatteryAfterHours(vehicle, hours);
  }

  public List<String> getVehiclesAndBatteryLevels() {
    return vehicleService.getVehiclesAndBatteryLevels();
  }
}