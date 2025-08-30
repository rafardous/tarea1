package com.pap.java.logica;

import jakarta.persistence.*;

// Clase abstracta para cualquier usuario del sistema
@Entity
@Table(name="Usuarios") 
@Inheritance(strategy = InheritanceType.JOINED) // permite herencia con subclases
public abstract class Usuario {

    @Id
    @Column(nullable = false, unique = true)
    private String email;   // Clave primaria usando el email

    @Column(nullable = false)
    private String nombre;

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }



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
}
