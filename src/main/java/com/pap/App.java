package com.pap;

import com.pap.presentacion.Principal;

/**
 * Clase principal que inicia la aplicacion de biblioteca
 */
public class App 
{
    public static void main( String[] args )
    {
        // Iniciar la interfaz grafica principal
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new Principal();
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicacion: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
