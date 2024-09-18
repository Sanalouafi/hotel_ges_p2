package main.java.service;

import main.java.entities.Hotel;
import main.java.dao.impl.HotelDaoImpl;

import java.util.Scanner;

public class HotelService {

    private HotelDaoImpl hotelDaoImpl;
    private Scanner scanner;

    public HotelService() {
        this.hotelDaoImpl = new HotelDaoImpl();
        this.scanner = new Scanner(System.in);
    }

    public void saveHotel() {
        System.out.println("Enter Hotel name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Hotel location: ");
        String location = scanner.nextLine();
        System.out.println("Enter Hotel contact info: ");
        String contactInfo = scanner.nextLine();
        Hotel hotel = new Hotel(0, name, location, contactInfo);
        hotelDaoImpl.saveHotel(hotel);
    }

    public void updateHotel() {
        System.out.println("Enter Hotel id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Hotel name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Hotel location: ");
        String location = scanner.nextLine();
        System.out.println("Enter Hotel contact info: ");
        String contactInfo = scanner.nextLine();
        Hotel hotel = new Hotel(id, name, location, contactInfo);
        hotelDaoImpl.updateHotel(hotel);
    }

    public void deleteHotel() {
        System.out.println("Enter Hotel id: ");
        int id = scanner.nextInt();
        hotelDaoImpl.deleteHotel(id);
    }

    public Hotel getHotelById() {
        System.out.println("Enter Hotel id: ");
        int id = scanner.nextInt();
        return hotelDaoImpl.getHotelById(id);
    }
}
