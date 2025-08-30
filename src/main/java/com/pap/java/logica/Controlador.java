package com.pap.java.logica;

import com.pap.java.interfaces.IControlador;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.datatypes.DtBibliotecario;
import com.pap.java.datatypes.DtLector;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Controlador implements IControlador {
    
    private ManejadorUsuarios manejadorUsuarios;
    
    public Controlador() {
        this.manejadorUsuarios = ManejadorUsuarios.getInstancia();
    }
    
    @Override
    public boolean registrarLector(Lector lector) throws Exception {
        if (lector != null && lector.getNombre() != null && lector.getEmail() != null) {
            manejadorUsuarios.agregarUsuario(lector); 
            return true;
        }
        throw new Exception("Datos incompletos del lector");
    }

    @Override
    public boolean registrarBibliotecario(Bibliotecario bibliotecario) throws Exception {
        if (bibliotecario != null && bibliotecario.getNombre() != null && bibliotecario.getEmail() != null) {
            manejadorUsuarios.agregarUsuario(bibliotecario); // puede lanzar excepción
            return true;
        }
        throw new Exception("Datos incompletos del bibliotecario");
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
    
    @Override
    public List<DtLector> listarLectores() {
        try {
            List<Usuario> usuarios = manejadorUsuarios.listarUsuarios();
            List<DtLector> dtLectores = new ArrayList<>();
            
            for (Usuario usuario : usuarios) {
                if (usuario instanceof Lector) {
                    Lector lector = (Lector) usuario;
                    DtLector dtLector = new DtLector(
                        lector.getNombre(),
                        lector.getEmail(),
                        lector.getDireccion(),
                        lector.getFechaRegistro(),
                        lector.getEstado(),
                        lector.getZona()
                    );
                    dtLectores.add(dtLector);
                }
            }
            
            return dtLectores;
        } catch (Exception e) {
            System.err.println("Error al listar lectores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<DtBibliotecario> listaBibliotecarios(){
        try {
            List<Usuario> usuarios = manejadorUsuarios.listarUsuarios();
            List<DtBibliotecario> dtBibliotecarios = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                if (usuario instanceof Bibliotecario) {
                    Bibliotecario bibliotecario = (Bibliotecario) usuario;
                    DtBibliotecario dtBibliotecario = new DtBibliotecario(
                        bibliotecario.getNombre(),
                        bibliotecario.getEmail(),
                        bibliotecario.getNumeroEmpleado()
                    );
                    dtBibliotecarios.add(dtBibliotecario);
                }
            }
            return dtBibliotecarios;
        } catch (Exception e) {
            System.err.println("Error al listar bibliotecarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
