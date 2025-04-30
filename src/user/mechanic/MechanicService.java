package user.mechanic;

import java.util.ArrayList;
import java.util.List;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import vehicle.Vehicle;
import vehicle.VehicleController;

public class MechanicService {
  private final List<Mechanic> mechanics = new ArrayList<>();
  private final VehicleController vehicleController;
  private final NotifyController notifyController;

  // Constructor con inyección de dependencias
  public MechanicService(VehicleController vehicleController, NotifyController notifyController) {
    this.vehicleController = vehicleController;
    this.notifyController = notifyController;
  }

  public boolean createMechanic(Mechanic mechanic) {
    return mechanics.add(mechanic);
  }

  public List<Mechanic> getAllMechanics() {
    return mechanics;
  }

  public Mechanic findMechanicById(int id) {
    return mechanics.stream()
        .filter(mechanic -> mechanic.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean deleteMechanicById(int id) {
    return mechanics.removeIf(mechanic -> mechanic.getId() == id);
  }

  public boolean updateMechanicById(Mechanic updatedMechanic) {
    for (int i = 0; i < mechanics.size(); i++) {
      if (mechanics.get(i).getId() == updatedMechanic.getId()) {
        mechanics.set(i, updatedMechanic);
        return true;
      }
    }
    return false;
  }

  public boolean setVehicleToWork(Mechanic mechanic, int vehicleId) {
    Vehicle vehicle = this.vehicleController.findVehicleById(vehicleId);
    if (vehicle == null) {
      this.notifyController.log(NotifyCodeEnum.NOT_FOUND, "Vehicle with ID " + vehicleId + " not found.");
      return false;
    }
    for (Mechanic m : mechanics) {
      if (m.getId() == mechanic.getId()) {
        m.setVehicleId(vehicle.getId());
        return true;
      }
    }
    return false;
  }
}