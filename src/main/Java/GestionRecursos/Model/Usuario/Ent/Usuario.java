package GestionRecursos.Model.Usuario.Ent;

public class Usuario {
    private int id;
    private String nombre;
    private String rol;

    public Usuario(int id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
