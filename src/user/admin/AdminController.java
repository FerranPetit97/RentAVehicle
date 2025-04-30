package user.admin;

import java.time.LocalDateTime;
import java.util.List;

import user.User;
import vehicle.Vehicle;
import vehicle.usage.Usage;
import task.Task;

public class AdminController {
  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  public List<Admin> getAllAdmins() {
    return adminService.getAllAdmins();
  }

  public boolean createUser(int id, String name, String email, String password, String type) {
    return adminService.createUser(id, name, email, password, type);
  }

  public List<User> getAllUsers() {
    return adminService.getAllUsers();
  }

  public List<User> getEligibleForPremium() {
    return adminService.getEligibleForPremium();
  }

  public boolean updateUserById(int id, String name, String email, String password, String role) {
    return adminService.updateUserById(id, name, email, password, role);
  }

  public boolean promoteToPremium(int userId, double discountPercentage) {
    return adminService.promoteToPremium(userId, discountPercentage);
  }

  public boolean deleteUserById(int id) {
    return adminService.deleteUserById(id);
  }

  public List<Task> getAllTasks() {
    return adminService.getAllTasks();
  }

  public List<String> getVehiclesAndBatteryLevels() {
    return adminService.getVehiclesAndBatteryLevels();
  }

  public List<String> getAllBasesInfo() {
    return adminService.getAllBasesInfo();
  }

  public List<String> getAllTripsInfo() {
    return adminService.getAllTripsInfo();
  }

  public List<Usage> getAllUsageRecords() {
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

  public boolean addVehicle(int id, String type, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    return adminService.addVehicle(id, type, isAvailable, hasNoDamage, batteryLevel);
  }

  public boolean updateVehicle(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
    return adminService.updateVehicle(id, isAvailable, hasNoDamage, batteryLevel);
  }

  public boolean deleteVehicle(int id) {
    return adminService.deleteVehicle(id);
  }
}