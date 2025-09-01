package com.pap.logica;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulos")
@PrimaryKeyJoinColumn(name = "id_articulo")
public class Articulo extends Material{

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "peso_kg")
    private float pesoKg;
    @Column(name = "dimensiones")
    private String dimensiones;

    public Articulo(){}

    public Articulo(Integer idMaterial, String id, Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
        super(idMaterial, id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }

    public Articulo(String id, Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
        super(null, id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPesoKg() {
        return pesoKg;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

}
