package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.ProductoDAO;
import app.model.Cliente;
import app.model.Producto;

@Repository
public class ProductoDAOH extends HibernateTemplate implements ProductoDAO {

    @Autowired
    public ProductoDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Producto getDAO(Producto t) {
        Criteria criteria = this.getSession().createCriteria(Producto.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Producto) criteria.uniqueResult();
    }

    @Override
    public List<Producto> getAllDAO() {
        Criteria criteria = this.getSession().createCriteria(Producto.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Producto t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Producto t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Producto t) {
        this.delete(t);
    }

    @Override
    public List<Producto> getByCliente(Cliente cliente) {
        Criteria criteria = this.getSession().createCriteria(Producto.class)
                .add(Restrictions.eq("cliente.id", cliente.getId()));
        return criteria.list();
    }
}