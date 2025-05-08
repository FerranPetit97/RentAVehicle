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
    private Base endBase;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double cost;

    public Rent(int id, Vehicle vehicle, User user, LocalDateTime startTime, Base startBase) {
        this.id = id;
        this.vehicle = vehicle;
        this.user = user;
        this.startBase = startBase;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
        return "{id=" + id + ", vehicle=" + vehicle + ", user=" + user +
                ", startTime=" + startTime + ", endTime=" + endTime + ", cost=" + cost + "}";
    }
}