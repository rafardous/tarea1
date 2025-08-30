package com.pap.java.logica;

import jakarta.persistence.*;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.logica.Prestamo;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lectores")
public class Lector extends Usuario {

    @Column(nullable = false)
    private String direccion;

    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Enumerated(EnumType.STRING)
    private EstadoLector estado;

    @Enumerated(EnumType.STRING)
    private Zona zona;

    // Relación: un lector puede tener muchos préstamos
    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;

    // Constructor vacío requerido por JPA
    public Lector() {}

    // Constructor con parámetros
    public Lector(String nombre, String email, String direccion, Date fechaRegistro, 
                  EstadoLector estado, Zona zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

    // Getters y setters
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoLector getEstado() {
        return estado;
    }

    public void setEstado(EstadoLector estado) {
        this.estado = estado;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
