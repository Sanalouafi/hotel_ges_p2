package main.java.dao.impl;
import main.java.dao.ClientDao;
import main.java.entities.Client;
import main.java.entities.Person;
import main.java.connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    private final Connection connection;

    public ClientDaoImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Person p INNER JOIN Client c ON p.id = c.user_id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Client client = (Client) createPersonFromResultSet(resultSet);
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client getClientById(int clientId) {
        Client client = null;
        String sql = "SELECT * FROM Person p INNER JOIN Client c ON p.id = c.user_id WHERE c.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                client = (Client) createPersonFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void saveClient(Client client) {
        String personSql = "INSERT INTO Person (fullname, username, password, email, phone_number, address, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String clientSql = "INSERT INTO Client (user_id) VALUES (?)";

        try (PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStatement = connection.prepareStatement(clientSql)) {

            // Insert into Person table
            personStatement.setString(1, client.getFullname());
            personStatement.setString(2, client.getUsername());
            personStatement.setString(3, client.getPassword());
            personStatement.setString(4, client.getEmail());
            personStatement.setString(5, client.getPhoneNumber());
            personStatement.setString(6, client.getAddress());
            personStatement.setTimestamp(7, client.getCreatedAt());
            personStatement.executeUpdate();

            // Get the generated Person ID
            ResultSet generatedKeys = personStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personId = generatedKeys.getInt(1);

                // Insert into Client table with the Person ID
                clientStatement.setInt(1, personId);
                clientStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client client) {
        String personSql = "UPDATE Person SET fullname = ?, username = ?, password = ?, email = ?, phone_number = ?, address = ?, created_at = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(personSql)) {

            statement.setString(1, client.getFullname());
            statement.setString(2, client.getUsername());
            statement.setString(3, client.getPassword());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getPhoneNumber());
            statement.setString(6, client.getAddress());
            statement.setTimestamp(7, client.getCreatedAt());
            statement.setInt(8, client.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClient(int clientId) {
        String sql = "DELETE FROM Person WHERE id = (SELECT user_id FROM Client WHERE id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Person createPersonFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String fullname = resultSet.getString("fullname");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        String phoneNumber = resultSet.getString("phone_number");
        String address = resultSet.getString("address");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        // Return a Client object since Client extends Person
        return new Client(id, fullname, username, password, email, phoneNumber, address, createdAt);
    }
}
