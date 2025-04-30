package user.client;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
  private final List<Client> clients = new ArrayList<>();

  public boolean createClient(Client client) {
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

  public boolean updateClient(Client client) {
    for (int i = 0; i < clients.size(); i++) {
      if (clients.get(i).getEmail().equalsIgnoreCase(client.getEmail())) {
        clients.set(i, client);
        return true;
      }
    }
    return false;
  }

  public boolean deleteClientByEmail(String email) {
    return clients.removeIf(client -> client.getEmail().equalsIgnoreCase(email));
  }
}