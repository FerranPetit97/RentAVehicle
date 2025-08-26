package task.maintenance;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceService {
  private final List<Maintenance> tasks;

  public MaintenanceService() {
    this.tasks = new ArrayList<>();
  }

  public List<Maintenance> getAllTasks() {
    return tasks;
  }

  public boolean completeTask(Maintenance task) {
    return tasks.remove(task);
  }
}