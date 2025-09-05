package com.pap.logica;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.DtBibliotecario;
import com.pap.datatypes.DtMaterial;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.EstadoPrestamo;
import com.pap.datatypes.Zona;
import com.pap.persistencia.Conexion;
import org.hibernate.Hibernate;

public class ManejadorControlSeguimiento {
    private static ManejadorControlSeguimiento instancia = null;
	
	private ManejadorControlSeguimiento(){}
	
	public static ManejadorControlSeguimiento getInstancia() {
		if (instancia == null)
			instancia = new ManejadorControlSeguimiento();
		return instancia;
	}

	public ArrayList<DtPrestamo> obtenerPrestamos(String variable, boolean funcion){
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		
		ArrayList<DtPrestamo> aRetornar = new ArrayList<>();

		if(funcion){
			try {
				Integer numeroEmpleado = Integer.parseInt(variable);
				// Buscar bibliotecario por numero de empleado
				TypedQuery<Bibliotecario> bibliotecarioQuery = em.createQuery("SELECT b FROM Bibliotecario b WHERE b.numeroEmpleado = :numeroEmpleado", Bibliotecario.class);
				bibliotecarioQuery.setParameter("numeroEmpleado", numeroEmpleado);
				Bibliotecario bibliotecario = bibliotecarioQuery.getSingleResult();
				
				// Buscar prestamos por email del bibliotecario
				TypedQuery<Prestamo> query = em.createQuery("SELECT p FROM Prestamo p WHERE p.bibliotecario.email = :emailBibliotecario", Prestamo.class);
				query.setParameter("emailBibliotecario", bibliotecario.getEmail());
				List<Prestamo> listPrestamo = query.getResultList();
				
				for (Prestamo p : listPrestamo) {
					// Inicializar las relaciones lazy
					Hibernate.initialize(p.getLector());
					Hibernate.initialize(p.getBibliotecario());
					Hibernate.initialize(p.getMaterial());
					
					// Crear DTOs
					DtLector dtLector = new DtLector(
						p.getLector().getNombre(),
						p.getLector().getEmail(),
						p.getLector().getDireccion(),
						p.getLector().getFechaRegistro(),
						p.getLector().getEstado(),
						p.getLector().getZona()
					);
					
					DtBibliotecario dtBibliotecario = new DtBibliotecario(
						p.getBibliotecario().getNombre(),
						p.getBibliotecario().getEmail(),
						p.getBibliotecario().getNumeroEmpleado()
					);
					
					// Crear DtMaterial segun el tipo
					DtMaterial dtMaterial;
					Material material = p.getMaterial();
					if (material instanceof Libro) {
						Libro libro = (Libro) material;
						dtMaterial = new DtLibro(
							libro.getId().toString(),
							libro.getFechaIngreso(),
							libro.getTitulo(),
							libro.getCantidadPaginas()
						);
					} else if (material instanceof Articulo) {
						Articulo articulo = (Articulo) material;
						dtMaterial = new DtArticulo(
							articulo.getId().toString(),
							articulo.getFechaIngreso(),
							articulo.getDescripcion(),
							articulo.getPesoKg(),
							articulo.getDimensiones()
						);
					} else {
						dtMaterial = new DtMaterial(
							material.getIdMaterial(),
							material.getId(),
							material.getFechaIngreso()
						);
					}
					
					DtPrestamo dto = new DtPrestamo(
						p.getFechaSolicitud(),
						p.getFechaDevolucion(),
						p.getEstado(),
						dtLector,
						dtBibliotecario,
						dtMaterial
					);
					
					// Establecer el ID usando reflection
					try {
						java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
						idField.setAccessible(true);
						idField.set(dto, p.getId());
					} catch (Exception e) {
						System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
					}
					
					aRetornar.add(dto);
				}
				return aRetornar;
				
			} catch (NoResultException arg0) {
				return aRetornar;
			}
		}else{
			try {
				Zona zona = Zona.valueOf(variable);
				TypedQuery<Prestamo> query = em.createQuery("SELECT p FROM Prestamo p", Prestamo.class);
				List<Prestamo> listPrestamo = query.getResultList();
			
				for (Prestamo p : listPrestamo) {
					// Inicializar las relaciones lazy
					Hibernate.initialize(p.getLector());
					Hibernate.initialize(p.getBibliotecario());
					Hibernate.initialize(p.getMaterial());
					
					Lector lector = p.getLector();
					if (lector.getZona() == zona) {
						// Crear DTOs
						DtLector dtLector = new DtLector(
							lector.getNombre(),
							lector.getEmail(),
							lector.getDireccion(),
							lector.getFechaRegistro(),
							lector.getEstado(),
							lector.getZona()
						);
						
						DtBibliotecario dtBibliotecario = new DtBibliotecario(
							p.getBibliotecario().getNombre(),
							p.getBibliotecario().getEmail(),
							p.getBibliotecario().getNumeroEmpleado()
						);
						
						// Crear DtMaterial segun el tipo
						DtMaterial dtMaterial;
						Material material = p.getMaterial();
						if (material instanceof Libro) {
							Libro libro = (Libro) material;
							dtMaterial = new DtLibro(
								libro.getId().toString(),
								libro.getFechaIngreso(),
								libro.getTitulo(),
								libro.getCantidadPaginas()
							);
						} else if (material instanceof Articulo) {
							Articulo articulo = (Articulo) material;
							dtMaterial = new DtArticulo(
								articulo.getId().toString(),
								articulo.getFechaIngreso(),
								articulo.getDescripcion(),
								articulo.getPesoKg(),
								articulo.getDimensiones()
							);
						} else {
							dtMaterial = new DtMaterial(
								material.getIdMaterial(),
								material.getId(),
								material.getFechaIngreso()
							);
						}
						
						DtPrestamo dto = new DtPrestamo(
							p.getFechaSolicitud(),
							p.getFechaDevolucion(),
							p.getEstado(),
							dtLector,
							dtBibliotecario,
							dtMaterial
						);
						
						// Establecer el ID usando reflection
						try {
							java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
							idField.setAccessible(true);
							idField.set(dto, p.getId());
						} catch (Exception e) {
							System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
						}
						
						aRetornar.add(dto);
					}
				}
				return aRetornar;
				
			} catch (NoResultException arg0) {
				return aRetornar;
			}
		}
	}

