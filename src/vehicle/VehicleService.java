package vehicle;

import java.util.ArrayList;
import java.util.List;

import city.CityController;
import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import task.maintenance.MaintenanceController;
import user.User;
import user.UserController;
import vehicle.bicycle.Bicycle;
import vehicle.motorbike.Motorbike;
import vehicle.skate.Skate;
import vehicle.usage.Usage;
import vehicle.usage.UsageController;
import utils.DistanceCalculator;

public class VehicleService {
  private final List<Vehicle> vehicles;
  private final CityController cityController;
  private final MaintenanceController maintenanceController;
  private final NotifyController notifyController;
  private final UserController userController;
  private final UsageController usageController;

  // Constructor con inyección de dependencias
  public VehicleService(
      CityController cityController,
      MaintenanceController maintenanceController,
      NotifyController notifyController,
      UserController userController,
      UsageController usageController) {
    this.vehicles = new ArrayList<>();
    this.cityController = cityController;
    this.maintenanceController = maintenanceController;
    this.notifyController = notifyController;
    this.userController = userController;
    this.usageController = usageController;
  }

  public boolean addVehicle(Vehicle vehicle) {
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

  public boolean startTrip(int vehicleId, int userId, boolean isPremium) {
    Vehicle vehicle = findVehicleById(vehicleId);
    User user = userController.findUserById(userId);
    if (user == null) {
      this.notifyController.log(NotifyCodeEnum.NOT_FOUND, "User not found");
      return false;
    }
    if (vehicle != null && vehicle.canStartTrip(isPremium)) {
      vehicle.setUser(user);
      vehicle.setAvailable(false);

      usageController.registerUsage(vehicle, user);

      return true;
    }

    System.err.println("Cannot start trip: Battery too low.");
    return false;
  }

  public void endTrip(int vehicleId, int minutes, String dropOffLocation) {
    Vehicle vehicle = findVehicleById(vehicleId);
    if (vehicle != null) {
      if (vehicle instanceof Bicycle) {
        ((Bicycle) vehicle).consumeBattery(minutes);
      } else if (vehicle instanceof Skate) {
        ((Skate) vehicle).consumeBattery(minutes);
      } else if (vehicle instanceof Motorbike) {
        ((Motorbike) vehicle).consumeBattery(minutes);
      }

      if (vehicle.isBatteryDepleted()) {
        System.err.println("Battery depleted! Applying penalty.");
      }

      if (vehicle.getBatteryLevel() < 20) {
        maintenanceController.addTask(new task.maintenance.Maintenance(
            vehicle.getId(),
            "Recharge battery",
            vehicle,
            "current location",
            dropOffLocation));
      }

      vehicle.setAvailable(true);

      for (Usage usage : usageController.getUsagesByVehicle(vehicle)) {
        if (usage.getEndTime() == null) {
          usageController.endUsage(usage.getId());
          break;
        }
      }
    }
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

  public List<String> getVehiclesAndBatteryLevels() {
    List<String> vehicleInfo = new ArrayList<>();
    for (Vehicle vehicle : vehicles) {
      vehicleInfo.add("Vehicle ID: " + vehicle.getId() + ", Battery Level: " + vehicle.getBatteryLevel());
    }
    return vehicleInfo;
  }
}