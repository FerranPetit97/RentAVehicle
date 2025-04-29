package user.manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerService {
  private final List<Manager> managers = new ArrayList<>();

  public boolean addManager(Manager manager) {
    return managers.add(manager);
  }

  public List<Manager> getAllManagers() {
    return managers;
  }

  public Manager findManagerByEmail(String email) {
    return managers.stream()
        .filter(manager -> manager.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
  }

  public boolean deleteManagerByEmail(String email) {
    return managers.removeIf(manager -> manager.getEmail().equalsIgnoreCase(email));
  }
}