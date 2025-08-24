package com.pap.java.datatypes;

import java.util.Date;

public class DtMaterial {
    private String id;
    private Date fechaIngreso;

    public DtMaterial() {}

    public DtMaterial(String id, Date fechaIngreso) {
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

    @Override
    public String toString() {
        return "ID: " + id + "\nFecha de Ingreso: " + fechaIngreso;
    }
}
