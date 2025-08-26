package vehicle.motorbike;


public class MotorbikeController {
  private final MotorbikeService motorbikeService;

  public MotorbikeController() {
    this.motorbikeService = new MotorbikeService();
  }

  public Motorbike findMotorbikeById(int id) {
    return motorbikeService.findMotorbikeById(id);
  }

  public boolean removeMotorbike(int id) {
    return motorbikeService.removeMotorbike(id);
  }
}