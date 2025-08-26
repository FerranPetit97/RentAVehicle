package vehicle.skate;

import java.util.List;

public class SkateController {
  private final SkateService skateService;

  public SkateController() {
    this.skateService = new SkateService();
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