package main.java.entities;

import main.java.enums.RoomType;

public class Room {
    private int id;
    private int hotelId; // change from Hotel to hotelId
    private String roomNumber;
    private RoomType roomType;
    private boolean availabilityStatus;

    public Room(int id, int hotelId, String roomNumber, RoomType roomType, boolean availabilityStatus) {
        this.id = id;
        this.hotelId = hotelId; // store hotel_id instead of the Hotel object
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
    public int getHotelId() {
        return hotelId;
    }
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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
        return "Room{" +
                "id=" + id +
                ", hotelId=" + hotelId +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", availabilityStatus=" + availabilityStatus +
                '}';
    }
}
