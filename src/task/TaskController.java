package task;

import task.maintenance.Maintenance;
import task.repair.Repair;
import vehicle.base.Base;
import vehicle.base.BaseController;

import java.util.List;

public class TaskController {
  private final TaskService taskService;
  private final BaseController baseController;

  public TaskController(TaskService taskService, BaseController baseController) {
    this.taskService = taskService;
    this.baseController = baseController;
  }

  public boolean createMaintenanceTask(String description, int baseId) {
    Base base = baseController.findBaseById(baseId);
    Maintenance maintenance = new Maintenance(0, description, base);
    return taskService.addTask(maintenance);
  }

  public boolean createRepairTask(String name, int vehicleId, String pickupLocation, String dropOffLocation) {
    Task task = new Repair(0, name, null, pickupLocation, dropOffLocation);
    return taskService.addTask(task);
  }

  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  public Task findTaskById(int taskId) {
    return taskService.findTaskById(taskId);
  }
}