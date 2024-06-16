package GestionRecursos.Model.Cultivo.DAO;

import Comunication.ConnBD.DataBaseConnection;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CultivoDAO implements DAO<Cultivo> {

    @Override
    public void insert(Cultivo cultivo) {

        String sql = "INSERT INTO cultivos (descripcion, idconfiguraciones) VALUES (?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, cultivo.getDescripcion());
            stmt.setInt(2, cultivo.getConfiguracion().getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void update(Cultivo cultivo) {

        String sql = "UPDATE cultivos SET descripcion = ?, idconfiguraciones = ? WHERE idcultivos = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, cultivo.getDescripcion());
            stmt.setInt(2, cultivo.getConfiguracion().getId());
            stmt.setInt(3, cultivo.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM cultivos WHERE idcultivos = ?";
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
    public Cultivo get(int id) {

        Cultivo cultivo = null;
        String sql = "SELECT * FROM cultivos WHERE idcultivos = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cultivo = crearCultivo(rs);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return cultivo;

    }

    @Override
    public Cultivo getBy(String topic) {

        Cultivo cultivo = null;
        String sql = "SELECT c.*" +
                "FROM cultivos c" +
                "JOIN cultivos_dispositivos cd ON c.idcultivos = cd.idcultivos" +
                "JOIN dispositivos d ON cd.iddispositivos = d.iddispositivos" +
                "WHERE d.topic = ?;";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, topic);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cultivo = crearCultivo(rs);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return cultivo;

    }


    public List<Cultivo> getAll() {

        List<Cultivo> cultivos = new ArrayList<>();
        String sql = "SELECT * FROM cultivos";
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Cultivo cultivo = crearCultivo(rs);
                cultivos.add(cultivo);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return cultivos;

    }

    private Cultivo crearCultivo(ResultSet rs) throws SQLException {

        return new Cultivo(rs.getInt(1), rs.getString(2), rs.getInt(3));

    }

}

