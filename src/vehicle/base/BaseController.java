package vehicle.base;

import vehicle.Vehicle;
import vehicle.rental.Rent;

import java.util.List;
import java.util.Map;

public class BaseController {
  private final BaseService baseService;

  public BaseController(BaseService baseService) {
    this.baseService = baseService;
  }

  public boolean addBase(int id, String location, int capacity, int x, int y) {
    Base base = new Base(id, location, capacity, x, y);
    return baseService.addBase(base);
  }

  public List<Base> getAllBases() {
    return baseService.getAllBases();
  }

  public Base findBaseById(int id) {
    return baseService.findBaseById(id);
  }

  public Base findVehicleBase(int vehicleId) {
    return baseService.findVehicleBase(vehicleId);
  }

  public boolean addVehicleToBase(int baseId, int vehicleId) {
    return baseService.addVehicleToBase(baseId, vehicleId);
  }

  public boolean removeVehicleFromBase(int baseId, Vehicle vehicle) {
    return baseService.removeVehicleFromBase(baseId, vehicle);
  }

  public Base findNearestBaseWithVehicles(int userX, int userY) {
    return baseService.findNearestBaseWithVehicles(userX, userY);
  }

  public Map<String, Base> getDemandStatistics(List<Rent> usageRecords) {
    return baseService.getDemandStatistics(usageRecords);
  }
}