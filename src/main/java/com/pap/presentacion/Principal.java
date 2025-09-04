package com.pap.presentacion;

import java.awt.Image;
import java.awt.MediaTracker;
import javax.swing.ImageIcon;


import java.awt.Color;
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


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import javax.swing.UIManager;


import com.pap.interfaces.Fabrica;
import com.pap.interfaces.IControlador;

import com.pap.presentacion.RegistrarDonacionLibro;
import com.pap.presentacion.RegistrarDonacionArticulo;
import com.pap.presentacion.ConsultarDonacionesRegistradas;
import com.pap.presentacion.ConsultarDonacionesPorFecha;
import com.pap.presentacion.RegistrarPrestamo;
import com.pap.presentacion.ActualizarPrestamo;
import com.pap.presentacion.HistorialPrestamosLector;


public class Principal {

    private JFrame frame;
    private JPanel mainPanel;
    
    // Internal Frames para las funcionalidades
    private AltaLector altaLectorInternalFrame;
    private AltaBibliotecario altaBibliotecarioInternalFrame;
    private ModificarEstadoLector modificarEstadoLectorInternalFrame;
    private CambiarZonaLector cambiarZonaLectorInternalFrame;
    
    // Internal Frames para gestión de materiales

    private RegistrarDonacionLibro registrarDonacionLibroInternalFrame;
    private RegistrarDonacionArticulo registrarDonacionArticuloInternalFrame;
    private ConsultarDonacionesRegistradas consultarDonacionesRegistradasInternalFrame;
    private ConsultarDonacionesPorFecha consultarDonacionesPorFechaInternalFrame;
    
    // Internal Frames para gestión de préstamos
    private RegistrarPrestamo registrarPrestamoInternalFrame;
    private ActualizarPrestamo actualizarPrestamoInternalFrame;
    private HistorialPrestamosLector historialPrestamosLectorInternalFrame;

    private IControlador controlador; // PRUEBO EL CONTROLADOR COMO ATRIBUTO A VER SI FUNCA ACA

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
        
        modificarEstadoLectorInternalFrame = new ModificarEstadoLector(controlador);
        modificarEstadoLectorInternalFrame.setClosable(true);
        modificarEstadoLectorInternalFrame.setLocation(200, 100);
        modificarEstadoLectorInternalFrame.setVisible(false);
        mainPanel.add(modificarEstadoLectorInternalFrame);
        
        cambiarZonaLectorInternalFrame = new CambiarZonaLector(controlador);
        cambiarZonaLectorInternalFrame.setClosable(true);
        cambiarZonaLectorInternalFrame.setLocation(200, 100);
        cambiarZonaLectorInternalFrame.setVisible(false);
        mainPanel.add(cambiarZonaLectorInternalFrame);
        

        // Crear los Internal Frames para gestión de materiales
        
        registrarDonacionLibroInternalFrame = new RegistrarDonacionLibro(controlador);
        registrarDonacionLibroInternalFrame.setClosable(true);
        registrarDonacionLibroInternalFrame.setLocation(200, 100);
        registrarDonacionLibroInternalFrame.setVisible(false);
        mainPanel.add(registrarDonacionLibroInternalFrame);
        
        registrarDonacionArticuloInternalFrame = new RegistrarDonacionArticulo(controlador);
        registrarDonacionArticuloInternalFrame.setClosable(true);
        registrarDonacionArticuloInternalFrame.setLocation(200, 100);
        registrarDonacionArticuloInternalFrame.setVisible(false);
        mainPanel.add(registrarDonacionArticuloInternalFrame);
        
        consultarDonacionesRegistradasInternalFrame = new ConsultarDonacionesRegistradas(controlador);
        consultarDonacionesRegistradasInternalFrame.setClosable(true);
        consultarDonacionesRegistradasInternalFrame.setLocation(200, 100);
        consultarDonacionesRegistradasInternalFrame.setVisible(false);
        mainPanel.add(consultarDonacionesRegistradasInternalFrame);
        
        consultarDonacionesPorFechaInternalFrame = new ConsultarDonacionesPorFecha(controlador);
        consultarDonacionesPorFechaInternalFrame.setClosable(true);
        consultarDonacionesPorFechaInternalFrame.setLocation(200, 100);
        consultarDonacionesPorFechaInternalFrame.setVisible(false);
        mainPanel.add(consultarDonacionesPorFechaInternalFrame);
        
        // Crear los Internal Frames para gestión de préstamos
        registrarPrestamoInternalFrame = new RegistrarPrestamo(controlador);
        registrarPrestamoInternalFrame.setClosable(true);
        registrarPrestamoInternalFrame.setLocation(200, 100);
        registrarPrestamoInternalFrame.setVisible(false);
        mainPanel.add(registrarPrestamoInternalFrame);
        
