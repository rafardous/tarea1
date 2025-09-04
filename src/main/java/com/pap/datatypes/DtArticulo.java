package com.pap.datatypes;

import java.util.Date;

public class DtArticulo extends DtMaterial{
    final private String descripcion;
    final private float pesoKg;
    final private String dimensiones;

    public DtArticulo(final Integer idMaterial, final String id, final Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
        super(idMaterial, id, fechaIngreso);
        this.descripcion = descripcion;
        this.pesoKg = pesoKg;
        this.dimensiones = dimensiones;
    }

    public DtArticulo(final String id, final Date fechaIngreso, String descripcion, float pesoKg, String dimensiones) {
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

}
