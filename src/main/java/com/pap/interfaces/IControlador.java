package com.pap.interfaces;

import com.pap.java.logica.Lector;
import com.pap.java.logica.Bibliotecario;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;

public interface IControlador {
    
    /**
     * Registra un nuevo usuario de tipo lector en la base de datos
     * @param lector El lector a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    boolean registrarLector(Lector lector);
    
    /**
     * Registra un nuevo usuario de tipo bibliotecario en la base de datos
     * @param bibliotecario El bibliotecario a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    boolean registrarBibliotecario(Bibliotecario bibliotecario);
    
    /**
     * Modifica el estado de un lector
     * @param lectorId El ID del lector
     * @param nuevoEstado El nuevo estado a asignar
     * @return true si se modificó correctamente, false en caso contrario
     */
    boolean modificarEstado(Long lectorId, EstadoLector nuevoEstado);
    
    /**
     * Cambia la zona de un lector
     * @param lectorId El ID del lector
     * @param nuevaZona La nueva zona a asignar
     * @return true si se cambió correctamente, false en caso contrario
     */
    boolean cambiarZona(Long lectorId, Zona nuevaZona);
}
