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

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public Timestamp getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice=totalPrice;
    }
    public ReservationStatus getStatus() {
        return status;
    }
    public void setStatus(ReservationStatus status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client.getId() +
                ", hotel_id=" + hotel.getId() +
                ", room_id=" + room.getId() +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", reservationDate=" + reservationDate +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}

