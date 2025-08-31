package com.pap.java.excepciones;

public class UsuarioRepetidoExc extends Exception{
	
	private static final long serialVersionUID = 1L;

	public UsuarioRepetidoExc(String string) {
		super(string);
	}
}
