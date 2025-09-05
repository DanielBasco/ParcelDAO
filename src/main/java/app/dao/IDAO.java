package app.dao;

import java.util.List;

public interface IDAO <T, I>{

    public T create(T t);
    List<T> getAll();
    public T getById(I id);
    public T  update(T t);
    public boolean delete(T t);
}
