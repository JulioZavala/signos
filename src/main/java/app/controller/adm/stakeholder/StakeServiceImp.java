package app.controller.adm.stakeholder;

import app.dao.ClienteDAO;
import app.dao.PersonaDAO;
import app.dao.ProductoDAO;
import app.dao.StakeholderDAO;
import app.model.Cliente;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StakeServiceImp implements StakeService {

    @Autowired
    ClienteDAO clienteDAO;
    @Autowired
    PersonaDAO personaDAO;
    @Autowired
    StakeholderDAO stakeholderDAO;
    @Autowired
    ProductoDAO productoDAO;

    @Override
    public List<Cliente> getClientes() {
        return clienteDAO.getAllDAO();
    }

    @Override
    public List<Producto> getProductos(Cliente c) {
        return productoDAO.getByCliente(c);
    }

    @Override
    public List<Stakeholder> getStakeholders() {
        return stakeholderDAO.getAllDAO();
    }

    @Override
    public Stakeholder getStake(Stakeholder stake) {
        return stakeholderDAO.getDAO(stake);
    }

    @Override
    public void addStake(Stakeholder stake) {
        personaDAO.addDAO(stake.getPersona());
        stakeholderDAO.addDAO(stake);
    }

    @Override
    public void updateStake(Stakeholder stake) {
        personaDAO.updateDAO(stake.getPersona());
        stakeholderDAO.updateDAO(stake);
    }

    @Override
    public void deleteStake(Stakeholder stake) {

        Stakeholder s = stakeholderDAO.getDAO(stake);
        Persona persona = new Persona(s.getPersona().getId());
  
        stakeholderDAO.deleteDAO(stake);

        personaDAO.deleteDAO(persona);

    }
}
