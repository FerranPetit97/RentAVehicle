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
  private boolean isBroken;
  private int mechanicId;

  public Base(int id, String location, int capacity, int x, int y, int mechanicId) {
    this.id = id;
    this.location = location;
    this.capacity = capacity;
    this.x = x;
    this.y = y;
    this.vehicles = new ArrayList<>();
    this.isBroken = false;
    this.mechanicId = mechanicId;
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

  public int getMechanicId() {
    return mechanicId;
  }

  public void setMechanicId(int mechanicId) {
    this.mechanicId = mechanicId;
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