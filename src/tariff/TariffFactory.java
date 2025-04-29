package tariff;

public class TariffFactory {
    private static TariffController instance;

    public static synchronized TariffController getInstance() {
        if (instance == null) {
            TariffService tariffService = new TariffService();
            instance = new TariffController(tariffService);
        }
        return instance;
    }
}