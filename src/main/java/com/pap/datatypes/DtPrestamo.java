package com.pap.datatypes;

import java.util.Date;

public class DtPrestamo {
    private int id;
    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;
    private DtLector lector;
    private DtBibliotecario bibliotecario;
    private DtMaterial material;

    public DtPrestamo() {}

    public DtPrestamo(Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado,
                      DtLector lector, DtBibliotecario bibliotecario, DtMaterial material) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.material = material;
    }

    public int getId() {
        return id;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public DtLector getLector() {
        return lector;
    }

    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public DtMaterial getMaterial(){
        return material;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Fecha de Solicitud: ").append(fechaSolicitud).append("\n");
        sb.append("Fecha de Devolución: ").append(fechaDevolucion).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Lector: ").append(lector != null ? lector.getNombre() : "N/A").append("\n");
        sb.append("Bibliotecario: ").append(bibliotecario != null ? bibliotecario.getNombre() : "N/A").append("\n");
        sb.append("Materiales: ").append(material != null ? material.getIdMaterial() : 0).append(" material");
        return sb.toString();
    }
}