package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.ReservationDao;
import main.java.entities.Client;
import main.java.entities.Hotel;
import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.ReservationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        String query = "SELECT * FROM Reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createReservationFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveReservation(Reservation reservation) {
        String query = "INSERT INTO Reservation (client_id, hotel_id, room_id, check_in_date, check_out_date, reservation_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, reservation.getClient().getId());
            preparedStatement.setInt(2, reservation.getHotel().getId());
            preparedStatement.setInt(3, reservation.getRoom().getId());
            preparedStatement.setDate(4, reservation.getCheckInDate());
            preparedStatement.setDate(5, reservation.getCheckOutDate());
            preparedStatement.setTimestamp(6, reservation.getReservationDate());
            preparedStatement.setBigDecimal(7, reservation.getTotalPrice());
            preparedStatement.setString(8, reservation.getStatus().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                reservation.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        String query = "UPDATE Reservation SET client_id = ?, hotel_id = ?, room_id = ?, check_in_date = ?, check_out_date = ?, reservation_date = ?, total_price = ?, status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservation.getClient().getId());
            preparedStatement.setInt(2, reservation.getHotel().getId());
            preparedStatement.setInt(3, reservation.getRoom().getId());
            preparedStatement.setDate(4, reservation.getCheckInDate());
            preparedStatement.setDate(5, reservation.getCheckOutDate());
            preparedStatement.setTimestamp(6, reservation.getReservationDate());
            preparedStatement.setBigDecimal(7, reservation.getTotalPrice());
            preparedStatement.setString(8, reservation.getStatus().name());
            preparedStatement.setInt(9, reservation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReservation(int reservationId) {
        String query = "DELETE FROM Reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Reservation createReservationFromResultSet(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getInt("id"),
                new ClientDaoImpl().getClientById(resultSet.getInt("client_id")),
                new HotelDaoImpl().getHotelById(resultSet.getInt("hotel_id")),
                new RoomDaoImpl().getRoomById(resultSet.getInt("room_id")),
                resultSet.getDate("check_in_date"),
                resultSet.getDate("check_out_date"),
                resultSet.getTimestamp("reservation_date"),
                resultSet.getBigDecimal("total_price"),
                ReservationStatus.valueOf(resultSet.getString("status"))
        );
    }
}
