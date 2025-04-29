package user.client.premium;

import java.util.ArrayList;
import java.util.List;

public class PremiumService {
  private final List<Premium> premiumUsers;

  public PremiumService() {
    this.premiumUsers = new ArrayList<>();
  }

  public boolean addPremiumUser(Premium premiumUser) {
    return premiumUsers.add(premiumUser);
  }

  public List<Premium> getAllPremiumUsers() {
    return premiumUsers;
  }

  public Premium findPremiumUserById(int id) {
    return premiumUsers.stream()
        .filter(user -> user.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean removePremiumUserById(int id) {
    return premiumUsers.removeIf(user -> user.getId() == id);
  }
}