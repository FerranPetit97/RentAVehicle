package task.maintenance;

public class MaintenanceFactory {
    private static MaintenanceController instance;

    public static MaintenanceController getInstance() {
        if (instance == null) {
            MaintenanceService maintenanceService = new MaintenanceService();
            instance = new MaintenanceController(maintenanceService);
        }
        return instance;
    }
}
