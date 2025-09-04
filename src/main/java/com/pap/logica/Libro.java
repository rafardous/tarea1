package com.pap.logica;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
@PrimaryKeyJoinColumn(name = "id_libro")
public class Libro extends Material{

    @Column(name = "titulo")
    private String titulo;
    @Column(name = "cantidad_paginas")
    private String cantidadPaginas;

    public Libro(){}

    public Libro(Integer idMaterial, String id, Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(idMaterial, id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public Libro(String id, Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(null, id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

}
