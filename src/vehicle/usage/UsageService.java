package vehicle.usage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import user.User;
import vehicle.Vehicle;

public class UsageService {
    private final List<Usage> usageRecords = new ArrayList<>();
    private int nextId = 1;

    public Usage registerUsage(Vehicle vehicle, User user) {
        Usage usage = new Usage(nextId++, vehicle, user, LocalDateTime.now());
        usageRecords.add(usage);
        return usage;
    }

    public boolean endUsage(int usageId) {
        for (Usage usage : usageRecords) {
            if (usage.getId() == usageId && usage.getEndTime() == null) {
                usage.setEndTime(LocalDateTime.now());
                return true;
            }
        }
        return false; // Usage not found or already ended
    }

    public List<Usage> getAllUsages() {
        return usageRecords;
    }

    public List<Usage> getUsagesByUser(User user) {
        List<Usage> userUsages = new ArrayList<>();
        for (Usage usage : usageRecords) {
            if (usage.getUser().equals(user)) {
                userUsages.add(usage);
            }
        }
        return userUsages;
    }

    public List<Usage> getUsagesByVehicle(Vehicle vehicle) {
        List<Usage> vehicleUsages = new ArrayList<>();
        for (Usage usage : usageRecords) {
            if (usage.getVehicle().equals(vehicle)) {
                vehicleUsages.add(usage);
            }
        }
        return vehicleUsages;
    }

    public List<Vehicle> getVehiclesInUseDuring(LocalDateTime start, LocalDateTime end) {
        List<Vehicle> vehiclesInUse = new ArrayList<>();
        for (Usage usage : usageRecords) {
            if (usage.isInUseDuring(start, end)) {
                vehiclesInUse.add(usage.getVehicle());
            }
        }
        return vehiclesInUse;
    }
}