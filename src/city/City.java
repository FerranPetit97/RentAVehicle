package city;

public class City {
  private final int westLimit;  // Límite oeste (x mínimo)
  private final int eastLimit;  // Límite este (x máximo)
  private final int southLimit; // Límite sur (y mínimo)
  private final int northLimit; // Límite norte (y máximo)

  public City(int westLimit, int eastLimit, int southLimit, int northLimit) {
    this.westLimit = westLimit;
    this.eastLimit = eastLimit;
    this.southLimit = southLimit;
    this.northLimit = northLimit;
  }

  public boolean isWithinLimits(int x, int y) {
    return x >= westLimit && x <= eastLimit && y >= southLimit && y <= northLimit;
  }

  @Override
  public String toString() {
    return "City{westLimit=" + westLimit + ", eastLimit=" + eastLimit +
           ", southLimit=" + southLimit + ", northLimit=" + northLimit + "}";
  }
}