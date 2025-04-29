package vehicle;

public class VehicleFactory {
    private static VehicleController instance;

    public static synchronized VehicleController getInstance() {
        if (instance == null) {
            instance = new VehicleController();
        }
        return instance;
    }
}