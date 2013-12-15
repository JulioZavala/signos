package app.controller.adm.cliente;

import static org.slf4j.LoggerFactory.getLogger;
import org.slf4j.Logger;

import app.model.Cliente;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("cliente")
@RequestMapping("/adm/cliente")
public class ClienteController {

    @Autowired
    ClienteService service;
    
    private static final Logger logger = getLogger(ClienteController.class);
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(HttpSession session) {
        logger.warn("LOG INTO INDEX");
        return new ModelAndView("adm/cliente/index", "clientes", service.getClientes());
    }

    @RequestMapping("new")
    public ModelAndView nuevo() {
        return new ModelAndView("adm/cliente/form", "cliente", new Cliente());
    }

    @RequestMapping("{id}/update")
    public ModelAndView update(@PathVariable("id") long id) {
        Cliente cliente = service.getCliente(new Cliente(id));
        return new ModelAndView("adm/cliente/form", "cliente", cliente);
    }

    @RequestMapping("save")
    public String save(@Valid Cliente cliente, BindingResult result, SessionStatus status) {

        if (result.hasErrors()) {
            return "adm/cliente/form";
        }

        if (cliente.getId() != null) {
            service.updateCliente(cliente);
        } else {
            service.addCliente(cliente);
        }

        status.isComplete();
        return "redirect:/adm/cliente/";
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResponse delete(@RequestParam String id) {
        JsonResponse json = new JsonResponse();
        try {
            service.deleteCliente(new Cliente(id));

            json.setSuccess(true);
            json.setMessage("Registro eliminado");

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
        } finally {
            return json;
        }
    }
}
