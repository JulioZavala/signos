package app.dao;

import app.model.Cliente;
import app.model.Producto;
import app.zelper.dao.GenericDAO;
import java.util.List;

public interface ProductoDAO extends GenericDAO<Producto> {

    List<Producto> getByCliente(Cliente cliente);
}
