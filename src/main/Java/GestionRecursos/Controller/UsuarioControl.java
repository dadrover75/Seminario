package GestionRecursos.Controller;

import GestionRecursos.Model.DAO;
import GestionRecursos.Model.Usuario.DAO.UsuarioDAO;
import GestionRecursos.Model.Usuario.Ent.Usuario;

import java.util.List;

public class UsuarioControl {

    private DAO<Usuario> usuarioDAO = new UsuarioDAO();

    public void crearUsuario(Usuario usuario) {
        usuarioDAO.insert(usuario);
    }

    public void modificarUsuario(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    public void eliminarUsuario(int id) {
        usuarioDAO.delete(id);
    }

    public Usuario buscarUsuario(int id) {
        return usuarioDAO.get(id);
    }

    public List<Usuario> listarUsuario() {
        return usuarioDAO.getAll();
    }

}
