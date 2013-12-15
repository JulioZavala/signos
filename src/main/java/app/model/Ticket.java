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
@Table(name = "ticket")
public class Ticket implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Column(name = "fecha_creacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaActualiza;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<Mensaje> mensajes;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona stake;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_encargado")
    private Persona staff;
    
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<AdjuntoTicket> adjuntos = new ArrayList<AdjuntoTicket>();

    public Ticket() {}

    public Ticket(Object codigoid) {
        this.codigo = TypesUtil.getDefaultInt(codigoid);
    }

    public Long getId() {
        return id;
    }	

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getAsunto() {
        return asunto;
    }	

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getEstado() {
        return estado;
    }	

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getPrioridad() {
        return prioridad;
    }	

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }	

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Persona getStake() {
        return stake;
    }

    public void setStake(Persona stake) {
        this.stake = stake;
    }

    public Persona getStaff() {
        return staff;
    }

    public void setStaff(Persona staff) {
        this.staff = staff;
    }

    public Date getFechaActualiza() {
        return fechaActualiza;
    }

    public void setFechaActualiza(Date fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }

    public List<AdjuntoTicket> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoTicket> adjuntos) {
        this.adjuntos = adjuntos;
    }
}