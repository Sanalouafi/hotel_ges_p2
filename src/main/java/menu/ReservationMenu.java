package main.java.menu;

import main.java.enums.RoomType;
import main.java.service.ReservationService;
import main.java.entities.Reservation;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ReservationMenu {

    private ReservationService reservationService;
    private Scanner scanner;

    public ReservationMenu(ReservationService reservationService) {
        this.reservationService = reservationService;
        this.scanner = new Scanner(System.in);
    }

    public void showReservationMenu() {
        while (true) {
            System.out.println("=== Reservation Menu ===");
            System.out.println("1. Save Reservation");
            System.out.println("2. Update Reservation");
            System.out.println("3. Delete Reservation");
            System.out.println("4. View Reservation by ID");
            System.out.println("5. View Client Reservations");
            System.out.println("6. View Reservations by Room Type");
            System.out.println("7. Back to Main Menu");

            int choice = getValidIntegerInput("Please enter your choice: ");

            switch (choice) {
                case 1:
                    saveReservation();
                    break;
                case 2:
                    updateReservation();
                    break;
                case 3:
                    deleteReservation();
                    break;
                case 4:
                    viewReservationById();
                    break;
                case 5:
                    viewClientReservations();
                    break;
                case 6:
                    viewReservationsByRoomType();
                    break;
                case 7:
                    return;  // Back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void saveReservation() {
        int clientId = getValidIntegerInput("Enter Client ID: ");
        int hotelId = getValidIntegerInput("Enter Hotel ID: ");
        int roomId = getValidIntegerInput("Enter Room ID: ");
        Date checkInDate = getValidDateInput("Enter Check-in Date (YYYY-MM-DD): ");
        Date checkOutDate = getValidDateInput("Enter Check-out Date (YYYY-MM-DD): ");

        if (checkInDate.after(checkOutDate)) {
            System.out.println("Check-in date cannot be after check-out date.");
            return;
        }

        reservationService.saveReservation(clientId, hotelId, roomId, checkInDate, checkOutDate);
    }


    private void updateReservation() {
        int id = getValidIntegerInput("Enter Reservation ID to update: ");
        int clientId = getValidIntegerInput("Enter new Client ID: ");
        int hotelId = getValidIntegerInput("Enter new Hotel ID: ");
        int roomId = getValidIntegerInput("Enter new Room ID: ");
        Date checkInDate = getValidDateInput("Enter new Check-in Date (YYYY-MM-DD): ");
        Date checkOutDate = getValidDateInput("Enter new Check-out Date (YYYY-MM-DD): ");

        if (checkInDate.after(checkOutDate)) {
            System.out.println("Check-in date cannot be after check-out date.");
            return;
        }

        reservationService.updateReservation(id, clientId, hotelId, roomId, checkInDate, checkOutDate);
    }

    private void deleteReservation() {
        int id = getValidIntegerInput("Enter Reservation ID to delete: ");
        reservationService.deleteReservation(id);
    }

    private void viewReservationById() {
        int id = getValidIntegerInput("Enter Reservation ID to view: ");
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            printReservationDetails(reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private void viewClientReservations() {
        int clientId = getValidIntegerInput("Enter Client ID to view reservations: ");
        List<Reservation> reservations = reservationService.getReservationsByClient(clientId);
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                printReservationDetails(reservation);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No reservations found for this client.");
        }
    }

    private void viewReservationsByRoomType() {
        System.out.println("Select Room Type:");
        for (RoomType type : RoomType.values()) {
            System.out.println(type.ordinal() + 1 + ". " + type);
        }
        int choice = getValidIntegerInput("Please enter your choice: ");
        if (choice < 1 || choice > RoomType.values().length) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        RoomType roomType = RoomType.values()[choice - 1];
        List<Reservation> reservations = reservationService.getReservationsByRoomType(roomType);
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                printReservationDetails(reservation);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("No reservations found for this room type.");
        }
    }

    private void printReservationDetails(Reservation reservation) {
        System.out.println("Reservation ID: " + reservation.getId());
        System.out.println("Client ID: " + reservation.getClient().getId());
        System.out.println("Hotel ID: " + reservation.getHotel().getId());
        System.out.println("Room ID: " + reservation.getRoom().getId());
        System.out.println("Check-in Date: " + reservation.getCheckInDate());
        System.out.println("Check-out Date: " + reservation.getCheckOutDate());
        System.out.println("Total Price: " + reservation.getTotalPrice());
        System.out.println("Status: " + reservation.getReservationStatus());
    }

    private int getValidIntegerInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private Date getValidDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Date.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please enter a date in the format YYYY-MM-DD.");
            }
        }
    }
}
