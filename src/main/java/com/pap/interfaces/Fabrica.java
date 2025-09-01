package com.pap.interfaces;

import com.pap.logica.Controlador;

public class Fabrica {
    
    private static Fabrica instancia = null;
    
    private Fabrica() {}
    
    /**
     * Obtiene la instancia única de la fábrica (Singleton)
     * @return La instancia de la fábrica
     */
    public static Fabrica getInstancia() {
        if (instancia == null) {
            instancia = new Fabrica();
        }
        return instancia;
    }
    
    /**
     * Crea y retorna una instancia del controlador
     * @return Una nueva instancia del controlador
     */
    public IControlador crearControlador() {
        return new Controlador();
    }
}
