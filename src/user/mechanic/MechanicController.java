package user.mechanic;


public class MechanicController {
  private final MechanicService mechanicService;

  public MechanicController(MechanicService mechanicService) {
    this.mechanicService = mechanicService;
  }

  public boolean setVehicleToWork(int mechanicId, int vehicleId) {

    return mechanicService.setVehicleToWorker(mechanicId, vehicleId);
  }
}