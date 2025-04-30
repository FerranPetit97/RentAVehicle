package user.client.premium;

import java.util.List;

import user.client.Client;

public class PremiumController {
  private final PremiumService premiumService;

  public PremiumController() {
    this.premiumService = new PremiumService();
  }

  public boolean addPremiumUser(Premium user) {
    Premium premiumUser = new Premium(user);
    return premiumService.addPremiumUser(premiumUser);
  }

  public List<Premium> getAllPremiumUsers() {
    return premiumService.getAllPremiumUsers();
  }

  public Premium findPremiumUserById(int id) {
    return premiumService.findPremiumUserById(id);
  }

  public boolean updateClient(Client client) {
    return clientService.updateClient(client);
  }

  public boolean removePremiumUserById(int id) {
    return premiumService.removePremiumUserById(id);
  }
}