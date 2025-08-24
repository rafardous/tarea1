package com.pap.java.datatypes;

public class DtLibro extends DtMaterial {
    private String titulo;
    private String cantidadPaginas;

    public DtLibro() {}

    public DtLibro(String id, java.util.Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(id, fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    // Getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    @Override
    public String toString() {
        return super.toString() + 
               "\nTítulo: " + titulo + 
               "\nCantidad de Páginas: " + cantidadPaginas;
    }
}
