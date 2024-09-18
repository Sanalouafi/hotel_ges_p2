package main.java.service;

import main.java.dao.impl.ReservationDaoImpl;
import main.java.entities.Reservation;
import main.java.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class StatisticService {

    private ReservationDaoImpl reservationDaoImpl;

    public StatisticService() {
        this.reservationDaoImpl = new ReservationDaoImpl();
    }

    public int getReservationCount() {
        return reservationDaoImpl.getReservationCount();
    }

    public BigDecimal calculateTotalRevenue() {
        return reservationDaoImpl.calculateTotalRevenue();
    }

    public double calculateOccupancyRate(Date startDate, Date endDate) {
        int totalRooms = reservationDaoImpl.getTotalRoomsAvailable();
        int occupiedRooms = reservationDaoImpl.getOccupiedRoomsCount(startDate, endDate);
        return totalRooms == 0 ? 0 : (double) occupiedRooms / totalRooms * 100; // Return as percentage
    }

    public int getReservedCount() {
        return reservationDaoImpl.getReservedCount();
    }

    public int getCancelledCount() {
        return reservationDaoImpl.getCancelledCount();
    }
}
