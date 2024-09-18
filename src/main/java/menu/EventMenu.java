package main.java.menu;

import main.java.service.EventService;

import java.util.Scanner;

public class EventMenu {
    private Scanner scanner;
    private EventService eventService;

    public EventMenu(EventService eventService) {
        this.scanner = new Scanner(System.in);
        this.eventService = eventService;
    }

    public void eventMenu() {
        while (true) {
            System.out.println("Event Menu:");
            System.out.println("1. Add Event");
            System.out.println("2. Update Event");
            System.out.println("3. Delete Event");
            System.out.println("4. Show Event By Id");
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
                    eventService.saveEvent();
                    break;
                case 2:
                    eventService.updateEvent();
                    break;
                case 3:
                    eventService.deleteEvent();
                    break;
                case 4:
                    eventService.getEventById();
                    break;
                case 5:
                    return; // Go back to the previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
