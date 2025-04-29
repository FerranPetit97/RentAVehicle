package task;

public class TaskFactory {
    private static TaskController instance;

    public static TaskController getInstance() {
        if (instance == null) {
            TaskService taskService = new TaskService();
            instance = new TaskController(taskService);
        }
        return instance;
    }
}
