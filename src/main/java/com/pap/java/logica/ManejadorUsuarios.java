package com.pap.java.logica;

import com.pap.java.persistencia.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * Singleton que maneja todas las operaciones de persistencia para usuarios.
 * Gestiona el ciclo de vida de las entidades y transacciones.
 */
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

    /**
     * Agrega un nuevo usuario al sistema.
     * @param usuario El usuario a agregar
     * @throws Exception Si hay un error en la persistencia
     */
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

    /**
     * Busca un usuario por su email.
     * @param email El email del usuario
     * @return El usuario encontrado o null si no existe
     */
    public Usuario buscarUsuario(String email) {
        try {
            return em.find(Usuario.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca un lector por su email.
     * @param email El email del lector
     * @return El lector encontrado o null si no existe
     */
    public Lector buscarLector(String email) {
        try {
            return em.find(Lector.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar lector: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca un bibliotecario por su email.
     * @param email El email del bibliotecario
     * @return El bibliotecario encontrado o null si no existe
     */
    public Bibliotecario buscarBibliotecario(String email) {
        try {
            return em.find(Bibliotecario.class, email);
        } catch (Exception e) {
            System.err.println("Error al buscar bibliotecario: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todos los usuarios del sistema.
     * @return Lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u ORDER BY u.nombre", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Lista solo los lectores del sistema.
     * @return Lista de lectores
     */
    public List<Lector> listarLectores() {
        try {
            TypedQuery<Lector> query = em.createQuery("SELECT l FROM Lector l ORDER BY l.nombre", Lector.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar lectores: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Lista solo los bibliotecarios del sistema.
     * @return Lista de bibliotecarios
     */
    public List<Bibliotecario> listarBibliotecarios() {
        try {
            TypedQuery<Bibliotecario> query = em.createQuery("SELECT b FROM Bibliotecario b ORDER BY b.nombre", Bibliotecario.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al listar bibliotecarios: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Verifica si existe un usuario con el email especificado.
     * @param email El email a verificar
     * @return true si existe, false en caso contrario
     */
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

    /**
     * Actualiza un usuario existente.
     * @param usuario El usuario a actualizar
     * @throws Exception Si hay un error en la actualización
     */
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

    /**
     * Elimina un usuario del sistema.
     * @param email El email del usuario a eliminar
     * @throws Exception Si hay un error en la eliminación
     */
    public void eliminarUsuario(String email) throws Exception {
        try {
            Usuario usuario = buscarUsuario(email);
            if (usuario != null) {
                em.getTransaction().begin();
                em.remove(usuario);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    /**
     * Busca usuarios por nombre (búsqueda parcial).
     * @param nombre El nombre o parte del nombre a buscar
     * @return Lista de usuarios que coinciden
     */
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
