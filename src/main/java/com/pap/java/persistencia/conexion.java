package com.pap.java.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import com.pap.java.interfaces.Fabrica;
import com.pap.java.interfaces.IControlador;
import com.pap.java.logica.Controlador;

public class conexion {
	private static conexion instancia = null;
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private conexion(){}
	
	public static conexion getInstancia() {
		if (instancia == null) {
			instancia = new conexion();
			emf = Persistence.createEntityManagerFactory("biblioteca");
			em=emf.createEntityManager();
		}
		return instancia;
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
	
	public void close() {
		this.em.close();
		this.emf.close();
	}
}
