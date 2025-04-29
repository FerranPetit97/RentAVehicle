package user.client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
  private final List<Client> clients = new ArrayList<>();

  public boolean addClient(Client client) {
    return clients.add(client);
  }

  public List<Client> getAllClients() {
    return clients;
  }

  public Client findClientByEmail(String email) {
    return clients.stream()
        .filter(client -> client.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
  }

  public boolean deleteClientByEmail(String email) {
    return clients.removeIf(client -> client.getEmail().equalsIgnoreCase(email));
  }
}