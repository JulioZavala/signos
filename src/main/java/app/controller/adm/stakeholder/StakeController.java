package app.controller.adm.stakeholder;

import app.model.Cliente;
import app.model.Producto;
import app.model.Stakeholder;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import app.zelper.misc.TypesUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adm/stakeholder")
@SessionAttributes("stake")
public class StakeController {

    @Autowired
    StakeService service;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.registerCustomEditor(List.class, "productos", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                Long id = null;

                if (element instanceof String && !((String) element).equals("")) {
                    // from JSP
                    id = TypesUtil.getDefaultLong(element);

                } else if (element instanceof Long) {
                    //From the database 'element' will be a Long
                    id = (Long) element;
                }

                return (id != null) ? new Producto(id) : null;
            }
        });

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("adm/stake/index", "stakes", service.getStakeholders());
    }

    @RequestMapping("new")
    public ModelAndView nuevo() {

        return new ModelAndView("adm/stake/form", "stake", new Stakeholder());
    }

    @RequestMapping("{id}/update")
    public String update(@PathVariable("id") long id, Model model) {
        Stakeholder stake = service.getStake(new Stakeholder(id));

        model.addAttribute("stake", stake);
        return "adm/stake/form";
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("stake") Stakeholder stake, BindingResult result, SessionStatus status) {

        if (result.hasErrors()) {
            for (ObjectError object : result.getAllErrors()) {
                System.out.println(object.toString());
            }

            return "adm/stake/form";
        }
        
        if (stake.getId() != null) {
            service.updateStake(stake);
        } else {
            service.addStake(stake);
        }

        status.isComplete();
        return "redirect:/adm/stakeholder/";
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResponse delete(@RequestParam String id) {
        JsonResponse json = new JsonResponse();
        try {
            service.deleteStake(new Stakeholder(id));

            json.setSuccess(true);
            json.setMessage("Registro eliminado");

        } catch (Exception e) {
            json.setSuccess(false);
            json.setMessage(AppConstant.APP_ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            return json;
        }

    }

    @ModelAttribute("clientes")
    public List<Cliente> getClientes() {
        return service.getClientes();
    }

    @ResponseBody
    @RequestMapping(value = "productos", method = RequestMethod.POST)
    public JsonResponse getProductos(@RequestParam String cli, @ModelAttribute("stake") Stakeholder stake) {

        JsonResponse root = new JsonResponse();
        JsonNodeFactory jsonFactory = JsonNodeFactory.instance;
        ArrayNode jsonArray = new ArrayNode(jsonFactory);

        try {

            List<Producto> productos = service.getProductos(new Cliente(cli));
            for (Producto producto : productos) {
                ObjectNode json = new ObjectNode(jsonFactory);
                json.put("id", producto.getId());
                json.put("name", producto.getNombre());
                json.put("selected", false);

                if (stake.getProductos() != null) {
                    for (Producto selected : stake.getProductos()) {
                        if (producto.getId() == selected.getId().longValue()) {
                            json.put("selected", true);
                        }
                    }
                }

                jsonArray.add(json);
            }

            root.setSuccess(true);
            root.setData(jsonArray);
            root.setTotal(jsonArray.size());

        } catch (Exception e) {
            e.printStackTrace();
            root.setSuccess(false);
            root.setMessage(AppConstant.APP_ERROR_MESSAGE);
        } finally {
            return root;
        }

    }
}
