package app.controller.staff;

import app.model.AdjuntoMensaje;
import app.model.Cliente;
import app.model.Mensaje;
import app.model.Persona;
import app.model.Producto;
import app.model.Stakeholder;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import app.zelper.enums.EstadoTicket;
import app.zelper.enums.PrioridadTicket;
import app.zelper.enums.Usuarios;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/staff")
@SessionAttributes("ticket")
public class StaffController {

    @Autowired
    StaffService service;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpSession session) {
        return "redirect:/staff/list";
    }

    @RequestMapping("list")
    public ModelAndView listTicket(@RequestParam(required = false, defaultValue = "open") String status, HttpSession session, Model model) {

        Ticket ticket = new Ticket();

        EstadoTicket estado = (status.equals("closed")) ? EstadoTicket.Resuelto : (status.equals("answered")) ? EstadoTicket.Respondido : EstadoTicket.Activo;
        ticket.setEstado(estado.getValue());

        Persona encargado = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);
        ticket.setStaff(encargado);

        List<Ticket> tickets = service.getTickets(ticket);


        model.addAttribute("tickets", tickets);
        model.addAttribute("totales", service.getTotales(ticket));

        return new ModelAndView("/staff/list");
    }

    @RequestMapping("newTicket")
    public String newTicket(HttpSession session, Model model) {

        model.addAttribute("clientes", service.getClientes());
        model.addAttribute("ticket", new Ticket());
        return "/staff/new";
    }

    @ResponseBody
    @RequestMapping("listProducto")
    public ArrayNode listProducto(@RequestParam Long cliente) {
        JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
        ArrayNode jsonArray = new ArrayNode(jsonFactory);

        List<Producto> list = service.getProductos(new Cliente(cliente));

        for (Producto item : list) {
            ObjectNode json = new ObjectNode(jsonFactory);
            json.put("id", item.getId());
            json.put("name", item.getNombre());
            jsonArray.add(json);
        }

        return jsonArray;
    }

    @ResponseBody
    @RequestMapping("listStake")
    public ArrayNode listStake(@RequestParam Long producto) {
        JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
        ArrayNode jsonArray = new ArrayNode(jsonFactory);

        List<Stakeholder> list = service.getStakeHolder(new Producto(producto));

        for (Stakeholder item : list) {
            ObjectNode json = new ObjectNode(jsonFactory);
            json.put("id", item.getPersona().getId());
            json.put("name", item.getPersona().getNombreCompleto());
            jsonArray.add(json);
        }

        return jsonArray;
    }

    @ResponseBody
    @RequestMapping("saveTicket")
    public JsonResponse saveTicket(@ModelAttribute("ticket") Ticket ticket, HttpSession session) {
        JsonResponse json = new JsonResponse();
        try {
            Persona encargado = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);
            ticket.setStaff(encargado);

            service.addTicket(ticket);

            json.setMessage("Ticket creado con éxito.");
            json.setSuccess(true);

        } catch (Exception e) {
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            json.setSuccess(false);
            e.printStackTrace();
        } finally {

            return json;
        }
    }

    @RequestMapping("ticket/{ticket}")
    public String ticketDetail(@PathVariable Integer ticket, HttpSession session, Model model) {

        List<Persona> staffs = service.getStaff();
        Ticket originalTicket = service.getTicket(new Ticket(ticket));

        if (originalTicket == null) {
            return "redirect:/staff/list";
        }

        model.addAttribute("ticket", originalTicket);
        model.addAttribute("staffs", staffs);

        return "/staff/ticket";
    }

    @ResponseBody
    @RequestMapping("addMessage")
    public JsonResponse addMessage(@RequestParam String comment, @ModelAttribute("ticket") Ticket ticket, @RequestParam String _cid, HttpSession session) {
        JsonResponse json = new JsonResponse();

        try {
            Persona stake = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);


            Mensaje newMessage = new Mensaje();
            newMessage.setFechaCreacion(new Date());
            newMessage.setTicket(ticket);
            newMessage.setMensaje(comment);
            newMessage.setPersona(stake);
            newMessage.setTipoPersona(Usuarios.Staff.getValue());

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

    @ResponseBody
    @RequestMapping("update")
    public JsonResponse updateTicket(@RequestParam String field, @RequestParam String value,
            @ModelAttribute("ticket") Ticket ticket, HttpSession session) {

        JsonResponse json = new JsonResponse();

        try {
            json.setSuccess(true);

            if (field.equals("prioridad")) {
                ticket.setPrioridad(PrioridadTicket.valueOf(value).getValue());
                service.updateTicket(ticket);

                json.setMessage("Prioridad asignada.");

            } else if (field.equals("estado")) {
                ticket.setEstado(EstadoTicket.valueOf(value).getValue());
                service.updateTicket(ticket);

                json.setMessage("Estado cambiado.");

            } else if (field.equals("staff")) {
                ticket.setStaff(new Persona(value));
                service.updateTicket(ticket);
                service.notifyAssign(ticket);

                json.setMessage("Ticket Asignado.");

            } else {
                throw new Exception();
            }


        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
        } finally {
            return json;
        }
    }
}