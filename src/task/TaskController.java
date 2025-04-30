package task;

import task.maintenance.Maintenance;
import task.repair.Repair;
import vehicle.Vehicle;
import vehicle.base.Base;

import java.util.List;

public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  public boolean createMaintenanceTask(int id, String description, Vehicle vehicle, String pickupLocation, String dropOffLocation) {
    Maintenance maintenance = new Maintenance(id, description, vehicle, pickupLocation, dropOffLocation);
    return taskService.addTask(maintenance);
  }

  public boolean createRepairTask(int id, String description, Vehicle vehicle, Base base) {
    Repair repair = new Repair(id, description, vehicle, base);
    return taskService.addTask(repair);
  }

  public List<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  public boolean completeTask(int taskId, double cost) {
    return taskService.completeTask(taskId, cost);
  }
}