package com.pap.datatypes;

import java.util.Date;

public class DtMaterial {
    private Integer idMaterial;
    private String id;
    private Date fechaIngreso;

    public DtMaterial(Integer idMaterial, String id, Date fechaIngreso) {
        super();
        this.idMaterial = idMaterial;
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public String getId() {
        return id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

}
