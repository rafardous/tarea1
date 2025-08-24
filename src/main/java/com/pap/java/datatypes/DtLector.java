package com.pap.java.datatypes;

import java.util.Date;

public class DtLector extends DtUsuario {
    private String direccion;
    private Date fechaRegistro;
    private EstadoLector estado;
    private Zona zona;

    public DtLector() {}

    public DtLector(String nombre, String email, String direccion, Date fechaRegistro, 
                    EstadoLector estado, Zona zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

 
    public String getDireccion() {
        return direccion;
    }


    public Date getFechaRegistro() {
        return fechaRegistro;
    }


    public EstadoLector getEstado() {
        return estado;
    }


    public Zona getZona() {
        return zona;
    }


    @Override
    public String toString() {
        return super.toString() + 
               "\nDirección: " + direccion + 
               "\nFecha de Registro: " + fechaRegistro + 
               "\nEstado: " + estado + 
               "\nZona: " + zona;
    }
}
