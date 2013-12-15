package app.mail;

import app.model.Mensaje;
import app.model.Ticket;
import app.zelper.enums.Usuarios;

public interface MailerService {

    public void generateMailTicket(Ticket ticket, String mensajeMail, Usuarios usuario);

    public void generateMailMessage(Mensaje mensaje, String mensajeMail, Usuarios usuario);
}
