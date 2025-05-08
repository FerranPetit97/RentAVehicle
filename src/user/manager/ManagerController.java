package user.manager;

import java.util.List;

public class ManagerController {
  private final ManagerService managerService;

  public ManagerController(ManagerService managerService) {
    this.managerService = managerService;
  }

  public boolean setVehicleToWork(Manager manager, int vehicleId) {
    return managerService.setVehicleToWork(manager, vehicleId);
  }

  public List<Manager> getAllManagers() {
    return managerService.getAllManagers();
  }

  public Manager findManagerByEmail(String email) {
    return managerService.findManagerByEmail(email);
  }

  public boolean deleteManagerByEmail(String email) {
    return managerService.deleteManagerByEmail(email);
  }
}