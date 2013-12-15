package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.AdjuntoMensajeDAO;
import app.model.AdjuntoMensaje;

@Repository
public class AdjuntoMensajeDAOH extends HibernateTemplate implements AdjuntoMensajeDAO{

    @Autowired
    public AdjuntoMensajeDAOH(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public AdjuntoMensaje getDAO(AdjuntoMensaje t){
        Criteria criteria = this.getSession().createCriteria(AdjuntoMensaje.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (AdjuntoMensaje) criteria.uniqueResult();
    }

    @Override
    public List<AdjuntoMensaje> getAllDAO(){
        Criteria criteria = this.getSession().createCriteria(AdjuntoMensaje.class);
        return criteria.list();
    }

    @Override
    public void addDAO(AdjuntoMensaje t){
        this.save(t);
    }

    @Override
    public void updateDAO(AdjuntoMensaje t){
        this.merge(t);
    }

    @Override
    public void deleteDAO(AdjuntoMensaje t){
        this.delete(t);
    }
}