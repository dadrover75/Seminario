package GestionRecursos.Model;

import java.util.List;

public interface DAO<T> {

    public void insert(T t);
    public void update(T t);
    public void delete(int id);
    public T get(int id);
    public List<T> getAll();

}
