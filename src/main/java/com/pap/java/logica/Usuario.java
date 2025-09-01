package com.pap.java.logica;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Constructor vacío requerido por JPA
    public Usuario() {
        // Ya no se inicializa fechaCreacion
    }

    // Constructor con parámetros
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y setters (sin fechaCreacion)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
