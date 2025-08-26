package user.manager;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import user.UserController;

public class ManagerService {
  private final UserController userController;
  private final NotifyController notifyController;

  public ManagerService(NotifyController notifyController,
      UserController userController) {
    this.notifyController = notifyController;
    this.userController = userController;
  }

  public Manager findManagerById(int id) {
    Manager manager = (Manager) userController.findUserById(id);
    if (manager == null) {
      notifyController.log(NotifyCodeEnum.NOT_FOUND, "Mechanic with ID " + id + " not found.");
    }
    return manager;
  }

}