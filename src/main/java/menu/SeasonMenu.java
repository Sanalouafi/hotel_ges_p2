package main.java.menu;

import main.java.service.SeasonService;
import main.java.entities.Season;

import java.sql.Date;
import java.util.Scanner;

public class SeasonMenu {

    private SeasonService seasonService;
    private Scanner scanner;

    public SeasonMenu(SeasonService seasonService) {
        this.seasonService = seasonService;
        this.scanner = new Scanner(System.in);
    }

    public void showSeasonMenu() {
        while (true) {
            System.out.println("=== Season Menu ===");
            System.out.println("1. Save Season");
            System.out.println("2. Update Season");
            System.out.println("3. Delete Season");
            System.out.println("4. View Season by ID");
            System.out.println("5. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    seasonService.saveSeason();
                    break;
                case 2:
                    updateSeason();
                    break;
                case 3:
                    deleteSeason();
                    break;
                case 4:
                    viewSeasonById();
                    break;
                case 5:
                    return;  // Back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateSeason() {
        System.out.println("Enter Season ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter new Season name: ");
        String name = scanner.nextLine();

        System.out.println("Enter new Season start date (YYYY-MM-DD): ");
        Date startDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter new Season end date (YYYY-MM-DD): ");
        Date endDate = Date.valueOf(scanner.nextLine());

        seasonService.updateSeason(id, name, startDate, endDate);
    }

    private void deleteSeason() {
        System.out.println("Enter Season ID to delete: ");
        int id = scanner.nextInt();
        seasonService.deleteSeason(id);
    }

    private void viewSeasonById() {
        System.out.println("Enter Season ID to view: ");
        int id = scanner.nextInt();
        Season season = seasonService.getSeasonById(id);
        if (season != null) {
            System.out.println("Season ID: " + season.getId());
            System.out.println("Season Name: " + season.getName());
            System.out.println("Start Date: " + season.getStartDate());
            System.out.println("End Date: " + season.getEndDate());
        } else {
            System.out.println("Season not found.");
        }
    }
}
