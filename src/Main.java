import main.java.connection.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getInstance().getConnection();
    }
}
