package city;

public class CityFactory {
  private static CityController instance;

  public static synchronized CityController getInstance(City city) {
    if (instance == null) {
      CityService cityService = new CityService(city);
      instance = new CityController(cityService);
    }
    return instance;
  }
}
