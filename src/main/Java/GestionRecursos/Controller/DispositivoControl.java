package GestionRecursos.Controller;

import GestionRecursos.Model.DAO;
import GestionRecursos.Model.Dispositivo.DAO.DispositivoDAO;
import GestionRecursos.Model.Dispositivo.Ent.Dispositivo;

import java.util.List;

public class DispositivoControl {

    private DispositivoDAO dispositivoDAO = new DispositivoDAO();

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

    public Dispositivo getSensorEsByCultivo( int id ) { return dispositivoDAO.getSensorsByCultivo(id); }

    public Dispositivo buscarBombasPorCultivo(int id) { return dispositivoDAO.getBombaByCultivo(id); }

    public Dispositivo buscarDispositivoPorTopic(String topic) { return dispositivoDAO.getByTopic(topic); }

    public Integer estadoDispositivo(String topic){ return buscarDispositivoPorTopic(topic).getEstado(); }

    public void cambiarEstadoBomba(String topic) { dispositivoDAO.cambiarEstadoBomba(topic); }

}
