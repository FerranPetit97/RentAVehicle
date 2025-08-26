package task;

public abstract class Task {
  private int id;
  private String description;
  private boolean isCompleted;
  private double cost;

  public Task(int id, String description) {
    this.id = id;
    this.description = description;
    this.isCompleted = false;
    this.cost = 0.0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void completeTask() {
    this.isCompleted = true;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "id=" + id + ", description='" + description + "', isCompleted=" + isCompleted + ", cost=" + cost;
  }
}