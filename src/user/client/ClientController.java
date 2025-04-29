package user.client;

import java.util.List;

public class ClientController {
  private final ClientService clientService;

  public ClientController() {
    this.clientService = new ClientService();
  }

  public boolean addClient(int id, String name, String email, String password, String role) {
    Client client = new Client(id, name, email, password, role);
    return clientService.addClient(client);
  }

  public List<Client> getAllClients() {
    return clientService.getAllClients();
  }

  public Client findClientByEmail(String email) {
    return clientService.findClientByEmail(email);
  }

  public boolean deleteClientByEmail(String email) {
    return clientService.deleteClientByEmail(email);
  }
}