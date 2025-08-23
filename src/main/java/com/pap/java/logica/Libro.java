package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Libro extends Material {
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false)
    private String cantidadPaginas;
    
    // Constructor vacío requerido por JPA
    public Libro() {}
    
    // Constructor con parámetros
    public Libro(String id, Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }
    
    // Getters y setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
}
