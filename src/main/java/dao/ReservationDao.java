package main.java.dao;

import main.java.entities.Reservation;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReservationDao {
    List<Reservation> getAllReservations();
    Reservation getReservationById(int reservationId);
    void saveReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(int reservationId);

    int getReservationCount();
    BigDecimal calculateTotalRevenue();
    double calculateOccupancyRate(Date startDate, Date endDate);
    int getReservedCount();
    int getCancelledCount();
}
