package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.MensajeDAO;
import app.model.Mensaje;

@Repository
public class MensajeDAOH extends HibernateTemplate implements MensajeDAO{

    @Autowired
    public MensajeDAOH(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public Mensaje getDAO(Mensaje t){
        Criteria criteria = this.getSession().createCriteria(Mensaje.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Mensaje) criteria.uniqueResult();
    }

    @Override
    public List<Mensaje> getAllDAO(){
        Criteria criteria = this.getSession().createCriteria(Mensaje.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Mensaje t){
        this.save(t);
    }

    @Override
    public void updateDAO(Mensaje t){
        this.merge(t);
    }

    @Override
    public void deleteDAO(Mensaje t){
        this.delete(t);
    }
}