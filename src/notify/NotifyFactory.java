package notify;

public class NotifyFactory {
    private static NotifyController instance;

    public static synchronized NotifyController getInstance() {
        if (instance == null) {
            NotifyService notifyService = new NotifyService();
            instance = new NotifyController(notifyService);
        }
        return instance;
    }
}