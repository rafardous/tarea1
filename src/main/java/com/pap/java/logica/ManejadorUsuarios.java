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
        // check si ya existe un usuario con ese email
        for (Usuario u : Usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                throw new Exception("Ya existe un usuario con el email: " + usuario.getEmail());
            }
        }
        Usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return Usuarios;
    }
}
