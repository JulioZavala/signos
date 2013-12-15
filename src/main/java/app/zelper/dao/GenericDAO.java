
package app.zelper.dao;

import java.util.List;

public interface GenericDAO<T> {

    public void addDAO(T t);

    public void updateDAO(T t);

    public void deleteDAO(T t);
    
    public T getDAO(T t);

    public List<T> getAllDAO();

    
}
