package main.java.service;

import main.java.dao.impl.ReservationDaoImpl;
import main.java.entities.Reservation;
import main.java.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticService {

    private ReservationDaoImpl reservationDaoImpl;

    public StatisticService() {
        this.reservationDaoImpl = new ReservationDaoImpl();
    }

    // Stream implementation for reservation count
    public int getReservationCount() {
        List<Reservation> reservations = reservationDaoImpl.getAllReservations();
        return (int) reservations.stream()
                .count(); // You can also use .collect(Collectors.toList()).size() but count is simpler
    }

    // Stream implementation for total revenue calculation
    public BigDecimal calculateTotalRevenue() {
        List<Reservation> reservations = reservationDaoImpl.getAllReservations();
        return reservations.stream()
                .map(Reservation::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum of all totalPrice values
    }

   
    public double calculateOccupancyRate(Date startDate, Date endDate) {
        List<Reservation> reservations = reservationDaoImpl.getReservationsBetweenDates(startDate, endDate);
        int totalRooms = reservationDaoImpl.getTotalRoomsAvailable();
        long occupiedRooms = reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.Confirmed) // Only count reserved rooms
                .count();
        return totalRooms == 0 ? 0 : (double) occupiedRooms / totalRooms * 100;
    }

    // Stream implementation for reserved count
    public int getReservedCount() {
        List<Reservation> reservations = reservationDaoImpl.getAllReservations();
        return (int) reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.Confirmed)
                .count();
    }

    // Stream implementation for cancelled count
    public int getCancelledCount() {
        List<Reservation> reservations = reservationDaoImpl.getAllReservations();
        return (int) reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.Confirmed)
                .count();
    }
}
