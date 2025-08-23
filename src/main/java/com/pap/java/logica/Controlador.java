package com.pap.java.logica;

import com.pap.java.interfaces.IControlador;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import java.util.List;

public class Controlador implements IControlador {
    
    private ManejadorUsuarios manejadorUsuarios;
    
    public Controlador() {
        this.manejadorUsuarios = ManejadorUsuarios.getInstancia();
    }
    
    @Override
    public boolean registrarLector(Lector lector) {
        try {
            if (lector != null && lector.getNombre() != null && lector.getEmail() != null) {
                manejadorUsuarios.agregarUsuario(lector);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al registrar lector: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean registrarBibliotecario(Bibliotecario bibliotecario) {
        try {
            if (bibliotecario != null && bibliotecario.getNombre() != null && 
                bibliotecario.getEmail() != null && bibliotecario.getNumeroEmpleado() != null) {
                manejadorUsuarios.agregarUsuario(bibliotecario);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al registrar bibliotecario: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean modificarEstado(Long lectorId, EstadoLector nuevoEstado) {
        try {
            if (lectorId != null && nuevoEstado != null) {
                List<Usuario> usuarios = manejadorUsuarios.listarUsuarios();
                for (Usuario usuario : usuarios) {
                    if (usuario instanceof Lector && usuario.getId().equals(lectorId)) {
                        Lector lector = (Lector) usuario;
                        lector.setEstado(nuevoEstado);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al modificar estado: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean cambiarZona(Long lectorId, Zona nuevaZona) {
        try {
            if (lectorId != null && nuevaZona != null) {
                List<Usuario> usuarios = manejadorUsuarios.listarUsuarios();
                boolean encontrado = false;
                for (Usuario usuario : usuarios) {
                    if (usuario instanceof Lector && usuario.getId().equals(lectorId)) {
                        Lector lector = (Lector) usuario;
                        lector.setZona(nuevaZona);
                        encontrado = true;
                        break;
                    }
                }
                return encontrado;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al cambiar zona: " + e.getMessage());
            return false;
        }
    }
}
