package com.pap.java.logica;

import com.pap.java.logica.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ManejadorUsuarios {

    private static ManejadorUsuarios instancia = null;
    private List<Usuario> Usuarios;

    

    private ManejadorUsuarios() {
        this.Usuarios = new ArrayList<>();
    }

    public static ManejadorUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorUsuarios();
        }
        return instancia;
    }


    public void agregarUsuario(Usuario usuario) throws Exception {
        for (Usuario u : Usuarios) {
            // ver si email repetido
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                throw new Exception("Ya existe un usuario con el email: " + usuario.getEmail());
            }
    
            // ver si número de empleado no esta en uso, sólo si se trata de un bibliotecario
            if (usuario instanceof Bibliotecario && u instanceof Bibliotecario) {
                Bibliotecario nuevo = (Bibliotecario) usuario;
                Bibliotecario existente = (Bibliotecario) u;
    
                if (nuevo.getNumeroEmpleado().equalsIgnoreCase(existente.getNumeroEmpleado())) {
                    throw new Exception("Ya existe un bibliotecario con el número de empleado: " 
                                        + nuevo.getNumeroEmpleado());
                }
            }
        }
    
        Usuarios.add(usuario);
    }
    

    public List<Usuario> listarUsuarios() {
        return Usuarios;
    }
}
