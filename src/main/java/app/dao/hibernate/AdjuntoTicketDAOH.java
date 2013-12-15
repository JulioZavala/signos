package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.AdjuntoTicketDAO;
import app.model.AdjuntoTicket;

@Repository
public class AdjuntoTicketDAOH extends HibernateTemplate implements AdjuntoTicketDAO{

    @Autowired
    public AdjuntoTicketDAOH(SessionFactory sessionFactory){
        super(sessionFactory);
    }

    @Override
    public AdjuntoTicket getDAO(AdjuntoTicket t){
        Criteria criteria = this.getSession().createCriteria(AdjuntoTicket.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (AdjuntoTicket) criteria.uniqueResult();
    }

    @Override
    public List<AdjuntoTicket> getAllDAO(){
        Criteria criteria = this.getSession().createCriteria(AdjuntoTicket.class);
        return criteria.list();
    }

    @Override
    public void addDAO(AdjuntoTicket t){
        this.save(t);
    }

    @Override
    public void updateDAO(AdjuntoTicket t){
        this.merge(t);
    }

    @Override
    public void deleteDAO(AdjuntoTicket t){
        this.delete(t);
    }
}