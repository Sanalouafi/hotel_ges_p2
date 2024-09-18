package main.java.menu;

import main.java.service.ReservationService;
import main.java.entities.Reservation;
import java.sql.Date;
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
            System.out.println("5. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Clear buffer

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
                    return;  // Back to Main Menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void saveReservation() {
        reservationService.saveReservation();
    }

    private void updateReservation() {
        System.out.println("Enter Reservation ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter new Client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter new Hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter new Room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        System.out.println("Enter new Check-in Date (YYYY-MM-DD): ");
        Date checkInDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter new Check-out Date (YYYY-MM-DD): ");
        Date checkOutDate = Date.valueOf(scanner.nextLine());

        // Call the method to update the reservation. You might need to adapt it based on your service class.
        reservationService.updateReservation(id, clientId, hotelId, roomId, checkInDate, checkOutDate);
    }

    private void deleteReservation() {
        System.out.println("Enter Reservation ID to delete: ");
        int id = scanner.nextInt();
        reservationService.deleteReservation(id);
    }

    private void viewReservationById() {
        System.out.println("Enter Reservation ID to view: ");
        int id = scanner.nextInt();
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            System.out.println("Reservation ID: " + reservation.getId());
            System.out.println("Client ID: " + reservation.getClient().getId());
            System.out.println("Hotel ID: " + reservation.getHotel().getId());
            System.out.println("Room ID: " + reservation.getRoom().getId());
            System.out.println("Check-in Date: " + reservation.getCheckInDate());
            System.out.println("Check-out Date: " + reservation.getCheckOutDate());
            System.out.println("Total Price: " + reservation.getTotalPrice());
            System.out.println("Status: " + reservation.getStatus());
        } else {
            System.out.println("Reservation not found.");
        }
    }
}
