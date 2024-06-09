package ControlRiego.Model;

import Comunication.ConnBD.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControlRiegoDAO {

    public Map<String,Integer> getInfoRiego(String topic){

        Map <String,Integer> infoRiego = new HashMap<>();
        String sql  = "SELECT c.idcultivos, c.descripcion, conf.minutos_riego, conf.humedad_min, conf.humedad_max " +
                "FROM dispositivos d JOIN cultivos_dispositivos cd ON d.iddispositivos = cd.iddispositivos " +
                "JOIN cultivos c ON cd.idcultivos = c.idcultivos " +
                "JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones " +
                "WHERE d.topic = ?;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, topic);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    infoRiego.put("idcultivos", rs.getInt("idcultivos"));
                    infoRiego.put("minutos_riego", rs.getInt("minutos_riego"));
                    infoRiego.put("humedad_min", rs.getInt("humedad_min"));
                    infoRiego.put("humedad_max", rs.getInt("humedad_max"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        return infoRiego;

    }

    public String getTopicBomba(String topicSensor){

        String topicBomba = null;
        String sql = "SELECT d.topic " +
                "FROM dispositivos d JOIN cultivos_dispositivos cd ON d.iddispositivos = cd.iddispositivos " +
                "JOIN cultivos c ON cd.idcultivos = c.idcultivos " +
                "JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones " +
                "WHERE d.topic LIKE \"%actuator%\" " +
                "AND c.idcultivos = ( " +
                "SELECT idcultivos FROM ( " +
                "SELECT c.idcultivos, c.descripcion, conf.minutos_riego, conf.humedad_min, conf.humedad_max " +
                "FROM dispositivos d JOIN cultivos_dispositivos cd ON d.iddispositivos = cd.iddispositivos " +
                "JOIN cultivos c ON cd.idcultivos = c.idcultivos " +
                "JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones " +
                "WHERE d.topic = ?) " +
                "AS RESULTADO);";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, topicSensor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    topicBomba = rs.getString("topic");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        return topicBomba;

    }


}
