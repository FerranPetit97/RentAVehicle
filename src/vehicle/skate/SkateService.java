package vehicle.skate;

import java.util.ArrayList;
import java.util.List;

public class SkateService {
  private final List<Skate> skates;

  public SkateService() {
    this.skates = new ArrayList<>();
  }

  public boolean addSkate(Skate skate) {
    return skates.add(skate);
  }

  public List<Skate> getAllSkates() {
    return skates;
  }

  public Skate findSkateById(int id) {
    return skates.stream()
        .filter(skate -> skate.getId() == id)
        .findFirst()
        .orElse(null);
  }

  public boolean removeSkate(int id) {
    Skate skate = findSkateById(id);
    if (skate != null) {
      return skates.remove(skate);
    }
    return false; // Skate not found
  }
}