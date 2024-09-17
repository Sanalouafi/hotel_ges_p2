package main.java.dao.impl;

import main.java.dao.PersonDao;
import main.java.entities.Person;
import main.java.connection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PersonDaoImpl implements PersonDao {

    protected Connection getConnection() {
        return DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM person";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Person person = createPersonFromResultSet(resultSet);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    @Override
    public Person getPersonById(int personId) {
        Person person = null;
        String query = "SELECT * FROM person WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                person = createPersonFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void savePerson(Person person) {
        String query = "INSERT INTO persons (fullname, username, password, email, phoneNumber, address, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)"; // Adjust the table name as needed

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, person.getFullname());
            preparedStatement.setString(2, person.getUsername());
            preparedStatement.setString(3, person.getPassword());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getPhoneNumber());
            preparedStatement.setString(6, person.getAddress());
            preparedStatement.setTimestamp(7, person.getCreatedAt());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePerson(Person person) {
        String query = "UPDATE person SET fullname = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ?, createdAt = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, person.getFullname());
            preparedStatement.setString(2, person.getUsername());
            preparedStatement.setString(3, person.getPassword());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getPhoneNumber());
            preparedStatement.setString(6, person.getAddress());
            preparedStatement.setTimestamp(7, person.getCreatedAt());
            preparedStatement.setInt(8, person.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(int personId) {
        String query = "DELETE FROM person WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, personId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //abstract class for creating a person object from a res
    protected abstract Person createPersonFromResultSet(ResultSet resultSet) throws SQLException;
}
