package vehicle.rental;

import java.time.LocalDateTime;
import java.util.List;

import vehicle.Vehicle;

public class RentController {
  private final RentService rentService;

  public RentController(RentService rentService) {
    this.rentService = rentService;
  }

  public boolean registerRent(int clientId, int vehicleId, int baseId, int duration) {
    return rentService.startRent(clientId, vehicleId, baseId, duration);
  }

  public boolean endRent(int rentId, int baseId) {
    return rentService.endRent(rentId, baseId);
  }

  public List<Rent> getAllRents() {
    return rentService.getAllRents();
  }

  // Método existente con fechas
  public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
    return rentService.getVehiclesInUseDuring(start, end);
  }

  // Nuevo método sin parámetros
  public List<Vehicle> getVehiclesInUse() {
    // Llama al servicio con null para obtener todos los vehículos en uso
    return rentService.getVehiclesInUseDuring(null, null);
  }
}