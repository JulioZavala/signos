package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.StakeholderDAO;
import app.model.Producto;
import app.model.Stakeholder;
import org.hibernate.criterion.MatchMode;

@Repository
public class StakeholderDAOH extends HibernateTemplate implements StakeholderDAO {

    @Autowired
    public StakeholderDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Stakeholder getDAO(Stakeholder t) {
        Criteria criteria = this.getSession().createCriteria(Stakeholder.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Stakeholder) criteria.uniqueResult();
    }

    @Override
    public List<Stakeholder> getAllDAO() {
        Criteria criteria = this.getSession().createCriteria(Stakeholder.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Stakeholder t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Stakeholder t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Stakeholder t) {
        this.delete(t);
    }

    @Override
    public Stakeholder getByEmail(String email) {
        Criteria criteria = this.getSession().createCriteria(Stakeholder.class);
        criteria.createCriteria("persona").add(Restrictions.like("email", email, MatchMode.EXACT));
        return (Stakeholder) criteria.uniqueResult();
    }

    @Override
    public List<Stakeholder> getByProducto(Producto producto) {
        Criteria criteria = this.getSession().createCriteria(Stakeholder.class);
        criteria.createCriteria("productos").add(Restrictions.eq("id", producto.getId()));
        return criteria.list();
    }
}