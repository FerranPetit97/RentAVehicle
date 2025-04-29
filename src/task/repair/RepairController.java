package task.repair;

import java.util.List;

import vehicle.Vehicle;
import vehicle.base.Base;

public class RepairController {
  private final RepairService repairService;

  public RepairController(RepairService repairService) {
    this.repairService = new RepairService();
  }

  public boolean createRepairTask(int id, String description, Vehicle vehicle, Base base) {
    Repair repairTask = new Repair(id, description, vehicle, base);
    return repairService.addRepairTask(repairTask);
  }

  public List<Repair> getAllRepairTasks() {
    return repairService.getAllRepairTasks();
  }

  public boolean completeRepairTask(int taskId, double repairCost) {
    Repair task = repairService.getAllRepairTasks().stream()
        .filter(t -> t.getId() == taskId)
        .findFirst()
        .orElse(null);
    if (task != null) {
      task.setCost(repairCost);
      task.completeTask();
      return true;
    }
    return false;
  }
}