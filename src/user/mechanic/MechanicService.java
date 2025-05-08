package user.mechanic;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import user.UserController;
import vehicle.Vehicle;
import vehicle.VehicleController;

public class MechanicService {
  private final VehicleController vehicleController;
  private final UserController userController;
  private final NotifyController notifyController;

  public MechanicService(VehicleController vehicleController, NotifyController notifyController,
      UserController userController) {
    this.vehicleController = vehicleController;
    this.notifyController = notifyController;
    this.userController = userController;
  }

  public Mechanic findMechanicById(int id) {
    Mechanic mechanic = (Mechanic) userController.findUserById(id);
    if (mechanic == null) {
      return null;
    }
    return mechanic;
  }

  public boolean updateMechanicById(Mechanic mechanic) {
    if (mechanic == null) {
      this.notifyController.log(NotifyCodeEnum.BAD_REQUEST, "Mechanic cannot be null.");
      return false;
    }

    this.userController.updateUser(mechanic.getId(), mechanic.getName(),
        mechanic.getEmail(), mechanic.getPassword(), mechanic.getRole(),
        mechanic.getVehicleId());
    return false;
  }

  public boolean setVehicleToWorker(int mechanicId, int vehicleId) {
    Vehicle vehicle = this.vehicleController.findVehicleById(vehicleId);
    Mechanic mechanic = findMechanicById(mechanicId);
    if (vehicle == null) {
      this.notifyController.log(NotifyCodeEnum.NOT_FOUND, "Vehicle with ID " + vehicleId + " not found.");
      return false;
    }
    this.userController.updateUser(mechanic.getId(), mechanic.getName(),
        mechanic.getEmail(), mechanic.getPassword(), mechanic.getRole(),
        vehicleId);
    return true;
  }
}