package city;

public class CityService {
  private final City city;

  public CityService(City city) {
    this.city = city;
  }

  public boolean isLocationWithinCityLimits(int x, int y) {
    return city.isWithinLimits(x, y);
  }

  public City getCityDetails() {
    return city;
  }
}