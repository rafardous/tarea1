package com.pap.logica;

import com.pap.persistencia.Conexion;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;

import com.pap.datatypes.DtMaterial;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.DtLibro;

public class ManejadorGestionMaterial {
    private static ManejadorGestionMaterial instancia = null;
	
	private ManejadorGestionMaterial(){}
	
	public static ManejadorGestionMaterial getInstancia() {
		if (instancia == null)
			instancia = new ManejadorGestionMaterial();
		return instancia;
	}

	public void registrarDonacionLibro(Libro libro) {
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		em.getTransaction().begin();
		
		em.persist(libro);
		
		em.getTransaction().commit();
	}

	public void registrarDonacionArticulo(Articulo articulo) {
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		em.getTransaction().begin();
		
		em.persist(articulo);
		
		em.getTransaction().commit();
	}

	public Material buscarMaterial(String idArticulo) {
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		try {
			return em.createQuery("SELECT m FROM Material m WHERE m.id = :codigo", Material.class).setParameter("codigo", idArticulo).getSingleResult();
		} catch (NoResultException arg0) {
			return null;
		}

	}
	
	public boolean existeMaterial(String idMaterial) {
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(m) FROM Material m WHERE m.id = :codigo", Long.class);
			query.setParameter("codigo", idMaterial);
			return query.getSingleResult() > 0;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			System.err.println("Error al verificar existencia de material: " + e.getMessage());
			return false;
		}
	}

	public ArrayList<DtArticulo> obtenerArticulos(){
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		
		ArrayList<DtArticulo> aRetornar = new ArrayList<>();

		try {
			TypedQuery<Articulo> query = em.createQuery("SELECT a FROM Articulo a", Articulo.class);
			List<Articulo> listArticulo = query.getResultList();
			
			for (Articulo a : listArticulo) {
				
				DtArticulo dto = new DtArticulo(
					a.getIdMaterial(),
					a.getId(),
					a.getFechaIngreso(),
					a.getDescripcion(),
					a.getPesoKg(),
					a.getDimensiones()
				);
				aRetornar.add(dto);
			}
			return aRetornar;

		} catch (NoResultException arg0) {
			return aRetornar;
		}

	}

	public ArrayList<DtLibro> obtenerLibros(){
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		
		ArrayList<DtLibro> aRetornar = new ArrayList<>();

		try {
			TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
			List<Libro> listLibro = query.getResultList();
			
			for (Libro l : listLibro) {
				
				DtLibro dto = new DtLibro(
					l.getIdMaterial(),
					l.getId(),
					l.getFechaIngreso(),
					l.getTitulo(),
					l.getCantidadPaginas()
				);
				aRetornar.add(dto);
			}
			return aRetornar;
			
		} catch (NoResultException arg0) {
			return aRetornar;
		}

	}

	public ArrayList<DtMaterial> obtenerMateriales(Date fechaDesde, Date fechaHasta){
		Conexion conexion = Conexion.getInstancia();
		EntityManager em = conexion.getEntityManager();
		
		ArrayList<DtMaterial> aRetornar = new ArrayList<>();

		try {
			TypedQuery<Material> query = em.createQuery("SELECT l FROM Material l WHERE l.fechaIngreso >= :fechaDesde <= :fechaHasta", Material.class);
			query.setParameter("fechaDesde", fechaDesde);
			query.setParameter("fechaHasta", fechaHasta);
			List<Material> listMaterial = query.getResultList();
			
			for (Material l : listMaterial) {
				
				DtMaterial dto = new DtMaterial(
					l.getIdMaterial(),
					l.getId(),
					l.getFechaIngreso()
				);
				aRetornar.add(dto);
			}
			return aRetornar;
			
		} catch (NoResultException arg0) {
			return aRetornar;
		}

	}
	
}