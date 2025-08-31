package com.pap.java.excepciones;

public class FechaInvalidaExc extends Exception {
    private static final long serialVersionUID = 1L;

    public FechaInvalidaExc(String mensaje) {
        super(mensaje);
    }
}