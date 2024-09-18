package main.java.menu;

import main.java.service.*;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private ReservationService reservationService;
    private PersonService personService;
    private EventService eventService;
    private HotelService hotelService;
    private PricingService pricingService;
    private SeasonService seasonService;
    private StatisticService statisticService;

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.reservationService = new ReservationService();
        this.personService = new PersonService();
        this.eventService = new EventService();
        this.hotelService = new HotelService();
        this.pricingService = new PricingService();
        this.seasonService = new SeasonService();
        this.statisticService = new StatisticService();
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. Reservations");
            System.out.println("2. Clients");
            System.out.println("3. Employees");
            System.out.println("4. Events");
            System.out.println("5. Hotels");
            System.out.println("6. Pricing");
            System.out.println("7. Seasons");
            System.out.println("8. Statistics");
            System.out.println("9. Exit");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    new ReservationMenu(reservationService).showReservationMenu();
                    break;
                case 2:
                    new ClientMenu(personService).clientMenu();
                    break;
                case 3:
                    new EmployeeMenu(personService).employeeMenu();
                    break;
                case 4:
                    new EventMenu(eventService).eventMenu();
                    break;
                case 5:
                    new HotelMenu(hotelService).hotelMenu();
                    break;
                case 6:
                    new PricingMenu(pricingService).pricingMenu();
                    break;
                case 7:
                    new SeasonMenu(seasonService).showSeasonMenu();
                    break;
                case 8:
                    new StatisticMenu(statisticService).showStatisticMenu();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;  // Exit the application
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
