package main.java.dao.impl;

import main.java.connection.DatabaseConnection;
import main.java.dao.SeasonDao;
import main.java.entities.Season;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeasonDaoImpl implements SeasonDao {
    private Connection connection;

    public SeasonDaoImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Season> getAllSeasons() {
        List<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM Season";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                seasons.add(createSeasonFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasons;
    }

    @Override
    public Season getSeasonById(int seasonId) {
        String query = "SELECT * FROM Season WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, seasonId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createSeasonFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveSeason(Season season) {
        if (!isValidDateRangeSeason(season.getStartDate(), season.getEndDate())) {
            System.out.println("Error: End date must be after the start date.");
            return;
        }

        String query = "INSERT INTO Season (name, start_date, end_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, season.getName());
            preparedStatement.setDate(2, new java.sql.Date(season.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(season.getEndDate().getTime()));

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    season.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDateRangeSeason(java.util.Date startDate, java.util.Date endDate) {
        return endDate.after(startDate);
    }


    @Override
    public void updateSeason(Season season) {
        if (!isValidDateRange(season.getStartDate(), season.getEndDate())) {
            System.out.println("Error: End date must be after the start date.");
            return;
        }

        String query = "UPDATE Season SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, season.getName());
            preparedStatement.setDate(2, new java.sql.Date(season.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(season.getEndDate().getTime()));
            preparedStatement.setInt(4, season.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSeason(int seasonId) {
        String query = "DELETE FROM Season WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, seasonId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidDateRange(java.util.Date startDate, java.util.Date endDate) {
        return endDate.after(startDate);
    }

    private Season createSeasonFromResultSet(ResultSet resultSet) throws SQLException {
        return new Season(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date")
        );
    }
}
