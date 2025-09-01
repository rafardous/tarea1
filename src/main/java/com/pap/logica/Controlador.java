package com.pap.logica;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.excepciones.UsuarioRepetidoExc;
import com.pap.datatypes.DtBibliotecario;
import com.pap.datatypes.DtLector;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


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
            if (manejadorUsuarios.buscarUsuario(lector.getEmail()) != null) {
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
            if (manejadorUsuarios.buscarUsuario(bibliotecario.getEmail()) != null) {
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

    // implementados aca para que los datos que vienen cargados en el dt sean validos
    // capaz lo saco y dejo solo verificacion en altalector, despues veo
    
    private boolean validarDatosLector(DtLector lector) {
        return lector.getNombre() != null && !lector.getNombre().trim().isEmpty() &&
               lector.getEmail() != null && !lector.getEmail().trim().isEmpty() &&
               lector.getDireccion() != null && !lector.getDireccion().trim().isEmpty() &&
               lector.getEstado() != null && lector.getZona() != null;
    }
    // lo mismo para el biblotecario
    private boolean validarDatosBibliotecario(DtBibliotecario bibliotecario) {
        return bibliotecario.getNombre() != null && !bibliotecario.getNombre().trim().isEmpty() &&
               bibliotecario.getEmail() != null && !bibliotecario.getEmail().trim().isEmpty() &&
               bibliotecario.getNumeroEmpleado() != null && !bibliotecario.getNumeroEmpleado().trim().isEmpty();
    }

    // para que quede lindo en la salida de enlistar lectores
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
    
    // lo mismo 
    private DtBibliotecario convertirADtBibliotecario(Bibliotecario bibliotecario) {
        return new DtBibliotecario(
            bibliotecario.getNombre(),
            bibliotecario.getEmail(),
            bibliotecario.getNumeroEmpleado()
        );
    }

          
}
