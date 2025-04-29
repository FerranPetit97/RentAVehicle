package user.client.premium;

import java.util.List;

public class PremiumController {
  private final PremiumService premiumService;

  public PremiumController() {
    this.premiumService = new PremiumService();
  }

  public boolean addPremiumUser(int id, String name, String email, String password, double discountPercentage) {
    Premium premiumUser = new Premium(id, name, email, password, discountPercentage);
    return premiumService.addPremiumUser(premiumUser);
  }

  public List<Premium> getAllPremiumUsers() {
    return premiumService.getAllPremiumUsers();
  }

  public Premium findPremiumUserById(int id) {
    return premiumService.findPremiumUserById(id);
  }

  public boolean removePremiumUserById(int id) {
    return premiumService.removePremiumUserById(id);
  }
}