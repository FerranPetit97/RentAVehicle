import city.*;
import notify.*;
import notify.enums.NotifyCodeEnum;
import tariff.*;
import task.*;
import task.maintenance.*;
import user.*;
import user.admin.*;
import user.client.*;
import user.client.premium.*;
import user.manager.*;
import user.mechanic.*;
import vehicle.*;
import vehicle.base.*;
import vehicle.usage.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar dependencias
        NotifyService notifyService = new NotifyService();
        NotifyController notifyController = new NotifyController(notifyService);

        City city = new City(-100, 100, -100, 100);
        CityService cityService = new CityService(city);
        CityController cityController = new CityController(cityService);

        VehicleService vehicleService = new VehicleService(
            cityController,
            new MaintenanceController(new MaintenanceService()),
            notifyController,
            null, // Se inicializará más adelante
            new UsageController(new UsageService())
        );
        VehicleController vehicleController = new VehicleController(vehicleService);

        BaseService baseService = new BaseService();
        BaseController baseController = new BaseController(baseService, notifyController);

        TariffService tariffService = new TariffService();
        TariffController tariffController = new TariffController(tariffService);

        ClientService clientService = new ClientService();
        ClientController clientController = new ClientController(clientService);

        PremiumController premiumController = new PremiumController();

        MechanicService mechanicService = new MechanicService(vehicleController, notifyController);
        MechanicController mechanicController = new MechanicController(mechanicService);

        ManagerService managerService = new ManagerService(vehicleController, notifyController);
        ManagerController managerController = new ManagerController(managerService);

        UsageController usageController = new UsageController(new UsageService());

        UserService userService = new UserService(clientController, premiumController, mechanicController,
            managerController, notifyController, usageController);
        UserController userController = new UserController(userService);

        // Actualizar la dependencia de UserController en VehicleService
        vehicleService = new VehicleService(
            cityController,
            new MaintenanceController(new MaintenanceService()),
            notifyController,
            userController,
            usageController
        );

        AdminService adminService = new AdminService(userController);
        AdminController adminController = new AdminController(adminService);

        // === Gestión de Usuarios ===
        System.out.println("=== Gestión de Usuarios ===");
        adminController.createUser(1, "John Doe", "john@example.com", "password", "client");
        adminController.createUser(2, "Alice Mechanic", "alice@mechanics.com", "password", "mechanic");
        adminController.createUser(3, "Bob Manager", "bob@managers.com", "password", "manager");

        System.out.println("\nUsuarios registrados:");
        adminController.getAllUsers().forEach(System.out::println);

        System.out.println("\nUsuarios elegibles para premium:");
        adminController.getEligibleForPremium().forEach(System.out::println);

        System.out.println("\nPromoviendo a premium...");
        adminController.promoteToPremium(1, 10.0);

        System.out.println("\nUsuarios después de la promoción:");
        adminController.getAllUsers().forEach(System.out::println);

        // === Gestión de Vehículos ===
        System.out.println("\n=== Gestión de Vehículos ===");
        adminController.addVehicle(1, "bicycle", true, true, 100);
        adminController.addVehicle(2, "skate", true, true, 80);
        adminController.addVehicle(3, "motorbike", true, true, 50);

        System.out.println("\nVehículos registrados:");
        vehicleController.getAllVehicles().forEach(System.out::println);

        System.out.println("\nIniciando un viaje...");
        vehicleController.startTrip(1, 1, false);

        System.out.println("\nVehículos después de iniciar el viaje:");
        vehicleController.getAllVehicles().forEach(System.out::println);

        System.out.println("\nFinalizando el viaje...");
        vehicleController.endTrip(1, 30, "Base Central");

        System.out.println("\nVehículos después de finalizar el viaje:");
        vehicleController.getAllVehicles().forEach(System.out::println);

        // === Gestión de Bases ===
        System.out.println("\n=== Gestión de Bases ===");
        baseController.addBase(1, "Base Central", 10, 0, 0, 3);
        baseController.addBase(2, "Base Norte", 5, 50, 50, 3);

        System.out.println("\nBases registradas:");
        baseController.getAllBases().forEach(System.out::println);

        System.out.println("\nAgregando vehículo a la base...");
        baseController.addVehicleToBase(1, vehicleController.findVehicleById(1));

        System.out.println("\nBases después de agregar vehículo:");
        baseController.getAllBases().forEach(System.out::println);

        // === Gestión de Tareas ===
        System.out.println("\n=== Gestión de Tareas ===");
        TaskController taskController = new TaskController(new TaskService());
        taskController.createMaintenanceTask(1, "Revisar batería", vehicleController.findVehicleById(1), "Base Central", "Base Norte");

        System.out.println("\nTareas registradas:");
        taskController.getAllTasks().forEach(System.out::println);

        // === Gestión de Tarifas ===
        System.out.println("\n=== Gestión de Tarifas ===");
        tariffController.defineTariff("bicycle", 0.5, 10.0);
        tariffController.defineTariff("skate", 0.8, 15.0);
        tariffController.defineTariff("motorbike", 1.2, 20.0);

        System.out.println("\nTarifas definidas:");
        tariffController.getAllTariffs().forEach((type, tariff) -> System.out.println(tariff));

        // === Notificaciones ===
        System.out.println("\n=== Notificaciones ===");
        notifyController.log(NotifyCodeEnum.BAD_REQUEST, "Invalid input data");
        notifyController.log(NotifyCodeEnum.NOT_FOUND, "Resource not found");

        System.out.println("\nErrores registrados:");
        notifyController.getAllErrors().forEach(System.out::println);
    }
}