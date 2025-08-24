package com.pap.java.presentacion;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;

import com.pap.java.interfaces.Fabrica;
import com.pap.java.interfaces.IControlador;

public class Principal {

    private JFrame frame;
    private JDesktopPane desktopPane;
    
    // Internal Frames para las funcionalidades
    private AltaLector altaLectorInternalFrame;
    private AltaBibliotecario altaBibliotecarioInternalFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal window = new Principal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Principal() {
        initialize();
        
        // Obtener instancia del controlador
        Fabrica fabrica = Fabrica.getInstancia();
        IControlador controlador = fabrica.crearControlador();
        
        // Crear los Internal Frames
        altaLectorInternalFrame = new AltaLector(controlador);
        altaLectorInternalFrame.setClosable(true);
        altaLectorInternalFrame.setLocation(150, 50);
        altaLectorInternalFrame.setVisible(false);
        desktopPane.add(altaLectorInternalFrame);
        
        altaBibliotecarioInternalFrame = new AltaBibliotecario(controlador);
        altaBibliotecarioInternalFrame.setClosable(true);
        altaBibliotecarioInternalFrame.setLocation(150, 50);
        altaBibliotecarioInternalFrame.setVisible(false);
        desktopPane.add(altaBibliotecarioInternalFrame);
        
        // Título de bienvenida
        JLabel lblBienvenida = new JLabel("Lectores.uy");
        lblBienvenida.setFont(new Font("Impact", Font.PLAIN, 72));
        lblBienvenida.setBounds(200, 200, 500, 100);
        desktopPane.add(lblBienvenida);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestión de Biblioteca Comunitaria");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setBounds(200, 300, 400, 30);
        desktopPane.add(lblSubtitulo);
    }

    private void initialize() {
        frame = new JFrame("Lectores.uy - Sistema de Biblioteca");
        frame.setBounds(100, 100, 900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        // Menú "Gestión de Usuarios"
        JMenu menuUsuarios = new JMenu("Gestión de Usuarios");
        menuUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menuBar.add(menuUsuarios);
        
        // Submenú "Registrar"
        JMenu menuRegistrar = new JMenu("Registrar");
        menuRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuUsuarios.add(menuRegistrar);
        
        // Item para registrar Lector
        JMenuItem menuItemLector = new JMenuItem("Nuevo Lector");
        menuItemLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemLector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                altaLectorInternalFrame.setVisible(true);
                altaLectorInternalFrame.toFront();
            }
        });
        menuRegistrar.add(menuItemLector);
        
        // Item para registrar Bibliotecario
        JMenuItem menuItemBibliotecario = new JMenuItem("Nuevo Bibliotecario");
        menuItemBibliotecario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemBibliotecario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                altaBibliotecarioInternalFrame.setVisible(true);
                altaBibliotecarioInternalFrame.toFront();
            }
        });
        menuRegistrar.add(menuItemBibliotecario);
        
        // Desktop Pane para los Internal Frames
        desktopPane = new JDesktopPane();
        frame.setContentPane(desktopPane);
    }
}
