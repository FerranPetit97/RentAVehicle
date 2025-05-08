package task.repair;

import java.util.ArrayList;
import java.util.List;

public class RepairService {
  private final List<Repair> repairTasks;

  public RepairService() {
    this.repairTasks = new ArrayList<>();
  }

  public boolean addRepairTask(Repair task) {
    return repairTasks.add(task);
  }

  public List<Repair> getAllRepairTasks() {
    return repairTasks;
  }

  public boolean completeRepairTask(int taskId, double repairCost) {
    for (Repair task : repairTasks) {
      if (task.getId() == taskId) {
        task.setCost(repairCost);
        task.completeTask();
        return true;
      }
    }
    return false;
  }
}