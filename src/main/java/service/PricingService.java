package main.java.service;

import main.java.entities.Event;
import main.java.entities.Pricing;
import main.java.entities.Season;
import main.java.enums.RoomType;
import main.java.dao.impl.PricingDaoImpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Scanner;

public class PricingService {

    private PricingDaoImpl pricingDaoImpl;
    private Scanner scanner;

    public PricingService() {
        this.pricingDaoImpl = new PricingDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void savePricing() {
        System.out.println("Enter Room Type (e.g., SINGLE, DOUBLE, SUITE): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());

        System.out.println("Enter Season ID (or leave blank if not applicable): ");
        String seasonInput = scanner.nextLine();
        Season season = null;
        if (!seasonInput.isEmpty()) {
            season = new Season(Integer.parseInt(seasonInput), "Season Name", null, null);
        }


        System.out.println("Enter Event ID (or leave blank if not applicable): ");
        String eventInput = scanner.nextLine();
        Event event = eventInput.isEmpty() ? null : new Event(Integer.parseInt(eventInput), "Event Name", new Date(System.currentTimeMillis()));

        System.out.println("Enter Base Price: ");
        BigDecimal basePrice = scanner.nextBigDecimal();

        System.out.println("Enter Price Multiplier (e.g., 1.5 for 50% increase): ");
        BigDecimal priceMultiplier = scanner.nextBigDecimal();

        scanner.nextLine();

        System.out.println("Enter Start Date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter End Date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        Pricing pricing = new Pricing(0, roomType, season, event, basePrice, priceMultiplier, startDate, endDate);
        pricingDaoImpl.savePricing(pricing);
        System.out.println("Pricing saved successfully.");
    }


    public void updatePricing() {
        System.out.println("Enter Pricing ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter Room Type (e.g., SINGLE, DOUBLE, SUITE): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());

        System.out.println("Enter Season ID (or leave blank if not applicable): ");
        String seasonInput = scanner.nextLine();
        Season season = null;
        if (!seasonInput.isEmpty()) {
            season = new Season(Integer.parseInt(seasonInput), "Season Name", null, null);
        }


        System.out.println("Enter Event ID (or leave blank if not applicable): ");
        String eventInput = scanner.nextLine();
        Event event = eventInput.isEmpty() ? null : new Event(Integer.parseInt(eventInput), "Event Name", new Date(System.currentTimeMillis()));

        System.out.println("Enter Base Price: ");
        BigDecimal basePrice = scanner.nextBigDecimal();

        System.out.println("Enter Price Multiplier (e.g., 1.5 for 50% increase): ");
        BigDecimal priceMultiplier = scanner.nextBigDecimal();

        scanner.nextLine();  // Clear buffer

        System.out.println("Enter Start Date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter End Date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        Pricing pricing = new Pricing(id, roomType, season, event, basePrice, priceMultiplier, startDate, endDate);
        pricingDaoImpl.updatePricing(pricing);
        System.out.println("Pricing updated successfully.");
    }

    public void deletePricing() {
        System.out.println("Enter Pricing ID: ");
        int id = scanner.nextInt();
        pricingDaoImpl.deletePricing(id);
        System.out.println("Pricing deleted successfully.");
    }

    public Pricing getPricingById() {
        System.out.println("Enter Pricing ID: ");
        int id = scanner.nextInt();
        Pricing pricing = pricingDaoImpl.getPricingById(id);
        System.out.println(pricing);
        return pricing;
    }
}
