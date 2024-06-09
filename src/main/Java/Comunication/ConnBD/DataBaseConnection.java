package Comunication.ConnBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection implements DatabaseConnectionManager {

    private static String urlBD = "jdbc:mysql://localhost:3306/seminario";
    private static String usuario = "root";
    private static String pass = "admin";
    private static Connection connection;


    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(urlBD, usuario, pass);
        return connection;
    }


    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}


