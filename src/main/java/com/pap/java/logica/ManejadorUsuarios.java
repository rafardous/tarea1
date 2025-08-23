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

    public void agregarUsuario(Usuario usuario) {
        Usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return Usuarios;
    }
}
