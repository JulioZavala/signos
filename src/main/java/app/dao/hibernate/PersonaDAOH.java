package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.PersonaDAO;
import app.model.Persona;
import app.zelper.enums.Usuarios;

@Repository
public class PersonaDAOH extends HibernateTemplate implements PersonaDAO {

    @Autowired
    public PersonaDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Persona getDAO(Persona t) {
        Criteria criteria = this.getSession().createCriteria(Persona.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Persona) criteria.uniqueResult();
    }

    @Override
    public List<Persona> getAllDAO() {
        Criteria criteria = this.getSession().createCriteria(Persona.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Persona t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Persona t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Persona t) {
        this.delete(t);
    }

    @Override
    public List<Persona> getAllUserDAO() {
        Criteria criteria = this.getSession().createCriteria(Persona.class);
        criteria.createCriteria("usuario").add(Restrictions.gt("id", 0L));
        return criteria.list();
    }

    @Override
    public List<Persona> getAllStaffDAO() {
        Criteria criteria = this.getSession().createCriteria(Persona.class);
        criteria.createCriteria("usuario").add(Restrictions.eq("tipoUsuario", Usuarios.Staff.getValue()));
        return criteria.list();
    }

    @Override
    public Persona getByEmail(String email) {
        Criteria criteria = this.getSession().createCriteria(Persona.class);
        criteria.add(Restrictions.eq("email", email));
        return (Persona) criteria.uniqueResult();
    }
}