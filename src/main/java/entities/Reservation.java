package main.java.entities;

import main.java.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Reservation {
    private int id;
    private Client client;
    private Hotel hotel;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private Timestamp reservationDate;
    private BigDecimal totalPrice;
    private ReservationStatus status;

    public Reservation(int id, Client clientI, Hotel hotel, Room room, Date checkInDate, Date checkOutDate, Timestamp reservationDate, BigDecimal totalPrice, ReservationStatus status) {
        this.id = id;
        this.client = client;
        this.hotel = hotel;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationDate = reservationDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", clientId=" + client +
                ", hotelId=" + hotel +
                ", roomId=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", reservationDate=" + reservationDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}

