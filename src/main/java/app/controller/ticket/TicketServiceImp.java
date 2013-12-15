package app.controller.ticket;

import app.controller.adjunto.AdjuntoService;
import app.controller.adm.cliente.ClienteController;
import app.dao.AdjuntoTicketDAO;
import app.dao.MensajeDAO;
import app.dao.PersonaDAO;
import app.dao.StakeholderDAO;
import app.dao.TicketDAO;
import app.mail.MailerService;
import app.model.AdjuntoMensaje;
import app.model.AdjuntoTicket;
import app.model.Mensaje;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.constant.MensajeMail;
import app.zelper.enums.EstadoTicket;
import app.zelper.enums.PrioridadTicket;
import app.zelper.enums.Usuarios;
import app.zelper.misc.FileSave;
import app.zelper.misc.TypesUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TicketServiceImp implements TicketService {

    @Autowired
    StakeholderDAO stakeDAO;
    @Autowired
    TicketDAO ticketDAO;
    @Autowired
    PersonaDAO personaDAO;
    @Autowired
    MensajeDAO mensajeDAO;
    @Autowired
    AdjuntoTicketDAO adjuntoTicketDAO;
    @Autowired
    AdjuntoService adjuntoService;
    @Autowired
    MailerService mailerService;
    private static final Logger logger = getLogger(ClienteController.class);

    @Override
    public Stakeholder getStakeholder(String email) {
        return stakeDAO.getByEmail(email);
    }

    @Override
    public List getTotales(Ticket ticket) {
        return ticketDAO.getTotalByStake(ticket);
    }

    @Override
    public Persona getStaff(String email) {
        return personaDAO.getByEmail(email);
    }

    @Override
    public boolean checkStakeholder(String email) {
        Stakeholder stake = stakeDAO.getByEmail(email);
        return (stake != null);
    }

    @Override
    public boolean checkTicket(String email, Integer codigo, Usuarios usuario) {
        Ticket ticket = null;

        if (usuario == Usuarios.Stakeholder) {
            Stakeholder stake = this.getStakeholder(email);
            if (stake != null) {
                ticket = ticketDAO.getByCodigoAndStakeDAO(stake.getProductos(), codigo);
            }
        } else if (usuario == Usuarios.Staff) {

            ticket = ticketDAO.getByCodigoAndStaffDAO(email, codigo);

        }

        return (ticket != null);
    }

    @Override
    public List<Producto> getProductos(String stakeEmail) {
        Stakeholder stake = stakeDAO.getByEmail(stakeEmail);
        return stake.getProductos();
    }

    @Override
    public void addTicket(Ticket ticket, String stakeEmail) {
        Stakeholder stake = stakeDAO.getByEmail(stakeEmail);
        ticket.setCodigo(this.getTicketId());
        ticket.setStake(stake.getPersona());
        ticket.setFechaCreacion(new Date());
        ticket.setFechaActualiza(ticket.getFechaCreacion());
        ticket.setEstado(EstadoTicket.Activo.getValue());
        ticket.setPrioridad(PrioridadTicket.Normal.getValue());

        Persona persona = personaDAO.getByEmail(AppConstant.EMAIL_SOPORTE);
        ticket.setStaff(persona);

        ticketDAO.addDAO(ticket);

        adjuntoService.saveAdjuntosTicket(ticket.getAdjuntos());


        mailerService.generateMailTicket(ticket, MensajeMail.STAKE_CREATE, Usuarios.Stakeholder);
        mailerService.generateMailTicket(ticket, MensajeMail.STAFF_CREATE, Usuarios.Staff);

    }

    @Override
    public List<Ticket> getTickets(Ticket ticket) {
        return ticketDAO.getByStatusAndStake(ticket);
    }

    @Override
    public Ticket getTicket(Ticket ticket) {
        return ticketDAO.getByCodigoDAO(ticket);
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
    public ObjectNode addMessage(Mensaje mensaje) {
        mensajeDAO.addDAO(mensaje);
        this.updateTicket(mensaje.getTicket(), EstadoTicket.Activo);

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


        mailerService.generateMailMessage(mensaje, MensajeMail.STAFF_REPLY, Usuarios.Staff);

        return t;

    }

    public void updateTicket(Ticket ticket, EstadoTicket estado) {
        ticket.setFechaActualiza(new Date());
        ticket.setEstado(estado.getValue());
        ticketDAO.updateDAO(ticket);
    }

    @Override
    public String upload(MultipartFile multipartFile, Ticket ticket) throws IOException {

        String fileName = TypesUtil.getUnixTime() + "." + TypesUtil.getClean(multipartFile.getOriginalFilename());
        String absoluteName = AppConstant.TMP_DIR + fileName;
        FileSave.saveToDisk(multipartFile, absoluteName);

        AdjuntoTicket adjunto = new AdjuntoTicket();
        adjunto.setFechaCreacion(new Date());
        adjunto.setTitulo(multipartFile.getOriginalFilename());
        adjunto.setNombre(fileName);
        adjunto.setContentType(multipartFile.getContentType());
        adjunto.setSize(multipartFile.getSize());
        adjunto.setTicket(ticket);

        ticket.getAdjuntos().add(adjunto);

        return fileName;

    }

    @Override
    public void remove(String fileName, Ticket ticket) {
        String absoluteName = AppConstant.TMP_DIR + fileName;

        List<AdjuntoTicket> adjuntos = ticket.getAdjuntos();
        for (AdjuntoTicket a : adjuntos) {
            if (a.getNombre().equals(fileName)) {

                FileSave.deleteFromDisk(absoluteName);
                adjuntos.remove(a);
                break;
            }
        }
    }
}
