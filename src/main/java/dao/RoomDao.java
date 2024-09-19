package main.java.dao;

import main.java.entities.Room;
import main.java.enums.RoomType;

import java.util.Date;
import java.util.List;

public interface RoomDao {
    List<Room> getAllRooms();
    Room getRoomById(int roomId);
    void saveRoom(Room room);
    void updateRoom(Room room);
    void deleteRoom(int roomId);

}
