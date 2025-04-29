package notify;

import java.util.ArrayList;
import java.util.List;

import notify.enums.NotifyCodeEnum;

public class NotifyService {
    private final List<Notify> notifies;

    public NotifyService() {
        this.notifies = new ArrayList<>();
    }

    public void logError(NotifyCodeEnum errorCode, String additionalMessage) {
        String fullMessage = errorCode.getMessage();
        if (additionalMessage != null && !additionalMessage.isEmpty()) {
            fullMessage += ": " + additionalMessage;
        }
        Notify error = new Notify(errorCode.getCode(), fullMessage);
        notifies.add(error);
        System.err.println("Error Code: " + errorCode.getCode() + " - Message: " + fullMessage); 
    }

    public List<Notify> getAllErrors() {
        return notifies;
    }
}