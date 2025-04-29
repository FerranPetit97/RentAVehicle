package vehicle.skate;

import java.util.List;

public class SkateController {
  private final SkateService skateService;

  public SkateController() {
    this.skateService = new SkateService();
  }

  public boolean addSkate(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    Skate skate = new Skate(id, isAvailable, hasNoDamage, batteryLevel);
    return skateService.addSkate(skate);
  }

  public List<Skate> getAllSkates() {
    return skateService.getAllSkates();
  }

  public Skate findSkateById(int id) {
    return skateService.findSkateById(id);
  }

  public boolean removeSkate(int id) {
    return skateService.removeSkate(id);
  }
}