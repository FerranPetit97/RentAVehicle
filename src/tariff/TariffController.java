package tariff;

import java.util.Map;

public class TariffController {
  private final TariffService tariffService;

  public TariffController(TariffService tariffService) {
    this.tariffService = tariffService;
  }

  public boolean defineTariff(String vehicleType, double baseRate, double premiumDiscount) {
    return tariffService.addOrUpdateTariff(vehicleType, baseRate, premiumDiscount);
  }

  public Tariff getTariff(String vehicleType) {
    return tariffService.getTariff(vehicleType);
  }

  public Map<String, Tariff> getAllTariffs() {
    return tariffService.getAllTariffs();
  }
}