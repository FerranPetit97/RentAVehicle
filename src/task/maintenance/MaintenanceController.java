package task.maintenance;

import java.util.List;

public class MaintenanceController {
  private final MaintenanceService maintenanceService;

  public MaintenanceController(MaintenanceService maintenanceService) {
    this.maintenanceService = maintenanceService;
  }

  public boolean addTask(Maintenance task) {
    return maintenanceService.addTask(task);
  }

  public List<Maintenance> getAllTasks() {
    return maintenanceService.getAllTasks();
  }

  public boolean completeTask(int taskId, double cost) {
    Maintenance task = maintenanceService.getAllTasks().stream()
        .filter(t -> t.getId() == taskId)
        .findFirst()
        .orElse(null);
    if (task != null) {
      task.setCost(cost);
      task.completeTask();
      return true;
    }
    return false;
  }
}