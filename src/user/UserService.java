package user;

import java.util.ArrayList;
import java.util.List;

import user.client.premium.Premium;

public class UserService {
  private final List<User> users = new ArrayList<>();
  private User currentUser;

  public UserService(User currentUser) {
    this.currentUser = currentUser;
  }

  public boolean createUser(User user) {
    if (!isAdmin(currentUser)) {
      throw new SecurityException("Only admins can create new users.");
    }
    return users.add(user);
  }

  public List<User> getAllUsers() {
    return users;
  }

  public User findUserById(int id) {
    if (!isAdmin(currentUser)) {
      throw new SecurityException("Only admins can create new users.");
    }
    return users.stream()
        .filter(user -> user.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean updateUserById(User updatedUser) {
    User user = this.findUserById(updatedUser.getId());
    if (user != null) {
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        return true;
    }
    return false;
}

  public boolean deleteUserByEmail(String email) {
    if (!isAdmin(currentUser)) {
      throw new SecurityException("Only admins can create new users.");
    }
    return users.removeIf(user -> user.getEmail().equalsIgnoreCase(email));
  }

  public List<User> getEligibleForPremium() {
    List<User> eligibleUsers = new ArrayList<>();
    for (User user : users) {
      if (!(user instanceof Premium) && meetsPremiumConditions(user)) {
        eligibleUsers.add(user);
      }
    }
    return eligibleUsers;
  }

  private boolean meetsPremiumConditions(User user) {
    // TODO: Implementar la lógica real de las condiciones de promoción
    return true; // Por ahora, asumimos que todos los usuarios cumplen las condiciones
  }

  private boolean isAdmin(User user) {
    return user != null && "admin".equalsIgnoreCase(user.getRole());
  }
}