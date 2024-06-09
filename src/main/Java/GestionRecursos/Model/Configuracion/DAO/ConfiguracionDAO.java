package GestionRecursos.Model.Configuracion.DAO;

import Comunication.ConnBD.DataBaseConnection;
import GestionRecursos.Model.Configuracion.Ent.Configuracion;
import GestionRecursos.Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDAO implements DAO<Configuracion> {

    @Override
    public void insert(Configuracion configuracion) {

        String sql = "INSERT INTO configuraciones (idusuarios, minutos_riego, humedad_max, humedad_min) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, configuracion.getUsuario().getId());
            stmt.setInt(2, configuracion.getMinutosRiego());
            stmt.setInt(3, configuracion.getHumMax());
            stmt.setInt(4, configuracion.getHumMin());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void update(Configuracion configuracion) {

        String sql = "UPDATE configuraciones SET idusuarios = ?, minutos_riego = ?, humedad_max = ?, humedad_min = ? WHERE idconfiguraciones = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, configuracion.getUsuario().getId());
            stmt.setInt(3, configuracion.getMinutosRiego());
            stmt.setInt(2, configuracion.getHumMax());
            stmt.setInt(1, configuracion.getHumMin());
            stmt.setInt(5, configuracion.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM configuraciones WHERE idconfiguraciones = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public Configuracion get(int id) {

        Configuracion configuracion = null;
        String sql = "SELECT * FROM configuraciones WHERE idconfiguraciones = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    configuracion = crearConfiguracion(rs);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        return configuracion;

    }

    public List<Configuracion> getAll() {

        List<Configuracion> configuraciones = new ArrayList<>();
        String sql = "SELECT * FROM configuraciones";
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Configuracion configuracion = crearConfiguracion(rs);
                configuraciones.add(configuracion);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return configuraciones;
    }

    private Configuracion crearConfiguracion (ResultSet rs) throws SQLException {

        return new Configuracion(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getInt(4),
                rs.getInt(5));

    }

}

