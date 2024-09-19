package main.java.menu;

import main.java.entities.Room;
import main.java.service.ReservationService;
import main.java.service.RoomService;

import java.util.Scanner;

public class RoomMenu {

    private RoomService roomService;
    private Scanner scanner;

    public RoomMenu(RoomService roomService ) {
        this.roomService = new RoomService();
        this.scanner = new Scanner(System.in);
    }

    public void displayRoomMenu() {
        int choice;
        do {
            System.out.println("===== Room Management Menu =====");
            System.out.println("1. Add a New Room");
            System.out.println("2. Update an Existing Room");
            System.out.println("3. Display All Rooms");
            System.out.println("4. Delete a Room");
            System.out.println("5. Search for a Room");
            System.out.println("6. Back to Main Menu");
            System.out.println("================================");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline left-over

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    updateRoom();
                    break;
                case 3:
                    displayAllRooms();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    searchRoom();
                    break;
                case 6:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);
    }

    private void addRoom() {
        System.out.println("=== Add New Room ===");
        roomService.saveRoom();
    }

    private void updateRoom() {
        System.out.println("=== Update Room ===");
        roomService.updateRoom();
    }

    private void displayAllRooms() {
        System.out.println("=== Display All Rooms ===");
        roomService.getAllRooms().forEach(System.out::println);
    }

    private void deleteRoom() {
        System.out.println("Enter Room id to delete: ");
        int roomId = scanner.nextInt();
        roomService.deleteRoom(roomId);
        System.out.println("Room deleted successfully.");
    }

    private void searchRoom() {
        System.out.println("Enter Room id to search: ");
        int roomId = scanner.nextInt();
        Room room = roomService.getRoomById(roomId);
        if (room != null) {
            System.out.println(room);
        } else {
            System.out.println("Room not found.");
        }
    }
}