	public ArrayList<DtPrestamo> obtenerMaterialPendiente(String estado){
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		
		ArrayList<DtPrestamo> aRetornar = new ArrayList<>();

		try {
			EstadoPrestamo estadoPrestamo = EstadoPrestamo.valueOf(estado);
			TypedQuery<Prestamo> query = em.createQuery("SELECT p FROM Prestamo p WHERE p.estado = :estado", Prestamo.class);
			query.setParameter("estado", estadoPrestamo);
			List<Prestamo> listPrestamo = query.getResultList();

			for (Prestamo p : listPrestamo) {
				// Inicializar las relaciones lazy
				Hibernate.initialize(p.getLector());
				Hibernate.initialize(p.getBibliotecario());
				Hibernate.initialize(p.getMaterial());
				
				// Crear DTOs
				DtLector dtLector = new DtLector(
					p.getLector().getNombre(),
					p.getLector().getEmail(),
					p.getLector().getDireccion(),
					p.getLector().getFechaRegistro(),
					p.getLector().getEstado(),
					p.getLector().getZona()
				);
				
				DtBibliotecario dtBibliotecario = new DtBibliotecario(
					p.getBibliotecario().getNombre(),
					p.getBibliotecario().getEmail(),
					p.getBibliotecario().getNumeroEmpleado()
				);
				
				// Crear DtMaterial segun el tipo
				DtMaterial dtMaterial;
				Material material = p.getMaterial();
				if (material instanceof Libro) {
					Libro libro = (Libro) material;
					dtMaterial = new DtLibro(
						libro.getId().toString(),
						libro.getFechaIngreso(),
						libro.getTitulo(),
						libro.getCantidadPaginas()
					);
				} else if (material instanceof Articulo) {
					Articulo articulo = (Articulo) material;
					dtMaterial = new DtArticulo(
						articulo.getId().toString(),
						articulo.getFechaIngreso(),
						articulo.getDescripcion(),
						articulo.getPesoKg(),
						articulo.getDimensiones()
					);
				} else {
					dtMaterial = new DtMaterial(
						material.getIdMaterial(),
						material.getId(),
						material.getFechaIngreso()
					);
				}
				
				DtPrestamo dto = new DtPrestamo(
					p.getFechaSolicitud(),
					p.getFechaDevolucion(),
					p.getEstado(),
					dtLector,
					dtBibliotecario,
					dtMaterial
				);
				
				// Establecer el ID usando reflection
				try {
					java.lang.reflect.Field idField = DtPrestamo.class.getDeclaredField("id");
					idField.setAccessible(true);
					idField.set(dto, p.getId());
				} catch (Exception e) {
					System.err.println("Error al establecer ID del prestamo: " + e.getMessage());
				}
				
				aRetornar.add(dto);
			}

			return aRetornar;

		} catch (NoResultException arg0) {
			return aRetornar;
		}

	}

}
