package GestionRecursos.Model.Configuracion.Ent;

import GestionRecursos.Model.Usuario.DAO.UsuarioDAO;
import GestionRecursos.Model.Usuario.Ent.Usuario;

public class Configuracion {
    private int id;
    private Usuario usuario;
    private int minutosRiego;
    private int humMax;
    private int humMin;

    public Configuracion(int id, int usuarioId, int minutosRiego, int humMax, int humMin) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        this.id = id;
        this.usuario = usuarioDAO.get(usuarioId);
        this.minutosRiego = minutosRiego;
        this.humMax = humMax;
        this.humMin = humMin;
    }

    public Configuracion() {
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getHumMin() {
        return humMin;
    }

    public void setHumMin(int humMin) {
        this.humMin = humMin;
    }

    public int getHumMax() {
        return humMax;
    }

    public void setHumMax(int humMax) {
        this.humMax = humMax;
    }

    public int getMinutosRiego() {
        return minutosRiego;
    }

    public void setMinutosRiego(int minutosRiego) {
        this.minutosRiego = minutosRiego;
    }
}
