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
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty
    @Size(min = 11, max=11)
    @Column(name = "ruc")
    private String ruc;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Email
    @NotEmpty
    @Column(name = "email_contacto")
    private String emailContacto;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Producto> productos;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Stakeholder> stakeholders;

    public Cliente() {}

    public Cliente(Object id) {
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }	

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }	

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getEmailContacto() {
        return emailContacto;
    }	

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Stakeholder> getStakeholders() {
        return stakeholders;
    }

    public void setStakeholders(List<Stakeholder> stakeholders) {
        this.stakeholders = stakeholders;
    }

    
}