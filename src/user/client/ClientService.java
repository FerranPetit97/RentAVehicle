package user.client;

import java.util.ArrayList;
import java.util.List;

import user.UserController;

public class ClientService {
  private final List<Client> clients = new ArrayList<>();

  private final UserController userController;

  public ClientService(UserController userController) {
    this.userController = userController;
  }

  public List<Client> getAllClients() {
    clients.clear();
    userController.getAllUsers().forEach(user -> {
      if (user instanceof Client) {
        clients.add((Client) user);
      }
    });
    return clients;
  }

  public Client findClientById(int id) {
    return getAllClients().stream()
        .filter(client -> client.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Client with ID " + id + " not found."));
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