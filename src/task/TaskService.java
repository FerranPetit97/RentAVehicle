package task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
  private final List<Task> tasks;

  public TaskService() {
    this.tasks = new ArrayList<>();
  }

  public boolean addTask(Task task) {
    return tasks.add(task);
  }

  public List<Task> getAllTasks() {
    return tasks;
  }

  public boolean completeTask(int taskId, double cost) {
    for (Task task : tasks) {
      if (task.getId() == taskId) {
        task.setCost(cost);
        task.completeTask();
        return true;
      }
    }
    return false;
  }
}