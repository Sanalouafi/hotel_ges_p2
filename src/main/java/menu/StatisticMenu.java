package main.java.menu;

import main.java.service.StatisticService;

import java.sql.Date;
import java.util.Scanner;

public class StatisticMenu {

    private StatisticService statisticService;
    private Scanner scanner;

    public StatisticMenu(StatisticService statisticService) {
        this.statisticService = statisticService;
        this.scanner = new Scanner(System.in);
    }

    public void showStatisticMenu() {
        while (true) {
            System.out.println("=== Statistic Menu ===");
            System.out.println("1. Total Reservations");
            System.out.println("2. Total Revenue");
            System.out.println("3. Occupancy Rate");
            System.out.println("4. Reserved Count");
            System.out.println("5. Cancelled Count");
            System.out.println("6. Back to Main Menu");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Total Reservations: " + statisticService.getReservationCount());
                    break;
                case 2:
                    System.out.println("Total Revenue: " + statisticService.calculateTotalRevenue());
                    break;
                case 3:
                    System.out.print("Enter start date (yyyy-mm-dd): ");
                    Date startDate = Date.valueOf(scanner.nextLine());
                    System.out.print("Enter end date (yyyy-mm-dd): ");
                    Date endDate = Date.valueOf(scanner.nextLine());
                    System.out.println("Occupancy Rate: " + statisticService.calculateOccupancyRate(startDate, endDate) + "%");
                    break;
                case 4:
                    System.out.println("Reserved Count: " + statisticService.getReservedCount());
                    break;
                case 5:
                    System.out.println("Cancelled Count: " + statisticService.getCancelledCount());
                    break;
                case 6:
                    return;  // Go back to the main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
