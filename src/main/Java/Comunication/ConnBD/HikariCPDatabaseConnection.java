package Comunication.ConnBD;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDatabaseConnection implements DatabaseConnectionManager {

    public static Connection getConnection() throws SQLException {
        return null;
    }

    public static void closeConnection() throws SQLException {

    }
}
