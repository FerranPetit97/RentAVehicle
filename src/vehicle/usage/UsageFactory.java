package vehicle.usage;

public class UsageFactory {
    private static UsageController instance;

    public static synchronized UsageController getInstance() {
        if (instance == null) {
            UsageService usageService = new UsageService();
            instance = new UsageController(usageService);
        }
        return instance;
    }
}