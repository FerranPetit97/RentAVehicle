package vehicle.bicycle;

import java.util.List;

public class BicycleController {
  private final BicycleService bicycleService;

  public BicycleController() {
    this.bicycleService = new BicycleService();
  }

  public boolean addBicycle(int id, boolean isAvailable, boolean hasNoDamage) {
    Bicycle bicycle = new Bicycle(id, isAvailable, hasNoDamage);
    return bicycleService.addBicycle(bicycle);
  }

  public List<Bicycle> getAllBicycles() {
    return bicycleService.getAllBicycles();
  }

  public Bicycle findBicycleById(int id) {
    return bicycleService.findBicycleById(id);
  }

  public boolean removeBicycle(int id) {
    return bicycleService.removeBicycle(id);
  }
}