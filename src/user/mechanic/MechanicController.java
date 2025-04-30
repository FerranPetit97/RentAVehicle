package user.mechanic;

import java.util.List;

public class MechanicController {
  private final MechanicService mechanicService;

  public MechanicController(MechanicService mechanicService) {
    this.mechanicService = mechanicService;
  }

  public boolean createMechanic(Mechanic user) {
    return mechanicService.createMechanic(user);
  }

  public boolean setVehicleToWork(Mechanic mechanic, int vehicleId) {
    return mechanicService.setVehicleToWork(mechanic, vehicleId);
  }

  public List<Mechanic> getAllMechanics() {
    return mechanicService.getAllMechanics();
  }

  public Mechanic findMechanicById(int id) {
    return mechanicService.findMechanicById(id);
  }

  public boolean deleteMechanicById(int id) {
    return mechanicService.deleteMechanicById(id);
  }

  public boolean updateMechanicById(Mechanic mechanic) {
    return mechanicService.updateMechanicById(mechanic);
  }
}