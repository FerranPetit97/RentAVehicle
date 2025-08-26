package user.client;

import java.util.List;

public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }
  
  public Client findClientById(int id) {
    return clientService.findClientById(id);
  }

  public boolean upgradeClientToPremium(int clientId) {
    return clientService.upgradeClientToPremium(clientId);
  }

}