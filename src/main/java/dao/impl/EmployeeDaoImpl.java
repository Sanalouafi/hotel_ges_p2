package main.java.dao.impl;

import main.java.dao.EmployeeDao;
import main.java.entities.Employee;
import main.java.entities.Person;
import main.java.connection.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl extends EmployeeDao {

    private final Connection connection;

    public EmployeeDaoImpl() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Person p INNER JOIN Employee e ON p.id = e.user_id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = (Employee) createPersonFromResultSet(resultSet);
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String sql = "SELECT * FROM Person p INNER JOIN Employee e ON p.id = e.user_id WHERE e.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employee = (Employee) createPersonFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void saveEmployee(Employee employee) {
        String personSql = "INSERT INTO Person (fullname, username, password, email, phone_number, address, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String employeeSql = "INSERT INTO Employee (user_id, position, hire_date, salary) VALUES (?, ?, ?, ?)";

        try (PreparedStatement personStatement = connection.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {


            personStatement.setString(1, employee.getFullname());
            personStatement.setString(2, employee.getUsername());
            personStatement.setString(3, employee.getPassword());
            personStatement.setString(4, employee.getEmail());
            personStatement.setString(5, employee.getPhoneNumber());
            personStatement.setString(6, employee.getAddress());
            personStatement.setTimestamp(7, employee.getCreatedAt());
            personStatement.executeUpdate();


            ResultSet generatedKeys = personStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personId = generatedKeys.getInt(1);


                employeeStatement.setInt(1, personId);
                employeeStatement.setString(2, employee.getPosition());
                employeeStatement.setDate(3, employee.getHireDate());
                employeeStatement.setBigDecimal(4, employee.getSalary());
                employeeStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        String personSql = "UPDATE Person SET fullname = ?, username = ?, password = ?, email = ?, phone_number = ?, address = ?, created_at = ? WHERE id = ?";
        String employeeSql = "UPDATE Employee SET position = ?, hire_date = ?, salary = ? WHERE user_id = ?";
        try (PreparedStatement personStatement = connection.prepareStatement(personSql);
             PreparedStatement employeeStatement = connection.prepareStatement(employeeSql)) {


            personStatement.setString(1, employee.getFullname());
            personStatement.setString(2, employee.getUsername());
            personStatement.setString(3, employee.getPassword());
            personStatement.setString(4, employee.getEmail());
            personStatement.setString(5, employee.getPhoneNumber());
            personStatement.setString(6, employee.getAddress());
            personStatement.setTimestamp(7, employee.getCreatedAt());
            personStatement.setInt(8, employee.getId());
            personStatement.executeUpdate();


            employeeStatement.setString(1, employee.getPosition());
            employeeStatement.setDate(2, employee.getHireDate());
            employeeStatement.setBigDecimal(3, employee.getSalary());
            employeeStatement.setInt(4, employee.getId());
            employeeStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Person WHERE id = (SELECT user_id FROM Employee WHERE id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeId);
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
        String position = resultSet.getString("position");
        Date hireDate = resultSet.getDate("hire_date");
        BigDecimal salary = resultSet.getBigDecimal("salary");


        return new Employee(id, fullname, username, password, email, phoneNumber, address, createdAt, position, hireDate, salary);
    }
}
