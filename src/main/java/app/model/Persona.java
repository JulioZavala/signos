package app.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import app.zelper.misc.TypesUtil;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
@Table(name = "persona")
public class Persona implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombres")
    private String nombres;
    
    @Column(name = "apellidos")
    private String apellidos;
   
    @Column(name = "email")
    private String email;
   
    @Column(name = "celular")
    private Integer celular;
   
    @OneToMany(mappedBy = "persona", fetch = FetchType.LAZY)
    private List<Mensaje> mensaje;
    
    @OneToOne(mappedBy = "persona", fetch = FetchType.LAZY)
    private Stakeholder stakeholder;
    
    @OneToMany(mappedBy = "stake", fetch = FetchType.LAZY)
    private List<Ticket> ticketsCreados;
   
    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private List<Ticket> ticketAsignados;
   
    @OneToOne(mappedBy = "persona", fetch = FetchType.LAZY)
    private Usuario usuario;

    public Persona() {
    }

    public Persona(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public List<Mensaje> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<Mensaje> mensaje) {
        this.mensaje = mensaje;
    }

    public Stakeholder getStakeholder() {
        return stakeholder;
    }

    public void setStakeholder(Stakeholder stakeholder) {
        this.stakeholder = stakeholder;
    }

    public List<Ticket> getTicketsCreados() {
        return ticketsCreados;
    }

    public void setTicketsCreados(List<Ticket> ticketsCreados) {
        this.ticketsCreados = ticketsCreados;
    }

    public List<Ticket> getTicketAsignados() {
        return ticketAsignados;
    }

    public void setTicketAsignados(List<Ticket> ticketAsignados) {
        this.ticketAsignados = ticketAsignados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }
}