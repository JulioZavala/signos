package app.controller.adm.staff;

import app.model.Persona;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
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
@SessionAttributes("staff")
@RequestMapping("/adm/staff")
public class AdmStaffController {

    @Autowired
    AdmStaffService service;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("adm/staff/index", "staffs", service.getAllStaff());
    }

    @RequestMapping("new")
    public ModelAndView agregarStaff() {
        return new ModelAndView("adm/staff/form", "staff", new Persona());
    }

    @RequestMapping("{id}/update")
    public ModelAndView update(@PathVariable("id") long id) {
        Persona staff = service.getStaff(new Persona(id));
        return new ModelAndView("adm/staff/form", "staff", staff);
    }

    @RequestMapping("save")
    public String saveStaff(@ModelAttribute("staff") Persona staff, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "adm/staff/form";
        }

        if (staff.getId() != null) {
            service.updateStaff(staff);
        } else {
            service.addStaff(staff);
        }

        status.isComplete();
        return "redirect:/adm/staff/";
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResponse deleteStaff(@RequestParam String id) {
        JsonResponse json = new JsonResponse();
        try {
            service.deleteStaff(new Persona(id));
            
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
