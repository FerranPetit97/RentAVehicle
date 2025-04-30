package vehicle.base;

import vehicle.Vehicle;
import vehicle.usage.Usage;

import java.util.List;
import java.util.Map;

import notify.NotifyController;
import user.mechanic.Mechanic;

public class BaseController {
  private final BaseService baseService;
  private final NotifyController notifyController;

  public BaseController(BaseService baseService, NotifyController notifyController) {
    this.baseService = baseService;
    this.notifyController = notifyController;
  }

  public boolean addBase(int id, String location, int capacity, int x, int y, int mechanicId) {
    Mechanic mechanic = null; // Aquí puedes integrar la lógica para obtener el mecánico
    if (mechanic == null) {
      this.notifyController.log(notify.enums.NotifyCodeEnum.NOT_FOUND, "Mechanic with ID " + mechanicId + " not found.");
      return false;
    }
    Base base = new Base(id, location, capacity, x, y, mechanicId);
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

  public Map<String, Base> getDemandStatistics(List<Usage> usageRecords) {
    return baseService.getDemandStatistics(usageRecords);
  }
}