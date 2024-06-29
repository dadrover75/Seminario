package GestionRecursos.Controller;

import GestionRecursos.Model.DAO;
import GestionRecursos.Model.Dispositivo.DAO.DispositivoDAO;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import java.util.List;

public class DispositivoControl {

    private DAO<Dispositivo> dispositivoDAO = new DispositivoDAO();

    public void crearDispositivo(Dispositivo dispositivo) {
        dispositivoDAO.insert(dispositivo);
    }

    public void modificarDispositivo(Dispositivo dispositivo) {
        dispositivoDAO.update(dispositivo);
    }

    public void eliminarDispositivo(int id) {
        dispositivoDAO.delete(id);
    }

    public Dispositivo buscarDispositivo(int id) {
        return dispositivoDAO.get(id);
    }

    public List<Dispositivo> listarDispositivo() {
        return dispositivoDAO.getAll();
    }

    public Dispositivo buscarDispositivoPorTopic(String topic) { return ((DispositivoDAO) dispositivoDAO).getByTopic(topic); }

    public Integer estadoDispositivo(String topic){ return buscarDispositivoPorTopic(topic).getEstado(); }

    public void cambiarEstadoBomba(String topic) { ((DispositivoDAO) dispositivoDAO).cambiarEstadoBomba(topic); }

    public List<Dispositivo> listarDispCultivo(String topic) { return ((DispositivoDAO) dispositivoDAO).getAllByCultivo(topic); }

    public List<Dispositivo> listarDispCultivo(int id) { return ((DispositivoDAO) dispositivoDAO).getAllByCultivoID(id); }

}
