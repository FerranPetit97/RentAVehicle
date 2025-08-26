package vehicle.rental;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tariff.TariffController;

import user.client.Client;
import user.client.ClientController;

import vehicle.Vehicle;
import vehicle.VehicleController;
import vehicle.base.Base;
import vehicle.base.BaseController;

public class RentService {
    private final List<Rent> rentRecords = new ArrayList<>();
    private int nextId = 1;

    private final ClientController clientController;
    private final VehicleController vehicleController;
    private final BaseController baseController;
    private final TariffController tariffController;

    public RentService(ClientController clientController, VehicleController vehicleController,
            BaseController baseController, TariffController tariffController) {
        this.clientController = clientController;
        this.vehicleController = vehicleController;
        this.baseController = baseController;
        this.tariffController = tariffController;
    }

    public boolean startRent(int clientId, int vehicleId, int baseId, int duration) {
        Client client = clientController.findClientById(clientId);
        Vehicle vehicle = vehicleController.findVehicleById(vehicleId);
        Base startBase = baseController.findBaseById(baseId);
        if (!client.hasPositiveBalance()) {
            throw new IllegalArgumentException("El usuario no tiene saldo suficiente.");
        }
        if (!vehicle.isAvailable()) {
            throw new IllegalArgumentException("El vehículo no está disponible.");
        }
        if (!startBase.removeVehicle(vehicle)) {
            throw new IllegalArgumentException("El vehículo no se pudo retirar de la base.");
        }
        if (!userHaveBalance(client, vehicle, LocalDateTime.now(), LocalDateTime.now().plusHours(duration))) {
            throw new IllegalArgumentException("El usuario no tiene saldo suficiente para el alquiler.");
        }
        vehicle.setAvailable(false);
        Rent rental = new Rent(nextId++, vehicle, client, LocalDateTime.now(), LocalDateTime.now().plusHours(duration),
                startBase);
        rentRecords.add(rental);
        return true;
    }

    public boolean endRent(int rentId, int baseId) {
        Rent rent = rentRecords.stream()
                .filter(r -> r.getId() == rentId)
                .findFirst()
                .orElse(null);

        if (rent == null) {
            throw new IllegalArgumentException("El alquiler con ID " + rentId + " no existe.");
        }

        Vehicle vehicle = rent.getVehicle();
        Client client = (Client) rent.getUser();
        Base endBase = baseController.findBaseById(baseId);

        if (endBase == null) {
            throw new IllegalArgumentException("La base con ID " + baseId + " no existe.");
        }

        // Calcular el coste del viaje
        int hoursRented = calculateHoursRented(rent.getStartTime(), rent.getEndTime());
        double cost = tariffController.getTariff(vehicle.getType())
                .calculateCost(hoursRented, client.isPremium());
        rent.setCost(cost);

        client.setBalance(client.getBalance() - cost);

        // Restaurar el vehículo
        vehicle.setAvailable(true);
        vehicle.setX(endBase.getX());
        vehicle.setY(endBase.getY());

        vehicle.setBatteryLevel(vehicleController.calculateBatteryAfterHours(vehicle, hoursRented));

        // Añadir el vehículo a la base
        if (!endBase.addVehicle(vehicle)) {
            throw new IllegalArgumentException("No se pudo añadir el vehículo a la base " + endBase.getLocation());
        }

        return true;
    }

    public List<Rent> getAllRents() {
        return rentRecords;
    }

    private int calculateHoursRented(LocalDateTime startTime, LocalDateTime endTime) {
        return (int) (java.time.Duration.between(startTime, endTime)).toHours();
    }

    private boolean userHaveBalance(Client client, Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        double cost = tariffController.getTariff(vehicle.getType())
                .calculateCost(calculateHoursRented(startTime, endTime), client.isPremium());
        if (client.getBalance() < cost) {
            return false;
        }
        return true;
    }

    public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
        List<Vehicle> vehiclesInUse = new ArrayList<>();
        for (Rent rent : rentRecords) {
            if (rent.isInUseDuring(start, end)) {
                vehiclesInUse.add(rent.getVehicle());
            }
        }
        return vehiclesInUse;
    }
}