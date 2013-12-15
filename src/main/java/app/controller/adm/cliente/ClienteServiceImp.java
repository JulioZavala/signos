package app.controller.adm.cliente;

import app.dao.ClienteDAO;
import app.model.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    ClienteDAO clienteDAO;

    @Override
    public List<Cliente> getClientes() {
        return clienteDAO.getAllDAO();
    }

    @Override
    public Cliente getCliente(Cliente cliente) {
        return clienteDAO.getDAO(cliente);
    }

    @Override
    public void addCliente(Cliente cliente) {
        clienteDAO.addDAO(cliente);
    }

    @Override
    public void updateCliente(Cliente cliente) {
        clienteDAO.updateDAO(cliente);
    }

    @Override
    public void deleteCliente(Cliente cliente) {
        clienteDAO.deleteDAO(cliente);
    }
}
