package task.repair;

public class RepairFactory {
    private static RepairController instance;

    public static RepairController getInstance() {
        if (instance == null) {
            RepairService repairService = new RepairService();
            instance = new RepairController(repairService);
        }
        return instance;
    }
}
