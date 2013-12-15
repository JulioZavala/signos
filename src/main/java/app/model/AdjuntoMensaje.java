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
import java.util.Date;
import javax.persistence.Temporal;

@Entity
@Table(name = "adjunto_mensaje")
public class AdjuntoMensaje implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @Column(name = "titulo")
    private String titulo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;
    
    @Column(name = "fecha_creacion")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_mensaje")
    private Mensaje mensaje;

    public AdjuntoMensaje() {}

    public AdjuntoMensaje(Object id) {
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    

   
}