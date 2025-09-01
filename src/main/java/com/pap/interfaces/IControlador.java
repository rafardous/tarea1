package com.pap.interfaces;
import com.pap.excepciones.*;
import com.pap.datatypes.*;
import java.util.ArrayList;

import java.util.List;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.datatypes.DtBibliotecario;
import com.pap.datatypes.DtLector;


public interface IControlador {
    

    boolean registrarLector(DtLector lector) throws Exception;


    boolean registrarBibliotecario(DtBibliotecario bibliotecario) throws Exception;


    boolean modificarEstado(String lectorEmail, EstadoLector nuevoEstado);


    boolean cambiarZona(String lectorEmail, Zona nuevaZona);
    

    List<DtLector> listarLectores();


    List<DtBibliotecario> listaBibliotecarios();

    public void RegistrarDonacionLibro(String idLibro, String titulo, String paginas) throws RegistrarDonacionLibroExcepcion;

    public void RegistrarDonacionArticulo(String idArticulo, String descripcion, float pesoKg, String dimensiones) throws RegistrarDonacionArticuloExcepcion;

	public ArrayList<DtArticulo> RegistroDonacionArticulo() throws RegistroDonacionExcepcion;

    public ArrayList<DtLibro> RegistroDonacionLibro() throws RegistroDonacionExcepcion;

    public void RegistroDonacionFecha() throws RegistroDonacionFechaExcepcion;
    
    public void RegistrarPrestamo() throws RegistrarPrestamoExcepcion;

    public void ActualizarEstadoPrestamo() throws ActualizarEstadoPrestamoExcepcion;

	public void ActualizarPrestamo() throws ActualizarPrestamoExcepcion;

    public void ListarPrestamoLector() throws ListarPrestamoLectorExcepcion;
    
    public void HistorialPrestamoBibliotecario() throws HistorialPrestamoBibliotecarioExcepcion;

	public void ReportePrestamoZona() throws ReportePrestamoZonaExcepcion;

    public void MaterialPendiente() throws MaterialPendienteExcepcion;

    
}
