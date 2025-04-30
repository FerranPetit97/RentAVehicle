package notify;

import java.util.List;

import notify.enums.NotifyCodeEnum;

public class NotifyController {
    private final NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    public void log(NotifyCodeEnum code, String message) {
        notifyService.logError(code, message);
    }

    public List<Notify> getAllErrors() {
        return notifyService.getAllErrors();
    }
}