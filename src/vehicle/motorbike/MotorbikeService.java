package vehicle.motorbike;

import java.util.ArrayList;
import java.util.List;

public class MotorbikeService {
  private final List<Motorbike> motorbikes;

  public MotorbikeService() {
    this.motorbikes = new ArrayList<>();
  }

  public boolean addMotorbike(Motorbike motorbike) {
    return motorbikes.add(motorbike);
  }

  public List<Motorbike> getAllMotorbikes() {
    return motorbikes;
  }

  public Motorbike findMotorbikeById(int id) {
    return motorbikes.stream()
        .filter(motorbike -> motorbike.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean removeMotorbike(int id) {
    Motorbike motorbike = findMotorbikeById(id);
    if (motorbike != null) {
      return motorbikes.remove(motorbike);
    }
    return false; // Motorbike not found
  }
}