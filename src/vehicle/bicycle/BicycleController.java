package vehicle.bicycle;

public class BicycleController {
  private final BicycleService bicycleService;

  public BicycleController() {
    this.bicycleService = new BicycleService();
  }

  public Bicycle findBicycleById(int id) {
    return bicycleService.findBicycleById(id);
  }

  public boolean removeBicycle(int id) {
    return bicycleService.removeBicycle(id);
  }
}