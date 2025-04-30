package user.client;

import java.util.List;

public class ClientController {
  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  public boolean createClient(Client user) {
    return clientService.createClient(user);
  }

  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  public Client findClientByEmail(String email) {
    return clientService.findClientByEmail(email);
  }

  public boolean updateClient(Client client) {
    return clientService.updateClient(client);
  }

  public boolean deleteClientByEmail(String email) {
    return clientService.deleteClientByEmail(email);
  }
}