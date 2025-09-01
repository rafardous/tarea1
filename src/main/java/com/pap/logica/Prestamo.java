package com.pap.logica;

import jakarta.persistence.*;
import com.pap.datatypes.EstadoPrestamo;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un préstamo en el sistema.
 * Gestiona la relación entre lectores, bibliotecarios y materiales.
 */
@Entity
@Table(name = "prestamos")
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fecha_solicitud", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    
    @Column(name = "fecha_devolucion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado;
    
    // Relación con Lector (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lector_email", nullable = false, referencedColumnName = "email")
    private Lector lector;
    
    // Relación con Bibliotecario (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_email", nullable = false, referencedColumnName = "email")
    private Bibliotecario bibliotecario;
    
    // Relación con Material (ManyToMany)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "prestamo_material",
        joinColumns = @JoinColumn(name = "prestamo_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materiales = new ArrayList<>();
    
    // Constructor vacío requerido por JPA
    public Prestamo() {
        this.fechaSolicitud = new Date();
        this.estado = EstadoPrestamo.PENDIENTE;
    }
    
    // Constructor con parámetros
    public Prestamo(Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado, 
                   Lector lector, Bibliotecario bibliotecario, List<Material> materiales) {
        this();
        this.fechaSolicitud = fechaSolicitud != null ? fechaSolicitud : new Date();
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado != null ? estado : EstadoPrestamo.PENDIENTE;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        if (materiales != null) {
            this.materiales.addAll(materiales);
        }
    }
    
    // Getters y setters
    public Long getId() {
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
    
    public List<Material> getMateriales() {
        return materiales;
    }
    
    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    // Métodos de conveniencia para manejar materiales
    public void agregarMaterial(Material material) {
        if (material != null && !materiales.contains(material)) {
            materiales.add(material);
        }
    }

    public void removerMaterial(Material material) {
        if (material != null) {
            materiales.remove(material);
        }
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
                ", materiales=" + materiales.size() +
                '}';
    }
}
