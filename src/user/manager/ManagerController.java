package user.manager;

import java.util.List;

public class ManagerController {
  private final ManagerService managerService;

  public ManagerController() {
    this.managerService = new ManagerService();
  }

  public boolean addManager(int id, String name, String email, String password) {
    Manager manager = new Manager(id, name, email, password);
    return managerService.addManager(manager);
  }

  public List<Manager> getAllManagers() {
    return managerService.getAllManagers();
  }

  public Manager findManagerByEmail(String email) {
    return managerService.findManagerByEmail(email);
  }

  public boolean deleteManagerByEmail(String email) {
    return managerService.deleteManagerByEmail(email);
  }
}