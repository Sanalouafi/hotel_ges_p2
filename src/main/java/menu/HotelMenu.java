package main.java.menu;
import main.java.service.HotelService;

import java.util.Scanner;

public class HotelMenu {
    private Scanner scanner;
    private HotelService hotelService;

    public HotelMenu(HotelService hotelService) {
        this.scanner = new Scanner(System.in);
        this.hotelService = hotelService;
    }

    public void hotelMenu() {
        while (true) {
            System.out.println("Hotel Menu:");
            System.out.println("1. Add Hotel");
            System.out.println("2. Update Hotel");
            System.out.println("3. Delete Hotel");
            System.out.println("4. Show Hotel By Id");
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
                    hotelService.saveHotel();
                    break;
                case 2:
                    hotelService.updateHotel();
                    break;
                case 3:
                    hotelService.deleteHotel();
                    break;
                case 4:
                    hotelService.getHotelById();
                    break;
                case 5:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

