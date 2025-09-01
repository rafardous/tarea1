package com.pap.excepciones;

public class RegistrarPrestamoExcepcion extends Exception{
    private static final long serialVersionUID = 1L;

	public RegistrarPrestamoExcepcion(String string) {
        super(string);
    }
}
