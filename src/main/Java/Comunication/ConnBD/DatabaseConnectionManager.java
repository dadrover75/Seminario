package Comunication.ConnBD;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionManager {
    static Connection getConnection() throws SQLException{
        return null;
    };
    static void closeConnection() throws SQLException{};
}
