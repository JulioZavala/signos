package app.controller.staff;

import app.controller.adjunto.AdjuntoService;
import app.dao.AdjuntoTicketDAO;
import app.dao.ClienteDAO;
import app.dao.MensajeDAO;
import app.dao.PersonaDAO;
import app.dao.ProductoDAO;
import app.dao.StakeholderDAO;
import app.dao.TicketDAO;
import app.mail.MailerService;
import app.model.AdjuntoMensaje;
import app.model.Cliente;
import app.model.Mensaje;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import app.model.Ticket;
import app.zelper.constant.MensajeMail;
import app.zelper.enums.EstadoTicket;
import app.zelper.enums.PrioridadTicket;
import app.zelper.enums.Usuarios;
import app.zelper.misc.TypesUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImp implements StaffService {

    @Autowired
    StakeholderDAO stakeDAO;
    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    PersonaDAO personaDAO;
    @Autowired
    MensajeDAO mensajeDAO;
    @Autowired
    ProductoDAO productoDAO;
    @Autowired
    ClienteDAO clienteDAO;
    @Autowired
    AdjuntoTicketDAO adjuntoTicketDAO;
    @Autowired
    AdjuntoService adjuntoService;
    @Autowired
    MailerService mailerService;

    @Override
    public List<Cliente> getClientes() {
        return clienteDAO.getAllDAO();
    }

    @Override
    public List<Producto> getProductos(Cliente cliente) {
        return productoDAO.getByCliente(cliente);
    }

    @Override
    public List<Stakeholder> getStakeHolder(Producto producto) {
        return stakeDAO.getByProducto(producto);
    }

    @Override
    public List<Ticket> getTickets(Ticket ticket) {
        return ticketDAO.getByStatusAndStaff(ticket);
    }

    @Override
    public void addTicket(Ticket ticket) {

        ticket.setCodigo(this.getTicketId());
        ticket.setFechaCreacion(new Date());
        ticket.setFechaActualiza(ticket.getFechaCreacion());
        ticket.setEstado(EstadoTicket.Activo.getValue());
        ticket.setPrioridad(PrioridadTicket.Normal.getValue());
        ticketDAO.addDAO(ticket);

        adjuntoService.saveAdjuntosTicket(ticket.getAdjuntos());

        mailerService.generateMailTicket(ticket, MensajeMail.STAKE_CREATE_STAFF, Usuarios.Stakeholder);

    }

    @Override
    public Ticket getTicket(Ticket ticket) {
        return ticketDAO.getByCodigoDAO(ticket);
    }

    @Override
    public ObjectNode addMessage(Mensaje mensaje) {
        mensajeDAO.addDAO(mensaje);
        this.updateTicket(mensaje.getTicket(), EstadoTicket.Respondido);

        adjuntoService.saveAdjuntosMensaje(mensaje.getAdjuntos());

        JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
        ObjectNode t = new ObjectNode(jsonFactory);
        t.put("nombreCreador", mensaje.getPersona().getNombreCompleto());
        t.put("fechaCreacion", TypesUtil.getStringDate(mensaje.getFechaCreacion(), "dd/MM/yyyy hh:mm"));
        t.put("mensaje", mensaje.getMensajeHtml());

        ArrayNode array = new ArrayNode(jsonFactory);
        for (AdjuntoMensaje adjunto : mensaje.getAdjuntos()) {
            ObjectNode a = new ObjectNode(jsonFactory);
            a.put("titulo", adjunto.getTitulo());
            a.put("nombre", adjunto.getNombre());
            a.put("ticket", mensaje.getTicket().getCodigo());

            array.add(a);
        }
        t.put("adjuntos", array);

        mailerService.generateMailMessage(mensaje, MensajeMail.STAKE_REPLY, Usuarios.Stakeholder);
        return t;

    }

    public void updateTicket(Ticket ticket, EstadoTicket estado) {
        ticket.setEstado(estado.getValue());
        ticket.setFechaActualiza(new Date());
        ticketDAO.updateDAO(ticket);
    }

    private Integer getTicketId() {
        boolean flag = false;
        Integer ticketId = 0;
        do {
            ticketId = TypesUtil.getRandom();

            Ticket temporal = new Ticket();
            temporal.setCodigo(ticketId);

            Ticket existe = ticketDAO.getByCodigoDAO(temporal);

            flag = (existe != null);
        } while (flag);

        return ticketId;
    }

    @Override
    public void updateTicket(Ticket ticket) {
        ticketDAO.updateDAO(ticket);
    }

    @Override
    public List<Persona> getStaff() {
        return personaDAO.getAllStaffDAO();
    }

    @Override
    public void notifyAssign(Ticket ticket) {
        mailerService.generateMailTicket(ticket, MensajeMail.STAFF_ASSIGN, Usuarios.Staff);
    }

    @Override
    public List getTotales(Ticket ticket) {
        return ticketDAO.getTotalByStaff(ticket);
    }
}
