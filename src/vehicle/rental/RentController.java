package vehicle.rental;

import java.time.LocalDateTime;
import java.util.List;

import vehicle.Vehicle;

public class RentController {
  private final RentService rentService;

  public RentController(RentService rentService) {
    this.rentService = rentService;
  }

  public boolean registerRent(int clientId, int vehicleId, int baseId) {
    return rentService.registerRent(clientId, vehicleId, baseId);
  }

  public boolean endRent(int rentId) {
    return rentService.endRent(rentId);
  }

  public List<Rent> getAllRents() {
    return rentService.getAllRents();
  }

  public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
    return rentService.getVehiclesInUseDuring(start, end);
  }
}