package com.pap.java.logica;

import jakarta.persistence.*;
import java.util.List;
import com.pap.java.logica.Prestamo;

@Entity
@Table(name = "bibliotecarios")
public class Bibliotecario extends Usuario {
    
    @Column(nullable = false)
    private String numeroEmpleado;
    
    // Relación: un bibliotecario puede tener muchos préstamos
    @OneToMany(mappedBy = "bibliotecario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos;
    
    // Constructor vacío requerido por JPA
    public Bibliotecario() {}
    
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
    
    public List<Prestamo> getPrestamos() {
        return prestamos;
    }
    
    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }
}
