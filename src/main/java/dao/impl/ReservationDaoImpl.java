package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.ClientDao;
import main.java.dao.HotelDao;
import main.java.dao.ReservationDao;
import main.java.dao.RoomDao;
import main.java.entities.Client;
import main.java.entities.Hotel;
import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.ReservationStatus;
import main.java.enums.RoomType;

import java.sql.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.Date;

public class ReservationDaoImpl implements ReservationDao {
    private Connection connection = DatabaseConnection.getInstance().getConnection();
    private ClientDao clientDao = new ClientDaoImpl();
    private HotelDao hotelDao = new HotelDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();
    private  RoomDaoImpl roomDaoImpl= new RoomDaoImpl();
    private HashMap<String, Boolean> roomAvailability = new HashMap<>();

    public ReservationDaoImpl() {
        loadRoomAvailability();
    }private void loadRoomAvailability() {
        roomAvailability.put("101", true);
        roomAvailability.put("102", true);
        roomAvailability.put("103", true);
        roomAvailability.put("104", false); // Example of an unavailable room
        // Additional room data can be loaded here from DB
    }


    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return reservations;
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createReservationFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return null;
    }



    @Override
    public void saveReservation(Reservation reservation) {
        String query = "INSERT INTO reservation (client_id, hotel_id, room_id, check_in_date, check_out_date, reservation_date, total_price, reservation_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservation.getClient().getId());
            preparedStatement.setInt(2, reservation.getHotel().getId());
            preparedStatement.setInt(3, reservation.getRoom().getId());
            preparedStatement.setDate(4, new java.sql.Date(reservation.getCheckInDate().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(reservation.getCheckOutDate().getTime()));
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(reservation.getReservationDate().getTime()));
            preparedStatement.setBigDecimal(7, reservation.getTotalPrice());
            preparedStatement.setString(8, reservation.getReservationStatus().name()); // Set the status to 'Confirmed'
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logger
        }
    }





    @Override
    public void updateReservation(Reservation reservation) {
        if (!isValidDateRange(reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            System.out.println("Error: Check-out date must be after check-in date.");
            return;
        }

        String query = "UPDATE reservation SET client_id = ?, hotel_id = ?, room_id = ?, check_in_date = ?, check_out_date = ?, reservation_date = ?, total_price = ?, reservation_status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservation.getClient().getId());
            preparedStatement.setInt(2, reservation.getHotel().getId());
            preparedStatement.setInt(3, reservation.getRoom().getId());
            preparedStatement.setDate(4, new java.sql.Date(reservation.getCheckInDate().getTime())); // Correct conversion
            preparedStatement.setDate(5, new java.sql.Date(reservation.getCheckOutDate().getTime())); // Correct conversion
            preparedStatement.setTimestamp(6, new java.sql.Timestamp(reservation.getReservationDate().getTime())); // Correct conversion
            preparedStatement.setBigDecimal(7, reservation.getTotalPrice());
            preparedStatement.setString(8, reservation.getReservationStatus().name());
            preparedStatement.setInt(9, reservation.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
    }

    @Override
    public void deleteReservation(int reservationId) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reservationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
    }

    @Override
    public int getReservationCount() {
        String query = "SELECT COUNT(*) FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return 0;
    }

    @Override
    public BigDecimal calculateTotalRevenue() {
        String query = "SELECT SUM(total_price) FROM reservation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return BigDecimal.ZERO;
    }

    @Override
    public double calculateOccupancyRate(Date startDate, Date endDate) {
        int totalRooms = getTotalRoomsAvailable();
        int occupiedRooms = getOccupiedRoomsCount(startDate, endDate);
        return totalRooms == 0 ? 0 : (double) occupiedRooms / totalRooms;
    }

    @Override
    public int getReservedCount() {
        return getReservationsByStatus(ReservationStatus.Confirmed).size();
    }

    @Override
    public int getCancelledCount() {
        return getReservationsByStatus(ReservationStatus.Canceled).size();
    }

    private Reservation createReservationFromResultSet(ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getInt("id"),
                clientDao.getClientById(resultSet.getInt("client_id")),
                hotelDao.getHotelById(resultSet.getInt("hotel_id")),
                roomDao.getRoomById(resultSet.getInt("room_id")),
                resultSet.getDate("check_in_date"),
                resultSet.getDate("check_out_date"),
                resultSet.getTimestamp("reservation_date"),
                resultSet.getBigDecimal("total_price"),
                ReservationStatus.valueOf(resultSet.getString("reservation_status"))
        );
    }

    public boolean isValidDateRange(Date checkInDate, Date checkOutDate) {
        return checkOutDate.after(checkInDate);
    }

    public int getTotalRoomsAvailable() {
        // Adjust this method to reflect how you track total rooms in your schema
        String query = "SELECT COUNT(*) FROM room";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return 0;
    }

    public int getOccupiedRoomsCount(Date startDate, Date endDate) {
        String query = "SELECT COUNT(DISTINCT room_id) FROM reservation WHERE check_in_date <= ? AND check_out_date >= ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Convert java.util.Date to java.sql.Date
            preparedStatement.setDate(1, new java.sql.Date(endDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return 0;
    }


    private List<Reservation> getReservationsByStatus(ReservationStatus status) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE status = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return reservations;
    }
    @Override
    public List<Reservation> getReservationsBetweenDates(Date startDate, Date endDate) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE check_in_date <= ? AND check_out_date >= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(endDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }

        return reservations;
    }
    @Override
    public List<Reservation> getReservationsByRoomId(int roomId) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE room_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetch related Client, Hotel, and Room objects
                Client client = clientDao.getClientById(resultSet.getInt("client_id"));
                Hotel hotel = hotelDao.getHotelById(resultSet.getInt("hotel_id"));
                Room room = roomDao.getRoomById(resultSet.getInt("room_id"));

                // Create Reservation object with fetched entities
                Reservation reservation = new Reservation(
                        resultSet.getInt("id"),
                        client,
                        hotel,
                        room,
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        resultSet.getTimestamp("reservation_date"),
                        resultSet.getBigDecimal("total_price"),
                        ReservationStatus.valueOf(resultSet.getString("status")) // Assuming status is stored as a String
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    @Override
    public void cancelReservation(int reservationId) {
        String query = "UPDATE reservation SET status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ReservationStatus.Canceled.name());
            preparedStatement.setInt(2, reservationId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
    }
    @Override
    public List<Reservation> getClientReservations(int clientId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation WHERE client_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Consider using a logger
        }
        return reservations;
    }
    @Override
    public List<Reservation> getReservationsByRoomType(RoomType roomType) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.* FROM reservation AS res " +
                "JOIN room AS r ON res.room_id = r.id " +
                "WHERE r.room_type = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, roomType.name());  // Assuming RoomType is an enum
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reservations.add(createReservationFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    @Override
    public boolean bookAvailableRoom(RoomType roomType) {
        Optional<String> availableRoomId = roomAvailability.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .findFirst();

        if (availableRoomId.isPresent()) {
            String roomId = availableRoomId.get();
            roomAvailability.put(roomId, false);

            Reservation reservation = new Reservation();
            reservation.setRoom(roomDao.getRoomById(Integer.parseInt(roomId)));
            reservation.setReservationStatus(ReservationStatus.Confirmed);
            reservation.setReservationDate(new Date());

            saveReservation(reservation);
            return true;
        }
    }


}
