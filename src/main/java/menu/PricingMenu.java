package main.java.menu;

import main.java.service.PricingService;

import java.util.Scanner;

public class PricingMenu {
    private Scanner scanner;
    private PricingService pricingService;

    public PricingMenu(PricingService pricingService) {
        this.scanner = new Scanner(System.in);
        this.pricingService = pricingService;
    }

    public void pricingMenu() {
        while (true) {
            System.out.println("Pricing Menu:");
            System.out.println("1. Add Pricing");
            System.out.println("2. Update Pricing");
            System.out.println("3. Delete Pricing");
            System.out.println("4. Show Pricing By Id");
            System.out.println("5. Back to Main Menu");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    pricingService.savePricing();
                    break;
                case 2:
                    pricingService.updatePricing();
                    break;
                case 3:
                    pricingService.deletePricing();
                    break;
                case 4:
                    pricingService.getPricingById();
                    break;
                case 5:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

