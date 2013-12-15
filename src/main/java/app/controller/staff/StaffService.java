package app.controller.staff;

import app.model.Cliente;
import app.model.Mensaje;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import app.model.Ticket;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;

public interface StaffService {

    List<Persona> getStaff();
    
    List<Cliente> getClientes();

    List<Producto> getProductos(Cliente cliente);

    List<Stakeholder> getStakeHolder(Producto producto);

    void addTicket(Ticket ticket);

    Ticket getTicket(Ticket ticket);

    ObjectNode addMessage(Mensaje mensaje);

    List<Ticket> getTickets(Ticket ticket);
    
    void updateTicket(Ticket ticket);
    
    void notifyAssign(Ticket ticket);
    
    List getTotales(Ticket ticket);
}
