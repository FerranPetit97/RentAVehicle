package user.mechanic;

import java.util.ArrayList;
import java.util.List;

public class MechanicService {
  private final List<Mechanic> mechanics = new ArrayList<>();

  public boolean addMechanic(Mechanic mechanic) {
    return mechanics.add(mechanic);
  }

  public List<Mechanic> getAllMechanics() {
    return mechanics;
  }

  public Mechanic findMechanicByEmail(String email) {
    return mechanics.stream()
        .filter(mechanic -> mechanic.getEmail().equalsIgnoreCase(email))
        .findFirst()
        .orElse(null);
  }

  public boolean deleteMechanicByEmail(String email) {
    return mechanics.removeIf(mechanic -> mechanic.getEmail().equalsIgnoreCase(email));
  }
}