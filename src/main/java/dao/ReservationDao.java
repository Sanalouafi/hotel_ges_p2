package main.java.dao;

import main.java.entities.Reservation;
import main.java.entities.Room;
import main.java.enums.RoomType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReservationDao {
    List<Reservation> getAllReservations();
    Reservation getReservationById(int reservationId);
    void saveReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(int reservationId);
    List<Reservation> getReservationsBetweenDates(Date startDate, Date endDate);
    int getReservationCount();
    BigDecimal calculateTotalRevenue();
    double calculateOccupancyRate(Date startDate, Date endDate);
     void cancelReservation(int reservationId);
    List<Reservation> getClientReservations(int clientId);
     List<Reservation> getReservationsByRoomType(RoomType roomType);
     List<Reservation> getReservationsByRoomId(int roomId);
     int getCancelledCount();
     int getReservedCount();
    boolean bookAvailableRoom(RoomType roomType);
}