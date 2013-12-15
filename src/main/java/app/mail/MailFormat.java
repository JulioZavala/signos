
package app.mail;

import app.model.Ticket;
import javax.mail.internet.InternetAddress;
import org.thymeleaf.context.Context;

public class MailFormat {
    private Ticket ticket;
    
    private InternetAddress from;
    
    private InternetAddress to;
    
    private Context context;
    
    private String template;
    
    private String subject;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    public InternetAddress getTo() {
        return to;
    }

    public void setTo(InternetAddress to) {
        this.to = to;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

   
    
    
    
}
