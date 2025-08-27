import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

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

        // Inicializar dependencias
        static NotifyService notifyService = new NotifyService();
        static NotifyController notifyController = new NotifyController(notifyService);

        static City city = new City(-100, 100, -100, 100);
        static CityService cityService = new CityService(city);
        static CityController cityController = new CityController(cityService);

        static VehicleService vehicleService = new VehicleService(
                        cityController,
                        new MaintenanceController(new MaintenanceService()),
                        notifyController);
        static VehicleController vehicleController = new VehicleController(vehicleService);

        static BaseService baseService = new BaseService(vehicleController);
        static BaseController baseController = new BaseController(baseService);

        static TariffService tariffService = new TariffService();
        static TariffController tariffController = new TariffController(tariffService);

        static TaskService taskService = new TaskService();
        static TaskController taskController = new TaskController(taskService, baseController);

        static UserService userService = new UserService(notifyController);
        static UserController userController = new UserController(userService);

        static MechanicService mechanicService = new MechanicService(vehicleController, notifyController,
                        userController);
        static MechanicController mechanicController = new MechanicController(mechanicService);

        static ManagerService managerService = new ManagerService(notifyController, userController);
        static ManagerController managerController = new ManagerController(managerService);

        static ClientService clientService = new ClientService(userController, null);
        static RentService rentService = new RentService(clientService, vehicleController, baseController,
                        tariffController);
        static ClientController clientController = new ClientController(clientService);
        static RentController rentController = new RentController(rentService);

        static AdminService adminService = new AdminService(userController, vehicleController, taskController,
                        notifyController, baseController, mechanicController, managerController,
                        tariffController, rentController, clientController);
        static AdminController adminController = new AdminController(adminService);

        static {
                clientService.setRentService(rentService);
        }

        public static void main(String[] args) {

                // Crear un cliente
                Client client = new Client(0, "John Doe", "john@example.com", "password123", 100.0);
                userService.createUser(client);

                // Crear un mecánico
                Mechanic mechanic = new Mechanic(0, "Jane Smith", "jane@example.com", "securepass");
                userService.createUser(mechanic);

                // Crear un gerente
                Manager manager = new Manager(0, "Alice Johnson", "alice@example.com", "adminpass");
                userService.createUser(manager);

                // Agregar vehículos
                vehicleController.addVehicle("bicycle");
                vehicleController.addVehicle("bicycle");
                vehicleController.addVehicle("motorbike");
                vehicleController.addVehicle("motorbike");
                vehicleController.addVehicle("skate");
                vehicleController.addVehicle("skate");

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

                // Crear un mantenimiento de base
                taskController.createMaintenanceTask("Check brakes", 1);

                // Crear una tarea de reparación
                int vehicleId = 1;
                String pickupLocation = "Central Park";
                String dropOffLocation = "Repair Shop";
                taskController.createRepairTask("Fix flat tire", vehicleId, pickupLocation, dropOffLocation);

                // Inicializar tarifas para cada tipo de vehículo
                tariffController.defineTariff("bicycle", 5.0, 10.0); // $5 por hora, 10% descuento para premium
                tariffController.defineTariff("motorbike", 10.0, 15.0); // $10 por hora, 15% descuento para premium
                tariffController.defineTariff("skate", 3.0, 5.0); // $3 por hora, 5% descuento para premium

                List<User> users = adminController.getAllUsers();
                List<Base> bases1 = baseController.getAllBases();

                User user1 = users.get(0);
                Base base1 = bases1.get(0);

                Optional<Vehicle> maybeVehicle = base1.getVehicles().stream()
                                .filter(v -> v.isAvailable() && v.hasNoDamage())
                                .findFirst();

                if (maybeVehicle.isPresent()) {
                        Vehicle vehicle1 = maybeVehicle.get();

                        boolean rentCreated1 = rentController.registerRent(
                                        user1.getId(),
                                        vehicle1.getId(),
                                        base1.getId(),
                                        2);

                        if (rentCreated1) {
                                System.out.println("Alquiler creado: Usuario " + user1.getName()
                                                + " alquiló el vehículo " + vehicle1.getType()
                                                + " desde la base " + base1.getLocation());
                                vehicle1.setAvailable(false); // Marcar el vehículo como no disponible
                        } else {
                                System.out.println(
                                                "No se pudo crear el alquiler para el usuario " + user1.getName());
                        }

                } else {
                        System.out.println("No hay vehículos disponibles en la base " + base1.getLocation());
                }

                int rentId = 1;
                int baseId = 2;

                try {
                        boolean rentEnded = rentController.endRent(rentId, baseId);
                        if (rentEnded) {
                                System.out.println("El alquiler con ID " + rentId + " ha finalizado correctamente.");
                        }
                } catch (IllegalArgumentException e) {
                        System.err.println("Error al finalizar el alquiler: " + e.getMessage());
                }

                // Crear 3 clientes con condiciones específicas
                Client client1 = new Client(0, "John Doe", "john002@example.com", "password123", 100.0);
                Client client2 = new Client(0, "Jane Smith", "jane002@example.com", "securepass", 150.0);
                Client client3 = new Client(0, "Alice Johnson", "alice002@example.com", "adminpass", 200.0);

                userService.createUser(client1);
                userService.createUser(client2);
                userService.createUser(client3);

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

                menu();

        }

        private static void menu() {

                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú principal ===");
                        System.out.println("1. Users");
                        System.out.println("2. Vehículos");
                        System.out.println("3. Reparaciones");
                        System.out.println("4. Alquileres");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Mostrando opciones de usuario...");
                                        usersMenu();
                                        break;
                                case 2:
                                        System.out.println("Mostrando opciones de vehículos...");
                                        vehiclesMenu();
                                        break;
                                case 3:
                                        System.out.println("Mostrando opciones de reparaciones...");
                                        repairsMenu();
                                        break;
                                case 4:
                                        System.out.println("Mostrando opciones de alquileres...");
                                        rentsMenu();
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Cerrando aplicación.");
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }

        public static void usersMenu() {
                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú de Usuario ===");
                        System.out.println("1. Ver usuarios");
                        System.out.println("2. Modificar usuario");
                        System.out.println("3. Borrar usuario");
                        System.out.println("4. Ver clientes elegibles para premium");
                        System.out.println("5. Promover clientes a premium");
                        System.out.println("0. Volver al menú principal");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Mostrando usuarios...");
                                        System.out.println("Lista de usuarios:");
                                        adminController.getAllUsers().forEach(System.out::println);
                                        break;
                                case 2:
                                        System.out.println("Editar usuario...");

                                        System.out.print("Ingrese el ID del usuario a modificar: ");
                                        int idToUpdate = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Nuevo nombre: ");
                                        String newName = sc.nextLine();

                                        System.out.print("Nuevo email: ");
                                        String newEmail = sc.nextLine();

                                        System.out.print("Nueva contraseña: ");
                                        String newPassword = sc.nextLine();

                                        System.out.print("Nuevo rol: ");
                                        String newRole = sc.nextLine();

                                        System.out.print("Nuevo vehicleId (solo si es mecánico, si no ponga -1): ");
                                        int newVehicleId = sc.nextInt();
                                        sc.nextLine();

                                        boolean updated = userController.updateUser(
                                                        idToUpdate,
                                                        newName,
                                                        newEmail,
                                                        newPassword,
                                                        newRole,
                                                        newVehicleId);

                                        if (updated) {
                                                System.out.println("Usuario actualizado correctamente.");
                                        } else {
                                                System.out.println("No se pudo actualizar el usuario con ID "
                                                                + idToUpdate);
                                        }
                                        break;
                                case 3:
                                        System.out.print("Ingrese el ID del usuario a borrar: ");
                                        int idToDelete = sc.nextInt();
                                        sc.nextLine();

                                        boolean deleted = userController.deleteUserById(idToDelete);

                                        if (deleted) {
                                                System.out.println("Usuario con ID " + idToDelete
                                                                + " eliminado correctamente.");
                                        } else {
                                                System.out.println("No se encontró un usuario con ID " + idToDelete);
                                        }
                                        break;
                                case 4:
                                        System.out.println("Clientes elegibles para ser premium:");
                                        clientService.getEligibleForPremium().forEach(eligibleClient -> System.out
                                                        .println(eligibleClient.getName() + " - "
                                                                        + eligibleClient.getEmail()));
                                        break;
                                case 5:
                                        clientService.getEligibleForPremium().forEach(eligibleClient -> clientService
                                                        .upgradeClientToPremium(eligibleClient.getId()));
                                        System.out.println("Clientes promovidos a premium.");
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Volviendo al menú principal.");
                                        menu();
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }

        public static void vehiclesMenu() {
                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú de Vehículos ===");
                        System.out.println("1. Visualizar vehículos");
                        System.out.println("2. Mostrar estado de las baterías");
                        System.out.println("3. Vehículos en uso");
                        System.out.println("4. Quitar vehículo del sistema");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Lista de vehículos:");
                                        vehicleController.getAllVehicles().forEach(System.out::println);
                                        break;
                                case 2:
                                        System.out.println("Estado de las baterías:");
                                        vehicleController.getVehiclesAndBatteryLevels()
                                                        .forEach(System.out::println);
                                        break;
                                case 3:
                                        System.out.println("Vehículos en uso:");
                                        rentController.getVehiclesInUse().forEach(System.out::println);
                                        break;
                                case 4:
                                        System.out.println("Que vehiculo quieres eliminar por su ID?");
                                        int vehicleId = sc.nextInt();
                                        sc.nextLine();

                                        boolean removed = vehicleController.removeVehicle(vehicleId);
                                        if (removed) {
                                                System.out.println("Vehículo con ID " + vehicleId
                                                                + " eliminado correctamente.");
                                        } else {
                                                System.out.println("No se encontró un vehículo con ID " + vehicleId);
                                        }
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Volviendo al menú principal.");
                                        menu();
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }

        public static void repairsMenu() {
                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú de Reparaciones ===");
                        System.out.println("1. Visualizar reparaciones");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Lista de tareas:");
                                        taskController.getAllTasks().forEach(System.out::println);
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Volviendo al menú principal.");
                                        menu();
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }

        public static void basesMenu() {
                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú de Bases ===");
                        System.out.println("1. Visualizar bases");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Lista de bases:");
                                        baseController.getAllBases().forEach(System.out::println);
                                        break;
                                case 2:
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Volviendo al menú principal.");
                                        menu();
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }

        public static void rentsMenu() {
                Scanner sc = new Scanner(System.in);
                boolean running = true;

                while (running) {
                        System.out.println("\n=== Menú de Alquileres ===");
                        System.out.println("1. Visualizar alquileres");
                        System.out.println("2. Alquilar vehículo");
                        System.out.println("3. Devolver vehículo");
                        System.out.println("4. Crear incidencia");
                        System.out.println("0. Salir");
                        System.out.print("Seleccione opción: ");

                        int option = sc.nextInt();
                        sc.nextLine();

                        switch (option) {
                                case 1:
                                        System.out.println("Lista de alquileres:");
                                        rentController.getAllRents().forEach(System.out::println);
                                        break;
                                case 2:
                                        System.out.println("Alquilar vehículo:");
                                        System.out.print("Ingrese su ID de cliente: ");
                                        int clientId = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Ingrese el ID del vehículo a alquilar: ");
                                        int vehicleId = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Ingrese el ID de la base de inicio: ");
                                        int baseId = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Ingrese la duración del alquiler en horas: ");
                                        int duration = sc.nextInt();
                                        sc.nextLine();
                                        boolean rentCreated = rentController.registerRent(clientId, vehicleId, baseId,
                                                        duration);
                                        if (rentCreated) {
                                                System.out.println("Alquiler creado correctamente.");
                                        } else {
                                                System.out.println("No se pudo crear el alquiler.");
                                        }
                                        break;
                                case 3:
                                        System.out.println("Devolver vehículo:");
                                        System.out.print("Ingrese el ID del alquiler a finalizar: ");
                                        int rentId = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Ingrese el ID de la base de devolución: ");
                                        int returnBaseId = sc.nextInt();
                                        sc.nextLine();
                                        try {
                                                boolean rentEnded = rentController.endRent(rentId, returnBaseId);
                                                if (rentEnded) {
                                                        System.out.println("Alquiler finalizado correctamente.");
                                                }
                                        } catch (IllegalArgumentException e) {
                                                System.err.println("Error al finalizar el alquiler: " + e.getMessage());
                                        }
                                        break;

                                case 4:
                                        System.out.println("Crear incidencia...");
                                        int vehicleIdTask = sc.nextInt();
                                        sc.nextLine();
                                        String issueDescription = sc.nextLine();
                                        sc.nextLine();
                                        taskController.createRepairTask(issueDescription, vehicleIdTask, null, null);
                                        break;
                                case 0:
                                        running = false;
                                        System.out.println("Volviendo al menú principal.");
                                        menu();
                                        break;
                                default:
                                        System.out.println("Opción no válida.");
                        }
                }

                sc.close();
        }
}