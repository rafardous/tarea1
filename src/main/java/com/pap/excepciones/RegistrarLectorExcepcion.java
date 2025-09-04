package com.pap.excepciones;

public class RegistrarLectorExcepcion extends Exception{
    private static final long serialVersionUID = 1L;

	public RegistrarLectorExcepcion(String string) {
        super(string);
    }
}
