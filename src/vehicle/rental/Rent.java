package vehicle.rental;

import java.time.LocalDateTime;

import user.User;
import vehicle.Vehicle;
import vehicle.base.Base;

public class Rent {
    private int id;
    private Vehicle vehicle;
    private User user;
    private Base startBase;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double cost;

    public Rent(int id, Vehicle vehicle, User user, LocalDateTime startTime, LocalDateTime endTime, Base startBase) {
        this.id = id;
        this.vehicle = vehicle;
        this.user = user;
        this.startBase = startBase;
        this.endTime = endTime;
        this.startTime = startTime;
        this.cost = 0.0;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        return "Rent{id=" + id + ", vehicle=" + vehicle + ", user=" + user +
                ", startTime=" + startTime + ", endTime=" + endTime + ", cost=" + cost + "}";
    }
}