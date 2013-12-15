package app.dao;

import app.model.Producto;
import app.model.Ticket;
import app.zelper.dao.GenericDAO;
import java.util.List;

public interface TicketDAO extends GenericDAO<Ticket> {

    List<Ticket> getByStatusAndStaff(Ticket ticket);

    List<Ticket> getByStatusAndStake(Ticket ticket);

    Ticket getByCodigoAndStakeDAO(List<Producto> productos, Integer codigo);

    Ticket getByCodigoAndStaffDAO(String staff, Integer codigo);

    Ticket getByCodigoDAO(Ticket ticket);

    List getTotalByStake(Ticket ticket);

    List getTotalByStaff(Ticket ticket);
}
