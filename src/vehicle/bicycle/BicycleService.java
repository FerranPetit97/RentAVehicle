package vehicle.bicycle;

import java.util.ArrayList;
import java.util.List;

public class BicycleService {
  private final List<Bicycle> bicycles;

  public BicycleService() {
    this.bicycles = new ArrayList<>();
  }

  public boolean addBicycle(Bicycle bicycle) {
    return bicycles.add(bicycle);
  }

  public List<Bicycle> getAllBicycles() {
    return bicycles;
  }

  public Bicycle findBicycleById(int id) {
    return bicycles.stream()
        .filter(bicycle -> bicycle.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean removeBicycle(int id) {
    Bicycle bicycle = findBicycleById(id);
    if (bicycle != null) {
      return bicycles.remove(bicycle);
    }
    return false; // Bicycle not found
  }
}