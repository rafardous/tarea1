package com.pap.java.logica;

import com.pap.java.persistencia.conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ManejadorUsuarios {

    private static ManejadorUsuarios instancia = null;
    private EntityManager em;

    private ManejadorUsuarios() {
        this.em = conexion.getInstancia().getEntityManager();
    }

    public static ManejadorUsuarios getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorUsuarios();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) throws Exception {
        // validar email duplicado
        TypedQuery<Long> q1 = em.createQuery(
            "SELECT COUNT(u) FROM Usuario u WHERE lower(u.email) = lower(:email)", Long.class);
        q1.setParameter("email", usuario.getEmail());
        if (q1.getSingleResult() > 0) {
            throw new Exception("Ya existe un usuario con el email: " + usuario.getEmail());
        }

        // validar número de empleado si es bibliotecario
        if (usuario instanceof Bibliotecario) {
            TypedQuery<Long> q2 = em.createQuery(
                "SELECT COUNT(b) FROM Bibliotecario b WHERE lower(b.numeroEmpleado) = lower(:num)", Long.class);
            q2.setParameter("num", ((Bibliotecario) usuario).getNumeroEmpleado());
            if (q2.getSingleResult() > 0) {
                throw new Exception("Ya existe un bibliotecario con el número de empleado: " 
                                     + ((Bibliotecario) usuario).getNumeroEmpleado());
            }
        }

        // persistir
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public List<Usuario> listarUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
}
