package vehicle;

import java.util.ArrayList;
import java.util.List;

import city.CityController;
import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import task.maintenance.MaintenanceController;
import vehicle.bicycle.Bicycle;
import vehicle.motorbike.Motorbike;
import vehicle.skate.Skate;
import utils.DistanceCalculator;

public class VehicleService {
  private final List<Vehicle> vehicles;
  private final CityController cityController;
  private final NotifyController notifyController;

  private int nextId = 0;

  // Constructor con inyección de dependencias
  public VehicleService(
      CityController cityController,
      MaintenanceController maintenanceController,
      NotifyController notifyController) {
    this.vehicles = new ArrayList<>();
    this.cityController = cityController;
    this.notifyController = notifyController;
  }

  public boolean addVehicle(String type) {
    Vehicle vehicle;
    switch (type.toLowerCase()) {
      case "bicycle":
        vehicle = new Bicycle(nextId++);
        break;
      case "motorbike":
        vehicle = new Motorbike(nextId++);
        break;
      case "skate":
        vehicle = new Skate(nextId++);
        break;
      default:
        notifyController.log(NotifyCodeEnum.BAD_REQUEST, "Invalid vehicle type: " + type);
        return false;
    }
    return vehicles.add(vehicle);
  }

  public List<Vehicle> getAllVehicles() {
    return vehicles;
  }

  public Vehicle findVehicleById(int id) {
    return vehicles.stream()
        .filter(vehicle -> vehicle.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean updateVehicleAvailability(int id, boolean isAvailable) {
    Vehicle vehicle = findVehicleById(id);
    if (vehicle != null) {
      vehicle.setAvailable(isAvailable);
      return true;
    }
    return false;
  }

  public boolean removeVehicle(int id) {
    Vehicle vehicle = findVehicleById(id);
    if (vehicle != null) {
      return vehicles.remove(vehicle);
    }
    return false;
  }

  public boolean updateVehicleLocation(int id, int x, int y) {
    Vehicle vehicle = findVehicleById(id);
    if (vehicle != null) {
      if (cityController.isLocationWithinCityLimits(x, y)) {
        vehicle.setX(x);
        vehicle.setY(y);
        return true;
      } else {
        System.err.println("Error: Vehicle location is outside city limits.");
        return false;
      }
    }
    return false;
  }

  public Vehicle findNearestVehicle(String vehicleType, int userX, int userY) {
    Vehicle nearestVehicle = null;
    double shortestDistance = Double.MAX_VALUE;

    for (Vehicle vehicle : vehicles) {
      if (vehicle.getType().equalsIgnoreCase(vehicleType) && vehicle.isAvailable() && !vehicle.isBroken()) {
        double distance = DistanceCalculator.calculateDistance(userX, userY, vehicle.getX(), vehicle.getY());
        if (distance < shortestDistance) {
          shortestDistance = distance;
          nearestVehicle = vehicle;
        }
      }
    }

    return nearestVehicle;
  }

  public int calculateBatteryAfterHours(Vehicle vehicle, int hours) {
    if (vehicle.getBatteryLevel() == 0) {
      System.err.println("El vehículo no tiene batería (por ejemplo, bicicletas).");
      return -1;
    }

    int currentBattery = vehicle.getBatteryLevel();
    int batteryAfterHours = currentBattery - (hours * 10);

    // Asegurarse de que la batería no sea menor que 0
    return Math.max(batteryAfterHours, 0);
  }

  public List<String> getVehiclesAndBatteryLevels() {
    List<String> vehicleInfo = new ArrayList<>();
    for (Vehicle vehicle : vehicles) {
      vehicleInfo.add("Vehicle ID: " + vehicle.getId() + ", Battery Level: " + vehicle.getBatteryLevel());
    }
    return vehicleInfo;
  }
}