        actualizarPrestamoInternalFrame = new ActualizarPrestamo(controlador);
        actualizarPrestamoInternalFrame.setClosable(true);
        actualizarPrestamoInternalFrame.setLocation(200, 100);
        actualizarPrestamoInternalFrame.setVisible(false);
        mainPanel.add(actualizarPrestamoInternalFrame);
        
        historialPrestamosLectorInternalFrame = new HistorialPrestamosLector(controlador);
        historialPrestamosLectorInternalFrame.setClosable(true);
        historialPrestamosLectorInternalFrame.setLocation(200, 100);
        historialPrestamosLectorInternalFrame.setVisible(false);
        mainPanel.add(historialPrestamosLectorInternalFrame);
        
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
        
        // Subtitulo
        JLabel lblSubtitulo = new JLabel("Sistema de Gestion de Biblioteca Comunitaria");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblSubtitulo.setForeground(new Color(255, 255, 255));
        lblSubtitulo.setBounds(200, 250, 400, 30);
        welcomePanel.add(lblSubtitulo);
        
        // Descripcion
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
        // Item para modificar estado de lector
        JMenuItem menuItemModificarEstadoLector = new JMenuItem("Modificar Estado de Lector") {
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
        menuItemModificarEstadoLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemModificarEstadoLector.setForeground(Color.BLACK);
        menuItemModificarEstadoLector.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemModificarEstadoLector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirModificarEstadoLector();
            }
        });
        menuUsuarios.add(menuItemModificarEstadoLector);
        
        // -------------------------------------------------------
        // Item para cambiar zona de lector
        JMenuItem menuItemCambiarZonaLector = new JMenuItem("Cambiar Zona de Lector") {
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
        menuItemCambiarZonaLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemCambiarZonaLector.setForeground(Color.BLACK);
        menuItemCambiarZonaLector.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemCambiarZonaLector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCambiarZonaLector();
            }
        });
        menuUsuarios.add(menuItemCambiarZonaLector);
        // -------------------------------------------------------
        
        // Menú "Gestión de Materiales" con estilo moderno
        JMenu menuMateriales = new JMenu("Gestion de Materiales") {
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
        menuMateriales.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuMateriales.setForeground(new Color(220, 220, 220));
        menuMateriales.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        menuBar.add(menuMateriales);
        
        // Item para registrar donación de libro con estilo moderno
        JMenuItem menuItemRegistrarLibro = new JMenuItem("Registrar donacion de libro") {
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
        menuItemRegistrarLibro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemRegistrarLibro.setForeground(Color.BLACK);
        menuItemRegistrarLibro.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemRegistrarLibro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarDonacionLibro();
            }
        });
        menuMateriales.add(menuItemRegistrarLibro);
        
        // Item para registrar donación de artículo con estilo moderno
        JMenuItem menuItemRegistrarArticulo = new JMenuItem("Registrar donacion de articulo") {
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
        menuItemRegistrarArticulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemRegistrarArticulo.setForeground(Color.BLACK);
        menuItemRegistrarArticulo.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemRegistrarArticulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarDonacionArticulo();
            }
        });
        menuMateriales.add(menuItemRegistrarArticulo);
        
        // Separador visual
        menuMateriales.addSeparator();
        
        // Item para consultar donaciones registradas
        JMenuItem menuItemConsultarDonaciones = new JMenuItem("Consultar donaciones registradas") {
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
        menuItemConsultarDonaciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemConsultarDonaciones.setForeground(Color.BLACK);
        menuItemConsultarDonaciones.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemConsultarDonaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirConsultarDonacionesRegistradas();
            }
        });
        menuMateriales.add(menuItemConsultarDonaciones);
        
        // Item para consultar donaciones por fecha
        JMenuItem menuItemConsultarPorFecha = new JMenuItem("Consultar donaciones por fecha") {
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
        menuItemConsultarPorFecha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemConsultarPorFecha.setForeground(Color.BLACK);
        menuItemConsultarPorFecha.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemConsultarPorFecha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirConsultarDonacionesPorFecha();
            }
        });
        menuMateriales.add(menuItemConsultarPorFecha);
        // -------------------------------------------------------
        
        // Menú "Gestión de Préstamos" con estilo moderno
        JMenu menuPrestamos = new JMenu("Gestion de Prestamos") {
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
        menuPrestamos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuPrestamos.setForeground(new Color(220, 220, 220));
        menuPrestamos.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        menuBar.add(menuPrestamos);
        
        // Item para registrar préstamo con estilo moderno
        JMenuItem menuItemRegistrarPrestamo = new JMenuItem("Registrar Prestamo") {
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
        menuItemRegistrarPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemRegistrarPrestamo.setForeground(Color.BLACK);
        menuItemRegistrarPrestamo.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemRegistrarPrestamo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistrarPrestamo();
            }
        });
        menuPrestamos.add(menuItemRegistrarPrestamo);
        
        // Item para actualizar préstamo con estilo moderno
        JMenuItem menuItemActualizarPrestamo = new JMenuItem("Actualizar Prestamo") {
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
        menuItemActualizarPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemActualizarPrestamo.setForeground(Color.BLACK);
        menuItemActualizarPrestamo.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemActualizarPrestamo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirActualizarPrestamo();
            }
        });
        menuPrestamos.add(menuItemActualizarPrestamo);
        
        // Item para historial de préstamos del lector con estilo moderno
        JMenuItem menuItemHistorialPrestamosLector = new JMenuItem("Historial Prestamos Lector") {
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
        menuItemHistorialPrestamosLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        menuItemHistorialPrestamosLector.setForeground(Color.BLACK);
        menuItemHistorialPrestamosLector.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        menuItemHistorialPrestamosLector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirHistorialPrestamosLector();
            }
        });
        menuPrestamos.add(menuItemHistorialPrestamosLector);
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
        // No agregar el frame al panel - debe ser una ventana independiente
        // mainPanel.add(listaDeLectoresFrame);
        listaDeLectoresFrame.toFront();
    }

    public void abrirListaDeBibliotecarios() {
        ListaDeBibliotecarios listaDeBibliotecariosFrame = new ListaDeBibliotecarios(controlador);
        listaDeBibliotecariosFrame.setLocation(200, 100);
        listaDeBibliotecariosFrame.setVisible(true);
        // No agregar el frame al panel - debe ser una ventana independiente
        // mainPanel.add(listaDeBibliotecariosFrame);
        listaDeBibliotecariosFrame.toFront();
    }
    
    public void abrirModificarEstadoLector() {
        // Recargar lectores antes de mostrar la ventana
        modificarEstadoLectorInternalFrame.cargarLectores();
        modificarEstadoLectorInternalFrame.setLocation(200, 100);
        modificarEstadoLectorInternalFrame.setVisible(true);
        modificarEstadoLectorInternalFrame.toFront();
    }
    
    public void abrirCambiarZonaLector() {
        // Recargar lectores antes de mostrar la ventana
        cambiarZonaLectorInternalFrame.cargarLectores();
        cambiarZonaLectorInternalFrame.setLocation(200, 100);
        cambiarZonaLectorInternalFrame.setVisible(true);
        cambiarZonaLectorInternalFrame.toFront();
    }
    

    // aca va lo d abrir ventanas de gestión de materiales
    public void abrirRegistrarDonacionLibro() {
        registrarDonacionLibroInternalFrame.setLocation(200, 100);
        registrarDonacionLibroInternalFrame.setVisible(true);
        registrarDonacionLibroInternalFrame.toFront();
    }
    
    public void abrirRegistrarDonacionArticulo() {
        registrarDonacionArticuloInternalFrame.setLocation(200, 100);
        registrarDonacionArticuloInternalFrame.setVisible(true);
        registrarDonacionArticuloInternalFrame.toFront();
    }
    
    public void abrirConsultarDonacionesRegistradas() {
        consultarDonacionesRegistradasInternalFrame.setLocation(200, 100);
        consultarDonacionesRegistradasInternalFrame.setVisible(true);
        consultarDonacionesRegistradasInternalFrame.toFront();
        // Cargar donaciones después de mostrar la ventana
        consultarDonacionesRegistradasInternalFrame.cargarDonaciones();
    }
    
    public void abrirConsultarDonacionesPorFecha() {
        consultarDonacionesPorFechaInternalFrame.setLocation(200, 100);
        consultarDonacionesPorFechaInternalFrame.setVisible(true);
        consultarDonacionesPorFechaInternalFrame.toFront();
    }
    
    // aca va lo d abrir ventanas de gestión de préstamos
    public void abrirRegistrarPrestamo() {
        registrarPrestamoInternalFrame.setLocation(200, 100);
        registrarPrestamoInternalFrame.setVisible(true);
        registrarPrestamoInternalFrame.toFront();
    }
    
    public void abrirActualizarPrestamo() {
        // Recargar préstamos antes de mostrar la ventana
        actualizarPrestamoInternalFrame.cargarPrestamos();
        actualizarPrestamoInternalFrame.setLocation(200, 100);
        actualizarPrestamoInternalFrame.setVisible(true);
        actualizarPrestamoInternalFrame.toFront();
    }
    
    public void abrirHistorialPrestamosLector() {
        historialPrestamosLectorInternalFrame.setLocation(200, 100);
        historialPrestamosLectorInternalFrame.setVisible(true);
        historialPrestamosLectorInternalFrame.toFront();
    }
}