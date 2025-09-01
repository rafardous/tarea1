package com.pap.logica;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "materiales")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se va a identificar por un generationtype
    @Column(name = "id_material")
    private Integer idMaterial;
    @Column(name = "id")
    private String id;
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    public Material(){}

    public Material(Integer idMaterial, String id, Date fechaIngreso) {
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

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

}
