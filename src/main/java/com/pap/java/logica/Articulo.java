package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articulos")
public class Articulo extends Material {
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private float pesoKg;
    
    @Column(nullable = false)
    private String dimensiones;
    
    // Constructor vacío requerido por JPA
    public Articulo() {}
    
    // Constructor con parámetros
    public Articulo(String id, Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
        super(id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }
    
    // Getters y setters
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public float getPesoKg() {
        return pesoKg;
    }
    
    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }
    
    public String getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
}
