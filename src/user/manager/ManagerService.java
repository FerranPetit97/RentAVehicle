package user.manager;

import java.util.ArrayList;
import java.util.List;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import vehicle.Vehicle;
import vehicle.VehicleController;

public class ManagerService {
  private final List<Manager> managers = new ArrayList<>();
  private final VehicleController vehicleController;
  private final NotifyController notifyController;

  // Constructor con inyección de dependencias
  public ManagerService(VehicleController vehicleController, NotifyController notifyController) {
    this.vehicleController = vehicleController;
    this.notifyController = notifyController;
  }

  public boolean addManager(Manager manager) {
    return managers.add(manager);
  }

  public List<Manager> getAllManagers() {
    return managers;
  }

  public Manager findManagerByEmail(String email) {
    return managers.stream()
        .filter(manager -> manager.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
  }

  public boolean deleteManagerByEmail(String email) {
    return managers.removeIf(manager -> manager.getEmail().equalsIgnoreCase(email));
  }

  public boolean setVehicleToWork(Manager manager, int vehicleId) {
    Vehicle vehicle = this.vehicleController.findVehicleById(vehicleId);
    if (vehicle == null) {
      this.notifyController.log(NotifyCodeEnum.NOT_FOUND, "Vehicle with ID " + vehicleId + " not found.");
      return false;
    }
    for (Manager m : managers) {
      if (m.getId() == manager.getId()) {
        m.setVehicleId(vehicle.getId());
        return true;
      }
    }
    return false;
  }
}