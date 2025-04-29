package vehicle.base;

import java.util.ArrayList;
import java.util.List;

import vehicle.Vehicle;

public class Base {
  private int id;
  private String location;
  private int capacity;
  private List<Vehicle> vehicles;
  private int x; // Coordenada x
  private int y; // Coordenada y
  private boolean isBroken; // Indica si el vehículo está averiado

  public Base(int id, String location, int capacity, int x, int y) {
    this.id = id;
    this.location = location;
    this.capacity = capacity;
    this.x = x;
    this.y = y;
    this.vehicles = new ArrayList<>();
    this.isBroken = false; // Por defecto, el vehículo no está averiado
  }

  public int getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  public int getCapacity() {
    return capacity;
  }

  public List<Vehicle> getVehicles() {
    return vehicles;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isBroken() {
    return isBroken;
  }

  public void setBroken(boolean broken) {
    isBroken = broken;
  }

  public boolean addVehicle(Vehicle vehicle) {
    if (vehicles.size() < capacity) {
      return vehicles.add(vehicle);
    }
    return false; // Base is full
  }

  public boolean removeVehicle(Vehicle vehicle) {
    return vehicles.remove(vehicle);
  }

  @Override
  public String toString() {
    return "Base{id=" + id + ", location='" + location + "', capacity=" + capacity +
        ", vehicles=" + vehicles + "}";
  }
}