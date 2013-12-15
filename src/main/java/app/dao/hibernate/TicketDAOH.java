package app.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import app.dao.TicketDAO;
import app.model.Producto;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.enums.EstadoTicket;
import java.util.ArrayList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

@Repository
public class TicketDAOH extends HibernateTemplate implements TicketDAO {

    @Autowired
    public TicketDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Ticket getDAO(Ticket t) {
        Criteria criteria = this.getSession().createCriteria(Ticket.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Ticket) criteria.uniqueResult();
    }

    @Override
    public List<Ticket> getAllDAO() {
        Criteria criteria = this.getSession().createCriteria(Ticket.class);
        return criteria.list();
    }

    @Override
    public void addDAO(Ticket t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Ticket t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Ticket t) {
        this.delete(t);
    }

    @Override
    public Ticket getByCodigoDAO(Ticket ticket) {
        Criteria criteria = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.eq("codigo", ticket.getCodigo()));

        return (Ticket) criteria.uniqueResult();
    }

    @Override
    public Ticket getByCodigoAndStakeDAO(List<Producto> productos, Integer codigo) {
        Criteria criteria = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.in("producto", productos))
                .add(Restrictions.eq("codigo", codigo));
        return (Ticket) criteria.uniqueResult();
    }

    @Override
    public Ticket getByCodigoAndStaffDAO(String staff, Integer codigo) {
        Criteria criteria = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.eq("codigo", codigo));

        if (!staff.equals(AppConstant.EMAIL_SOPORTE)) {
            criteria.createCriteria("staff").add(Restrictions.eq("email", staff));
        }

        return (Ticket) criteria.uniqueResult();
    }

    @Override
    public List<Ticket> getByStatusAndStake(Ticket ticket) {
        List<Producto> productos = ticket.getStake().getStakeholder().getProductos();

        Criteria criteria = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.in("producto", productos));

        if (ticket.getEstado() == EstadoTicket.Resuelto.getValue()) {
            criteria.add(Restrictions.eq("estado", EstadoTicket.Resuelto.getValue()));
        } else {
            criteria.add(Restrictions.ne("estado", EstadoTicket.Resuelto.getValue()));
        }

        criteria.addOrder(Order.desc("fechaActualiza"));

        return criteria.list();
    }

    @Override
    public List<Ticket> getByStatusAndStaff(Ticket ticket) {
        Criteria criteria = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.eq("estado", ticket.getEstado()))
                .addOrder(Order.desc("fechaActualiza"));

        if (!ticket.getStaff().getEmail().equals(AppConstant.EMAIL_SOPORTE)) {
            criteria.add(Restrictions.eq("staff", ticket.getStaff()));
        }

        return criteria.list();
    }

    @Override
    public List getTotalByStake(Ticket ticket) {
        List<Producto> productos = ticket.getStake().getStakeholder().getProductos();

        List totales = new ArrayList();

        Criteria criteriaActivo = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.in("producto", productos))
                .add(Restrictions.eq("estado", EstadoTicket.Activo.getValue()));
        Long activo = (Long) criteriaActivo.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteriaAnsw = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.in("producto", productos))
                .add(Restrictions.eq("estado", EstadoTicket.Respondido.getValue()));
        Long answered = (Long) criteriaAnsw.setProjection(Projections.rowCount()).uniqueResult();

        Criteria criteriaResuelto = this.getSession().createCriteria(Ticket.class)
                .add(Restrictions.in("producto", productos))
                .add(Restrictions.eq("estado", EstadoTicket.Resuelto.getValue()));
        Long resuelto = (Long) criteriaResuelto.setProjection(Projections.rowCount()).uniqueResult();

        totales.add(activo);
        totales.add(answered);
        totales.add(resuelto);
        return totales;

    }

    @Override
    public List getTotalByStaff(Ticket ticket) {
        List totales = new ArrayList();

        Criteria criteriaActivo = this.getSession().createCriteria(Ticket.class);
        Criteria criteriaAnsw = this.getSession().createCriteria(Ticket.class);
        Criteria criteriaResuelto = this.getSession().createCriteria(Ticket.class);

        if (!ticket.getStaff().getEmail().equals(AppConstant.EMAIL_SOPORTE)) {

            criteriaActivo.add(Restrictions.eq("staff", ticket.getStaff()));
            criteriaAnsw.add(Restrictions.eq("staff", ticket.getStaff()));
            criteriaResuelto.add(Restrictions.eq("staff", ticket.getStaff()));
        }

        criteriaActivo.add(Restrictions.eq("estado", EstadoTicket.Activo.getValue()));
        criteriaAnsw.add(Restrictions.eq("estado", EstadoTicket.Respondido.getValue()));
        criteriaResuelto.add(Restrictions.eq("estado", EstadoTicket.Resuelto.getValue()));

        Long activo = (Long) criteriaActivo.setProjection(Projections.rowCount()).uniqueResult();
        Long answered = (Long) criteriaAnsw.setProjection(Projections.rowCount()).uniqueResult();
        Long resuelto = (Long) criteriaResuelto.setProjection(Projections.rowCount()).uniqueResult();

        totales.add(activo);
        totales.add(answered);
        totales.add(resuelto);
        return totales;
    }
}