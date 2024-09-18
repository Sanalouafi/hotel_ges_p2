package main.java.dao;

import main.java.entities.Pricing;
import java.util.List;

public interface PricingDao {
    List<Pricing> getAllPricings();
    Pricing getPricingById(int pricingId);
    void savePricing(Pricing pricing);
    void updatePricing(Pricing pricing);
    void deletePricing(int pricingId);
}
