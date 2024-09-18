package main.java.dao;

import main.java.entities.Reservation;
import java.util.List;

public interface ReservationDao {
    List<Reservation> getAllReservations();
    Reservation getReservationById(int reservationId);
    void saveReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(int reservationId);
}
