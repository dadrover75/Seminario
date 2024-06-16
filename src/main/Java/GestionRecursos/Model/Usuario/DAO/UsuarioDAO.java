package GestionRecursos.Model.Usuario.DAO;

import Comunication.ConnBD.DataBaseConnection;
import GestionRecursos.Model.DAO;
import GestionRecursos.Model.Usuario.Ent.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public void insert(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nombre, rol) VALUES (?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void update(Usuario usuario) {

        String sql = "UPDATE usuarios SET nombre = ?, rol = ? WHERE idusuarios = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getRol());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM usuarios WHERE idusuarios = ?";
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
    public Usuario get(int id) {

        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE idusuarios = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuario(rs);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return usuario;

    }

    @Override
    public Usuario getBy(String topic) {
        return null;
    }


    public List<Usuario> getAll() {

        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next()) {
                Usuario usuario = crearUsuario(rs);
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return usuarios;

    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {

        return new Usuario(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
        );
    }

}

