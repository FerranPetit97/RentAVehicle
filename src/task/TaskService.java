package task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
  private final List<Task> tasks;
  private int nextId = 1;

  public TaskService() {
    this.tasks = new ArrayList<>();
  }

  public boolean addTask(Task task) {
    task.setId(nextId++);
    return tasks.add(task);
  }

  public Task findTaskById(int taskId) {
    for (Task task : tasks) {
      if (task.getId() == taskId) {
        return task;
      }
    }
    return null;
  }

  public List<Task> getAllTasks() {
    return tasks;
  }

}