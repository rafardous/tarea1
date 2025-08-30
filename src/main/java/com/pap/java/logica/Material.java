package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;
import com.pap.java.logica.Prestamo;

@Entity
@Table(name="materiales")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Material {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    
    // Relación: un material puede ser parte de un préstamo
    @ManyToOne
    @JoinColumn(name = "prestamo_id")
    private Prestamo prestamo;
    
    public Material() {}
    
    // Constructor con parámetros
    public Material(String id, Date fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
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
    
    public Prestamo getPrestamo() {
        return prestamo;
    }
    
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
}
