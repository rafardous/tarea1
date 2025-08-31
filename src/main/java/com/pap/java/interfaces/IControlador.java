package com.pap.java.interfaces;

import java.util.List;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.datatypes.DtBibliotecario;
import com.pap.java.datatypes.DtLector;

/**
 * Interfaz que define el contrato para el controlador del sistema.
 * Define todas las operaciones disponibles para la gestión de usuarios.
 */
public interface IControlador {
    
    /**
     * Registra un nuevo lector en el sistema.
     * @param lector Los datos del lector a registrar
     * @return true si se registró exitosamente, false en caso contrario
     * @throws Exception Si hay un error en el registro
     */
    boolean registrarLector(DtLector lector) throws Exception;

    /**
     * Registra un nuevo bibliotecario en el sistema.
     * @param bibliotecario Los datos del bibliotecario a registrar
     * @return true si se registró exitosamente, false en caso contrario
     * @throws Exception Si hay un error en el registro
     */
    boolean registrarBibliotecario(DtBibliotecario bibliotecario) throws Exception;

    /**
     * Modifica el estado de un lector.
     * @param lectorEmail El email del lector a modificar
     * @param nuevoEstado El nuevo estado del lector
     * @return true si se modificó exitosamente, false en caso contrario
     */
    boolean modificarEstado(String lectorEmail, EstadoLector nuevoEstado);

    /**
     * Cambia la zona de un lector.
     * @param lectorEmail El email del lector a modificar
     * @param nuevaZona La nueva zona del lector
     * @return true si se cambió exitosamente, false en caso contrario
     */
    boolean cambiarZona(String lectorEmail, Zona nuevaZona);
    
    /**
     * Lista todos los lectores del sistema.
     * @return Lista de lectores
     */
    List<DtLector> listarLectores();

    /**
     * Lista todos los bibliotecarios del sistema.
     * @return Lista de bibliotecarios
     */
    List<DtBibliotecario> listaBibliotecarios();
}
