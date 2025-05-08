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

    public boolean registerRent(int clientId, int vehicleId, int baseId) {
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
        Rent rental = new Rent(nextId++, vehicle, client, LocalDateTime.now(), startBase);
        rentRecords.add(rental);
        return true;
    }

    public boolean endRent(int rentId) {
        for (Rent rent : rentRecords) {
            if (rent.getId() == rentId && rent.getEndTime() == null) {
                rent.setEndTime(LocalDateTime.now().plusHours(2));
                rent.setCost(tariffController.getTariff(rent.getVehicle().getType())
                        .calculateCost(calculateHoursRented(rent), false));
                return true;
            }
        }
        return false;
    }

    public List<Rent> getAllRents() {
        return rentRecords;
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

    private int calculateHoursRented(Rent rent) {
        return (int) (java.time.Duration.between(rent.getStartTime(), rent.getEndTime()).toHours());
    }
}