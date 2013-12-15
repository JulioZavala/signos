package app.model;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import app.zelper.misc.TypesUtil;
import java.io.Serializable;
import javax.persistence.ManyToMany;

@Entity
@Table(name = "producto")
public class Producto implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Ticket> ticket;
    
    @ManyToMany (mappedBy = "productos")
    private List<Stakeholder> stakeholders;
    
    public Producto() {}

    public Producto(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }	

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }	

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
   
    public List<Ticket> getTicket() {
           return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

    public List<Stakeholder> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(List<Stakeholder> stakeholders) {
        this.stakeholders = stakeholders;
    }
    
    
}