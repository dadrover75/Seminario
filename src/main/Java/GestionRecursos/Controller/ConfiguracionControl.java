package GestionRecursos.Controller;

import GestionRecursos.Model.DAO;
import GestionRecursos.Model.Configuracion.DAO.ConfiguracionDAO;
import GestionRecursos.Model.Configuracion.Ent.Configuracion;

import java.util.List;

public class ConfiguracionControl {

    private DAO<Configuracion> configuracionDAO = new ConfiguracionDAO();

    public void crearConfiguracion(Configuracion usuario) {
        configuracionDAO.insert(usuario);
    }

    public void modificarConfiguracion(Configuracion usuario) {
        configuracionDAO.update(usuario);
    }

    public void eliminarConfiguracion(int id) {
        configuracionDAO.delete(id);
    }

    public Configuracion buscarConfiguracion(int id) {
        return configuracionDAO.get(id);
    }

    public List<Configuracion> listarConfiguracion() {
        return configuracionDAO.getAll();
    }

}
