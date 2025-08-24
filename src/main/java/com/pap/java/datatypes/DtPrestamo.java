package com.pap.java.datatypes;

import java.util.Date;
import java.util.List;

public class DtPrestamo {
    private Long id;
    private Date fechaSolicitud;
    private Date fechaDevolucion;
    private EstadoPrestamo estado;
    private DtLector lector;
    private DtBibliotecario bibliotecario;
    private List<DtMaterial> materiales;

    public DtPrestamo() {}

    public DtPrestamo(Long id, Date fechaSolicitud, Date fechaDevolucion, EstadoPrestamo estado,
                      DtLector lector, DtBibliotecario bibliotecario, List<DtMaterial> materiales) {
        this.id = id;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.materiales = materiales;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public DtLector getLector() {
        return lector;
    }

    public void setLector(DtLector lector) {
        this.lector = lector;
    }

    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(DtBibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public List<DtMaterial> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<DtMaterial> materiales) {
        this.materiales = materiales;
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
        sb.append("Materiales: ").append(materiales != null ? materiales.size() : 0).append(" material(es)");
        return sb.toString();
    }
}