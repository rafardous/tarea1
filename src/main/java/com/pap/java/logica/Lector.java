package com.pap.java.logica;

import jakarta.persistence.*;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un lector del sistema.
 * Extiende de Usuario y utiliza discriminador "Lector".
 */
@Entity
@Table(name = "lectores")
@DiscriminatorValue("Lector")
@PrimaryKeyJoinColumn(name = "email")
public class Lector extends Usuario {

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "fecha_registro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoLector estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "zona", nullable = false)
    private Zona zona;

    // Relación: un lector puede tener muchos préstamos
    @OneToMany(mappedBy = "lector", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Lector() {
        super();
        this.fechaRegistro = new Date();
        this.estado = EstadoLector.ACTIVO; // Estado por defecto
        this.zona = Zona.BIBLIOTECA_CENTRAL; // Zona por defecto
    }

    // Constructor con parámetros
    public Lector(String nombre, String email, String direccion, Date fechaRegistro, 
                  EstadoLector estado, Zona zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : new Date();
        this.estado = estado != null ? estado : EstadoLector.ACTIVO;
        this.zona = zona != null ? zona : Zona.BIBLIOTECA_CENTRAL;
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

    @Override
    public String toString() {
        return "Lector{" +
                "email='" + getEmail() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", estado=" + estado +
                ", zona=" + zona +
                '}';
    }
}
