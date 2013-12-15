package app.controller.ticket;

import app.model.Mensaje;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import app.model.Ticket;
import app.zelper.enums.Usuarios;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface TicketService {

    Stakeholder getStakeholder(String email);

    Persona getStaff(String email);

    boolean checkStakeholder(String email);

    boolean checkTicket(String email, Integer codigoTicket, Usuarios tipo);

    List<Producto> getProductos(String stakeEmail);

    void addTicket(Ticket ticket, String stakeEmail);

    Ticket getTicket(Ticket ticket);

    ObjectNode addMessage(Mensaje mensaje);

    List<Ticket> getTickets(Ticket ticket);
    
    String upload(MultipartFile file, Ticket ticket) throws IOException;
    
    public void remove(String fileName, Ticket ticket);
    
    List getTotales(Ticket ticket);
}
