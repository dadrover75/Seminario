package Comunication.ConnBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection implements DatabaseConnectionManager {

    // conexion base de datos local
    private static String urlBD = "jdbc:mysql://localhost:3306/seminario";
    private static String usuario = "root";
    private static String pass = "admin";
    private static Connection connection;

    /*
    //  conexion base dde datos clevercloud:
        private static String urlBD = "jdbc:mysql://u53ngguldptjcjou:ajamrvfE4sVkAWvdxwXY@bpa6pfgm41rcui1cjoyo-mysql.services.clever-cloud.com:3306/bpa6pfgm41rcui1cjoyo"; // MYSQL_ADDON_URI= mysql://localhost:3306/seminario
        private static String usuario = "u53ngguldptjcjou"; // root
        private static String pass = "ajamrvfE4sVkAWvdxwXY"; // admin
        private static Connection connection;
    */


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


