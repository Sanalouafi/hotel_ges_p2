package main.java.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    // Use the service name 'postgresql' from the Docker Compose configuration
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_ges_p2";
    private static final String USER = "Hotel_ges_p2";
    private static final String PASSWORD = "1234";

    private DatabaseConnection() {
        try {
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
