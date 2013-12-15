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
import app.zelper.misc.TypesUtil;
import java.io.Serializable;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "stakeholder")
public class Stakeholder implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "stakeholder_producto",
            joinColumns = {@JoinColumn(name = "id_stakeholder")},
            inverseJoinColumns = {@JoinColumn(name = "id_producto")})
    private List<Producto> productos;

    public Stakeholder() {
    }

    public Stakeholder(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}