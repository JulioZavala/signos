package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.ClienteDAO;
import app.model.Cliente;

@Repository
public class ClienteDAOH extends HibernateTemplate implements ClienteDAO{

    @Autowired
    public ClienteDAOH(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public Cliente getDAO(Cliente t){
        Criteria criteria = this.getSession().createCriteria(Cliente.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Cliente) criteria.uniqueResult();
    }

    @Override
    public List<Cliente> getAllDAO(){
        Criteria criteria = this.getSession().createCriteria(Cliente.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Cliente t){
        this.save(t);
    }

    @Override
    public void updateDAO(Cliente t){
        this.merge(t);
    }

    @Override
    public void deleteDAO(Cliente t){
        this.delete(t);
    }
}