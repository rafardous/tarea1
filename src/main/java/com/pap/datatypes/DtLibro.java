package com.pap.datatypes;

import java.util.Date;

public class DtLibro extends DtMaterial{
    final private String titulo;
    final private String cantidadPaginas;

    public DtLibro(final Integer idMaterial, final String id, final Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(idMaterial, id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public DtLibro(final String id, final Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(null, id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCantidadPaginas() {
        return cantidadPaginas;
    }

}
