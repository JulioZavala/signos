package app.mail;

import app.controller.adm.cliente.ClienteController;
import app.dao.TicketDAO;
import app.model.Mensaje;
import app.model.Ticket;
import app.zelper.constant.AppConstant;
import app.zelper.enums.Usuarios;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailerServiceImp implements MailerService {

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    TicketDAO ticketDAO;
    
    private static final org.slf4j.Logger logger = getLogger(ClienteController.class);

    @Async
    @Override
    public void generateMailTicket(Ticket t, String mensajeMail, Usuarios usuario) {
        try {
            Ticket ticket = ticketDAO.getDAO(t);
            
            String to = (usuario == Usuarios.Stakeholder) ? ticket.getStake().getEmail() : ticket.getStaff().getEmail();

            Context ctx = new Context();
            ctx.setVariable("ticket", ticket);
            ctx.setVariable("mensaje", mensajeMail);
            ctx.setVariable("detalle", "");
            ctx.setVariable("usuario", usuario);



            MailFormat mail = new MailFormat();
            mail.setContext(ctx);
            mail.setTemplate("notify");
            mail.setSubject("Ticket #" + ticket.getCodigo() + " " + ticket.getAsunto());
            mail.setTo(new InternetAddress(to));
            mail.setFrom(new InternetAddress(AppConstant.EMAIL_SOPORTE, "Soporte Albatross"));

            sendMail(mail);
        } catch (UnsupportedEncodingException ex) {
            logger.info(ex.getMessage());
        } catch (AddressException ex) {
            logger.info(ex.getMessage());
        }
    }

    @Async
    @Override
    public void generateMailMessage(Mensaje mensaje, String mensajeMail, Usuarios usuario) {
        try {
            Ticket ticket = mensaje.getTicket();

            String to = (usuario == Usuarios.Stakeholder) ? ticket.getStake().getEmail() : ticket.getStaff().getEmail();

            Context ctx = new Context();
            ctx.setVariable("ticket", ticket);
            ctx.setVariable("mensaje", mensajeMail);
            ctx.setVariable("detalle", mensaje.getMensajeHtml());
            ctx.setVariable("usuario", usuario);


            MailFormat mail = new MailFormat();
            mail.setContext(ctx);
            mail.setTemplate("notify");
            mail.setSubject("Soporte Albatross - Ticket #" + ticket.getCodigo() + " - " + ticket.getAsunto());
            mail.setTo(new InternetAddress(to));
            mail.setFrom(new InternetAddress(AppConstant.EMAIL_SOPORTE, "Soporte Albatross"));

            sendMail(mail);

        } catch (UnsupportedEncodingException ex) {
            logger.info(ex.getMessage());
        } catch (AddressException ex) {
            logger.info(ex.getMessage());
        }
    }

    private void sendMail(MailFormat mail) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(mail.getSubject());
            message.setFrom(mail.getFrom());
            message.setTo(mail.getTo());

            String htmlContent = this.templateEngine.process(mail.getTemplate(), mail.getContext());
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);

        } catch (MessagingException ex) {
            Logger.getLogger(MailerServiceImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}