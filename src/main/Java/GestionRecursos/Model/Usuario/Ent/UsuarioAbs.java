package GestionRecursos.Model.Usuario.Ent;

public abstract class UsuarioAbs {
    protected int id;
    protected String nombre;
    protected String password;
    protected String rol;

    public abstract int getId();

    public abstract String getNombre();

    public abstract String getPassword();

    public abstract void setPassword(String password);

    public abstract void setNombre(String nombre);

    public abstract String getRol();

    public abstract void setRol(String rol);
}
