package com.pap.logica;
import com.pap.datatypes.*;
import com.pap.excepciones.*;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.datatypes.DtPrestamo;
import com.pap.excepciones.UsuarioRepetidoExc;
import com.pap.datatypes.DtBibliotecario;
import com.pap.datatypes.DtLector;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Date;
import org.hibernate.Hibernate;


public class Controlador implements IControlador {
    
    private final ManejadorUsuarios manejadorUsuarios;
    private final ManejadorPrestamos manejadorPrestamos;
    private final ManejadorGestionMaterial manejadorMaterial;
    private final ManejadorControlSeguimiento mCS;
    
    public Controlador() {
        this.manejadorUsuarios = ManejadorUsuarios.getInstancia();
        this.manejadorPrestamos = ManejadorPrestamos.getInstancia();
        this.manejadorMaterial = ManejadorGestionMaterial.getInstancia();
        this.mCS = ManejadorControlSeguimiento.getInstancia();
    }
    

    // -- FUNCIONES DE GESTION DE USUARIOS --------------------------------
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


    // FUNCIONES DE GESTION DE MATERIALES --------------------------------

    @Override
    public void RegistrarDonacionLibro(String idLibro, String titulo, String paginas) throws RegistrarDonacionLibroExcepcion{
		ManejadorGestionMaterial mGM = ManejadorGestionMaterial.getInstancia();
		Material material = mGM.buscarMaterial(idLibro);

		if (material != null){
			throw new RegistrarDonacionLibroExcepcion("El Material: " + idLibro + " ya esta registrado");
		}

		Libro libro = new Libro(idLibro, new Date(), titulo, paginas);
		mGM.registrarDonacionLibro(libro);
	}

	@Override
    public void RegistrarDonacionArticulo(String idArticulo, String descripcion, float pesoKg, String dimensiones) throws RegistrarDonacionArticuloExcepcion{
		ManejadorGestionMaterial mGM = ManejadorGestionMaterial.getInstancia();
		Material material = mGM.buscarMaterial(idArticulo);

		if (material != null){
			throw new RegistrarDonacionArticuloExcepcion("El Material: " + idArticulo + " ya esta registrado");
		}

		Articulo articulo = new Articulo(idArticulo, new Date(), descripcion, pesoKg, dimensiones);
		mGM.registrarDonacionArticulo(articulo);
	}

	@Override
	public ArrayList<DtArticulo> RegistroDonacionArticulo() throws RegistroDonacionExcepcion{
		ManejadorGestionMaterial mGM = ManejadorGestionMaterial.getInstancia();

		ArrayList<DtArticulo> articulos = mGM.obtenerArticulos();
		
		return articulos;
	}

	@Override
	public ArrayList<DtLibro> RegistroDonacionLibro() throws RegistroDonacionExcepcion{
		ManejadorGestionMaterial mGM = ManejadorGestionMaterial.getInstancia();

		ArrayList<DtLibro> libros = mGM.obtenerLibros();
		
		return libros;
	}

    @Override
    public ArrayList<DtMaterial> RegistroDonacionFecha(Date fechaDesde, Date fechaHasta) throws RegistroDonacionFechaExcepcion{
		ManejadorGestionMaterial mGM = ManejadorGestionMaterial.getInstancia();

		ArrayList<DtMaterial> materiales = mGM.obtenerMateriales(fechaDesde, fechaHasta);
		
		return materiales;
	}



// FUNCIONES DE GESTION DE PRESTAMOS --------------------------------


