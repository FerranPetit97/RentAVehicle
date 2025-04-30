package vehicle.usage;

import java.time.LocalDateTime;
import java.util.List;

import user.User;
import vehicle.Vehicle;

public class UsageController {
  private final UsageService usageService;

  public UsageController(UsageService usageService) {
    this.usageService = usageService;
  }

  public Usage registerUsage(Vehicle vehicle, User user) {
    return usageService.registerUsage(vehicle, user);
  }

  public boolean endUsage(int usageId) {
    return usageService.endUsage(usageId);
  }

  public List<Usage> getAllUsages() {
    return usageService.getAllUsages();
  }

  public List<Usage> getUsagesByUser(User user) {
    return usageService.getUsagesByUser(user);
  }

  public List<Usage> getUsagesByVehicle(Vehicle vehicle) {
    return usageService.getUsagesByVehicle(vehicle);
  }

  public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
    return usageService.getVehiclesInUseDuring(start, end);
  }
}