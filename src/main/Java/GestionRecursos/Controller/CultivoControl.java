package GestionRecursos.Controller;

import GestionRecursos.Model.Cultivo.DAO.CultivoDAO;
import GestionRecursos.Model.Cultivo.Ent.Cultivo;
import GestionRecursos.Model.DAO;

import java.util.List;

public class CultivoControl {

    private DAO<Cultivo> cultivoDAO = new CultivoDAO();

    public void crearCultivo(Cultivo cultivo) {
        cultivoDAO.insert(cultivo);
    }

    public void modificarCultivo(Cultivo cultivo) {
        cultivoDAO.update(cultivo);
    }

    public void eliminarCultivo(int id) {
        cultivoDAO.delete(id);
    }

    public Cultivo buscarCultivo(int id) {
        return cultivoDAO.get(id);
    }

    public List<Cultivo> listarCultivo() {
        return cultivoDAO.getAll();
    }

    public Cultivo buscarCultivoPorTopic(String topic) { return cultivoDAO.getBy(topic); }

}
