package task.maintenance;

public class MaintenanceController {
  private final MaintenanceService maintenanceService;

  public MaintenanceController(MaintenanceService maintenanceService) {
    this.maintenanceService = maintenanceService;
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