package main.java.dao;

import main.java.entities.Hotel;
import java.util.List;

public interface HotelDao {
    List<Hotel> getAllHotels();
    Hotel getHotelById(int hotelId);
    void saveHotel(Hotel hotel);
    void updateHotel(Hotel hotel);
    void deleteHotel(int hotelId);
}
