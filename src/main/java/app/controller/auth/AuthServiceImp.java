package app.controller.auth;

import app.dao.UsuarioDAO;
import app.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    CryptMoodle crypt;

    @Override
    public boolean changePassword(Usuario usuario, String oldPass, String newPass) {
        if (!crypt.encodePassword(oldPass, null).equals(usuario.getPassword())) {
            return false;
        }

        usuario.setPassword(crypt.encodePassword(newPass, null));
        usuarioDAO.updateDAO(usuario);

        return true;

    }
}
