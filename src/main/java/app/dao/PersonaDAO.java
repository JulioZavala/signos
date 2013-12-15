package app.dao;

import app.model.Persona;
import app.zelper.dao.GenericDAO;
import java.util.List;

public interface PersonaDAO extends GenericDAO<Persona> {

    List<Persona> getAllStaffDAO();
    
    List<Persona> getAllUserDAO();
    
    Persona getByEmail(String email);
    
}
