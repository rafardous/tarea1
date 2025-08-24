package com.pap.java.interfaces;

import com.pap.java.logica.Lector;
import com.pap.java.logica.Bibliotecario;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;

public interface IControlador {
    
    
    // en vez de void, vamo a probar con que devuelva true si pudo registrar/modificar algo y false si hubo error en cada caso de estos
    boolean registrarLector(Lector lector);

    boolean registrarBibliotecario(Bibliotecario bibliotecario);

    boolean modificarEstado(Long lectorId, EstadoLector nuevoEstado);

    boolean cambiarZona(Long lectorId, Zona nuevaZona);
}
