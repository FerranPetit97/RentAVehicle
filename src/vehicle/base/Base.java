package vehicle.base;

import java.util.ArrayList;
import java.util.List;

import vehicle.Vehicle;

public class Base {
  private int id;
  private String location;
  private int capacity;
  private List<Vehicle> vehicles;
  private int x;
  private int y;

  public Base(int id, String location, int capacity, int x, int y) {
    this.id = id;
    this.location = location;
    this.capacity = capacity;
    this.x = x;
    this.y = y;
    this.vehicles = new ArrayList<>();
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

  public void setCapacity(int capacity) {
    this.capacity = capacity;
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

  public boolean addVehicle(Vehicle vehicle) {
    if (vehicles.size() < capacity) {
      return vehicles.add(vehicle);
    }
    return false;
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