    public boolean RegistrarPrestamo(Date fechaSol, Date fechaDev, EstadoPrestamo estado, String emailLector, String emailBiblio, String material) throws RegistrarPrestamoExcepcion{
        
        // existe ellector?
        if (!manejadorUsuarios.existeLector(emailLector)) {
            throw new RegistrarPrestamoExcepcion("El lector con email '" + emailLector + "' no existe en el sistema");
        }
        
        // existe bibliotecario?
        if (!manejadorUsuarios.existeBibliotecario(emailBiblio)) {
            throw new RegistrarPrestamoExcepcion("El bibliotecario con email '" + emailBiblio + "' no existe en el sistema");
        }
        
        // y material
        if (!manejadorMaterial.existeMaterial(material)) {
            throw new RegistrarPrestamoExcepcion("El material con ID '" + material + "' no existe en el sistema");
        }
        
        Prestamo nuevoPrestamo = new Prestamo(
                fechaSol,
                fechaDev,
                estado,
                manejadorUsuarios.buscarLector(emailLector),
                manejadorUsuarios.buscarBibliotecario(emailBiblio),
                manejadorMaterial.buscarMaterial(material)
            );

        try {
            manejadorPrestamos.agregarPrestamo(nuevoPrestamo);
            return true;
        } catch (Exception e) {
            throw new RegistrarPrestamoExcepcion("Error al registrar el prestamo: " + e.getMessage());
        }
    }

	@Override
	public boolean ActualizarPrestamo(int id, Date fechaSol, Date fechaDev, EstadoPrestamo estado) throws ActualizarPrestamoExcepcion{

        try {
            Prestamo prestamo = manejadorPrestamos.buscarPrestamo(id);

        if (prestamo != null){
            prestamo.setFechaSolicitud(fechaSol);
            prestamo.setFechaDevolucion(fechaDev);
            prestamo.setEstado(estado);
            manejadorPrestamos.actualizarPrestamo(prestamo);
            return true;
        }else {
            return false;
        }
        } catch (Exception e) {
            System.err.println("Error al cambiar datos Prestamo: " + e.getMessage());
            return false;
        }
	}

