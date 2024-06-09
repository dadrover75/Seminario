package GestionRecursos.Model.Cultivo.Ent;

import GestionRecursos.Model.Configuracion.DAO.ConfiguracionDAO;
import GestionRecursos.Model.Configuracion.Ent.Configuracion;

public class Cultivo {

    int id;
    String descripcion;
    Configuracion configuracion;

    public Cultivo(int id, String descripcion, int idConfig) {
        ConfiguracionDAO configuracionDAO = new ConfiguracionDAO();

        this.id = id;
        this.descripcion = descripcion;
        this.configuracion = configuracionDAO.get(idConfig);
    }

    public Cultivo() {
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Configuracion getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

}
