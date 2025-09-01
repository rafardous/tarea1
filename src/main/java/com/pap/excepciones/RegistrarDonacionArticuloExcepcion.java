package com.pap.excepciones;

public class RegistrarDonacionArticuloExcepcion extends Exception{
    private static final long serialVersionUID = 1L;

	public RegistrarDonacionArticuloExcepcion(String string) {
        super(string);
    }
}
