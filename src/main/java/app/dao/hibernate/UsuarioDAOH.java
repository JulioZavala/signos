package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.UsuarioDAO;
import app.model.Persona;
import app.model.Usuario;

@Repository
public class UsuarioDAOH extends HibernateTemplate implements UsuarioDAO {

    @Autowired
    public UsuarioDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Usuario getDAO(Usuario t) {
        Criteria criteria = this.getSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Usuario) criteria.uniqueResult();
    }

    @Override
    public List<Usuario> getAllDAO() {
        Criteria criteria = this.getSession().createCriteria(Usuario.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Usuario t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Usuario t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Usuario t) {
        this.delete(t);
    }

    @Override
    public Usuario getUsuarioForAuthDAO(Persona persona) {
        Criteria criteria = this.getSession().createCriteria(Usuario.class);
        criteria.createCriteria("persona").add(Restrictions.eq("email", persona.getEmail()));
        return (Usuario) criteria.uniqueResult();
    }
}