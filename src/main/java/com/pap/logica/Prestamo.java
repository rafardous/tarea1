package com.pap.logica;

import jakarta.persistence.*;
import com.pap.datatypes.EstadoPrestamo;
import java.util.Date;


@Entity
@Table(name = "prestamos")
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "fecha_solicitud", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    
    @Column(name = "fecha_devolucion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado;
    
    // ManyToOne con lector!
    @ManyToOne(fetch = FetchType.LAZY) // fetchtype lazy significa que se carga un valor desde lector y no antes.
    @JoinColumn(name = "lector_email", nullable = false, referencedColumnName = "email") // referencedcolumnname significa que es la columna email de la clase lector!
    private Lector lector;
    
    // lo mismo aca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_email", nullable = false, referencedColumnName = "email")
    private Bibliotecario bibliotecario;
    
    // y tambien aca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false, referencedColumnName = "id_material")
    private Material material;
    
    // construct:
 
    public Prestamo(Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado, 
                   Lector lector, Bibliotecario bibliotecario, Material material) {
        this.fechaSolicitud = fechaSolicitud != null ? fechaSolicitud : new Date(); 
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado != null ? estado : EstadoPrestamo.PENDIENTE;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.material = material;
    }
    
    public int getId() {
        return id;
    }
    
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public EstadoPrestamo getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }
    
    public Lector getLector() {
        return lector;
    }
    
    public void setLector(Lector lector) {
        this.lector = lector;
    }
    
    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }
    
    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }


    public void cambiarEstado(EstadoPrestamo nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", fechaSolicitud=" + fechaSolicitud +
                ", fechaDevolucion=" + fechaDevolucion +
                ", estado=" + estado +
                ", lector=" + (lector != null ? lector.getEmail() : "null") +
                ", bibliotecario=" + (bibliotecario != null ? bibliotecario.getEmail() : "null") +
                ", material=" + material +
                '}';
    }
}
