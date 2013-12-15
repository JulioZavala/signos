
package app.controller.auth;

import app.model.Usuario;

public interface AuthService {

    
    boolean changePassword(Usuario usuario, String oldPass, String newPass);
}
