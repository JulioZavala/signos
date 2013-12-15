package app.controller.adm.productos;

import app.model.Cliente;
import app.model.Producto;
import java.util.List;

public interface ProductoService {

    List<Cliente> getClientes();

    List<Producto> getProductos();

    Producto getProducto(Producto producto);

    void addProducto(Producto producto);

    void updateProducto(Producto producto);

    void deleteProducto(Producto producto);
}
