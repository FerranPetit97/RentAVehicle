package vehicle.motorbike;

import java.util.List;

public class MotorbikeController {
  private final MotorbikeService motorbikeService;

  public MotorbikeController() {
    this.motorbikeService = new MotorbikeService();
  }

  public boolean addMotorbike(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    Motorbike motorbike = new Motorbike(id, isAvailable, hasNoDamage, batteryLevel);
    return motorbikeService.addMotorbike(motorbike);
  }

  public List<Motorbike> getAllMotorbikes() {
    return motorbikeService.getAllMotorbikes();
  }

  public Motorbike findMotorbikeById(int id) {
    return motorbikeService.findMotorbikeById(id);
  }

  public boolean removeMotorbike(int id) {
    return motorbikeService.removeMotorbike(id);
  }
}