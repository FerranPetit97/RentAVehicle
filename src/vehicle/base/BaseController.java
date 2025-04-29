package vehicle.base;

import vehicle.Vehicle;

import java.util.List;

public class BaseController {
  private final BaseService baseService;

  public BaseController() {
    this.baseService = new BaseService();
  }

  public boolean addBase(int id, String location, int capacity, int x, int y) {
    Base base = new Base(id, location, capacity, x , y);
    return baseService.addBase(base);
  }

  public List<Base> getAllBases() {
    return baseService.getAllBases();
  }

  public Base findBaseById(int id) {
    return baseService.findBaseById(id);
  }

  public boolean addVehicleToBase(int baseId, Vehicle vehicle) {
    return baseService.addVehicleToBase(baseId, vehicle);
  }

  public boolean removeVehicleFromBase(int baseId, Vehicle vehicle) {
    return baseService.removeVehicleFromBase(baseId, vehicle);
  }

  public Base findNearestBaseWithVehicles(int userX, int userY) {
    return baseService.findNearestBaseWithVehicles(userX, userY);
  }
}