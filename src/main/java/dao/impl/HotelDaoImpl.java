package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.HotelDao;
import main.java.entities.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDaoImpl implements HotelDao {

    private Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM Hotel";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                hotels.add(createHotelFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public Hotel getHotelById(int hotelId) {

        String query = "SELECT * FROM Hotel WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createHotelFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveHotel(Hotel hotel) {
        String query = "INSERT INTO Hotel (name, location, contact_info) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setString(2, hotel.getLocation());
            preparedStatement.setString(3, hotel.getContactInfo());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                hotel.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHotel(Hotel hotel) {
        String query = "UPDATE Hotel SET name = ?, location = ?, contact_info = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, hotel.getName());
            preparedStatement.setString(2, hotel.getLocation());
            preparedStatement.setString(3, hotel.getContactInfo());
            preparedStatement.setInt(4, hotel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHotel(int hotelId) {
        String query = "DELETE FROM Hotel WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, hotelId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Hotel createHotelFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String location = resultSet.getString("location");
        String contactInfo = resultSet.getString("contact_info");

        System.out.println("Hotel ID: " + id);
        System.out.println("Hotel Name: " + name);
        System.out.println("Hotel Location: " + location);
        System.out.println("Hotel Contact Info: " + contactInfo);


        return new Hotel(id, name, location, contactInfo);
    }

}
