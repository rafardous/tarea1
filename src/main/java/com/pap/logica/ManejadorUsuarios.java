package com.pap.logica;

import com.pap.persistencia.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.util.List;



 //singleton
public class ManejadorUsuarios {

    private static ManejadorUsuarios instancia = null;
    private final EntityManager em;

    private ManejadorUsuarios() {
        this.em = Conexion.getInstancia().getEntityManager();
    }

    public static ManejadorUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorUsuarios();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) throws Exception {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al agregar usuario: " + e.getMessage(), e);
        }
    }

    public Usuario buscarUsuario(String email) {
        try {
            return em.find(Usuario.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return null;
        }
    }

    public Lector buscarLector(String email) {
        try {
            return em.find(Lector.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar lector: " + e.getMessage());
            return null;
        }
    }

    public Bibliotecario buscarBibliotecario(String email) {
        try {
            return em.find(Bibliotecario.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar bibliotecario: " + e.getMessage());
            return null;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u ORDER BY u.nombre", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            return List.of();
        }
    }

    public List<Lector> listarLectores() {
        try {
            TypedQuery<Lector> query = em.createQuery("SELECT l FROM Lector l ORDER BY l.nombre", Lector.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar lectores: " + e.getMessage());
            return List.of();
        }
    }

    public List<Bibliotecario> listarBibliotecarios() {
        try {
            TypedQuery<Bibliotecario> query = em.createQuery("SELECT b FROM Bibliotecario b ORDER BY b.nombre", Bibliotecario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar bibliotecarios: " + e.getMessage());
            return List.of();
        }
    }

    public boolean existeUsuario(String email) {
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            System.err.println("Error al verificar existencia de usuario: " + e.getMessage());
            return false;
        }
    }

    public void actualizarUsuario(Usuario usuario) throws Exception {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    // si me da el tiempo, hago en el jinternalframe la opcion de buscar por nombre al usuario a modificar como detallecito extra
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        try {
            TypedQuery<Usuario> query = em.createQuery(
                "SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(:nombre) ORDER BY u.nombre", 
                Usuario.class
            );
            query.setParameter("nombre", "%" + nombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al buscar usuarios por nombre: " + e.getMessage());
            return List.of();
        }
    }
}
