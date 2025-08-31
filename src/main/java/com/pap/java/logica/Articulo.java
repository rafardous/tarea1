package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad que representa un artículo en el sistema.
 * Extiende de Material y se almacena en tabla separada.
 */
@Entity
@Table(name = "articulos")
@PrimaryKeyJoinColumn(name = "material_id")
public class Articulo extends Material {
    
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    
    @Column(name = "peso_kg")
    private Float pesoKg;
    
    @Column(name = "dimensiones", length = 100)
    private String dimensiones;
    
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @Column(name = "estado_conservacion", length = 20)
    private String estadoConservacion;
    
    // Constructor vacío requerido por JPA
    public Articulo() {
        super();
    }
    
    // Constructor con parámetros
    public Articulo(String id, Date fechaIngreso, String descripcion, Float pesoKg, 
                   String dimensiones, String categoria, String estadoConservacion) {
        super(id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
        this.categoria = categoria;
        this.estadoConservacion = estadoConservacion;
    }
    
    // Constructor simplificado
    public Articulo(String id, Date fechaIngreso, String descripcion, Float pesoKg, String dimensiones) {
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
    
    public Float getPesoKg() {
        return pesoKg;
    }
    
    public void setPesoKg(Float pesoKg) {
        this.pesoKg = pesoKg;
    }
    
    public String getDimensiones() {
        return dimensiones;
    }
    
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getEstadoConservacion() {
        return estadoConservacion;
    }
    
    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id='" + getId() + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", pesoKg=" + pesoKg +
                ", dimensiones='" + dimensiones + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estadoConservacion='" + estadoConservacion + '\'' +
                ", disponible=" + isDisponible() +
                '}';
    }
}
