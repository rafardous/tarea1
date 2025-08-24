package com.pap.java.datatypes;

public class DtArticulo extends DtMaterial {
    private String descripcion;
    private float pesoKg;
    private String dimensiones;

    public DtArticulo() {}

    public DtArticulo(String id, java.util.Date fechaIngreso, String descripcion, 
                      float pesoKg, String dimensiones) {
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

    public float getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(float pesoKg) {
        this.pesoKg = pesoKg;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    @Override
    public String toString() {
        return super.toString() + 
               "\nDescripción: " + descripcion + 
               "\nPeso (kg): " + pesoKg + 
               "\nDimensiones: " + dimensiones;
    }
}
