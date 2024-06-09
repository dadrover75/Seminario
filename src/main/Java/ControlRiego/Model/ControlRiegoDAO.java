package ControlRiego.Model;

import Comunication.ConnBD.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ControlRiegoDAO {

    public Map<String,Integer> getInfoCultivo(String topic){

        Map <String,Integer> infoCultivo = new HashMap<>();
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
                    infoCultivo.put("idcultivos", rs.getInt("idcultivos"));
                    infoCultivo.put("minutos_riego", rs.getInt("minutos_riego"));
                    infoCultivo.put("humedad_min", rs.getInt("humedad_min"));
                    infoCultivo.put("humedad_max", rs.getInt("humedad_max"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        return infoCultivo;

    }

    public int estadoBomba(String topic){

        int estado = 0;
        String sql = "SELECT estado FROM dispositivos WHERE topic = ?;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, topic);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estado = rs.getInt("estado");
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        return estado;

    }

    public void cambiarEstadoBomba(String topic){

        int estado = (estadoBomba(topic) == 0) ? 1 : 0;
        String sql = "UPDATE dispositivos SET estado = ? WHERE topic = ?;";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, estado);
            stmt.setString(2, topic);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

}
