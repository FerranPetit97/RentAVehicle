import java.util.List;
import java.util.Map;

import tariff.Tariff;
import tariff.TariffController;
import tariff.TariffFactory;
import task.maintenance.Maintenance;
import task.maintenance.MaintenanceController;
import task.maintenance.MaintenanceFactory;
import task.repair.Repair;
import task.repair.RepairController;
import task.repair.RepairFactory;
import user.User;
import user.admin.Admin;
import user.admin.AdminController;
import user.admin.AdminFactory;
import vehicle.base.Base;
import vehicle.base.BaseController;
import vehicle.Vehicle;
import vehicle.VehicleController;
import vehicle.VehicleFactory;
import vehicle.bicycle.Bicycle;
import vehicle.skate.Skate;
import vehicle.motorbike.Motorbike;

import city.City;
import city.CityController;
import city.CityFactory;

public class Main {

    private CityController cityController;
    private AdminController adminController;
    private BaseController baseController;
    private VehicleController vehicleController;
    private TariffController tariffController;
    private MaintenanceController maintenanceController;
    private RepairController repairController;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void run() {

        Admin adminUser = new Admin(1, "Admin", "admin@gmail.com", "123456");

        // Initialize controllers
        City city = new City(0, 100, 0, 100);
        cityController = CityFactory.getInstance(city);
        adminController = AdminFactory.getInstance(adminUser);
        baseController = new BaseController();
        maintenanceController = MaintenanceFactory.getInstance();
        repairController = RepairFactory.getInstance();
        vehicleController = VehicleFactory.getInstance();
        tariffController = TariffFactory.getInstance();

        // Add users
        printSectionHeader("Adding Users");
        adminController.registerUser(2, "Ferran", "ferran@gmail.com", "123456", "client");
        adminController.registerUser(3, "Maria", "maria@gmail.com", "123456", "client");
        adminController.registerUser(4, "Antonio", "antonio@gmail.com", "123456", "manager");
        adminController.registerUser(5, "Luis", "luis@gmail.com", "123456", "mechanic");
        adminController.registerUser(6, "Admin", "admin@gmail.com", "admin123", "admin");
        printUsers(adminController.getAllUsers());

        // Add bases
        printSectionHeader("Adding Bases");
        baseController.addBase(1, "Central Park", 5, 10, 20);
        baseController.addBase(2, "Downtown", 3, 50, 50);
        printBases(baseController.getAllBases());

        // Add vehicles
        printSectionHeader("Adding Vehicles");
        vehicleController.addVehicle(new Bicycle(1, true, true));
        vehicleController.addVehicle(new Skate(2, true, true, 80));
        vehicleController.addVehicle(new Motorbike(3, true, true, 100));
        vehicleController.addVehicle(new Bicycle(4, true, true));
        vehicleController.addVehicle(new Skate(5, true, true, 60));
        printVehicles(vehicleController.getAllVehicles());

        // Define tariffs
        printSectionHeader("Defining Tariffs");
        tariffController.defineTariff("bicycle", 0.5, 10.0); // $0.5 per minute, 10% discount for premium
        tariffController.defineTariff("skate", 0.7, 15.0); // $0.7 per minute, 15% discount for premium
        tariffController.defineTariff("motorbike", 1.0, 20.0); // $1.0 per minute, 20% discount for premium
        printTariffs(tariffController.getAllTariffs());

        // Assign vehicles to bases
        printSectionHeader("Assigning Vehicles to Bases");
        baseController.addVehicleToBase(1, vehicleController.findVehicleById(1)); // Bicycle to Central Park
        baseController.addVehicleToBase(1, vehicleController.findVehicleById(2)); // Skate to Central Park
        baseController.addVehicleToBase(2, vehicleController.findVehicleById(3)); // Motorbike to Downtown
        baseController.addVehicleToBase(2, vehicleController.findVehicleById(4)); // Bicycle to Downtown
        printBaseVehicles(baseController);

        // Update vehicle location
        printSectionHeader("Updating Vehicle Location");
        boolean success = vehicleController.updateVehicleLocation(3, 50, 50); // Within city limits
        System.out.println("Update successful: " + success);
        success = vehicleController.updateVehicleLocation(3, 150, 50); // Outside city limits
        System.out.println("Update successful: " + success);

        // Start and end trips
        printSectionHeader("Starting and Ending Trips");
        success = vehicleController.startTrip(3, false); // Motorbike, not premium
        System.out.println("Can start trip: " + success);
        vehicleController.endTrip(3, 50, "Downtown Base"); // 50 minutes trip

        // Simulate a trip that depletes the battery
        printSectionHeader("Simulating Battery Depletion");
        vehicleController.startTrip(2, false); // Start a trip with a Skate (not premium)
        vehicleController.endTrip(2, 200, "Central Park Base"); // 200 minutes trip, depletes battery
        printMaintenanceTasks(maintenanceController.getAllTasks());

        // Report and complete repair tasks
        printSectionHeader("Reporting and Completing Repairs");
        repairController.createRepairTask(1, "Flat tire", vehicleController.findVehicleById(2), null); // Vehicle issue
        repairController.createRepairTask(2, "Broken charging station", null, baseController.findBaseById(1)); // Base issue
        printRepairTasks(repairController.getAllRepairTasks());
        repairController.completeRepairTask(1, 50.0); // Repair cost: $50
        repairController.completeRepairTask(2, 100.0); // Repair cost: $100
        printRepairTasks(repairController.getAllRepairTasks());

        // Find nearest vehicle and base
        printSectionHeader("Finding Nearest Vehicle and Base");
        int userX = 30, userY = 40;
        String vehicleType = "motorbike";
        Vehicle nearestVehicle = vehicleController.findNearestVehicle(vehicleType, userX, userY);
        System.out.println("Nearest " + vehicleType + ": " + (nearestVehicle != null ? nearestVehicle : "None found"));
        Base nearestBase = baseController.findNearestBaseWithVehicles(userX, userY);
        System.out.println("Nearest base: " + (nearestBase != null ? nearestBase : "None found"));
    }

