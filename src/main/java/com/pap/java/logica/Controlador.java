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
import java.util.Date;

/**
 * Controlador principal que implementa la lógica de negocio del sistema.
 * Gestiona todas las operaciones relacionadas con usuarios.
 */
public class Controlador implements IControlador {
    
    private final ManejadorUsuarios manejadorUsuarios;
    
    public Controlador() {
        this.manejadorUsuarios = ManejadorUsuarios.getInstancia();
    }
    
    @Override
    public boolean registrarLector(DtLector lector) throws UsuarioRepetidoExc, Exception {
        try {
            // Validación de datos
            if (lector == null || !validarDatosLector(lector)) {
                throw new Exception("Datos incompletos o inválidos del lector");
            }
            
            // Verificar si ya existe un usuario con ese email
            if (manejadorUsuarios.existeUsuario(lector.getEmail())) {
                throw new UsuarioRepetidoExc("Ya existe un usuario con el email: " + lector.getEmail());
            }
            
            // Crear y persistir el nuevo lector
            Lector nuevoLector = new Lector(
                lector.getNombre(),
                lector.getEmail(), 
                lector.getDireccion(), 
                lector.getFechaRegistro(), 
                lector.getEstado(), 
                lector.getZona()
            );
            
            manejadorUsuarios.agregarUsuario(nuevoLector);
            return true;
            
        } catch (UsuarioRepetidoExc e) {
            throw e; // Re-lanzar excepción específica
        } catch (Exception e) {
            throw new Exception("Error al registrar lector: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean registrarBibliotecario(DtBibliotecario bibliotecario) throws UsuarioRepetidoExc, Exception {
        try {
            // Validación de datos
            if (bibliotecario == null || !validarDatosBibliotecario(bibliotecario)) {
                throw new Exception("Datos incompletos o inválidos del bibliotecario");
            }
            
            // Verificar si ya existe un usuario con ese email
            if (manejadorUsuarios.existeUsuario(bibliotecario.getEmail())) {
                throw new UsuarioRepetidoExc("Ya existe un usuario con el email: " + bibliotecario.getEmail());
            }
            
            // Crear y persistir el nuevo bibliotecario
            Bibliotecario nuevoBibliotecario = new Bibliotecario(
                bibliotecario.getNombre(),
                bibliotecario.getEmail(), 
                bibliotecario.getNumeroEmpleado()
            );
            
            manejadorUsuarios.agregarUsuario(nuevoBibliotecario);
            return true;
            
        } catch (UsuarioRepetidoExc e) {
            throw e; // Re-lanzar excepción específica
        } catch (Exception e) {
            throw new Exception("Error al registrar bibliotecario: " + e.getMessage(), e);
        }
    }
   
    @Override
    public boolean modificarEstado(String lectorEmail, EstadoLector nuevoEstado) {
        try {
            if (lectorEmail == null || nuevoEstado == null) {
                return false;
            }
            
            Lector lector = manejadorUsuarios.buscarLector(lectorEmail);
            if (lector != null) {
                lector.setEstado(nuevoEstado);
                manejadorUsuarios.actualizarUsuario(lector);
                return true;
            }
            return false;
            
        } catch (Exception e) {
            System.err.println("Error al modificar estado: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean cambiarZona(String lectorEmail, Zona nuevaZona) {
        try {
            if (lectorEmail == null || nuevaZona == null) {
                return false;
            }
            
            Lector lector = manejadorUsuarios.buscarLector(lectorEmail);
            if (lector != null) {
                lector.setZona(nuevaZona);
                manejadorUsuarios.actualizarUsuario(lector);
                return true;
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
            List<Lector> lectores = manejadorUsuarios.listarLectores();
            return lectores.stream()
                .map(this::convertirADtLector)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.err.println("Error al listar lectores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<DtBibliotecario> listaBibliotecarios() {
        try {
            List<Bibliotecario> bibliotecarios = manejadorUsuarios.listarBibliotecarios();
            return bibliotecarios.stream()
                .map(this::convertirADtBibliotecario)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.err.println("Error al listar bibliotecarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Métodos auxiliares privados
    
    private boolean validarDatosLector(DtLector lector) {
        return lector.getNombre() != null && !lector.getNombre().trim().isEmpty() &&
               lector.getEmail() != null && !lector.getEmail().trim().isEmpty() &&
               lector.getDireccion() != null && !lector.getDireccion().trim().isEmpty() &&
               lector.getEstado() != null && lector.getZona() != null;
    }
    
    private boolean validarDatosBibliotecario(DtBibliotecario bibliotecario) {
        return bibliotecario.getNombre() != null && !bibliotecario.getNombre().trim().isEmpty() &&
               bibliotecario.getEmail() != null && !bibliotecario.getEmail().trim().isEmpty() &&
               bibliotecario.getNumeroEmpleado() != null && !bibliotecario.getNumeroEmpleado().trim().isEmpty();
    }
    
    private DtLector convertirADtLector(Lector lector) {
        return new DtLector(
            lector.getNombre(),
            lector.getEmail(),
            lector.getDireccion(),
            lector.getFechaRegistro(),
            lector.getEstado(),
            lector.getZona()
        );
    }
    
    private DtBibliotecario convertirADtBibliotecario(Bibliotecario bibliotecario) {
        return new DtBibliotecario(
            bibliotecario.getNombre(),
            bibliotecario.getEmail(),
            bibliotecario.getNumeroEmpleado()
        );
    }

    // Métodos adicionales útiles
    
    public DtLector buscarLector(String email) {
        try {
            Lector lector = manejadorUsuarios.buscarLector(email);
            return lector != null ? convertirADtLector(lector) : null;
        } catch (Exception e) {
            System.err.println("Error al buscar lector: " + e.getMessage());
            return null;
        }
    }
    
    public DtBibliotecario buscarBibliotecario(String email) {
        try {
            Bibliotecario bibliotecario = manejadorUsuarios.buscarBibliotecario(email);
            return bibliotecario != null ? convertirADtBibliotecario(bibliotecario) : null;
        } catch (Exception e) {
            System.err.println("Error al buscar bibliotecario: " + e.getMessage());
            return null;
        }
    }
    
    public boolean eliminarUsuario(String email) {
        try {
            manejadorUsuarios.eliminarUsuario(email);
            return true;
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public List<DtLector> buscarLectoresPorNombre(String nombre) {
        try {
            List<Usuario> usuarios = manejadorUsuarios.buscarUsuariosPorNombre(nombre);
            return usuarios.stream()
                .filter(u -> u instanceof Lector)
                .map(u -> convertirADtLector((Lector) u))
                .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al buscar lectores por nombre: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
