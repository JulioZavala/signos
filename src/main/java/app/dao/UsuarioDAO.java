package app.dao;

import app.model.Persona;
import app.model.Usuario;
import app.zelper.dao.GenericDAO;

public interface UsuarioDAO extends GenericDAO<Usuario> {

    Usuario getUsuarioForAuthDAO(Persona persona);
}
