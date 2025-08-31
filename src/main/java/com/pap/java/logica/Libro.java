package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad que representa un libro en el sistema.
 * Extiende de Material y se almacena en tabla separada.
 */
@Entity
@Table(name = "libros")
@PrimaryKeyJoinColumn(name = "material_id")
public class Libro extends Material {
    
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    
    @Column(name = "autor", nullable = false, length = 100)
    private String autor;
    
    @Column(name = "isbn", unique = true, length = 20)
    private String isbn;
    
    @Column(name = "cantidad_paginas")
    private Integer cantidadPaginas;
    
    @Column(name = "editorial", length = 100)
    private String editorial;
    
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;
    
    // Constructor vacío requerido por JPA
    public Libro() {
        super();
    }
    
    // Constructor con parámetros
    public Libro(String id, Date fechaIngreso, String titulo, String autor, String isbn, 
                 Integer cantidadPaginas, String editorial, Integer anioPublicacion) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.cantidadPaginas = cantidadPaginas;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
    }
    
    // Constructor simplificado
    public Libro(String id, Date fechaIngreso, String titulo, String autor) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.autor = autor;
    }
    
    // Getters y setters
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public Integer getCantidadPaginas() {
        return cantidadPaginas;
    }
    
    public void setCantidadPaginas(Integer cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id='" + getId() + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", cantidadPaginas=" + cantidadPaginas +
                ", editorial='" + editorial + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", disponible=" + isDisponible() +
                '}';
    }
}
