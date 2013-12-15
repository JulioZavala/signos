
package app.controller.adm.stakeholder;

import app.model.Cliente;
import app.model.Producto;
import app.model.Stakeholder;
import java.util.List;


public interface StakeService {
    
    List<Cliente> getClientes();
    
    List<Producto> getProductos(Cliente cliente);

    List<Stakeholder> getStakeholders();

    Stakeholder getStake(Stakeholder stake);

    void addStake(Stakeholder stake);
    
    void updateStake(Stakeholder stake);

    void deleteStake(Stakeholder stake);
    
}
