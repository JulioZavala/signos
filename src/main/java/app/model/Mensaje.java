package app.model;

import java.util.List;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.OneToMany;
import app.zelper.misc.TypesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
   
    @Column(name = "mensaje")
    private String mensaje;
   
    @Column(name = "tipo_persona")
    private Integer tipoPersona;
    
    @Column(name = "fecha_creacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @OneToMany(mappedBy = "mensaje", fetch = FetchType.LAZY)
    private List<AdjuntoMensaje> adjuntos = new ArrayList<AdjuntoMensaje>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;

    public Mensaje() {
    }

    public Mensaje(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public String getMensajeHtml() {
        return mensaje.replace("\n", "<br/>");
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<AdjuntoMensaje> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoMensaje> adjuntos) {
        if (adjuntos != null) {
            this.adjuntos.addAll(adjuntos);
            for (AdjuntoMensaje adjunto : adjuntos) {
                adjunto.setMensaje(this);
            }
        }
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Integer tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
}