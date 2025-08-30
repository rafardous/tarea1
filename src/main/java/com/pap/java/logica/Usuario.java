package com.pap.java.logica;

import jakarta.persistence.*;

// Clase abstracta para cualquier usuario del sistema
@Entity
@Table(name="Usuarios") 
@Inheritance(strategy = InheritanceType.JOINED) // permite herencia con subclases
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria (no estaba en el UML, pero es necesaria en BD)

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
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