    // para listar todos los prestamos   -- no pude achicar mas, viejo
    public ArrayList<DtPrestamo> listarTodosLosPrestamos() {
        try {
            List<Prestamo> prestamos = manejadorPrestamos.listarPrestamos();
            
            ArrayList<DtPrestamo> dtPrestamos = new ArrayList<>();
            
            if (prestamos != null) {            // pruebo con hacer una lista de DtPrestamos asi, queda medio chancha pero we
                for (Prestamo prestamo : prestamos) {
                    // Inicializar entidades lazy para evitar problemas de casting
                    Hibernate.initialize(prestamo.getLector());
                    Hibernate.initialize(prestamo.getBibliotecario());
                    Hibernate.initialize(prestamo.getMaterial());
                    
                    // Determinar el tipo de material usando Hibernate.unproxy() para obtener la clase real
                    Material material = prestamo.getMaterial();
                    Material materialReal = (Material) Hibernate.unproxy(material);
                    DtMaterial dtMaterial;
                                       
                    
                        if (materialReal.getClass().getSimpleName().equals("Libro")) {
                         // Es un libro, obtener datos específicos
                         Libro libro = (Libro) materialReal;
                         dtMaterial = new DtLibro(
                             libro.getId().toString(), // Convertir Integer a String
                             libro.getFechaIngreso(),
                             libro.getTitulo(),
                             libro.getCantidadPaginas()
                         );
                         // Establecer idMaterial usando reflexión
                         try {
                             java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                             idMaterialField.setAccessible(true);
                             idMaterialField.set(dtMaterial, libro.getIdMaterial());
                         } catch (Exception e) {
                             System.err.println("Error al establecer idMaterial del libro: " + e.getMessage());
                         }
                    } else if (materialReal.getClass().getSimpleName().equals("Articulo")) {
                         // Es un artículo, obtener datos específicos
                         Articulo articulo = (Articulo) materialReal;
                         dtMaterial = new DtArticulo(
                             articulo.getId().toString(), // Convertir Integer a String
                             articulo.getFechaIngreso(),
                             articulo.getDescripcion(),
                             articulo.getPesoKg(),
                             articulo.getDimensiones()
                         );
                         // Establecer idMaterial usando reflexión
                         try {
                             java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                             idMaterialField.setAccessible(true);
                             idMaterialField.set(dtMaterial, articulo.getIdMaterial());
                         } catch (Exception e) {
                             System.err.println("Error al establecer idMaterial del articulo: " + e.getMessage());
                         }
                    } else {
                         // Material genérico como fallback
                         dtMaterial = new DtMaterial(
                             material.getIdMaterial(),
                             material.getId(),
                             material.getFechaIngreso()
                         );
                    }
                    
                    DtPrestamo dtPrestamo = new DtPrestamo(
                        prestamo.getId(),
                        prestamo.getFechaSolicitud(),
                        prestamo.getFechaDevolucion(),
                        prestamo.getEstado(),
                        new DtLector(
                            prestamo.getLector().getNombre(),
                            prestamo.getLector().getEmail(),
                            prestamo.getLector().getDireccion(),
                            prestamo.getLector().getFechaRegistro(),
                            prestamo.getLector().getEstado(),
                            prestamo.getLector().getZona()
                        ),
                        new DtBibliotecario(
                            prestamo.getBibliotecario().getNombre(),
                            prestamo.getBibliotecario().getEmail(),
                            prestamo.getBibliotecario().getNumeroEmpleado()
                        ),
                        dtMaterial
                    );
                    // Usar reflexión para establecer el id ya que es privado
                    try {
                        java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
                        idField.setAccessible(true);
                        idField.set(dtPrestamo, prestamo.getId());
                    } catch (Exception e) {
                        System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
                    }
                    dtPrestamos.add(dtPrestamo);
                }
            }
            
            return dtPrestamos;
        } catch (Exception e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

	@Override
    public ArrayList<DtPrestamo> ListarPrestamoLector(String emailLector) throws ListarPrestamoLectorExcepcion{ 
        // uso el mismo criterio que en la anterior listarTodosLosPrestamos pro filtro por emailLector
        try {           
            List<Prestamo> prestamos = manejadorPrestamos.listarPrestamos();
            
            ArrayList<DtPrestamo> dtPrestamos = new ArrayList<>();
            
            if (prestamos != null) {            
                for (Prestamo prestamo : prestamos) {
                    Hibernate.initialize(prestamo.getLector());
                    Hibernate.initialize(prestamo.getBibliotecario());
                    Hibernate.initialize(prestamo.getMaterial());
                    
                    String lector = prestamo.getLector().getEmail();        // obtener email del elctor para comparar
                    Material material = prestamo.getMaterial();
                    Material materialReal = (Material) Hibernate.unproxy(material);
                    DtMaterial dtMaterial;
                                       
                    if (lector.equalsIgnoreCase(emailLector)) {     // comparo por email, no salgo del FOR!
                        if (materialReal.getClass().getSimpleName().equals("Libro")) {
                            Libro libro = (Libro) materialReal;
                            dtMaterial = new DtLibro(
                                libro.getId().toString(), // conv integer a String
                                libro.getFechaIngreso(),
                                libro.getTitulo(),
                                libro.getCantidadPaginas()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, libro.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del libro: " + e.getMessage());
                            }
                        } else if (materialReal.getClass().getSimpleName().equals("Articulo")) {
                            Articulo articulo = (Articulo) materialReal;
                            dtMaterial = new DtArticulo(
                                articulo.getId().toString(), 
                                articulo.getFechaIngreso(),
                                articulo.getDescripcion(),
                                articulo.getPesoKg(),
                                articulo.getDimensiones()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, articulo.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del articulo: " + e.getMessage());
                            }
                        } else {
                            dtMaterial = new DtMaterial(
                                material.getIdMaterial(),
                                material.getId(),
                                material.getFechaIngreso()
                            );
                        }
                        
                        DtPrestamo dtPrestamo = new DtPrestamo(
                            prestamo.getId(),
                            prestamo.getFechaSolicitud(),
                            prestamo.getFechaDevolucion(),
                            prestamo.getEstado(),
                            new DtLector(
                                prestamo.getLector().getNombre(),
                                prestamo.getLector().getEmail(),
                                prestamo.getLector().getDireccion(),
                                prestamo.getLector().getFechaRegistro(),
                                prestamo.getLector().getEstado(),
                                prestamo.getLector().getZona()
                            ),
                            new DtBibliotecario(
                                prestamo.getBibliotecario().getNombre(),
                                prestamo.getBibliotecario().getEmail(),
                                prestamo.getBibliotecario().getNumeroEmpleado()
                            ),
                            dtMaterial
                        );
                        try {
                            java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
                            idField.setAccessible(true);
                            idField.set(dtPrestamo, prestamo.getId());
                        } catch (Exception e) {
                            System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
                        }
                        dtPrestamos.add(dtPrestamo);
                    }   
                }
            }           // para no confundirme, este esl del IF inicial
            
            return dtPrestamos;
        } catch (Exception e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
            return new ArrayList<>();
        }
	}


    @Override
    public ArrayList<DtPrestamo> ListarPrestamoBiblioteacrio(String nroEmpleado) throws HistorialPrestamoBibliotecarioExcepcion{ 
        // uso el mismo criterio que en la anterior listarTodosLosPrestamos pro filtro por bibliotecario
        // uso el mismo criterio que en la anterior listarTodosLosPrestamos pro filtro por bibliotecario
        try {           
            List<Prestamo> prestamos = manejadorPrestamos.listarPrestamos();
            
            ArrayList<DtPrestamo> dtPrestamos = new ArrayList<>();
            
            if (prestamos != null) {            
                for (Prestamo prestamo : prestamos) {
                    Hibernate.initialize(prestamo.getLector());
                    Hibernate.initialize(prestamo.getBibliotecario());
                    Hibernate.initialize(prestamo.getMaterial());
                    
                    String bibliotecario = prestamo.getBibliotecario().getNumeroEmpleado();        // obtener nro del bibliotecario para comparar
                    Material material = prestamo.getMaterial();
                    Material materialReal = (Material) Hibernate.unproxy(material);
                    DtMaterial dtMaterial;
                                    
                    if (bibliotecario.equalsIgnoreCase(nroEmpleado)) {     // comparo por email, no salgo del FOR!
                        if (materialReal.getClass().getSimpleName().equals("Libro")) {
                            Libro libro = (Libro) materialReal;
                            dtMaterial = new DtLibro(
                                libro.getId().toString(), // conv integer a String
                                libro.getFechaIngreso(),
                                libro.getTitulo(),
                                libro.getCantidadPaginas()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, libro.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del libro: " + e.getMessage());
                            }
                        } else if (materialReal.getClass().getSimpleName().equals("Articulo")) {
                            Articulo articulo = (Articulo) materialReal;
                            dtMaterial = new DtArticulo(
                                articulo.getId().toString(), 
                                articulo.getFechaIngreso(),
                                articulo.getDescripcion(),
                                articulo.getPesoKg(),
                                articulo.getDimensiones()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, articulo.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del articulo: " + e.getMessage());
                            }
                        } else {
                            dtMaterial = new DtMaterial(
                                material.getIdMaterial(),
                                material.getId(),
                                material.getFechaIngreso()
                            );
                        }
                        
                        DtPrestamo dtPrestamo = new DtPrestamo(
                            prestamo.getId(),
                            prestamo.getFechaSolicitud(),
                            prestamo.getFechaDevolucion(),
                            prestamo.getEstado(),
                            new DtLector(
                                prestamo.getLector().getNombre(),
                                prestamo.getLector().getEmail(),
                                prestamo.getLector().getDireccion(),
                                prestamo.getLector().getFechaRegistro(),
                                prestamo.getLector().getEstado(),
                                prestamo.getLector().getZona()
                            ),
                            new DtBibliotecario(
                                prestamo.getBibliotecario().getNombre(),
                                prestamo.getBibliotecario().getEmail(),
                                prestamo.getBibliotecario().getNumeroEmpleado()
                            ),
                            dtMaterial
                        );
                        try {
                            java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
                            idField.setAccessible(true);
                            idField.set(dtPrestamo, prestamo.getId());
                        } catch (Exception e) {
                            System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
                        }
                        dtPrestamos.add(dtPrestamo);
                    }   
                }
            }           // para no confundirme, este esl del IF inicial
            
            return dtPrestamos;
        } catch (Exception e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
            return new ArrayList<>();
        }
	}


	@Override
	public ArrayList<DtPrestamo> ReportePrestamoZona(Zona zona) throws ReportePrestamoZonaExcepcion{
        // uso el mismo criterio que en la anterior listarTodosLosPrestamos pro filtro por zona
        try {           
            List<Prestamo> prestamos = manejadorPrestamos.listarPrestamos();
            
            ArrayList<DtPrestamo> dtPrestamos = new ArrayList<>();
            
            if (prestamos != null) {            
                for (Prestamo prestamo : prestamos) {
                    Hibernate.initialize(prestamo.getLector());
                    Hibernate.initialize(prestamo.getBibliotecario());
                    Hibernate.initialize(prestamo.getMaterial());
                    
                    Lector lector = prestamo.getLector();        // obtener email del elctor para comparar
                    Material material = prestamo.getMaterial();
                    Material materialReal = (Material) Hibernate.unproxy(material);
                    DtMaterial dtMaterial;
                                       
                    if (lector.getZona() == zona) {     // comparo por zona DEL LECTOR QUE SOLICITÓ         // AHORA QUE LO PIENSO, PODRIA HABER DEJADO ESTE CHORI EN UNA FUNCION APARTE
                        if (materialReal.getClass().getSimpleName().equals("Libro")) {              // Y HABERLA CITADO LAS 3 VECES
                            Libro libro = (Libro) materialReal;
                            dtMaterial = new DtLibro(
                                libro.getId().toString(), // conv integer a String
                                libro.getFechaIngreso(),
                                libro.getTitulo(),
                                libro.getCantidadPaginas()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, libro.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del libro: " + e.getMessage());
                            }
                        } else if (materialReal.getClass().getSimpleName().equals("Articulo")) {
                            Articulo articulo = (Articulo) materialReal;
                            dtMaterial = new DtArticulo(
                                articulo.getId().toString(), 
                                articulo.getFechaIngreso(),
                                articulo.getDescripcion(),
                                articulo.getPesoKg(),
                                articulo.getDimensiones()
                            );
                            try {
                                java.lang.reflect.Field idMaterialField = DtMaterial.class.getDeclaredField("idMaterial");
                                idMaterialField.setAccessible(true);
                                idMaterialField.set(dtMaterial, articulo.getIdMaterial());
                            } catch (Exception e) {
                                System.err.println("Error al establecer idMaterial del articulo: " + e.getMessage());
                            }
                        } else {
                            dtMaterial = new DtMaterial(
                                material.getIdMaterial(),
                                material.getId(),
                                material.getFechaIngreso()
                            );
                        }
                        
                        DtPrestamo dtPrestamo = new DtPrestamo(
                            prestamo.getId(),
                            prestamo.getFechaSolicitud(),
                            prestamo.getFechaDevolucion(),
                            prestamo.getEstado(),
                            new DtLector(
                                prestamo.getLector().getNombre(),
                                prestamo.getLector().getEmail(),
                                prestamo.getLector().getDireccion(),
                                prestamo.getLector().getFechaRegistro(),
                                prestamo.getLector().getEstado(),
                                prestamo.getLector().getZona()
                            ),
                            new DtBibliotecario(
                                prestamo.getBibliotecario().getNombre(),
                                prestamo.getBibliotecario().getEmail(),
                                prestamo.getBibliotecario().getNumeroEmpleado()
                            ),
                            dtMaterial
                        );
                        try {
                            java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
                            idField.setAccessible(true);
                            idField.set(dtPrestamo, prestamo.getId());
                        } catch (Exception e) {
                            System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
                        }
                        dtPrestamos.add(dtPrestamo);
                    }   
                }
            }           // para no confundirme, este esl del IF inicial
            
            return dtPrestamos;
        } catch (Exception e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
            return new ArrayList<>();
        }
	}



	@Override
    public ArrayList<DtPrestamo> MaterialPendiente(String estado) throws MaterialPendienteExcepcion{
		ArrayList<DtPrestamo> prestamos = mCS.obtenerMaterialPendiente(estado);
		
		return prestamos;
	}



          
}
