package com.pap.interfaces;

import java.util.List;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.datatypes.DtBibliotecario;
import com.pap.datatypes.DtLector;

/**
 * Interfaz que define el contrato para el controlador del sistema.
 * Define todas las operaciones disponibles para la gestión de usuarios.
 */
public interface IControlador {
    

    boolean registrarLector(DtLector lector) throws Exception;


    boolean registrarBibliotecario(DtBibliotecario bibliotecario) throws Exception;


    boolean modificarEstado(String lectorEmail, EstadoLector nuevoEstado);


    boolean cambiarZona(String lectorEmail, Zona nuevaZona);
    

    List<DtLector> listarLectores();


    List<DtBibliotecario> listaBibliotecarios();

    
}
