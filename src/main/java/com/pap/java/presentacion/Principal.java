package com.pap.java.presentacion;

import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.pap.java.interfaces.Fabrica;
import com.pap.java.interfaces.IControlador;

public class Principal {

    private JFrame frame;
    private JPanel mainPanel;
    
    // Internal Frames para las funcionalidades
    private AltaLector altaLectorInternalFrame;
    private AltaBibliotecario altaBibliotecarioInternalFrame;

    private IControlador controlador; // PRUEBO EL CONTROLADOR COMO ATRIBUTO A VER SI FUNCA ACÁ

    public static void main(String[] args) {
        try {
            // Aplicar look and feel moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
        controlador = fabrica.crearControlador();
        
        // Crear los Internal Frames
        altaLectorInternalFrame = new AltaLector(controlador);
        altaLectorInternalFrame.setClosable(true);
        altaLectorInternalFrame.setLocation(200, 100);
        altaLectorInternalFrame.setVisible(false);
        mainPanel.add(altaLectorInternalFrame);
        
        altaBibliotecarioInternalFrame = new AltaBibliotecario(controlador);
        altaBibliotecarioInternalFrame.setClosable(true);
        altaBibliotecarioInternalFrame.setLocation(200, 100);
        altaBibliotecarioInternalFrame.setVisible(false);
        mainPanel.add(altaBibliotecarioInternalFrame);
        
        // Panel de bienvenida con imagen de fondo
        JPanel welcomePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                try {
                    // Cargar la imagen de fondo
                    ImageIcon icon = new ImageIcon(getClass().getResource("/wallpaper biblioteca.jpg"));
                    if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        // Dibujar la imagen ocupando todo el panel
                        Image img = icon.getImage();
                        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                        
                        // Agregar una capa semitransparente para mejorar la legibilidad del texto
                        g2d.setColor(new Color(0, 0, 0, 100));
                        g2d.fillRect(0, 0, getWidth(), getHeight());
                    }
                } catch (Exception e) {
                    // Si no se puede cargar la imagen, usar el gradiente original
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(52, 152, 219),
                        getWidth(), getHeight(), new Color(41, 128, 185)
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
                
                // Círculo decorativo
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(getWidth() - 150, getHeight() - 150, 200, 200);
                
                g2d.dispose();
            }
        };
        welcomePanel.setLayout(null);
        // Hacer que el panel de bienvenida ocupe toda la ventana
        welcomePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        
        // Título principal con sombra
        JLabel lblBienvenida = new JLabel("Lectores.uy");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 64));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBounds(200, 150, 400, 80);
        welcomePanel.add(lblBienvenida);
        
        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestion de Biblioteca Comunitaria");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSubtitulo.setForeground(new Color(255, 255, 255));
        lblSubtitulo.setBounds(200, 250, 400, 30);
        welcomePanel.add(lblSubtitulo);
        
        // Descripción
        JLabel lblDescripcion = new JLabel("<html><div style='text-align: center; width: 400px;'>" +
            "Bienvenido al sistema de gestion integral para bibliotecas comunitarias.<br>" +
            "Desde aqui podras administrar usuarios, materiales y prestamos de manera eficiente.</div></html>");
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(255, 255, 255));
        lblDescripcion.setBounds(200, 300, 400, 60);
        welcomePanel.add(lblDescripcion);
        
        mainPanel.add(welcomePanel);
        
        // Listener para cambios de tamaño de la ventana
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                welcomePanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                welcomePanel.repaint();
            }
        });
    }

    private void initialize() {
        frame = new JFrame("Lectores.uy - Sistema de Biblioteca");
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(true);
        
        // Agregar WindowListener para manejar el cierre de la ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Panel principal sin fondo (asi se ve la imagen completa mas linda )
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);
        
        // Crear barra de menu 
        JMenuBar menuBar = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo con gradiente moderno (tipo Steam)
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(45, 45, 45),
                    0, getHeight(), new Color(60, 60, 60)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // Borde superior sutil
                g2d.setColor(new Color(80, 80, 80));
                g2d.drawLine(0, 0, getWidth(), 0);
                
                g2d.dispose();
            }
        };
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.setJMenuBar(menuBar);
        
        // Menú "Gestión de Usuarios" con estilo moderno
        JMenu menuUsuarios = new JMenu("Gestion de Usuarios") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    g2d.dispose();
                }
            }
        };
        menuUsuarios.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuUsuarios.setForeground(new Color(220, 220, 220));
        menuUsuarios.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        menuBar.add(menuUsuarios);
        
        // Submenú "Registrar" con estilo moderno
        JMenu menuRegistrar = new JMenu("Registrar") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    g2d.dispose();
                }
            }
        };
        menuRegistrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuRegistrar.setForeground(Color.BLACK);
        menuRegistrar.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        menuUsuarios.add(menuRegistrar);
        
        // Item para registrar Lector con estilo moderno
        JMenuItem menuItemLector = new JMenuItem("Nuevo Lector") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2d.dispose();
                }
            }
        };
        menuItemLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemLector.setForeground(Color.BLACK);
        menuItemLector.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemLector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                altaLectorInternalFrame.setVisible(true);
                altaLectorInternalFrame.toFront();
            }
        });
        menuRegistrar.add(menuItemLector);
        
        // Item para registrar Bibliotecario con estilo moderno
        JMenuItem menuItemBibliotecario = new JMenuItem("Nuevo Bibliotecario") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2d.dispose();
                }
            }
        };
        menuItemBibliotecario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemBibliotecario.setForeground(Color.BLACK);
        menuItemBibliotecario.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemBibliotecario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                altaBibliotecarioInternalFrame.setVisible(true);
                altaBibliotecarioInternalFrame.toFront();
            }
        });
        menuRegistrar.add(menuItemBibliotecario);
        
        // Separador visual
        menuUsuarios.addSeparator();
        
        // Item para listar Lectores
        JMenuItem menuItemListarLectores = new JMenuItem("Listar Lectores") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2d.dispose();
                }
            }
        };
        menuItemListarLectores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemListarLectores.setForeground(Color.BLACK);
        menuItemListarLectores.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemListarLectores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirListaDeLectores();
            }
        });
        menuUsuarios.add(menuItemListarLectores);

        // -------------------------------------------------------
        // Item para listar Bibliotecarios
        JMenuItem menuItemListarBibliotecarios = new JMenuItem("Listar Bibliotecarios") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(70, 130, 180, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2d.dispose();
                }
            }
        };
        menuItemListarBibliotecarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemListarBibliotecarios.setForeground(Color.BLACK);
        menuItemListarBibliotecarios.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemListarBibliotecarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirListaDeBibliotecarios();
            }
        });
        menuUsuarios.add(menuItemListarBibliotecarios);
        // -------------------------------------------------------
        
        // Menú "Salir" con estilo moderno
        JMenu menuSalir = new JMenu("Salir") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(180, 70, 70, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    g2d.dispose();
                }
            }
        };
        menuSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuSalir.setForeground(new Color(220, 220, 220));
        menuSalir.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        menuBar.add(menuSalir);
        
        JMenuItem menuItemSalir = new JMenuItem("Cerrar Aplicacion") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isArmed()) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(180, 70, 70, 150));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                    g2d.dispose();
                }
            }
        };
        menuItemSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemSalir.setForeground(Color.BLACK);
        menuItemSalir.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuSalir.add(menuItemSalir);
    }
    
    public void abrirListaDeLectores() {
        ListaDeLectores listaDeLectoresFrame = new ListaDeLectores(controlador);
        listaDeLectoresFrame.setLocation(200, 100);
        listaDeLectoresFrame.setVisible(true);
        mainPanel.add(listaDeLectoresFrame);
        listaDeLectoresFrame.toFront();
    }

    public void abrirListaDeBibliotecarios() {
        ListaDeBibliotecarios listaDeBibliotecariosFrame = new ListaDeBibliotecarios(controlador);
        listaDeBibliotecariosFrame.setLocation(200, 100);
        listaDeBibliotecariosFrame.setVisible(true);
        mainPanel.add(listaDeBibliotecariosFrame);
        listaDeBibliotecariosFrame.toFront();
    }
}