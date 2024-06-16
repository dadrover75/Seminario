package GestionRecursos.Model;

import GestionRecursos.Model.Cultivo.Ent.Cultivo;

import java.util.List;

public interface DAO<T> {

    public void insert(T t);
    public void update(T t);
    public void delete(int id);
    public T get(int id);
    T getBy(String topic);
    public List<T> getAll();

}
