package user.client.premium;

import java.util.List;


public class PremiumController {
  private final PremiumService premiumService;

  public PremiumController() {
    this.premiumService = new PremiumService();
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