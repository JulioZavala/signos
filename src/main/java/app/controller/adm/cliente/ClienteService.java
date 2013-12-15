package app.controller.adm.cliente;

import app.model.Cliente;
import java.util.List;

public interface ClienteService {

    List<Cliente> getClientes();

    Cliente getCliente(Cliente cliente);

    void addCliente(Cliente cliente);
    
    void updateCliente(Cliente cliente);

    void deleteCliente(Cliente cliente);
}
