package vehicle;

import java.util.ArrayList;
import java.util.List;

import city.CityController;
import city.CityFactory;
import task.maintenance.MaintenanceController;
import task.maintenance.MaintenanceFactory;
import vehicle.bicycle.Bicycle;
import vehicle.motorbike.Motorbike;
import vehicle.skate.Skate;

import utils.DistanceCalculator;

public class VehicleService {
  private final List<Vehicle> vehicles;
  private final CityController cityController;
  private final MaintenanceController maintenanceController;

  public VehicleService() {
    this.vehicles = new ArrayList<>();
    this.cityController = CityFactory.getInstance(null);
    this.maintenanceController = MaintenanceFactory.getInstance();
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

  public boolean startTrip(int vehicleId, boolean isPremium) {
    Vehicle vehicle = findVehicleById(vehicleId);
    if (vehicle != null && vehicle.canStartTrip(isPremium)) {
      vehicle.setAvailable(false);
      return true;
    }
    System.err.println("Cannot start trip: Battery too low.");
    return false;
  }

  public void endTrip(int vehicleId, int minutes, String dropOffLocation) {
    Vehicle vehicle = findVehicleById(vehicleId);
    if (vehicle != null) {
      // Consumir batería según el tipo de vehículo
      if (vehicle instanceof Bicycle) {
        ((Bicycle) vehicle).consumeBattery(minutes);
      } else if (vehicle instanceof Skate) {
        ((Skate) vehicle).consumeBattery(minutes);
      } else if (vehicle instanceof Motorbike) {
        ((Motorbike) vehicle).consumeBattery(minutes);
      }

      // Verificar si la batería se agotó
      if (vehicle.isBatteryDepleted()) {
        System.err.println("Battery depleted! Applying penalty.");
        // Aplicar penalización al usuario
      }

      // Si la batería está por debajo del 20%, asignar tarea de mantenimiento
      if (vehicle.getBatteryLevel() < 20) {
        maintenanceController.addTask(new task.maintenance.Maintenance(
            vehicle.getId(),
            "Recharge battery",
            vehicle,
            "current location",
            dropOffLocation));
      }

      vehicle.setAvailable(true);
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