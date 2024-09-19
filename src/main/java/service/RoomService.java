package main.java.service;

import main.java.dao.impl.RoomDaoImpl;
import main.java.entities.Room;
import main.java.enums.RoomType;

import java.util.List;
import java.util.Scanner;

public class RoomService {

    private RoomDaoImpl roomDaoImpl;
    private Scanner scanner;

    public RoomService() {
        this.roomDaoImpl = new RoomDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void saveRoom() {
        System.out.println("Enter Hotel id: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Room number: ");
        String roomNumber = scanner.nextLine();
        if (roomDaoImpl.roomExists(hotelId, roomNumber)) {
            System.out.println("Room number already exists in this hotel. Please enter a unique room number.");
            return;
        }

        System.out.println("Enter Room type (e.g., SINGLE, DOUBLE, SUITE): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());

        System.out.println("Is the room available? (true/false): ");
        boolean availabilityStatus = scanner.nextBoolean();

        Room room = new Room(0, hotelId, roomNumber, roomType, availabilityStatus);
        roomDaoImpl.saveRoom(room);
        System.out.println("Room saved successfully.");
    }

    public void updateRoom() {
        System.out.println("Enter Room id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Hotel id: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Room number: ");
        String roomNumber = scanner.nextLine();
        if (roomDaoImpl.roomExists(hotelId, roomNumber)) {
            System.out.println("Room number already exists in this hotel. Please enter a unique room number.");
            return;
        }

        System.out.println("Enter Room type (e.g., Simple, Double, Suite): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr.toUpperCase());

        System.out.println("Is the room available? (true/false): ");
        boolean availabilityStatus = scanner.nextBoolean();

        Room room = new Room(id, hotelId, roomNumber, roomType, availabilityStatus);
        roomDaoImpl.updateRoom(room);
        System.out.println("Room updated successfully.");
    }

    public List<Room> getAllRooms() {
        return roomDaoImpl.getAllRooms();
    }

    public Room getRoomById(int roomId) {
        return roomDaoImpl.getRoomById(roomId);
    }

    public void deleteRoom(int roomId) {
        roomDaoImpl.deleteRoom(roomId);
    }
}
