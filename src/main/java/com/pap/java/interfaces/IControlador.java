package com.pap.java.interfaces;

import java.util.List;
import com.pap.java.logica.Lector;
import com.pap.java.logica.Bibliotecario;
import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.datatypes.DtBibliotecario;
import com.pap.java.datatypes.DtLector;

public interface IControlador {
    
    
    // en vez de void, vamo a probar con que devuelva true si pudo registrar/modificar algo y false si hubo error en cada caso de estos
    boolean registrarLector(Lector lector) throws Exception;

    boolean registrarBibliotecario(Bibliotecario bibliotecario);

    boolean modificarEstado(Long lectorId, EstadoLector nuevoEstado);

    boolean cambiarZona(Long lectorId, Zona nuevaZona);
    
    // metodo para listar lectores y la otra para bibliotecarios
    List<DtLector> listarLectores();

    List<DtBibliotecario> listaBibliotecarios();
}
