package com.pap.java.logica;

import jakarta.persistence.*;
import com.pap.java.datatypes.EstadoPrestamo;
import java.util.Date;
import java.util.List;

@Entity
public class Prestamo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrestamo estado;
    
    // Relación con Lector (1..*)
    @ManyToOne
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;
    
    // Relación con Bibliotecario (1..*)
    @ManyToOne
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    private Bibliotecario bibliotecario;
    
    // Relación con Material (1..*)
    @ManyToMany
    @JoinTable(
        name = "prestamo_material",
        joinColumns = @JoinColumn(name = "prestamo_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materiales;
    
    // Constructor vacío requerido por JPA
    public Prestamo() {}
    
    // Constructor con parámetros
    public Prestamo(Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado, 
                   Lector lector, Bibliotecario bibliotecario, List<Material> materiales) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.materiales = materiales;
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
}
