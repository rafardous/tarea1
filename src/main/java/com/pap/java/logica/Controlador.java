package com.pap.java.logica;

import com.pap.java.interfaces.IControlador;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.excepciones.UsuarioRepetidoExc;
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
    public boolean registrarLector(DtLector lector) throws UsuarioRepetidoExc, Exception {
        ManejadorUsuarios mu = ManejadorUsuarios.getInstancia();
        if (lector != null && lector.getNombre() != null && lector.getEmail() != null && lector.getDireccion() != null) {
            if (mu.buscarLector(lector.getEmail()) != null){
                throw new UsuarioRepetidoExc("Ya existe un usuario con ese email");
            } else {
                Usuario nuevoUsuario = null;
                nuevoUsuario = new Lector(lector.getNombre(),
                                         lector.getEmail(), 
                                         lector.getDireccion(), 
                                         lector.getFechaRegistro(), 
                                         lector.getEstado(), 
                                         lector.getZona()
                                         );

                mu.agregarUsuario(nuevoUsuario); 
            return true;
            }
        } else {
        throw new Exception("Datos incompletos del lector");
        }
    }

    @Override
    public boolean registrarBibliotecario(DtBibliotecario bibliotecario) throws UsuarioRepetidoExc, Exception {
        ManejadorUsuarios mu = ManejadorUsuarios.getInstancia();
        if (bibliotecario != null && bibliotecario.getNombre() != null && bibliotecario.getEmail() != null && bibliotecario.getNumeroEmpleado() != null) {
            if (mu.buscarLector(bibliotecario.getEmail()) != null){
                throw new UsuarioRepetidoExc("Ya existe un usuario con ese email");
            } else {
                Usuario nuevoUsuario = null;
                nuevoUsuario = new Bibliotecario(bibliotecario.getNombre(),
                                                bibliotecario.getEmail(), 
                                                bibliotecario.getNumeroEmpleado()
                                                ); 

                mu.agregarUsuario(nuevoUsuario); 
            return true;
            }
        } else {
        throw new Exception("Datos incompletos del bibliotecario");
        }
    }
   
    
    @Override
    public boolean modificarEstado(Long lectorId, EstadoLector nuevoEstado) {
        try {
            if (lectorId != null && nuevoEstado != null) {
                List<Usuario> usuarios = manejadorUsuarios.listarUsuarios();
                for (Usuario usuario : usuarios) {
                    if (usuario instanceof Lector && usuario.getEmail().equals(lectorId.toString())) {
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
                    if (usuario instanceof Lector && usuario.getEmail().equals(lectorId.toString())) {
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
