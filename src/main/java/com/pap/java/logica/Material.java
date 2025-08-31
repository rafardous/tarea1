package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un material en el sistema.
 * Utiliza herencia JOINED para separar diferentes tipos de material.
 */
@Entity
@Table(name = "materiales")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Material {
    
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;
    
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    
    @Column(name = "disponible", nullable = false)
    private boolean disponible = true;
    
    // Relación: un material puede ser parte de varios préstamos
    @ManyToMany(mappedBy = "materiales", fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();
    
    // Constructor vacío requerido por JPA
    public Material() {
        this.fechaIngreso = new Date();
    }
    
    // Constructor con parámetros
    public Material(String id, Date fechaIngreso) {
        this();
        this.id = id;
        if (fechaIngreso != null) {
            this.fechaIngreso = fechaIngreso;
        }
    }
    
    // Getters y setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Date getFechaIngreso() {
        return fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    // Métodos de conveniencia
    public void marcarComoPrestado() {
        this.disponible = false;
    }

    public void marcarComoDisponible() {
        this.disponible = true;
    }

    public boolean puedeSerPrestado() {
        return disponible;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", disponible=" + disponible +
                ", prestamos=" + prestamos.size() +
                '}';
    }
}
