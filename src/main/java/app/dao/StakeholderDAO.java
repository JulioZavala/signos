package app.dao;

import app.model.Producto;
import app.model.Stakeholder;
import app.zelper.dao.GenericDAO;
import java.util.List;

public interface StakeholderDAO extends GenericDAO<Stakeholder> {

    Stakeholder getByEmail(String email);
    public List<Stakeholder> getByProducto(Producto producto);
    
}
