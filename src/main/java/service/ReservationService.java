package main.java.service;

import main.java.entities.*;
import main.java.dao.impl.*;
import main.java.enums.RoomType;
import main.java.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private ReservationDaoImpl reservationDaoImpl;
    private RoomDaoImpl roomDaoImpl;
    private SeasonDaoImpl seasonDaoImpl;
    private EventDaoImpl eventDaoImpl;
    private PricingDaoImpl pricingDaoImpl;
    private HotelDaoImpl hotelDaoImpl;
    private ClientDaoImpl clientDaoImpl;
    private Scanner scanner;

    public ReservationService() {
        this.reservationDaoImpl = new ReservationDaoImpl();
        this.roomDaoImpl = new RoomDaoImpl();
        this.seasonDaoImpl = new SeasonDaoImpl();
        this.eventDaoImpl = new EventDaoImpl();
        this.pricingDaoImpl = new PricingDaoImpl();
        this.hotelDaoImpl = new HotelDaoImpl();
        this.clientDaoImpl = new ClientDaoImpl();
        this.scanner = new Scanner(System.in);
    }
    public Reservation getReservationById(int id) {
        return reservationDaoImpl.getReservationById(id);
    }

    public void saveReservation(int clientId, int hotelId, int roomId, Date checkInDate, Date checkOutDate) {
        if (!isRoomAvailable(roomId, checkInDate, checkOutDate)) {
            System.out.println("Room is not available for the given dates.");
            return;
        }


        BigDecimal totalPrice = calculateTotalPrice(roomId, checkInDate, checkOutDate);

        Room room = roomDaoImpl.getRoomById(roomId);
        Client client = clientDaoImpl.getClientById(clientId);
        Hotel hotel = hotelDaoImpl.getHotelById(hotelId); // Use hotelId passed as parameter

        Reservation reservation = new Reservation(0, client, hotel, room, checkInDate, checkOutDate, new Timestamp(System.currentTimeMillis()), totalPrice, ReservationStatus.Confirmed);
        reservationDaoImpl.saveReservation(reservation);

        roomDaoImpl.updateRoomAvailability(roomId, false);

        System.out.println("Reservation saved successfully.");
    }
    public void updateReservation(int id, int clientId, int hotelId, int roomId, Date checkInDate, Date checkOutDate) {
        // Fetch existing reservation
        Reservation reservation = reservationDaoImpl.getReservationById(id);
        if (reservation != null) {
            // Update details
            reservation.setClient(clientDaoImpl.getClientById(clientId));
            reservation.setHotel(hotelDaoImpl.getHotelById(hotelId));
            reservation.setRoom(roomDaoImpl.getRoomById(roomId));
            reservation.setCheckInDate(checkInDate);
            reservation.setCheckOutDate(checkOutDate);
            // Save updated reservation
            reservationDaoImpl.updateReservation(reservation);
            System.out.println("Reservation updated successfully.");
        } else {
            System.out.println("Reservation not found.");
        }
    }

    public void deleteReservation(int id) {
        reservationDaoImpl.deleteReservation(id);
    }


    public void cancelReservation(int reservationId) {
        reservationDaoImpl.cancelReservation(reservationId);
        System.out.println("Reservation canceled successfully.");
    }

    public List<Reservation> getReservationsByRoomType(RoomType roomType) {
        return reservationDaoImpl.getReservationsByRoomType(roomType);
    }

    public List<Reservation> getReservationsByClient(int clientId) {
        return reservationDaoImpl.getClientReservations(clientId);
    }

    public List<Reservation> getReservationsBetweenDates(Date startDate, Date endDate) {
        return reservationDaoImpl.getReservationsBetweenDates(startDate, endDate);
    }

    public int getTotalReservations() {
        return reservationDaoImpl.getReservationCount();
    }

    public BigDecimal getTotalRevenue() {
        return reservationDaoImpl.calculateTotalRevenue();
    }

    public double getOccupancyRate(Date startDate, Date endDate) {
        return reservationDaoImpl.calculateOccupancyRate(startDate, endDate);
    }

    public int getReservedCount() {
        return reservationDaoImpl.getReservedCount();
    }

    public int getCancelledCount() {
        return reservationDaoImpl.getCancelledCount();
    }

    private boolean isRoomAvailable(int roomId, Date checkInDate, Date checkOutDate) {
        List<Reservation> reservations = reservationDaoImpl.getReservationsByRoomId(roomId);

        for (Reservation reservation : reservations) {
            if ((checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate()))) {
                return false;
            }
        }
        return true;
    }

    private BigDecimal calculateTotalPrice(int roomId, Date checkInDate, Date checkOutDate) {
        Room room = roomDaoImpl.getRoomById(roomId);
        RoomType roomType = room.getRoomType();

        BigDecimal basePrice = new BigDecimal(100);

        Season season = getSeasonForDateRange(checkInDate, checkOutDate);
        Event event = getEventForDateRange(checkInDate, checkOutDate);

        Pricing pricing = pricingDaoImpl.getPricingForRoomTypeAndCondition(roomType, season, event);

        if (pricing != null) {
            basePrice = pricing.getBasePrice();
            BigDecimal priceMultiplier = pricing.getPriceMultiplier();
            long days = ChronoUnit.DAYS.between(checkInDate.toLocalDate(), checkOutDate.toLocalDate());
            return basePrice.multiply(priceMultiplier).multiply(BigDecimal.valueOf(days));
        }

        long days = ChronoUnit.DAYS.between(checkInDate.toLocalDate(), checkOutDate.toLocalDate());
        return basePrice.multiply(BigDecimal.valueOf(days));
    }

    private Season getSeasonForDateRange(Date checkInDate, Date checkOutDate) {
        List<Season> seasons = seasonDaoImpl.getAllSeasons();
        for (Season season : seasons) {
            if ((checkInDate.before(season.getEndDate()) || checkInDate.equals(season.getEndDate()))
                    && (checkOutDate.after(season.getStartDate()) || checkOutDate.equals(season.getStartDate()))) {
                return season;
            }
        }
        return null;
    }

    private Event getEventForDateRange(Date checkInDate, Date checkOutDate) {
        List<Event> events = eventDaoImpl.getAllEvents();
        for (Event event : events) {
            if ((checkInDate.before(event.getEventDate()) || checkInDate.equals(event.getEventDate()))
                    && (checkOutDate.after(event.getEventDate()) || checkOutDate.equals(event.getEventDate()))) {
                return event;
            }
        }
        return null;
    }
    public boolean bookRoom(RoomType roomType) {
        return reservationDao.bookAvailableRoom(roomType);
    }
}
