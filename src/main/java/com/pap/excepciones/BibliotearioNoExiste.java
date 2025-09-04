package com.pap.excepciones;

public class BibliotearioNoExiste extends Exception{
	private static final long serialVersionUID = 1L;

    public BibliotearioNoExiste(String message) {
        super(message);
    }
}