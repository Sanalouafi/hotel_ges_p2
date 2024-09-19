package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.RoomDao;
import main.java.entities.Room;
import main.java.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    private Connection connection;

    public RoomDaoImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

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
        String query = "INSERT INTO Room (hotel_id, room_number, room_type, availability_status) VALUES (?, ?, CAST(? AS roomtype), ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, room.getHotelId());
            preparedStatement.setString(2, room.getRoomNumber());
            preparedStatement.setString(3, room.getRoomType().name()); // Convert RoomType to String
            preparedStatement.setBoolean(4, room.isAvailabilityStatus());
            preparedStatement.executeUpdate();

            // Retrieve the generated key
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    room.setId(resultSet.getInt(1)); // Set the generated ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void updateRoom(Room room) {
        String query = "UPDATE Room SET hotel_id = ?, room_number = ?, room_type = ?, availability_status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getHotelId());
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

    public boolean roomExists(int hotelId, String roomNumber) {
        String query = "SELECT COUNT(*) FROM Room WHERE hotel_id = ? AND room_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, hotelId);
            ps.setString(2, roomNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateRoomAvailability(int roomId, boolean isAvailable) {
        String sql = "UPDATE Room SET availability_status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, isAvailable);
            statement.setInt(2, roomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
