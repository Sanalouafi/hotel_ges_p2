package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.PricingDao;
import main.java.entities.Pricing;
import main.java.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PricingDaoImpl implements PricingDao {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public List<Pricing> getAllPricings() {
        List<Pricing> pricings = new ArrayList<>();
        String query = "SELECT * FROM Pricing";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                pricings.add(createPricingFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pricings;
    }

    @Override
    public Pricing getPricingById(int pricingId) {
        String query = "SELECT * FROM Pricing WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pricingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createPricingFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void savePricing(Pricing pricing) {
        String query = "INSERT INTO Pricing (room_type, season_id, event_id, base_price, price_multiplier, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pricing.getRoomType().name());
            preparedStatement.setInt(2, pricing.getSeason() != null ? pricing.getSeason().getId() : null);
            preparedStatement.setInt(3, pricing.getEvent() != null ? pricing.getEvent().getId() : null);
            preparedStatement.setBigDecimal(4, pricing.getBasePrice());
            preparedStatement.setBigDecimal(5, pricing.getPriceMultiplier());
            preparedStatement.setDate(6, pricing.getStartDate());
            preparedStatement.setDate(7, pricing.getEndDate());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                pricing.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePricing(Pricing pricing) {
        String query = "UPDATE Pricing SET room_type = ?, season_id = ?, event_id = ?, base_price = ?, price_multiplier = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pricing.getRoomType().name());
            preparedStatement.setInt(2, pricing.getSeason() != null ? pricing.getSeason().getId() : null);
            preparedStatement.setInt(3, pricing.getEvent() != null ? pricing.getEvent().getId() : null);
            preparedStatement.setBigDecimal(4, pricing.getBasePrice());
            preparedStatement.setBigDecimal(5, pricing.getPriceMultiplier());
            preparedStatement.setDate(6, pricing.getStartDate());
            preparedStatement.setDate(7, pricing.getEndDate());
            preparedStatement.setInt(8, pricing.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePricing(int pricingId) {
        String query = "DELETE FROM Pricing WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pricingId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pricing createPricingFromResultSet(ResultSet resultSet) throws SQLException {
        return new Pricing(
                resultSet.getInt("id"),
                RoomType.valueOf(resultSet.getString("room_type")),
                resultSet.getInt("season_id") != 0 ? new SeasonDaoImpl().getSeasonById(resultSet.getInt("season_id")) : null,
                resultSet.getInt("event_id") != 0 ? new EventDaoImpl().getEventById(resultSet.getInt("event_id")) : null,
                resultSet.getBigDecimal("base_price"),
                resultSet.getBigDecimal("price_multiplier"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date")
        );
    }
}
