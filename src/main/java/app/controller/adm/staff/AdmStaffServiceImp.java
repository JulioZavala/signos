package app.controller.adm.staff;

import app.dao.PersonaDAO;
import app.dao.StakeholderDAO;
import app.dao.UsuarioDAO;
import app.model.Persona;
import app.model.Usuario;
import app.zelper.enums.Usuarios;
import app.zelper.misc.TypesUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdmStaffServiceImp implements AdmStaffService {

    @Autowired
    PersonaDAO staffDAO;
    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    StakeholderDAO stakeDAO;

    @Override
    public List<Persona> getAllStaff() {
        return staffDAO.getAllUserDAO();
    }

    @Override
    public Persona getStaff(Persona staff) {
        return staffDAO.getDAO(staff);
    }

    @Override
    public void addStaff(Persona staff) {
        
        staffDAO.addDAO(staff);
        
        
        try {
            Usuario usuario = new Usuario();
            usuario.setPersona(staff);
            usuario.setTipoUsuario(Usuarios.Staff.getValue());
            usuario.setPassword(TypesUtil.getMD5("temporal"));
            usuarioDAO.addDAO(usuario);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateStaff(Persona staff) {
        staffDAO.updateDAO(staff);
    }

    @Override
    public void deleteStaff(Persona staff) {
        staffDAO.deleteDAO(staff);
    }
}
