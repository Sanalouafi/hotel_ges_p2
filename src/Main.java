import main.java.connection.DatabaseConnection;
import main.java.menu.MainMenu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getInstance().getConnection();
        MainMenu mainMenu = new MainMenu();
        mainMenu.showMainMenu();
    }
}
