package user.admin;

import java.time.LocalDateTime;
import java.util.List;

import user.User;
import vehicle.Vehicle;
import vehicle.rental.Rent;
import task.Task;

public class AdminController {
  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  public List<User> getAllUsers() {
    return adminService.getAllUsers();
  }

  // public List<User> getEligibleForPremium() {
  // return adminService.getEligibleForPremium();
  // }

  public boolean updateUser(int id, String name, String email, String password, String role) {
    return adminService.updateUser(id, name, email, password, role);
  }

  // public boolean promoteToPremium(int userId, double discountPercentage) {
  // return adminService.promoteToPremium(userId, discountPercentage);
  // }

  public boolean deleteUserById(int id) {
    return adminService.deleteUserById(id);
  }

  public List<String> getVehiclesAndBatteryLevels() {
    return adminService.getVehiclesAndBatteryLevels();
  }

  public List<Task> getAllTasks() {
    return adminService.getAllTasks();
  }

  public List<String> getAllBasesInfo() {
    return adminService.getAllBasesInfo();
  }

  public List<String> getAllTripsInfo() {
    return adminService.getAllTripsInfo();
  }

  public List<Rent> getAllUsageRecords() {
    return adminService.getAllUsageRecords();
  }

  public boolean assignVehicleToMechanic(int vehicleId, int mechanicId) {
    return adminService.assignVehicleToMechanic(vehicleId, mechanicId);
  }

  public List<String> getVehiclesWithUsersAndTariffs() {
    return adminService.getVehiclesWithUsersAndTariffs();
  }

  public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
    return adminService.getVehiclesInUseDuring(start, end);
  }

  public boolean updateVehicle(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    return adminService.updateVehicle(id, isAvailable, hasNoDamage, batteryLevel);
  }

  public boolean deleteVehicle(int id) {
    return adminService.deleteVehicle(id);
  }
}