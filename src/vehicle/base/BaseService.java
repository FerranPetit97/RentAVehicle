package vehicle.base;

import vehicle.Vehicle;
import vehicle.VehicleController;
import vehicle.rental.Rent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.DistanceCalculator;

public class BaseService {
  private final List<Base> bases;
  private final VehicleController vehicleController;

  public BaseService(VehicleController vehicleController) {
    this.bases = new ArrayList<>();
    this.vehicleController = vehicleController;
  }

  public boolean addBase(Base base) {
    return bases.add(base);
  }

  public List<Base> getAllBases() {
    return bases;
  }

  public Base findBaseById(int id) {
    return bases.stream()
        .filter(base -> base.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean addVehicleToBase(int baseId, int vehicleId) {
    Base base = findBaseById(baseId);
    Vehicle vehicle = vehicleController.findVehicleById(vehicleId);
    if (base != null) {
      return base.addVehicle(vehicle);
    }
    return false;
  }

  public boolean removeVehicleFromBase(int baseId, Vehicle vehicle) {
    Base base = findBaseById(baseId);
    if (base != null) {
      return base.removeVehicle(vehicle);
    }
    return false;
  }

  public Base findVehicleBase(int vehicleId) {
    for (Base base : bases) {
      if (base.getVehicles().contains(vehicleController.findVehicleById(vehicleId))) {
        return base;
      }
    }
    return null;
  }

  public Base findNearestBaseWithVehicles(int userX, int userY) {
    Base nearestBase = null;
    double shortestDistance = Double.MAX_VALUE;

    for (Base base : bases) {
      if (!base.getVehicles().isEmpty()) {
        double distance = DistanceCalculator.calculateDistance(userX, userY, base.getX(), base.getY());
        if (distance < shortestDistance) {
          shortestDistance = distance;
          nearestBase = base;
        }
      }
    }
    return nearestBase;
  }

  public Map<String, Base> getDemandStatistics(List<Rent> usageRecords) {
    Map<Integer, Integer> baseUsageCount = new HashMap<>();

    for (Rent usage : usageRecords) {
      Vehicle vehicle = usage.getVehicle();
      Base base = findBaseById(vehicle.getX());
      if (base != null) {
        baseUsageCount.put(base.getId(), baseUsageCount.getOrDefault(base.getId(), 0) + 1);
      }
    }

    Base mostDemandedBase = bases.stream()
        .max(Comparator.comparingInt(base -> baseUsageCount.getOrDefault(base.getId(), 0)))
        .orElse(null);

    Base leastDemandedBase = bases.stream()
        .min(Comparator.comparingInt(base -> baseUsageCount.getOrDefault(base.getId(), 0)))
        .orElse(null);

    // Retornar las estadísticas
    Map<String, Base> statistics = new HashMap<>();
    statistics.put("mostDemanded", mostDemandedBase);
    statistics.put("leastDemanded", leastDemandedBase);
    return statistics;
  }
}