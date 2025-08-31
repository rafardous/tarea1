package com.pap.java.persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton que gestiona la conexión a la base de datos.
 * Maneja el ciclo de vida del EntityManagerFactory y EntityManager.
 */
public class Conexion {
    private static Conexion instancia = null;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    private Conexion() {
        inicializarConexion();
    }
    
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    /**
     * Inicializa la conexión a la base de datos.
     * Crea el EntityManagerFactory y el EntityManager.
     */
    private void inicializarConexion() {
        try {
            if (emf == null || !emf.isOpen()) {
                emf = Persistence.createEntityManagerFactory("biblioteca");
            }
            if (em == null || !em.isOpen()) {
                em = emf.createEntityManager();
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar la conexión: " + e.getMessage());
            throw new RuntimeException("No se pudo inicializar la conexión a la base de datos", e);
        }
    }
    
    /**
     * Obtiene el EntityManager activo.
     * @return El EntityManager
     */
    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            inicializarConexion();
        }
        return em;
    }
    
    /**
     * Cierra la conexión y libera los recursos.
     * Debe ser llamado al finalizar la aplicación.
     */
    public void close() {
        try {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si la conexión está activa.
     * @return true si está activa, false en caso contrario
     */
    public boolean isActive() {
        return em != null && em.isOpen() && emf != null && emf.isOpen();
    }
    
    /**
     * Reinicia la conexión en caso de problemas.
     */
    public void reiniciarConexion() {
        close();
        inicializarConexion();
    }
}
