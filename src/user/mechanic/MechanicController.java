package user.mechanic;

import java.util.List;

public class MechanicController {
  private final MechanicService mechanicService;

  public MechanicController() {
    this.mechanicService = new MechanicService();
  }

  public boolean addMechanic(int id, String name, String email, String password) {
    Mechanic mechanic = new Mechanic(id, name, email, password);
    return mechanicService.addMechanic(mechanic);
  }

  public List<Mechanic> getAllMechanics() {
    return mechanicService.getAllMechanics();
  }

  public Mechanic findMechanicByEmail(String email) {
    return mechanicService.findMechanicByEmail(email);
  }

  public boolean deleteMechanicByEmail(String email) {
    return mechanicService.deleteMechanicByEmail(email);
  }
}