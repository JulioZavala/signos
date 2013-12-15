package app.controller.auth;

import app.dao.UsuarioDAO;
import app.model.Persona;
import app.model.Usuario;
import app.zelper.constant.AppConstant;
import app.zelper.enums.Usuarios;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
        Persona u = new Persona();
        u.setEmail(a.getName());

        Usuario autenticado = usuarioDAO.getUsuarioForAuthDAO(u);
        request.getSession().setAttribute(AppConstant.SESSION_USUARIO, autenticado.getPersona());

        if (autenticado.getTipoUsuario() == Usuarios.Administrador.getValue()) {
            setDefaultTargetUrl("/adm/cliente");

        } else if (autenticado.getTipoUsuario() == Usuarios.Staff.getValue()) {
            setDefaultTargetUrl("/staff");

        }

        super.onAuthenticationSuccess(request, response, a);
    }
}
