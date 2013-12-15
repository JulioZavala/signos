package app.controller.adm.productos;

import app.model.Cliente;
import app.model.Producto;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@SessionAttributes("producto")
@RequestMapping("/adm/producto")
public class ProductoController {

    @Autowired
    ProductoService service;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("clientes")
    public List<Cliente> getClientes() {
        return service.getClientes();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/adm/producto/index", "productos", service.getProductos());
    }

    @RequestMapping("new")
    public ModelAndView nuevo() {
        ModelAndView mv = new ModelAndView("/adm/producto/form", "producto", new Producto());
        return mv;
    }

    @RequestMapping("{id}/update")
    public ModelAndView update(@PathVariable("id") long id) {
        Producto producto = service.getProducto(new Producto(id));
        return new ModelAndView("adm/producto/form", "producto", producto);
    }

    @RequestMapping("save")
    public String save(@ModelAttribute("producto") Producto producto, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "adm/producto/form";
        }

        if (producto.getId() != null) {
            service.updateProducto(producto);
        } else {
            service.addProducto(producto);
        }
        status.isComplete();

        return "redirect:/adm/producto/";
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResponse delete(@RequestParam String id) {
        JsonResponse json = new JsonResponse();
        try {
            service.deleteProducto(new Producto(id));

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
}
