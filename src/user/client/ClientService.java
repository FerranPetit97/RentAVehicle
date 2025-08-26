package user.client;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import user.UserController;
import vehicle.rental.Rent;

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

  public boolean deleteClientByEmail(String email) {
    return clients.removeIf(client -> client.getEmail().equalsIgnoreCase(email));
  }

  public List<Client> getEligibleForPremium(List<Rent> rentRecords) {
    List<Client> eligibleClients = new ArrayList<>();

    for (Client client : getAllClients()) {
      List<Rent> clientRents = rentRecords.stream()
          .filter(rent -> rent.getUser().getId() == client.getId())
          .toList();

      if (hasUsedAtLeast15TimesLastMonth(clientRents) ||
          hasUsed10TimesFor3ConsecutiveMonths(clientRents) ||
          hasUsedAllVehicleTypesFor6ConsecutiveMonths(clientRents)) {
        eligibleClients.add(client);
      }
    }

    return eligibleClients;
  }

  public boolean upgradeClientToPremium(int clientId) {
    Client client = findClientById(clientId);
    if (client == null) {
      throw new IllegalArgumentException("Client with ID " + clientId + " not found.");
    }
    client.setPremium(true);

    return true;
  }

  private boolean hasUsedAtLeast15TimesLastMonth(List<Rent> clientRents) {
    LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
    long count = clientRents.stream()
        .filter(rent -> rent.getStartTime().isAfter(oneMonthAgo))
        .count();
    return count >= 15;
  }

  private boolean hasUsed10TimesFor3ConsecutiveMonths(List<Rent> clientRents) {
    Map<YearMonth, Long> rentsPerMonth = clientRents.stream()
        .collect(Collectors.groupingBy(
            rent -> YearMonth.from(rent.getStartTime()),
            Collectors.counting()));

    List<YearMonth> sortedMonths = rentsPerMonth.keySet().stream()
        .sorted()
        .toList();

    int consecutiveMonths = 0;
    for (int i = 0; i < sortedMonths.size() - 1; i++) {
      YearMonth current = sortedMonths.get(i);
      YearMonth next = sortedMonths.get(i + 1);

      if (rentsPerMonth.get(current) >= 10 &&
          current.plusMonths(1).equals(next)) {
        consecutiveMonths++;
        if (consecutiveMonths == 3) {
          return true;
        }
      } else {
        consecutiveMonths = 0;
      }
    }

    return false;
  }

  private boolean hasUsedAllVehicleTypesFor6ConsecutiveMonths(List<Rent> clientRents) {
    Map<YearMonth, Set<String>> vehicleTypesPerMonth = clientRents.stream()
        .collect(Collectors.groupingBy(
            rent -> YearMonth.from(rent.getStartTime()),
            Collectors.mapping(
                rent -> rent.getVehicle().getType(),
                Collectors.toSet())));

    List<YearMonth> sortedMonths = vehicleTypesPerMonth.keySet().stream()
        .sorted()
        .toList();

    int consecutiveMonths = 0;
    for (int i = 0; i < sortedMonths.size() - 1; i++) {
      YearMonth current = sortedMonths.get(i);
      YearMonth next = sortedMonths.get(i + 1);

      if (vehicleTypesPerMonth.get(current).containsAll(List.of("motorbike", "bicycle", "skate")) &&
          current.plusMonths(1).equals(next)) {
        consecutiveMonths++;
        if (consecutiveMonths == 6) {
          return true;
        }
      } else {
        consecutiveMonths = 0;
      }
    }

    return false;
  }

}