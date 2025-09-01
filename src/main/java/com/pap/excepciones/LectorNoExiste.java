package com.pap.excepciones;

public class LectorNoExiste extends Exception{
	private static final long serialVersionUID = 1L;

    public LectorNoExiste(String message) {
        super(message);
    }
}