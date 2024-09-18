package main.java.service;

import main.java.dao.impl.ReservationDaoImpl;
import main.java.dao.impl.SeasonDaoImpl;
import main.java.dao.impl.EventDaoImpl;
import main.java.dao.impl.PricingDaoImpl;
import main.java.dao.impl.ClientDaoImpl;
import main.java.dao.impl.HotelDaoImpl;
import main.java.dao.impl.RoomDaoImpl;
import main.java.entities.Reservation;
import main.java.entities.Client;
import main.java.entities.Hotel;
import main.java.entities.Room;
import main.java.entities.Pricing;
import main.java.enums.ReservationStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private ReservationDaoImpl reservationDaoImpl;
    private SeasonDaoImpl seasonDaoImpl;
    private EventDaoImpl eventDaoImpl;
    private PricingDaoImpl pricingDaoImpl;
    private ClientDaoImpl clientDaoImpl;
    private HotelDaoImpl hotelDaoImpl;
    private RoomDaoImpl roomDaoImpl;
    private Scanner scanner;

    public ReservationService() {
        this.reservationDaoImpl = new ReservationDaoImpl();
        this.seasonDaoImpl = new SeasonDaoImpl();
        this.eventDaoImpl = new EventDaoImpl();
        this.pricingDaoImpl = new PricingDaoImpl();
        this.clientDaoImpl = new ClientDaoImpl();
        this.hotelDaoImpl = new HotelDaoImpl();
        this.roomDaoImpl = new RoomDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void saveReservation() {
        System.out.println("Enter Client ID: ");
        int clientId = scanner.nextInt();
        System.out.println("Enter Hotel ID: ");
        int hotelId = scanner.nextInt();
        System.out.println("Enter Room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();  // Clear buffer

        Client client = clientDaoImpl.getClientById(clientId);
        Hotel hotel = hotelDaoImpl.getHotelById(hotelId);
        Room room = roomDaoImpl.getRoomById(roomId);

        if (client == null || hotel == null || room == null) {
            System.out.println("Error: Invalid Client, Hotel, or Room ID.");
            return;
        }

        System.out.println("Enter Check-in Date (YYYY-MM-DD): ");
        Date checkInDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter Check-out Date (YYYY-MM-DD): ");
        Date checkOutDate = Date.valueOf(scanner.nextLine());

        if (!isValidDateRange(checkInDate, checkOutDate)) {
            System.out.println("Error: Check-out date must be after check-in date.");
            return;
        }

        BigDecimal totalPrice = calculateTotalPrice(checkInDate, checkOutDate);

        Reservation reservation = new Reservation(
                0,  // ID will be set after saving
                client,
                hotel,
                room,
                checkInDate,
                checkOutDate,
                new Timestamp(System.currentTimeMillis()),  // Current timestamp
                totalPrice,
                ReservationStatus.Confirmed  // Default status
        );

        reservationDaoImpl.saveReservation(reservation);
        System.out.println("Reservation saved successfully.");
    }

    public void updateReservation(int id, int clientId, int hotelId, int roomId, Date checkInDate, Date checkOutDate) {
        Client client = clientDaoImpl.getClientById(clientId);
        Hotel hotel = hotelDaoImpl.getHotelById(hotelId);
        Room room = roomDaoImpl.getRoomById(roomId);

        if (client == null || hotel == null || room == null) {
            System.out.println("Error: Invalid Client, Hotel, or Room ID.");
            return;
        }

        if (!isValidDateRange(checkInDate, checkOutDate)) {
            System.out.println("Error: Check-out date must be after check-in date.");
            return;
        }

        BigDecimal totalPrice = calculateTotalPrice(checkInDate, checkOutDate);

        Reservation reservation = new Reservation(
                id,
                client,
                hotel,
                room,
                checkInDate,
                checkOutDate,
                new Timestamp(System.currentTimeMillis()),  // Current timestamp
                totalPrice,
                ReservationStatus.Confirmed  // Default status
        );

        reservationDaoImpl.updateReservation(reservation);
        System.out.println("Reservation updated successfully.");
    }

    public void deleteReservation(int id) {
        reservationDaoImpl.deleteReservation(id);
        System.out.println("Reservation deleted successfully.");
    }

    public Reservation getReservationById(int id) {
        return reservationDaoImpl.getReservationById(id);
    }

    private BigDecimal calculateTotalPrice(Date checkInDate, Date checkOutDate) {
        List<Pricing> pricingList = pricingDaoImpl.getAllPricings();  // Fetch all pricing

        for (Pricing pricing : pricingList) {
            if (isDateInRange(checkInDate, checkOutDate, pricing)) {
                BigDecimal basePrice = pricing.getBasePrice();
                BigDecimal multiplier = pricing.getPriceMultiplier();
                return basePrice.multiply(multiplier);  // Calculate total price
            }
        }

        return BigDecimal.ZERO;
    }

    private boolean isDateInRange(Date checkInDate, Date checkOutDate, Pricing pricing) {
        return !checkInDate.after(pricing.getEndDate()) &&
                !checkOutDate.before(pricing.getStartDate());
    }

    private boolean isValidDateRange(Date checkInDate, Date checkOutDate) {
        return checkOutDate.after(checkInDate);
    }
}
