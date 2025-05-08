import city.*;
import notify.*;
import tariff.*;
import task.*;
import task.maintenance.*;
import user.*;
import user.admin.*;
import user.client.*;
import user.manager.*;
import user.mechanic.*;
import vehicle.*;
import vehicle.base.*;
import vehicle.rental.*;

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
                                notifyController);
                VehicleController vehicleController = new VehicleController(vehicleService);

                BaseService baseService = new BaseService(vehicleController);
                BaseController baseController = new BaseController(baseService);

                TariffService tariffService = new TariffService();
                TariffController tariffController = new TariffController(tariffService);

                TaskService taskService = new TaskService();
                TaskController taskController = new TaskController(taskService);

                UserService userService = new UserService(notifyController);
                UserController userController = new UserController(userService);

                MechanicService mechanicService = new MechanicService(vehicleController, notifyController,
                                userController);
                MechanicController mechanicController = new MechanicController(mechanicService);

                ManagerService managerService = new ManagerService(vehicleController, notifyController);
                ManagerController managerController = new ManagerController(managerService);

                ClientService clientService = new ClientService(userController);
                ClientController clientController = new ClientController(clientService);

                RentService rentService = new RentService(clientController, vehicleController, baseController,
                                tariffController);
                RentController rentController = new RentController(rentService);

                AdminService adminService = new AdminService(userController, vehicleController, taskController,
                                notifyController, baseController, mechanicController, managerController,
                                tariffController, rentController, clientController);
                AdminController adminController = new AdminController(adminService);

                // === Crear Usuarios ===
                System.out.println("=== Creando Usuarios ===");
                for (int i = 1; i <= 10; i++) {
                        adminController.createClient(i, "Client " + i, "client" + i + "@example.com", "password",
                                        i * 10.0);
                }
                for (int i = 11; i <= 15; i++) {
                        adminController.createMechanic(i, "Mechanic " + i, "mechanic" + i + "@example.com", "password");
                }
                for (int i = 16; i <= 20; i++) {
                        adminController.createManager(i, "Manager " + i, "manager" + i + "@example.com", "password");
                }

                System.out.println("\nUsuarios registrados:");
                adminController.getAllUsers().forEach(System.out::println);

                // === Crear Vehículos ===
                System.out.println("\n=== Creando Vehículos ===");
                for (int i = 1; i <= 7; i++) {
                        adminController.addVehicle(i, "bicycle", true, true, i * 10);
                }
                for (int i = 8; i <= 14; i++) {
                        adminController.addVehicle(i, "motorbike", true, true, i * 10);
                }
                for (int i = 15; i <= 20; i++) {
                        adminController.addVehicle(i, "skate", true, true, i * 10);
                }

                // Visualización del estado de batería de todos los vehículos.
                System.out.println("\nVehículos registrados:");
                adminController.getVehiclesAndBatteryLevels().forEach(System.out::println);

                // === Crear Bases ===
                System.out.println("\n=== Creando Bases ===");
                for (int i = 1; i <= 20; i++) {
                        baseController.addBase(i, "Base " + i, i * 5, i * 10, i * 10);
                }

                System.out.println("\nBases registradas:");
                baseController.getAllBases().forEach(System.out::println);

                // === Registrar Vehículos en Bases ===
                System.out.println("\n=== Registrando Vehículos en Bases ===");
                for (int i = 1; i <= 20; i++) {
                        int vehicleId = i;
                        int baseId = (i % 20) + 1;
                        baseController.addVehicleToBase(baseId, vehicleId);
                }

                System.out.println("\nBases después de registrar vehículos:");
                baseController.getAllBases().forEach(base -> {
                        System.out.println("Base ID: " + base.getId() + ", Vehículos: " + base.getVehicles());
                });

                // === Gestión del Tarifas ===
                System.out.println("\n=== Gestión de Tarifas ===");
                tariffController.defineTariff("bicycle", 10.0, 20.0);
                tariffController.defineTariff("motorbike", 10.0, 20.0);
                tariffController.defineTariff("skate", 10.0, 20.0);

                System.out.println("\nTarifas registradas:");
                tariffController.getAllTariffs().forEach((vehicleType, tariff) -> {
                        System.out.println(vehicleType + ": " + tariff);
                });

                // === Gestión del Alquiler ===
                System.out.println("\n=== Gestión de Alquiler ===");
                rentController.registerRent(1, 20, 1);
                rentController.registerRent(2, 1, 2);
                rentController.registerRent(3, 2, 3);

                System.out.println("\nAlquileres registrados:");
                rentController.getAllRents().forEach(System.out::println);

                rentController.endRent(1);
                rentController.endRent(2);

                System.out.println("\nAlquileres registrados terminados:");
                rentController.getAllRents().forEach(System.out::println);

                // === Gestión del Tareas ===
                System.out.println("\n=== Gestión de Tareas ===");
                taskController.createMaintenanceTask(1, "Mantenimiento 1", vehicleController.findVehicleById(1), "1",
                                "10.0");
                taskController.createRepairTask(2, "Reparación 1", vehicleController.findVehicleById(2),
                                baseController.findBaseById(1));
                taskController.createMaintenanceTask(3, "Mantenimiento 2", vehicleController.findVehicleById(3), "2",
                                "20.0");
                taskController.createRepairTask(4, "Reparación 2", vehicleController.findVehicleById(4),
                                baseController.findBaseById(2));

                // Visualización de los avisos de problemas mecánicos en los vehículos.
                System.out.println("\nTareas registradas:");
                taskController.getAllTasks().forEach(System.out::println);

                taskController.completeTask(1, 50.0);
                taskController.completeTask(2, 100.0);

                System.out.println("\nTareas registradas despues de completar:");
                taskController.getAllTasks().forEach(System.out::println);

                // Visualización del estado de las bases de bicicletas y patinetes (vehículos
                // disponibles, huecos disponibles, fallos mecánicos).

                System.out.println("\nAll bases information: ");
                adminController.getAllBasesInfo().forEach(System.out::println);

        }
}