package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.PricingDao;
import main.java.entities.Event;
import main.java.entities.Pricing;
import main.java.entities.Season;
import main.java.enums.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PricingDaoImpl implements PricingDao {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public List<Pricing> getAllPricings() {
        List<Pricing> pricings = new ArrayList<>();
        String query = "SELECT * FROM pricing";
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
        String query = "INSERT INTO Pricing (room_type, season_id, event_id, base_price, price_multiplier) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set the room type; ensure it's a valid ENUM or custom type
            preparedStatement.setObject(1, pricing.getRoomType().name(), Types.OTHER);

            // Set the season_id, using Types.NULL if the season is null
            preparedStatement.setObject(2, pricing.getSeason() != null ? pricing.getSeason().getId() : Types.NULL, Types.INTEGER);

            // Set the event_id, using Types.NULL if the event is null
            preparedStatement.setObject(3, pricing.getEvent() != null ? pricing.getEvent().getId() : Types.NULL, Types.INTEGER);

            // Set other parameters
            preparedStatement.setBigDecimal(4, pricing.getBasePrice());
            preparedStatement.setBigDecimal(5, pricing.getPriceMultiplier());

            // Execute the update
            preparedStatement.executeUpdate();

            // Get the generated keys (auto-generated ID)
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    // Set the generated ID back to the Pricing object
                    pricing.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }




    @Override
    public void updatePricing(Pricing pricing) {
        String query = "UPDATE Pricing SET room_type = ?, season_id = ?, event_id = ?, base_price = ?, price_multiplier = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pricing.getRoomType().name());
            preparedStatement.setInt(2, pricing.getSeason() != null ? pricing.getSeason().getId() : null);
            preparedStatement.setInt(3, pricing.getEvent() != null ? pricing.getEvent().getId() : null);
            preparedStatement.setBigDecimal(4, pricing.getBasePrice());
            preparedStatement.setBigDecimal(5, pricing.getPriceMultiplier());
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
                resultSet.getBigDecimal("price_multiplier")

        );
    }
    public Pricing getPricingForRoomTypeAndCondition(RoomType roomType, Season season, Event event) {
        try {
            String query = "SELECT * FROM pricing WHERE room_type = ? AND (season_id = ? OR ? IS NULL) AND (event_id = ? OR ? IS NULL)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roomType.toString());
            statement.setObject(2, season != null ? season.getId() : null);
            statement.setObject(3, season != null ? season.getId() : null);
            statement.setObject(4, event != null ? event.getId() : null);
            statement.setObject(5, event != null ? event.getId() : null);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Pricing(
                        resultSet.getInt("id"),
                        RoomType.valueOf(resultSet.getString("room_type")),
                        season,
                        event,
                        resultSet.getBigDecimal("base_price"),
                        resultSet.getBigDecimal("price_multiplier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
