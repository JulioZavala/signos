package app.controller.ticket;

import app.controller.adm.cliente.ClienteController;
import app.controller.auth.AuthManually;
import app.model.AdjuntoMensaje;
import app.model.Mensaje;
import app.model.Persona;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import app.zelper.enums.EstadoTicket;
import app.zelper.enums.Usuarios;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/*")
@SessionAttributes("ticket")
public class TicketController {

    @Autowired
    TicketService service;
    @Autowired
    AuthManually authManually;
    private static final Logger logger = getLogger(ClienteController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpSession session) {
        Persona usuario = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);

        if (usuario != null) {
            if (usuario.getUsuario().getTipoUsuario() == Usuarios.Administrador.getValue()) {
                return ("redirect:/adm/cliente");

            } else if (usuario.getUsuario().getTipoUsuario() == Usuarios.Staff.getValue()) {
                return ("redirect:/staff");
            }
        }


        session.setAttribute(AppConstant.NEW_TICKET_EMAIL, null);
        return "/ticket/index";
    }

    @ResponseBody
    @RequestMapping("checkEmail")
    public JsonResponse checkEmail(@RequestParam String email, HttpSession session) {
        JsonResponse json = new JsonResponse();
        try {
            boolean existMail = service.checkStakeholder(email);
            json.setSuccess(existMail);
            json.setMessage("Correo no registrado.");

            if (existMail) {
                session.setAttribute(AppConstant.NEW_TICKET_EMAIL, email);
            }

        } catch (Exception e) {
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            json.setSuccess(false);
        } finally {
            return json;
        }
    }

    @ResponseBody
    @RequestMapping("checkTicket")
    public JsonResponse checkTicket(@RequestParam String email, @RequestParam Integer nro_ticket) {
        JsonResponse json = new JsonResponse();
        try {
            boolean existTicket = service.checkTicket(email, nro_ticket, Usuarios.Stakeholder);
            json.setSuccess(existTicket);
            json.setMessage("# Ticket o Correo erroneos.");

            if (existTicket) {
                json.setData("stake/" + nro_ticket + "/" + email);
            }

        } catch (Exception e) {
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            json.setSuccess(false);
        } finally {
            return json;
        }
    }

    @RequestMapping("newTicket")
    public String newTicket(HttpSession session, Model model) {
        String stakeEmail = (String) session.getAttribute(AppConstant.NEW_TICKET_EMAIL);
        if (stakeEmail == null) {
            return "redirect:/index";
        }

        model.addAttribute("productos", service.getProductos(stakeEmail));
        model.addAttribute("ticket", new Ticket());
        return "/ticket/new";
    }

    @ResponseBody
    @RequestMapping("saveTicket")
    public JsonResponse saveTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session) {
        JsonResponse json = new JsonResponse();
        try {

            String stakeEmail = (String) session.getAttribute(AppConstant.NEW_TICKET_EMAIL);
            service.addTicket(ticket, stakeEmail);

            json.setMessage("Ticket creado con éxito. En breve responderemos su consulta.");
            json.setSuccess(true);

        } catch (Exception e) {
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            json.setSuccess(false);
            e.printStackTrace();
        } finally {

            return json;
        }
    }

    @RequestMapping("stake/{ticket}/{email:.*}")
    public String ticketDetailStake(@PathVariable Integer ticket, @PathVariable String email, Model model, HttpSession session) {

        if (!service.checkTicket(email, ticket, Usuarios.Stakeholder)) {
            return "redirect:/";
        }

        authManually.login(email, "pass", Usuarios.Stakeholder.toString());
        session.setAttribute(AppConstant.SESSION_USUARIO, service.getStakeholder(email).getPersona());
        session.setAttribute(AppConstant.NEW_TICKET_EMAIL, email);

        model.addAttribute("ticket", service.getTicket(new Ticket(ticket)));

        return "/ticket/ticket";
    }

    @RequestMapping("ffats/{ticket}/{email:.*}")
    public String ticketDetailStaff(@PathVariable Integer ticket, @PathVariable String email, Model model, HttpSession session) {

        if (!service.checkTicket(email, ticket, Usuarios.Staff)) {
            return "redirect:/";
        }

        authManually.login(email, "pass", Usuarios.Staff.toString());
        session.setAttribute(AppConstant.SESSION_USUARIO, service.getStaff(email));

        model.addAttribute("ticket", service.getTicket(new Ticket(ticket)));
        return "/staff/ticket";
    }

    @RequestMapping("ticket/list")
    public ModelAndView listTicket(@RequestParam(required = false, defaultValue = "open") String status, HttpSession session, Model model) {
        Ticket ticket = new Ticket();

        EstadoTicket estado = (status.equals("closed")) ? EstadoTicket.Resuelto : EstadoTicket.Activo;
        ticket.setEstado(estado.getValue());

        Persona persona = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);
        ticket.setStake(persona);

        List<Ticket> tickets = service.getTickets(ticket);


        model.addAttribute("tickets", tickets);
        model.addAttribute("totales", service.getTotales(ticket));

        return new ModelAndView("/ticket/list");
    }

    @RequestMapping("ticket/{ticket}")
    public ModelAndView ticketDetail(@PathVariable Integer ticket, HttpSession session) {
        Persona persona = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);

        if (!service.checkTicket(persona.getEmail(), ticket, Usuarios.Stakeholder)) {
            return new ModelAndView("redirect:/ticket/list");
        }

        return new ModelAndView("/ticket/ticket", "ticket", service.getTicket(new Ticket(ticket)));
    }

    @ResponseBody
    @RequestMapping("ticket/addMessage")
    public JsonResponse addMessage(@RequestParam String comment, @ModelAttribute("ticket") Ticket ticket, @RequestParam String _cid, HttpSession session) {
        JsonResponse json = new JsonResponse();

        try {
            Persona stake = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);
            if (stake == null) {
                throw new Exception("Error, no existe Stakeholder en session");
            }


            Mensaje newMessage = new Mensaje();
            newMessage.setFechaCreacion(new Date());
            newMessage.setTicket(ticket);
            newMessage.setMensaje(comment);
            newMessage.setPersona(stake);
            newMessage.setTipoPersona(Usuarios.Stakeholder.getValue());

            List<AdjuntoMensaje> adjuntos = (List<AdjuntoMensaje>) session.getAttribute(_cid + AppConstant.MMM);
            newMessage.setAdjuntos(adjuntos);

            ObjectNode resultado = service.addMessage(newMessage);
            if (adjuntos != null) {
                adjuntos.clear();
            }
            json.setSuccess(true);
            json.setMessage("Su mensaje se agrego con éxito.");
            json.setData(resultado);

        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
        } finally {
            return json;
        }
    }
}