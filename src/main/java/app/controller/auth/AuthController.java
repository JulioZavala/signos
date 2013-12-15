package app.controller.auth;

import app.model.Persona;
import app.model.Usuario;
import app.zelper.constant.AppConstant;
import app.zelper.controller.JsonResponse;
import app.zelper.enums.Usuarios;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    @Autowired
    AuthService service;

    @RequestMapping("login")
    public String index(HttpServletRequest rq, @RequestParam(required = false) String error, Model model) throws Exception {

        Persona usuario = (Persona) rq.getSession().getAttribute(AppConstant.SESSION_USUARIO);

        if (usuario != null) {
            if (usuario.getUsuario().getTipoUsuario() == Usuarios.Administrador.getValue()) {
                return ("redirect:/adm/cliente");

            } else if (usuario.getUsuario().getTipoUsuario() == Usuarios.Staff.getValue()) {
                return ("redirect:/staff");

            }
        }

        if (error != null) {
            model.addAttribute("loginError", true);
        }

        return "login/index";
    }

    @RequestMapping(value = "auth/change", method = RequestMethod.GET)
    public String change() {
        return "login/change";
    }

    @ResponseBody
    @RequestMapping(value = "auth/change", method = RequestMethod.POST)
    public JsonResponse changePass(@RequestParam String now, @RequestParam String passwo, HttpSession session) {
        JsonResponse json = new JsonResponse();
        json.setMessage("Contraseña actual incorrecta.");
        json.setSuccess(false);

        try {
            Persona persona = (Persona) session.getAttribute(AppConstant.SESSION_USUARIO);
            boolean changePassword = service.changePassword(persona.getUsuario(), now, passwo);

            if (changePassword) {
                json.setSuccess(true);
                json.setMessage("Contraseña actualizada");
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
