package vehicle.usage;

import java.time.LocalDateTime;

import user.User;
import vehicle.Vehicle;

public class Usage {
    private int id;
    private Vehicle vehicle;
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Usage(int id, Vehicle vehicle, User user, LocalDateTime startTime) {
        this.id = id;
        this.vehicle = vehicle;
        this.user = user;
        this.startTime = startTime;
        this.endTime = null; // End time is null until the trip ends
    }

    public int getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isInUseDuring(LocalDateTime start, LocalDateTime end) {
        if (endTime == null) {
            return start.isBefore(LocalDateTime.now()) && end.isAfter(startTime);
        }
        return start.isBefore(endTime) && end.isAfter(startTime);
    }

    @Override
    public String toString() {
        return "Usage{id=" + id + ", vehicle=" + vehicle + ", user=" + user +
               ", startTime=" + startTime + ", endTime=" + endTime + "}";
    }
}