    private void printSectionHeader(String title) {
        System.out.println("\n--------------------");
        System.out.println(title.toUpperCase());
        System.out.println("--------------------");
    }

    private void printUsers(List<User> users) {
        System.out.printf("%-5s %-15s %-25s %-10s%n", "ID", "Name", "Email", "Role");
        for (User user : users) {
            System.out.printf("%-5d %-15s %-25s %-10s%n", user.getId(), user.getName(), user.getEmail(), user.getRole());
        }
    }

    private void printBases(List<Base> bases) {
        System.out.printf("%-5s %-15s %-10s %-15s %-10s%n", "ID", "Location", "Capacity", "Coordinates", "Broken");
        for (Base base : bases) {
            System.out.printf("%-5d %-15s %-10d (%-3d,%-3d)   %-10s%n", base.getId(), base.getLocation(),
                    base.getCapacity(), base.getX(), base.getY(), base.isBroken());
        }
    }

    private void printVehicles(List<Vehicle> vehicles) {
        System.out.printf("%-5s %-15s %-10s %-10s %-10s%n", "ID", "Type", "Available", "Battery", "Broken");
        for (Vehicle vehicle : vehicles) {
            System.out.printf("%-5d %-15s %-10s %-10d %-10s%n", vehicle.getId(), vehicle.getType(),
                    vehicle.isAvailable(), vehicle.getBatteryLevel(), vehicle.isBroken());
        }
    }

    private void printTariffs(Map<String, Tariff> tariffs) {
        System.out.printf("%-15s %-15s %-20s%n", "Type", "Base Rate", "Premium Discount");
        for (Map.Entry<String, Tariff> entry : tariffs.entrySet()) {
            Tariff tariff = entry.getValue();
            System.out.printf("%-15s %-15.2f %-20.2f%n", entry.getKey(), tariff.getBaseRate(), tariff.getPremiumDiscount());
        }
    }

    private void printBaseVehicles(BaseController baseController) {
        for (Base base : baseController.getAllBases()) {
            System.out.printf("Base %-5d (%-15s):%n", base.getId(), base.getLocation());
            for (Vehicle vehicle : base.getVehicles()) {
                System.out.printf("\t%-5d %-15s %-10s %-10d %-10s%n", vehicle.getId(), vehicle.getType(),
                        vehicle.isAvailable(), vehicle.getBatteryLevel(), vehicle.isBroken());
            }
        }
    }

    private void printMaintenanceTasks(List<Maintenance> tasks) {
        System.out.printf("%-5s %-20s %-15s %-15s %-15s %-10s%n", "ID", "Description", "Vehicle", "Pickup", "Dropoff", "Completed");
        for (Maintenance task : tasks) {
            System.out.printf("%-5d %-20s %-15s %-15s %-15s %-10s%n", task.getId(), task.getDescription(),
                    task.getVehicle(), task.getPickupLocation(), task.getDropOffLocation(), task.isCompleted());
        }
    }

    private void printRepairTasks(List<Repair> tasks) {
        System.out.printf("%-5s %-20s %-20s %-10s %-10s%n", "ID", "Description", "Vehicle/Base", "Cost", "Completed");
        for (Repair task : tasks) {
            System.out.printf("%-5d %-20s %-20s %-10.2f %-10s%n", task.getId(), task.getDescription(),
                    (task.getVehicle() != null ? task.getVehicle() : task.getBase()), task.getCost(), task.isCompleted());
        }
    }
}