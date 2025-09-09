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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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
import com.pap.presentacion.HistorialPrestamoBibliotecario;
import com.pap.presentacion.ReportePrestamoZona;
import com.pap.presentacion.ConsultarMaterialesPendientes;

public class Principal {

    private JFrame frame;
    private static Principal INSTANCE;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel contentPanel;
    
    // Internal Frames para las funcionalidades
    private AltaLector altaLectorInternalFrame;
    private AltaBibliotecario altaBibliotecarioInternalFrame;
    private ModificarEstadoLector modificarEstadoLectorInternalFrame;
    private CambiarZonaLector cambiarZonaLectorInternalFrame;
    
    // Internal Frames para gestion de materiales
    private RegistrarDonacionLibro registrarDonacionLibroInternalFrame;
    private RegistrarDonacionArticulo registrarDonacionArticuloInternalFrame;
    private ConsultarDonacionesRegistradas consultarDonacionesRegistradasInternalFrame;
    private ConsultarDonacionesPorFecha consultarDonacionesPorFechaInternalFrame;
    
    // Internal Frames para gestion de prestamos
    private RegistrarPrestamo registrarPrestamoInternalFrame;
    private ActualizarPrestamo actualizarPrestamoInternalFrame;
    private HistorialPrestamosLector historialPrestamosLectorInternalFrame;
    
    // Internal Frames para control y seguimiento
    private HistorialPrestamoBibliotecario historialPrestamoBibliotecarioInternalFrame;
    private ReportePrestamoZona reportePrestamoZonaInternalFrame;
    private ConsultarMaterialesPendientes consultarMaterialesPendientesInternalFrame;

    private IControlador controlador;

    public static void main(String[] args) {
        try {
            // Aplicar look and feel moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Personalizar colores de la barra de titulo
            UIManager.put("InternalFrame.titleBackground", new Color(34, 51, 59));
            UIManager.put("InternalFrame.titleForeground", Color.WHITE);
            UIManager.put("InternalFrame.border", BorderFactory.createLineBorder(new Color(34, 51, 59), 1));
            
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
        INSTANCE = this;
        
        // Obtener instancia del controlador
        Fabrica fabrica = Fabrica.getInstancia();
        controlador = fabrica.crearControlador();
        
        // Crear los Internal Frames
        crearInternalFrames();
        
        // Crear panel de bienvenida
        crearPanelBienvenida();
        
        // Actualizar posiciones iniciales
        actualizarPosicionPaneles();
    }

    // Acceso global controlado
    public static Principal getInstance() { return INSTANCE; }

    public void volverAPantallaInicialPublic() { volverAPantallaInicial(); }

    public void irASubmenuUsuarios() {
        contentPanel.removeAll();
        crearPanelBienvenida();
        mostrarSubmenuUsuarios();
        actualizarPosicionPaneles();
    }

    public void irASubmenuMateriales() {
        contentPanel.removeAll();
        crearPanelBienvenida();
        mostrarSubmenuMateriales();
        actualizarPosicionPaneles();
    }

    public void irASubmenuPrestamos() {
        contentPanel.removeAll();
        crearPanelBienvenida();
        mostrarSubmenuPrestamos();
        actualizarPosicionPaneles();
    }

    public void irASubmenuControlSeguimiento() {
        contentPanel.removeAll();
        crearPanelBienvenida();
        mostrarSubmenuControlSeguimiento();
        actualizarPosicionPaneles();
    }

    private void initialize() {
        frame = new JFrame("Lectores.uy - Sistema de Biblioteca");
        
        // Tamaño inicial y redimensionable
        frame.setSize(new Dimension(1200, 800));
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(true);
        
        // Agregar WindowListener para manejar el cierre de la ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Panel principal con layout absoluto para mejor control de posicionamiento
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(66, 69, 73));
        frame.setContentPane(mainPanel);
        
        // Crear panel de contenido principal primero
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(66, 69, 73));
        
        // Crear panel lateral izquierdo
        crearPanelLateral();
        
        // Crear panel lateral derecho
        crearPanelDerecho();
        
