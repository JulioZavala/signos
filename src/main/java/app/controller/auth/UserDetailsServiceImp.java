package app.controller.auth;

import app.dao.UsuarioDAO;
import app.model.Persona;
import app.model.Usuario;
import app.model.spring.UserEntity;
import app.zelper.enums.Usuarios;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private AssemblerService assembler;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {

        Persona u = new Persona();
        u.setEmail(string);

        Usuario user = usuarioDAO.getUsuarioForAuthDAO(u);
        
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<String> roles = new ArrayList<String>();
        roles.add(Usuarios.get(user.getTipoUsuario()).toString());
        
        UserEntity userEntity = new UserEntity();
        userEntity.setActive(true);
        userEntity.setName(user.getPersona().getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRoles(roles);

        return assembler.buildUserFromUserEntity(userEntity);
    }
}
