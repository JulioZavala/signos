package app.controller.adm.productos;

import app.dao.ClienteDAO;
import app.dao.ProductoDAO;
import app.model.Cliente;
import app.model.Producto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    ProductoDAO productoDAO;
    @Autowired
    ClienteDAO clienteDAO;

    @Override
    public List<Producto> getProductos() {
        return productoDAO.getAllDAO();
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteDAO.getAllDAO();
    }

    @Override
    public Producto getProducto(Producto producto) {
        return productoDAO.getDAO(producto);
    }

    @Override
    public void addProducto(Producto producto) {
        productoDAO.addDAO(producto);
    }

    @Override
    public void updateProducto(Producto producto) {
        productoDAO.updateDAO(producto);
    }

    @Override
    public void deleteProducto(Producto producto) {
        productoDAO.deleteDAO(producto);
    }
}
