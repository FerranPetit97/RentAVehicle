import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
                TaskController taskController = new TaskController(taskService, baseController, vehicleController);

                UserService userService = new UserService(notifyController);
                UserController userController = new UserController(userService);

                MechanicService mechanicService = new MechanicService(vehicleController, notifyController,
                                userController);
                MechanicController mechanicController = new MechanicController(mechanicService);

                ManagerService managerService = new ManagerService(vehicleController, notifyController, userController);
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

                // Crear un cliente
                Client client = new Client(0, "John Doe", "john@example.com", "password123", 100.0);
                userService.createUser(client);

                // Crear un mecánico
                Mechanic mechanic = new Mechanic(0, "Jane Smith", "jane@example.com", "securepass");
                userService.createUser(mechanic);

                // Crear un gerente
                Manager manager = new Manager(0, "Alice Johnson", "alice@example.com", "adminpass");
                userService.createUser(manager);

                System.out.println("Lista de usuarios:");
                adminController.getAllUsers().forEach(System.out::println);

                // Agregar vehículos
                vehicleController.addVehicle("bicycle");
                vehicleController.addVehicle("bicycle");
                vehicleController.addVehicle("motorbike");
                vehicleController.addVehicle("motorbike");
                vehicleController.addVehicle("skate");
                vehicleController.addVehicle("skate");

                // Mostrar todos los vehículos
                System.out.println("Lista de vehículos:");
                vehicleController.getAllVehicles().forEach(System.out::println);

                // Crear 5 bases
                baseController.addBase("Central Park", 10, 0, 0);
                baseController.addBase("Downtown", 15, 10, 10);
                baseController.addBase("Uptown", 20, -10, 10);
                baseController.addBase("Suburbs", 25, -10, -10);
                baseController.addBase("Airport", 30, 20, 20);

                List<Vehicle> vehicles = vehicleController.getAllVehicles();
                List<Base> bases = baseController.getAllBases();
                Random random = new Random();

                for (Vehicle vehicle : vehicles) {
                        Base randomBase = bases.get(random.nextInt(bases.size()));
                        baseController.addVehicleToBase(randomBase.getId(), vehicle.getId());
                }

                // Mostrar todas las bases
                System.out.println("Lista de bases:");
                baseController.getAllBases().forEach(System.out::println);

                // Mostrar base mas cercana con vehículos
                System.out.println("Base mas cercana:");
                System.out.println(baseController.findNearestBaseWithVehicles(0, 0));

                // Crear un mantenimiento de base
                taskController.createMaintenanceTask("Check brakes", 1);

                // Crear una tarea de reparación
                int vehicleId = 1;
                String pickupLocation = "Central Park";
                String dropOffLocation = "Repair Shop";
                taskController.createRepairTask("Fix flat tire", vehicleId, pickupLocation, dropOffLocation);

                System.out.println("Lista de tareas:");
                taskController.getAllTasks().forEach(System.out::println);

                // Inicializar tarifas para cada tipo de vehículo
                tariffController.defineTariff("bicycle", 5.0, 10.0); // $5 por hora, 10% descuento para premium
                tariffController.defineTariff("motorbike", 10.0, 15.0); // $10 por hora, 15% descuento para premium
                tariffController.defineTariff("skate", 3.0, 5.0); // $3 por hora, 5% descuento para premium

                // Mostrar todas las tarifas
                System.out.println("Lista de tarifas:");
                tariffController.getAllTariffs().forEach((type, tariff) -> System.out.println(tariff));

                // Mostrar todos los vehículos
                System.out.println("Lista de vehículos:");
                vehicleController.getAllVehicles().forEach(System.out::println);

                // Crear un alquiler para cada usuario con un vehículo

                // Obtener usuarios, vehículos y bases
                List<User> users = adminController.getAllUsers();
                List<Base> bases1 = baseController.getAllBases();

                // Usuario 1
                User user1 = users.get(0);
                Base base1 = bases1.get(0); // Central Park
                Vehicle vehicle1 = base1.getVehicles().get(0); // Primer vehículo en la base Central Park
                boolean rentCreated1 = rentController.registerRent(user1.getId(), vehicle1.getId(), base1.getId(), 2);

                if (rentCreated1) {
                        System.out.println("Alquiler creado: Usuario " + user1.getName() + " alquiló el vehículo "
                                        + vehicle1.getType() + " desde la base " + base1.getLocation());
                        vehicle1.setAvailable(false); // Marcar el vehículo como no disponible
                } else {
                        System.out.println("No se pudo crear el alquiler para el usuario " + user1.getName());
                }

                // Mostrar todos los alquileres
                System.out.println("Lista de alquileres:");
                rentController.getAllRents().forEach(System.out::println);

                // Finalizar un alquiler
                int rentId = 1; // ID del alquiler
                int baseId = 2; // ID de la base de destino (Downtown)

                try {
                        boolean rentEnded = rentController.endRent(rentId, baseId);
                        if (rentEnded) {
                                System.out.println("El alquiler con ID " + rentId + " ha finalizado correctamente.");
                        }
                } catch (IllegalArgumentException e) {
                        System.err.println("Error al finalizar el alquiler: " + e.getMessage());
                }

                // Mostrar todos los alquileres
                System.out.println("Lista de alquileres:");
                rentController.getAllRents().forEach(System.out::println);

                System.out.println("Lista de vehículos:");
                vehicleController.getAllVehicles().forEach(System.out::println);

                // Crear 3 clientes con condiciones específicas
                Client client1 = new Client(0, "John Doe", "john002@example.com", "password123", 100.0);
                Client client2 = new Client(0, "Jane Smith", "jane002@example.com", "securepass", 150.0);
                Client client3 = new Client(0, "Alice Johnson", "alice002@example.com", "adminpass", 200.0);

                userService.createUser(client1);
                userService.createUser(client2);
                userService.createUser(client3);

                System.out.println("Lista de usuarios:");
                adminController.getAllUsers().forEach(System.out::println);

                // Crear historial de alquileres para los clientes
                List<Rent> rentRecords = rentService.getAllRents();

                // Simular alquileres para cumplir con las condiciones
                LocalDateTime now = LocalDateTime.now();

                // Cliente 1: Alquila 15 veces en el último mes
                for (int i = 0; i < 15; i++) {
                        rentRecords.add(new Rent(i + 1, vehicleController.findVehicleById(1), client1, now.minusDays(i),
                                        now.minusDays(i).plusHours(2), baseController.findBaseById(1)));
                }

                // Cliente 2: Alquila 10 veces por 3 meses consecutivos
                for (int i = 0; i < 10; i++) {
                        rentRecords.add(new Rent(16 + i, vehicleController.findVehicleById(2), client2,
                                        now.minusMonths(1).minusDays(i), now.minusMonths(1).minusDays(i).plusHours(2),
                                        baseController.findBaseById(2)));
                        rentRecords.add(new Rent(26 + i, vehicleController.findVehicleById(2), client2,
                                        now.minusMonths(2).minusDays(i), now.minusMonths(2).minusDays(i).plusHours(2),
                                        baseController.findBaseById(2)));
                        rentRecords.add(new Rent(36 + i, vehicleController.findVehicleById(2), client2,
                                        now.minusMonths(3).minusDays(i), now.minusMonths(3).minusDays(i).plusHours(2),
                                        baseController.findBaseById(2)));
                }

                // Cliente 3: Usa los tres tipos de vehículos durante 6 meses consecutivos
                for (int i = 0; i < 6; i++) {
                        rentRecords.add(new Rent(46 + i, vehicleController.findVehicleById(3), client3,
                                        now.minusMonths(i), now.minusMonths(i).plusHours(2),
                                        baseController.findBaseById(3)));
                        rentRecords.add(new Rent(52 + i, vehicleController.findVehicleById(4), client3,
                                        now.minusMonths(i), now.minusMonths(i).plusHours(2),
                                        baseController.findBaseById(3)));
                        rentRecords.add(new Rent(58 + i, vehicleController.findVehicleById(5), client3,
                                        now.minusMonths(i), now.minusMonths(i).plusHours(2),
                                        baseController.findBaseById(3)));
                }

                // Mostrar clientes elegibles para ser premium
                List<Client> eligibleClients = clientService.getEligibleForPremium(rentRecords);
                System.out.println("Clientes elegibles para ser premium:");
                eligibleClients.forEach(eligibleClient -> System.out
                                .println(eligibleClient.getName() + " - " + eligibleClient.getEmail()));

                // Promover clientes a premium
                eligibleClients.forEach(eligibleClient -> clientService.upgradeClientToPremium(eligibleClient.getId()));

                // Mostrar clientes después de ser promovidos
                System.out.println("Clientes después de ser promovidos a premium:");
                clientService.getAllClients().forEach(
                                currentClient -> System.out.println(
                                                currentClient.getName() + " - Premium: " + currentClient.isPremium()));

        }
}