package task.repair;

import task.TaskController;
import vehicle.Vehicle;
import vehicle.VehicleController;

public class RepairService {
  private final TaskController taskController;
  private final VehicleController vehicleController;

  public RepairService(TaskController taskController, VehicleController vehicleController) {
    this.taskController = taskController;
    this.vehicleController = vehicleController;
  }

  public boolean addRepairTask(String description, int vehicleId, String pickupLocation,
      String dropOffLocation) {
    Vehicle vehicle = vehicleController.findVehicleById(vehicleId);
    Repair repair = new Repair(0, description, vehicle, pickupLocation, dropOffLocation);
    return taskController.createRepairTask(repair);
  }

  public boolean completeRepairTask(int taskId, double repairCost) {
    taskController.findTaskById(taskId).setCost(repairCost);
    return true;
  }
}