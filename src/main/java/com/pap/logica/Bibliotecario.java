package com.pap.logica;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa a un bibliotecario del sistema.
 * Extiende de Usuario con herencia por tablas separadas.
 */
@Entity
@Table(name = "bibliotecarios")
@PrimaryKeyJoinColumn(name = "email")
public class Bibliotecario extends Usuario {
    
    @Column(name = "numero_empleado", nullable = false, unique = true, length = 50)
    private String numeroEmpleado;
    
    // Relación: un bibliotecario puede tener muchos préstamos
    @OneToMany(mappedBy = "bibliotecario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();
    
    // Constructor vacío requerido por JPA
    public Bibliotecario() {
        super();
    }
    
    // Constructor con parámetros
    public Bibliotecario(String nombre, String email, String numeroEmpleado) {
        super(nombre, email);
        this.numeroEmpleado = numeroEmpleado;
    }
    
    // Getters y setters
    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }
    
    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }
    
    @Override
    public String toString() {
        return "Bibliotecario{" +
                "email='" + getEmail() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", numeroEmpleado='" + numeroEmpleado + '\'' +
                '}';
    }
}
