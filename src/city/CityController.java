package city;

public class CityController {
  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  public boolean isLocationWithinCityLimits(int x, int y) {
    return cityService.isLocationWithinCityLimits(x, y);
  }

  public City getCityDetails() {
    return cityService.getCityDetails();
  }
}