        // Add component listener to handle window resizing
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarPosicionPaneles();
            }
        });
    }

    private void crearPanelLateral() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(new Color(66, 69, 73)); // Dark mode color
        leftPanel.setBounds(0, 0, 300, frame.getHeight()); // Posición fija en el lado izquierdo
        
        // Panel para los botones de menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        menuPanel.setBackground(new Color(66, 69, 73));
        menuPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        // Crear botones de menu con iconos (sin bordes)
        JButton btnGestionUsuarios = crearBotonMenu("Gestion de Usuarios", "Gestion_usuarios.png");
        btnGestionUsuarios.addActionListener(e -> mostrarSubmenuUsuarios());
        
        JButton btnGestionMateriales = crearBotonMenu("Gestion de Materiales", "Gestion_materiales.png");
        btnGestionMateriales.addActionListener(e -> mostrarSubmenuMateriales());
        
        JButton btnGestionPrestamos = crearBotonMenu("Gestion de Prestamos", "Gestion_prestamos.png");
        btnGestionPrestamos.addActionListener(e -> mostrarSubmenuPrestamos());
        
        JButton btnControlSeguimiento = crearBotonMenu("Control y Seguimiento", "Control_y_seguimiento.png");
        btnControlSeguimiento.addActionListener(e -> mostrarSubmenuControlSeguimiento());
        
        JButton btnSalir = crearBotonMenu("Salir", "Salir.png");
        btnSalir.addActionListener(e -> System.exit(0));
        
        menuPanel.add(btnGestionUsuarios);
        menuPanel.add(btnGestionMateriales);
        menuPanel.add(btnGestionPrestamos);
        menuPanel.add(btnControlSeguimiento);
        menuPanel.add(btnSalir);
        
        leftPanel.add(menuPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel);
    }

    private void crearPanelDerecho() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(new Color(83, 104, 120)); // Color ligeramente mas claro
        rightPanel.setBounds(300, 0, 255, frame.getHeight()); // Posicionado justo al lado derecho del panel izquierdo
        rightPanel.setVisible(false); // Inicialmente oculto
        
        mainPanel.add(rightPanel);
        
        // Set initial bounds for content panel and add it to main panel
        contentPanel.setBounds(300, 0, Math.max(600, frame.getWidth() - 300), Math.max(400, frame.getHeight()));
        mainPanel.add(contentPanel);
    }

    private JButton crearBotonMenu(String texto, String nombreIcono) {
        JButton boton = new JButton();
        boton.setText(texto);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Aumentado de 14 a 18 (1.5x)
        boton.setForeground(Color.WHITE); // White text for dark background
        boton.setBackground(new Color(66, 69, 73)); // Dark mode color
        boton.setBorder(null); // Sin borde
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(260, 80)); // Aumentado para texto mas grande
        
        // Cargar y establecer icono
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/" + nombreIcono));
            if (icono.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // Redimensionar icono con mejor calidad
                Image img = icono.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH); // Mayor resolución
                boton.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + nombreIcono);
        }
        
        // Efecto hover sutil
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(76, 79, 83)); // Color ligeramente mas claro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(66, 69, 73)); // Color original
            }
        });
        
        return boton;
    }

    private JButton crearBotonOpcion(String texto) {
        JButton boton = new JButton();
        boton.setText(texto);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Bold text
        boton.setForeground(Color.WHITE); // White text
        boton.setBackground(new Color(83, 104, 120)); // Match panel background
        boton.setBorder(null); // Sin borde
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(190, 50)); // Adjusted for narrower panel (220 * 0.85 ≈ 190)
        
        // Efecto hover sutil
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 120, 140)); // Slightly lighter on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(83, 104, 120)); // Back to original
            }
        });
        
        return boton;
    }

    private JButton crearBotonInicio() {
        JButton boton = new JButton("Volver");
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(52, 152, 219));
        boton.setBorder(null);
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(190, 50));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        boton.addActionListener(e -> volverAPantallaInicial());
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(52, 152, 219));
            }
        });
        
        return boton;
    }

    private void agregarListenerCerrarPanel() {
        // Agregar listener al contentPanel para cerrar el panel derecho al hacer clic
        contentPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (rightPanel.isVisible()) {
                    ocultarPanelDerecho();
                }
            }
        });
    }

    private void mostrarSubmenuUsuarios() {
        rightPanel.removeAll();
        rightPanel.setVisible(true);
        
        // Ajustar posiciones de los paneles
        actualizarPosicionPaneles();
        
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        opcionesPanel.setBackground(new Color(83, 104, 120));
        opcionesPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel titulo = new JLabel("Gestion de Usuarios");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setPreferredSize(new Dimension(190, 40));
        opcionesPanel.add(titulo);
        
        JButton btnRegistrarLector = crearBotonOpcion("Registrar Lector");
        btnRegistrarLector.addActionListener(e -> abrirAltaLector());
        
        JButton btnRegistrarBibliotecario = crearBotonOpcion("Registrar Bibliotecario");
        btnRegistrarBibliotecario.addActionListener(e -> abrirAltaBibliotecario());
        
        JButton btnListarLectores = crearBotonOpcion("Listar Lectores");
        btnListarLectores.addActionListener(e -> abrirListaDeLectores());
        
        JButton btnListarBibliotecarios = crearBotonOpcion("Listar Bibliotecarios");
        btnListarBibliotecarios.addActionListener(e -> abrirListaDeBibliotecarios());
        
        JButton btnModificarEstado = crearBotonOpcion("Modificar Estado de Lector");
        btnModificarEstado.addActionListener(e -> abrirModificarEstadoLector());
        
        JButton btnCambiarZona = crearBotonOpcion("Cambiar Zona de Lector");
        btnCambiarZona.addActionListener(e -> abrirCambiarZonaLector());
        
        opcionesPanel.add(btnRegistrarLector);
        opcionesPanel.add(btnRegistrarBibliotecario);
        opcionesPanel.add(btnListarLectores);
        opcionesPanel.add(btnListarBibliotecarios);
        opcionesPanel.add(btnModificarEstado);
        opcionesPanel.add(btnCambiarZona);
        
        // Botón Inicio al final
        JButton btnInicio = crearBotonInicio();
        opcionesPanel.add(btnInicio);
        
        rightPanel.add(opcionesPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        
        // Agregar listener para cerrar panel al hacer clic afuera
        agregarListenerCerrarPanel();
    }

    private void mostrarSubmenuMateriales() {
        rightPanel.removeAll();
        rightPanel.setVisible(true);
        
        // Ajustar posiciones de los paneles
        actualizarPosicionPaneles();
        
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        opcionesPanel.setBackground(new Color(83, 104, 120));
        opcionesPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel titulo = new JLabel("Gestion de Materiales");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setPreferredSize(new Dimension(190, 40));
        opcionesPanel.add(titulo);
        
        JButton btnRegistrarLibro = crearBotonOpcion("Registrar Donacion de Libro");
        btnRegistrarLibro.addActionListener(e -> abrirRegistrarDonacionLibro());
        
        JButton btnRegistrarArticulo = crearBotonOpcion("Registrar Donacion de Articulo");
        btnRegistrarArticulo.addActionListener(e -> abrirRegistrarDonacionArticulo());
        
        JButton btnConsultarDonaciones = crearBotonOpcion("Consultar Donaciones Registradas");
        btnConsultarDonaciones.addActionListener(e -> abrirConsultarDonacionesRegistradas());
        
        JButton btnConsultarPorFecha = crearBotonOpcion("Consultar Donaciones por Fecha");
        btnConsultarPorFecha.addActionListener(e -> abrirConsultarDonacionesPorFecha());
        
        opcionesPanel.add(btnRegistrarLibro);
        opcionesPanel.add(btnRegistrarArticulo);
        opcionesPanel.add(btnConsultarDonaciones);
        opcionesPanel.add(btnConsultarPorFecha);
        
        // Botón Inicio al final
        JButton btnInicio = crearBotonInicio();
        opcionesPanel.add(btnInicio);
        
        rightPanel.add(opcionesPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        
        // Agregar listener para cerrar panel al hacer clic afuera
        agregarListenerCerrarPanel();
    }

    private void mostrarSubmenuPrestamos() {
        rightPanel.removeAll();
        rightPanel.setVisible(true);
        
        // Ajustar posiciones de los paneles
        actualizarPosicionPaneles();
        
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        opcionesPanel.setBackground(new Color(83, 104, 120));
        opcionesPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel titulo = new JLabel("Gestion de Prestamos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setPreferredSize(new Dimension(190, 40));
        opcionesPanel.add(titulo);
        
        JButton btnRegistrarPrestamo = crearBotonOpcion("Registrar Prestamo");
        btnRegistrarPrestamo.addActionListener(e -> abrirRegistrarPrestamo());
        
        JButton btnActualizarPrestamo = crearBotonOpcion("Actualizar Prestamo");
        btnActualizarPrestamo.addActionListener(e -> abrirActualizarPrestamo());
        
        JButton btnHistorialLector = crearBotonOpcion("Historial Prestamos Lector");
        btnHistorialLector.addActionListener(e -> abrirHistorialPrestamosLector());
        
        opcionesPanel.add(btnRegistrarPrestamo);
        opcionesPanel.add(btnActualizarPrestamo);
        opcionesPanel.add(btnHistorialLector);
        
        // Botón Inicio al final
        JButton btnInicio = crearBotonInicio();
        opcionesPanel.add(btnInicio);
        
        rightPanel.add(opcionesPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        
        // Agregar listener para cerrar panel al hacer clic afuera
        agregarListenerCerrarPanel();
    }

    private void mostrarSubmenuControlSeguimiento() {
        rightPanel.removeAll();
        rightPanel.setVisible(true);
        
        // Ajustar posiciones de los paneles
        actualizarPosicionPaneles();
        
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        opcionesPanel.setBackground(new Color(83, 104, 120));
        opcionesPanel.setBorder(new EmptyBorder(30, 20, 30, 20));
        
        JLabel titulo = new JLabel("Control y Seguimiento");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setPreferredSize(new Dimension(190, 40));
        opcionesPanel.add(titulo);
        
        JButton btnHistorialBibliotecario = crearBotonOpcion("Historial Prestamos Bibliotecario");
        btnHistorialBibliotecario.addActionListener(e -> abrirHistorialPrestamoBibliotecario());
        
        JButton btnReporteZona = crearBotonOpcion("Reporte Prestamos por Zona");
        btnReporteZona.addActionListener(e -> abrirReportePrestamoZona());
        
        JButton btnMaterialesSolicitados = crearBotonOpcion("Materiales mas Solicitados");
        btnMaterialesSolicitados.addActionListener(e -> abrirConsultarMaterialesPendientes());
        
        opcionesPanel.add(btnHistorialBibliotecario);
        opcionesPanel.add(btnReporteZona);
        opcionesPanel.add(btnMaterialesSolicitados);
        
        // Botón Inicio al final
        JButton btnInicio = crearBotonInicio();
        opcionesPanel.add(btnInicio);
        
        rightPanel.add(opcionesPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
        
        // Agregar listener para cerrar panel al hacer clic afuera
        agregarListenerCerrarPanel();
    }

    private void crearInternalFrames() {
        // Crear los Internal Frames para gestion de usuarios
        altaLectorInternalFrame = new AltaLector(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        altaBibliotecarioInternalFrame = new AltaBibliotecario(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        modificarEstadoLectorInternalFrame = new ModificarEstadoLector(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        cambiarZonaLectorInternalFrame = new CambiarZonaLector(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        // Crear los Internal Frames para gestion de materiales
        registrarDonacionLibroInternalFrame = new RegistrarDonacionLibro(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        registrarDonacionArticuloInternalFrame = new RegistrarDonacionArticulo(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        consultarDonacionesRegistradasInternalFrame = new ConsultarDonacionesRegistradas(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        consultarDonacionesPorFechaInternalFrame = new ConsultarDonacionesPorFecha(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        // Crear los Internal Frames para gestion de prestamos
        registrarPrestamoInternalFrame = new RegistrarPrestamo(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        actualizarPrestamoInternalFrame = new ActualizarPrestamo(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        historialPrestamosLectorInternalFrame = new HistorialPrestamosLector(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        // Crear los Internal Frames para control y seguimiento
        historialPrestamoBibliotecarioInternalFrame = new HistorialPrestamoBibliotecario(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        reportePrestamoZonaInternalFrame = new ReportePrestamoZona(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
        
        consultarMaterialesPendientesInternalFrame = new ConsultarMaterialesPendientes(controlador);
        // No es necesario configurar como InternalFrame ya que es un JPanel
    }

    private void configurarInternalFrame(javax.swing.JInternalFrame internalFrame) {
        internalFrame.setClosable(true);
        internalFrame.setResizable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setIconifiable(true);
        internalFrame.setLocation(50, 50);
        internalFrame.setVisible(false);
        
        // Configurar bordes y colores
        internalFrame.setBorder(BorderFactory.createLineBorder(new Color(34, 51, 59), 1));
        
        contentPanel.add(internalFrame);
    }

    private void crearPanelBienvenida() {
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
                
                // Circulo decorativo
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(getWidth() - 150, getHeight() - 150, 200, 200);
                
                g2d.dispose();
            }
        };
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
        
        // Titulo principal con sombra
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
        
        contentPanel.add(welcomePanel);
        
        // Listener para cambios de tamano de la ventana
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                welcomePanel.setBounds(0, 0, contentPanel.getWidth(), contentPanel.getHeight());
                welcomePanel.repaint();
            }
        });
    }

    private void ocultarPanelDerecho() {
        if (rightPanel != null) {
            rightPanel.setVisible(false);
        }
        // Ajustar el panel de contenido para ocupar todo el espacio disponible
        if (contentPanel != null) {
            contentPanel.setBounds(300, 0, frame.getWidth() - 300, frame.getHeight());
        }
        if (mainPanel != null) {
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }
    
    private void actualizarPosicionPaneles() {
        // Actualizar posiciones cuando la ventana cambia de tamaño
        if (leftPanel != null) {
            leftPanel.setBounds(0, 0, 300, frame.getHeight());
        }
        
        if (rightPanel != null && contentPanel != null) {
            rightPanel.setBounds(300, 0, 255, frame.getHeight());
            
            if (rightPanel.isVisible()) {
                // Si el panel derecho está visible, el contenido empieza después de él
                contentPanel.setBounds(555, 0, frame.getWidth() - 555, frame.getHeight());
            } else {
                // Si el panel derecho está oculto, el contenido empieza después del panel izquierdo
                contentPanel.setBounds(300, 0, frame.getWidth() - 300, frame.getHeight());
            }
        }
        
        if (mainPanel != null) {
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }

    private void mostrarFuncionEnEspacioPrincipal(javax.swing.JInternalFrame internalFrame) {
        // Ocultar panel de bienvenida
        contentPanel.removeAll();
        
        // Crear un panel contenedor para la función
        JPanel funcionPanel = new JPanel(new BorderLayout());
        funcionPanel.setBackground(new Color(66, 69, 73));
        funcionPanel.setOpaque(true);
        
        // Crear panel de título
        JPanel tituloPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Fondo con gradiente moderno
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 51, 59),
                    getWidth(), getHeight(), new Color(52, 73, 94)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        tituloPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 20));
        funcionPanel.add(tituloPanel, BorderLayout.NORTH);
        
        // Título de la función
        JLabel lblTitulo = new JLabel(internalFrame.getTitle());
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        tituloPanel.add(lblTitulo);
        
        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(231, 76, 60));
        btnVolver.setBorder(null);
        btnVolver.setFocusPainted(false);
        btnVolver.setBounds(funcionPanel.getWidth() - 150, 25, 100, 35);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> volverAPantallaInicial());
        tituloPanel.add(btnVolver);
        
        // Agregar el contenido de la función
        internalFrame.setBounds(0, 80, funcionPanel.getWidth(), funcionPanel.getHeight() - 80);
        internalFrame.setVisible(true);
        funcionPanel.add(internalFrame);
        
        contentPanel.add(funcionPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void mostrarFuncionEnEspacioPrincipalPanel(JPanel panel) {
        // Ocultar panel de bienvenida
        contentPanel.removeAll();
        
        // Crear un panel contenedor para la función con BorderLayout
        JPanel funcionPanel = new JPanel();
        funcionPanel.setLayout(new BorderLayout());
        funcionPanel.setBackground(new Color(66, 69, 73)); // Dark background
        
        // Crear panel de título
        JPanel tituloPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Fondo con gradiente moderno
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(34, 51, 59),
                    getWidth(), getHeight(), new Color(52, 73, 94)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        tituloPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        tituloPanel.setPreferredSize(new Dimension(0, 80));
        
        // Título de la función (dinámico basado en el tipo de panel)
        String titulo = obtenerTituloPanel(panel);
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        tituloPanel.add(lblTitulo);
        
        // Add title panel to NORTH
        funcionPanel.add(tituloPanel, BorderLayout.NORTH);
        
        // Agregar el contenido de la función
        panel.setVisible(true);
        funcionPanel.add(panel, BorderLayout.CENTER);
        
        contentPanel.add(funcionPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private String obtenerTituloPanel(JPanel panel) {
        if (panel instanceof AltaLector) {
            return "Registrar Nuevo Lector";
        } else if (panel instanceof AltaBibliotecario) {
            return "Registrar Nuevo Bibliotecario";
        } else if (panel instanceof ModificarEstadoLector) {
            return "Modificar Estado de Lector";
        } else if (panel instanceof CambiarZonaLector) {
            return "Cambiar Zona de Lector";
        } else if (panel instanceof RegistrarDonacionLibro) {
            return "Registrar Donación de Libro";
        } else if (panel instanceof RegistrarDonacionArticulo) {
            return "Registrar Donación de Artículo";
        } else if (panel instanceof ConsultarDonacionesRegistradas) {
            return "Consultar Donaciones Registradas";
        } else if (panel instanceof ConsultarDonacionesPorFecha) {
            return "Consultar Donaciones por Fecha";
        } else if (panel instanceof RegistrarPrestamo) {
            return "Registrar Nuevo Préstamo";
        } else if (panel instanceof ActualizarPrestamo) {
            return "Actualizar Prestamo";
        } else if (panel instanceof HistorialPrestamosLector) {
            return "Historial de Prestamos por Lector";
        } else if (panel instanceof HistorialPrestamoBibliotecario) {
            return "Historial de Prestamos por Bibliotecario";
        } else if (panel instanceof ReportePrestamoZona) {
            return "Reporte de Prestamos por Zona";
        } else if (panel instanceof ConsultarMaterialesPendientes) {
            return "Materiales con Prestamos Pendientes";
        }
        return "Funcion";
    }

    private void volverAPantallaInicial() {
        // Ocultar el panel secundario
        ocultarPanelDerecho();
        
        // Ocultar todas las funciones
        contentPanel.removeAll();
        
        // Mostrar panel de bienvenida
        crearPanelBienvenida();
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Metodos para abrir ventanas de gestion de usuarios
    public void abrirAltaLector() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(altaLectorInternalFrame);
    }
    
    public void abrirAltaBibliotecario() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(altaBibliotecarioInternalFrame);
    }
    
    public void abrirListaDeLectores() {
        ListaDeLectores listaDeLectoresFrame = new ListaDeLectores(controlador);
        listaDeLectoresFrame.setLocation(200, 100);
        listaDeLectoresFrame.setVisible(true);
        listaDeLectoresFrame.toFront();
    }

    public void abrirListaDeBibliotecarios() {
        ListaDeBibliotecarios listaDeBibliotecariosFrame = new ListaDeBibliotecarios(controlador);
        listaDeBibliotecariosFrame.setLocation(200, 100);
        listaDeBibliotecariosFrame.setVisible(true);
        listaDeBibliotecariosFrame.toFront();
    }
    
    public void abrirModificarEstadoLector() {
        ocultarPanelDerecho();
        modificarEstadoLectorInternalFrame.cargarLectores();
        mostrarFuncionEnEspacioPrincipalPanel(modificarEstadoLectorInternalFrame);
    }
    
    public void abrirCambiarZonaLector() {
        ocultarPanelDerecho();
        cambiarZonaLectorInternalFrame.cargarLectores();
        mostrarFuncionEnEspacioPrincipalPanel(cambiarZonaLectorInternalFrame);
    }

    // Metodos para abrir ventanas de gestion de materiales
    public void abrirRegistrarDonacionLibro() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(registrarDonacionLibroInternalFrame);
    }
    
    public void abrirRegistrarDonacionArticulo() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(registrarDonacionArticuloInternalFrame);
    }
    
    public void abrirConsultarDonacionesRegistradas() {
        ocultarPanelDerecho();
        consultarDonacionesRegistradasInternalFrame.cargarDonaciones();
        mostrarFuncionEnEspacioPrincipalPanel(consultarDonacionesRegistradasInternalFrame);
    }
    
    public void abrirConsultarDonacionesPorFecha() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(consultarDonacionesPorFechaInternalFrame);
    }
    
    // Metodos para abrir ventanas de gestion de prestamos
    public void abrirRegistrarPrestamo() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(registrarPrestamoInternalFrame);
    }
    
    public void abrirActualizarPrestamo() {
        ocultarPanelDerecho();
        actualizarPrestamoInternalFrame.cargarPrestamos();
        mostrarFuncionEnEspacioPrincipalPanel(actualizarPrestamoInternalFrame);
    }
    
    public void abrirHistorialPrestamosLector() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(historialPrestamosLectorInternalFrame);
    }
    
    // Metodos para abrir ventanas de control y seguimiento
    public void abrirHistorialPrestamoBibliotecario() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(historialPrestamoBibliotecarioInternalFrame);
    }
    
    public void abrirReportePrestamoZona() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(reportePrestamoZonaInternalFrame);
    }
    
    public void abrirConsultarMaterialesPendientes() {
        ocultarPanelDerecho();
        mostrarFuncionEnEspacioPrincipalPanel(consultarMaterialesPendientesInternalFrame);
    }
}