package com.pap.java.logica;

import com.pap.java.persistencia.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ManejadorUsuarios {

    private static ManejadorUsuarios instancia = null;
    private EntityManager em;

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
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public Usuario buscarUsuario(String email) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();
        Usuario usuario = em.find(Usuario.class, email);
        
        return usuario;
    }

    public Lector buscarLector(String email) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();
        Lector lector = em.find(Lector.class, email);

        return lector;
    }

    public Bibliotecario buscarBiblioteacrio(String email) {
        Conexion conexion = Conexion.getInstancia();
        EntityManager em = conexion.getEntityManager();
        Bibliotecario bibliotecario = em.find(Bibliotecario.class, email);

        return bibliotecario;
    }

    public List<Usuario> listarUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
}
