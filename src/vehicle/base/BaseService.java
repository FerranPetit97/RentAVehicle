package vehicle.base;

import vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

import utils.DistanceCalculator;

public class BaseService {
  private final List<Base> bases;

  public BaseService() {
    this.bases = new ArrayList<>();
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

  public boolean addVehicleToBase(int baseId, Vehicle vehicle) {
    Base base = findBaseById(baseId);
    if (base != null) {
      return base.addVehicle(vehicle);
    }
    return false; // Base not found or full
  }

  public boolean removeVehicleFromBase(int baseId, Vehicle vehicle) {
    Base base = findBaseById(baseId);
    if (base != null) {
      return base.removeVehicle(vehicle);
    }
    return false; // Base not found
  }

  public Base findNearestBaseWithVehicles(int userX, int userY) {
    Base nearestBase = null;
    double shortestDistance = Double.MAX_VALUE;

    for (Base base : bases) {
      if (!base.isBroken() && !base.getVehicles().isEmpty()) {
        double distance = DistanceCalculator.calculateDistance(userX, userY, base.getX(), base.getY());
        if (distance < shortestDistance) {
          shortestDistance = distance;
          nearestBase = base;
        }
      }
    }

    return nearestBase;
  }
}