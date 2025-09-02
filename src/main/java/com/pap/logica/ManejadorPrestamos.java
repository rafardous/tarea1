package com.pap.logica;

import com.pap.persistencia.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.util.List;
import jakarta.persistence.TypedQuery;

// tambien singleton
public class ManejadorPrestamos {

    private static ManejadorPrestamos instancia = null;
    private final EntityManager em;

    private ManejadorPrestamos() {
        this.em = Conexion.getInstancia().getEntityManager();
    }

    public static ManejadorPrestamos getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorPrestamos();
        }
        return instancia;
    }

    public void agregarPrestamo(Prestamo prestamo) throws Exception {
        try {
            em.getTransaction().begin();
            em.persist(prestamo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public Prestamo buscarPrestamo(int id) {
        try {
            return em.find(Prestamo.class, id);
        } catch (Exception e) {
            System.err.println("Error al buscar prestamo: " + e.getMessage());
            return null;
        }
    }

    public List<Prestamo> listarPrestamos() {
        try {
            return em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
            return null;
        }
    }

    public void actualizarPrestamo(Prestamo prestamo) throws Exception {    // toy en duda de si crear un prestamo o usar un dtprestamo -- consultar eficiencia a nivel de memoria
        try {
            em.getTransaction().begin();
            em.merge(prestamo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public List<Prestamo> listarPrestamosLector(String email) {
        try {
            return em.createQuery("SELECT p FROM Prestamo p WHERE p.lector.email = :email", Prestamo.class).setParameter("email", email).getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar prestamos del lector: " + e.getMessage());
            return null;
        }
    }

}
