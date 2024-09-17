package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.RoomDao;
import main.java.entities.Room;
import main.java.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {

    private Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Room";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                rooms.add(createRoomFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public Room getRoomById(int roomId) {
        String query = "SELECT * FROM Room WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createRoomFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveRoom(Room room) {
        String query = "INSERT INTO Room (hotel_id, room_number, room_type, availability_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, room.getHotelId()); // pass hotelId directly
            preparedStatement.setString(2, room.getRoomNumber());
            preparedStatement.setString(3, room.getRoomType().name());
            preparedStatement.setBoolean(4, room.isAvailabilityStatus());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                room.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRoom(Room room) {
        String query = "UPDATE Room SET hotel_id = ?, room_number = ?, room_type = ?, availability_status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getHotelId()); // pass hotelId directly
            preparedStatement.setString(2, room.getRoomNumber());
            preparedStatement.setString(3, room.getRoomType().name());
            preparedStatement.setBoolean(4, room.isAvailabilityStatus());
            preparedStatement.setInt(5, room.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRoom(int roomId) {
        String query = "DELETE FROM Room WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Room createRoomFromResultSet(ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getInt("id"),
                resultSet.getInt("hotel_id"), 
                resultSet.getString("room_number"),
                RoomType.valueOf(resultSet.getString("room_type")),
                resultSet.getBoolean("availability_status")
        );
    }
}
