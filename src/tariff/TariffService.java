package tariff;

import java.util.HashMap;
import java.util.Map;

public class TariffService {
  private final Map<String, Tariff> tariffs;

  public TariffService() {
    this.tariffs = new HashMap<>();
  }

  public boolean addOrUpdateTariff(String vehicleType, double baseRate, double premiumDiscount) {
    Tariff tariff = new Tariff(vehicleType, baseRate, premiumDiscount);
    tariffs.put(vehicleType.toLowerCase(), tariff);
    return true;
  }

  public Tariff getTariff(String vehicleType) {
    return tariffs.get(vehicleType.toLowerCase());
  }

  public Map<String, Tariff> getAllTariffs() {
    return tariffs;
  }
}