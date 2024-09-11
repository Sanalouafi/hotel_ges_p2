package main.java.entities;

import main.java.enums.RoomType;

public class Room {
    private int id;
    private Hotel hotel;
    private String roomNumber;
    private RoomType roomType;
    private boolean availabilityStatus;

    public Room(int id, Hotel hotel, String roomNumber, RoomType roomType, boolean availabilityStatus) {
        this.id = id;
        this.hotel = hotel;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.availabilityStatus = availabilityStatus;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public RoomType getRoomType() {
        return roomType;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }
    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
    @Override
    public String toString() {
        return "Room{"+
                ",id="+id+
                ", hotel=" + hotel+
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", availabilityStatus=" + availabilityStatus +
                '}';

    }

}
