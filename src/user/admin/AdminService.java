package user.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import notify.NotifyController;
import notify.enums.NotifyCodeEnum;
import task.Task;
import task.TaskController;
import tariff.Tariff;
import tariff.TariffController;
import user.User;
import user.UserController;
import user.client.Client;
import user.client.ClientController;
import user.manager.Manager;
import user.manager.ManagerController;
import user.mechanic.Mechanic;
import user.mechanic.MechanicController;
import vehicle.Vehicle;
import vehicle.VehicleController;
import vehicle.base.Base;
import vehicle.base.BaseController;
import vehicle.rental.Rent;
import vehicle.rental.RentController;

public class AdminService {
    private final List<Admin> admins = new ArrayList<>();
    private final UserController userController;
    private final VehicleController vehicleController;
    private final TaskController taskController;
    private final NotifyController notifyController;
    private final BaseController baseController;
    private final MechanicController mechanicController;
    private final ManagerController managerController;
    private final TariffController tariffController;
    private final RentController usageController;

    // Constructor con inyección de dependencias
    public AdminService(
            UserController userController,
            VehicleController vehicleController,
            TaskController taskController,
            NotifyController notifyController,
            BaseController baseController,
            MechanicController mechanicController,
            ManagerController managerController,
            TariffController tariffController,
            RentController usageController,
            ClientController clientController) {
        this.userController = userController;
        this.vehicleController = vehicleController;
        this.taskController = taskController;
        this.notifyController = notifyController;
        this.baseController = baseController;
        this.mechanicController = mechanicController;
        this.managerController = managerController;
        this.tariffController = tariffController;
        this.usageController = usageController;
    }

    // public List<User> getEligibleForPremium() {
    // return userController.getEligibleForPremium();
    // }

    // public boolean promoteToPremium(int userId, double discountPercentage) {
    // return userController.promoteToPremium(userId, discountPercentage);
    // }

    public Admin findAdminById(int id) {
        return admins.stream()
                .filter(admin -> admin.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean createClient(int id, String name, String email, String password, double balance) {
        Client client = new Client(id, name, email, password, balance);
        return userController.createClient(client);
    }

    public boolean createMechanic(int id, String name, String email, String password) {
        Mechanic mechanic = new Mechanic(id, name, email, password);
        return userController.createMechanic(mechanic);
    }

    public boolean createManager(int id, String name, String email, String password) {
        Manager manager = new Manager(id, name, email, password);
        return userController.createManager(manager);
    }

    public List<User> getAllUsers() {
        return userController.getAllUsers();
    }

    public boolean updateUser(int id, String name, String email, String password, String role) {
        return userController.updateUser(id, name, email, password, role, -1);
    }

    public boolean deleteUserById(int id) {
        try {
            return userController.deleteUserById(id);
        } catch (SecurityException e) {
            this.notifyController.log(NotifyCodeEnum.UNAUTHORIZED, "Permission denied: " + e.getMessage());
            return false;
        }
    }

    public boolean addVehicle(int id, String type, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
        return vehicleController.addVehicle(id, type, isAvailable, hasNoDamage, batteryLevel);
    }

    public List<String> getVehiclesAndBatteryLevels() {
        return vehicleController.getVehiclesAndBatteryLevels();
    }

    public boolean updateVehicle(int id, boolean isAvailable, boolean hasNoDamage, int batteryLevel) {
        Vehicle vehicle = vehicleController.findVehicleById(id);
        if (vehicle == null) {
            notifyController.log(NotifyCodeEnum.NOT_FOUND, "Vehicle with ID " + id + " not found.");
            return false;
        }
        vehicle.setAvailable(isAvailable);
        vehicle.setHasNoDamage(hasNoDamage);
        vehicle.setBatteryLevel(batteryLevel);
        return true;
    }

    public boolean deleteVehicle(int id) {
        Vehicle vehicle = vehicleController.findVehicleById(id);
        if (vehicle == null) {
            notifyController.log(NotifyCodeEnum.NOT_FOUND, "Vehicle with ID " + id + " not found.");
            return false;
        }
        return vehicleController.removeVehicle(id);
    }

    public List<Task> getAllTasks() {
        return taskController.getAllTasks();
    }

    public List<String> getAllBasesInfo() {
        List<String> baseInfoList = new ArrayList<>();
        for (Base base : baseController.getAllBases()) {
            int availableVehicles = base.getVehicles().size();
            int freeSpaces = base.getCapacity() - availableVehicles;

            String baseInfo = String.format(
                    "Base ID: %d, Location: %s, Available Vehicles: %d, Free Spaces: %d, Mechanical Issues: %s",
                    base.getId(),
                    base.getLocation(),
                    availableVehicles,
                    freeSpaces,
                    "N/A");
            baseInfoList.add(baseInfo);
        }
        return baseInfoList;
    }

    public boolean setVehicleToManager(Manager manager, int vehicleId) {
        return managerController.setVehicleToWork(manager, vehicleId);
    }

    public List<String> getAllTripsInfo() {
        List<String> vehicleInfoList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleController.getAllVehicles();
        Map<String, Tariff> tariffs = tariffController.getAllTariffs();

        for (Vehicle vehicle : vehicles) {
            User user = vehicle.getUser();
            String userName = (user != null) ? user.getName() : "No user";
            String userEmail = (user != null) ? user.getEmail() : "N/A";

            Tariff tariff = tariffs.get(vehicle.getType().toLowerCase());
            double rate = (tariff != null) ? tariff.getBaseRate() : 0.0;

            String vehicleInfo = String.format(
                    "Vehicle ID: %d, Type: %s, User: %s, Email: %s, Tariff: $%.2f",
                    vehicle.getId(),
                    vehicle.getType(),
                    userName,
                    userEmail,
                    rate);
            vehicleInfoList.add(vehicleInfo);
        }

        return vehicleInfoList;
    }

    public List<Rent> getAllUsageRecords() {
        return usageController.getAllRents();
    }

    public boolean assignVehicleToMechanic(int vehicleId, int mechanicId) {
        return mechanicController.setVehicleToWork(vehicleId, mechanicId);
    }

    public List<String> getVehiclesWithUsersAndTariffs() {
        List<String> vehicleInfoList = new ArrayList<>();
        List<Vehicle> vehicles = vehicleController.getAllVehicles();
        Map<String, Tariff> tariffs = tariffController.getAllTariffs();

        for (Vehicle vehicle : vehicles) {
            User user = vehicle.getUser();
            String userName = (user != null) ? user.getName() : "No user";
            String userEmail = (user != null) ? user.getEmail() : "N/A";

            Tariff tariff = tariffs.get(vehicle.getType().toLowerCase());
            double rate = (tariff != null) ? tariff.getBaseRate() : 0.0;

            String vehicleInfo = String.format(
                    "Vehicle ID: %d, Type: %s, User: %s, Email: %s, Tariff: $%.2f",
                    vehicle.getId(),
                    vehicle.getType(),
                    userName,
                    userEmail,
                    rate);
            vehicleInfoList.add(vehicleInfo);
        }

        return vehicleInfoList;
    }

    public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
        return usageController.getVehiclesInUseDuring(start, end);
    }

    public Map<String, Base> getDemandStatistics(List<Rent> usageRecords) {
        return baseController.getDemandStatistics(usageRecords);
    }
}