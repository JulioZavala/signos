package app.model;
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

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "tipo_usuario")
    private Integer tipoUsuario;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_persona")
    private Persona persona;

    public Usuario() {}

    public Usuario(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }	

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }	

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }	

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}