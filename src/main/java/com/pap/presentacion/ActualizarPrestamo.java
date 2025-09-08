package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoPrestamo;
import com.pap.excepciones.ActualizarPrestamoExcepcion;
import com.pap.datatypes.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ActualizarPrestamo extends JPanel {
    
    private IControlador controlador;
    
    private JComboBox<String> comboPrestamos;
    private JTable tablaPrestamo;
    private DefaultTableModel modeloTabla;
    private JSpinner spinnerFechaSolicitud;
    private JSpinner spinnerFechaDevolucion;
    private JComboBox<EstadoPrestamo> comboEstado;
    private JButton btnActualizar;
    private JButton btnLimpiar;
    private JButton btnCerrar;
    
    // Lista de préstamos para el combo
    private ArrayList<DtPrestamo> prestamos;
    
    // Formato para fechas
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    // Formato para mostrar en el combo
    private SimpleDateFormat formatoCombo = new SimpleDateFormat("dd/MM/yyyy");

    public ActualizarPrestamo(IControlador controlador) {
        this.controlador = controlador;
        this.prestamos = new ArrayList<>();
        initialize();
        cargarPrestamos();
    }

    private void initialize() {
        setLayout(null);
        setBounds(0, 0, 1200, 800);
        setBackground(new Color(74, 76, 81)); // Dark theme background
        
        // Panel de fondo con gradiente
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(74, 76, 81),
                    getWidth(), getHeight(), new Color(84, 86, 91)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        contentPanel.setLayout(null);
        contentPanel.setBounds(0, 0, getWidth(), getHeight());
        add(contentPanel);
        
        // Titulo con estilo moderno
        JLabel lblTitulo = new JLabel("Actualizar Informacion de Prestamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(250, 20, 500, 30);
        contentPanel.add(lblTitulo);
        
        // Subtitulo explicativo
        JLabel lblSubtitulo = new JLabel("Selecciona un prestamo del combo y modifica sus datos");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setBounds(300, 50, 400, 20);
        contentPanel.add(lblSubtitulo);
        
        // Panel de selección de préstamo
        crearPanelSeleccionPrestamo(contentPanel);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamo);
        scrollPane.setBounds(30, 180, 940, 200);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        contentPanel.add(scrollPane);
        
        // Panel de edición
        crearPanelEdicion(contentPanel);
        
        // Botones modernos
        btnActualizar = createStyledButton("Actualizar", new Color(46, 204, 113));
        btnActualizar.setBounds(200, 600, 120, 35);
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPrestamo();
            }
        });
        contentPanel.add(btnActualizar);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setBounds(350, 600, 120, 35);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        contentPanel.add(btnLimpiar);
        
        btnCerrar = createStyledButton("Cerrar", new Color(231, 76, 60));
        btnCerrar.setBounds(500, 600, 120, 35);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        contentPanel.add(btnCerrar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setBounds(640, 600, 120, 35);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuPrestamos();
            }
        });
        contentPanel.add(btnVolver);
    }
    
    private void crearPanelSeleccionPrestamo(JPanel parent) {
        // Panel de selección con borde
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(null);
        panelSeleccion.setBounds(30, 90, 940, 70);
        panelSeleccion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelSeleccion.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelSeleccion);
        
        // Label para el combo
        JLabel lblPrestamo = new JLabel("Seleccionar Prestamo:");
        lblPrestamo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPrestamo.setForeground(Color.WHITE);
        lblPrestamo.setBounds(20, 15, 150, 20);
        panelSeleccion.add(lblPrestamo);
        
        // Combo de préstamos
        comboPrestamos = new JComboBox<>();
        comboPrestamos.setBounds(180, 15, 400, 25);
        comboPrestamos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboPrestamos.getSelectedIndex() >= 0) {
                    mostrarPrestamoSeleccionado();
                }
            }
        });
        panelSeleccion.add(comboPrestamos);
        
        // Información adicional
        JLabel lblInfo = new JLabel("info prestamo: ID | Email Lector | Email Bibliotecario | Titulo/Descripcion del Material");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(20, 40, 500, 20);
        panelSeleccion.add(lblInfo);
    }
    
    private void crearTabla() {
                 // Definir columnas de la tabla
         String[] columnas = {
             "ID", "Fecha Solicitud", "Fecha Devolucion", "Email Lector", "Email Bibliotecario", "ID Material", "Nombre/Descripcion Material", "Estado"
         };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablaPrestamo = new JTable(modeloTabla);
        tablaPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPrestamo.setRowHeight(25);
        tablaPrestamo.setGridColor(new Color(52, 152, 219));
        tablaPrestamo.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaPrestamo.setSelectionForeground(Color.BLACK);
        
        // Configurar el ordenador de filas para permitir ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tablaPrestamo.setRowSorter(sorter);
        
                 // Configurar anchos de columna
         tablaPrestamo.getColumnModel().getColumn(0).setPreferredWidth(60);   // ID
         tablaPrestamo.getColumnModel().getColumn(1).setPreferredWidth(120);  // Fecha Solicitud
         tablaPrestamo.getColumnModel().getColumn(2).setPreferredWidth(120);  // Fecha Devolucion
         tablaPrestamo.getColumnModel().getColumn(3).setPreferredWidth(180);  // Email Lector
         tablaPrestamo.getColumnModel().getColumn(4).setPreferredWidth(180);  // Email Bibliotecario
         tablaPrestamo.getColumnModel().getColumn(5).setPreferredWidth(100);  // ID Material
         tablaPrestamo.getColumnModel().getColumn(6).setPreferredWidth(200);  // Nombre/Descripcion Material
         tablaPrestamo.getColumnModel().getColumn(7).setPreferredWidth(100);  // Estado
    }
    
    private void crearPanelEdicion(JPanel parent) {
        // Panel de edición con borde
        JPanel panelEdicion = new JPanel();
        panelEdicion.setLayout(null);
        panelEdicion.setBounds(30, 400, 940, 180);
        panelEdicion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panelEdicion.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelEdicion);
        
        // Titulo del panel
        JLabel lblTituloEdicion = new JLabel("Modificar Datos del Prestamo:");
        lblTituloEdicion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTituloEdicion.setForeground(Color.WHITE);
        lblTituloEdicion.setBounds(20, 10, 250, 20);
        panelEdicion.add(lblTituloEdicion);
        
        // Fecha de solicitud
        JLabel lblFechaSolicitud = new JLabel("Nueva Fecha de Solicitud:");
        lblFechaSolicitud.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaSolicitud.setForeground(Color.WHITE);
        lblFechaSolicitud.setBounds(20, 40, 180, 20);
        panelEdicion.add(lblFechaSolicitud);
        
        // Spinner para fecha de solicitud
        Calendar calSolicitud = Calendar.getInstance();
        SpinnerDateModel modelSolicitud = new SpinnerDateModel(calSolicitud.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaSolicitud = new JSpinner(modelSolicitud);
        spinnerFechaSolicitud.setEditor(new JSpinner.DateEditor(spinnerFechaSolicitud, "dd/MM/yyyy"));
        spinnerFechaSolicitud.setBounds(210, 40, 120, 25);
        panelEdicion.add(spinnerFechaSolicitud);
        
        // Fecha de devolución
        JLabel lblFechaDevolucion = new JLabel("Nueva Fecha de Devolucion:");
        lblFechaDevolucion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaDevolucion.setForeground(Color.WHITE);
        lblFechaDevolucion.setBounds(20, 80, 180, 20);
        panelEdicion.add(lblFechaDevolucion);
        
        // Spinner para fecha de devolución
        Calendar calDevolucion = Calendar.getInstance();
        calDevolucion.add(Calendar.DAY_OF_MONTH, 7); // Por defecto 7 días
        SpinnerDateModel modelDevolucion = new SpinnerDateModel(calDevolucion.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaDevolucion = new JSpinner(modelDevolucion);
        spinnerFechaDevolucion.setEditor(new JSpinner.DateEditor(spinnerFechaDevolucion, "dd/MM/yyyy"));
        spinnerFechaDevolucion.setBounds(210, 80, 120, 25);
        panelEdicion.add(spinnerFechaDevolucion);
        
        // Estado del préstamo
        JLabel lblEstado = new JLabel("Nuevo Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setBounds(20, 120, 180, 20);
        panelEdicion.add(lblEstado);
        
        // Combo de estados
        comboEstado = new JComboBox<>(EstadoPrestamo.values());
        comboEstado.setBounds(210, 120, 120, 25);
        panelEdicion.add(comboEstado);
        
        // Informacion adicional
        JLabel lblInfo = new JLabel("Modifica los campos necesarios y haz clic en 'Actualizar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(20, 150, 400, 20);
        panelEdicion.add(lblInfo);
    }
    
    public void cargarPrestamos() {
        try {
            prestamos = controlador.listarTodosLosPrestamos();
            
            comboPrestamos.removeAllItems();
            
            if (prestamos != null && !prestamos.isEmpty()) {
                for (DtPrestamo prestamo : prestamos) {
                    String descripcionMaterial = "";
                    if (prestamo.getMaterial() instanceof com.pap.datatypes.DtLibro) {
                        descripcionMaterial = ((com.pap.datatypes.DtLibro) prestamo.getMaterial()).getTitulo();
                    } else if (prestamo.getMaterial() instanceof com.pap.datatypes.DtArticulo) {
                        descripcionMaterial = ((com.pap.datatypes.DtArticulo) prestamo.getMaterial()).getDescripcion();
                    }
                    
                                         String itemCombo = "ID: " + prestamo.getId() + " | " + 
                                      prestamo.getLector().getEmail() + " | " + 
                                      prestamo.getBibliotecario().getEmail() + " | " + 
                                      descripcionMaterial;
                    comboPrestamos.addItem(itemCombo);
                }
                
                // Seleccionar el primer item por defecto
                if (comboPrestamos.getItemCount() > 0) {
                    comboPrestamos.setSelectedIndex(0);
                    mostrarPrestamoSeleccionado();
                }
            } else {
                comboPrestamos.addItem("No hay prestamos disponibles");
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar prestamos: " + e.getMessage());
            e.printStackTrace();
            comboPrestamos.removeAllItems();
            comboPrestamos.addItem("Error al cargar prestamos");
        }
    }
    
    private void mostrarPrestamoSeleccionado() {
        int selectedIndex = comboPrestamos.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < prestamos.size()) {
            DtPrestamo prestamo = prestamos.get(selectedIndex);
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
                         // descripcion del material
             String descripcionMaterial = "";
             
             // Usar instanceof directamente
             if (prestamo.getMaterial() instanceof DtLibro) {
                 DtLibro dtLibro = (DtLibro) prestamo.getMaterial();
                 descripcionMaterial = dtLibro.getTitulo();
             } else if (prestamo.getMaterial() instanceof DtArticulo) {
                 DtArticulo dtArticulo = (DtArticulo) prestamo.getMaterial();
                 descripcionMaterial = dtArticulo.getDescripcion();
             } else {
                 descripcionMaterial = "Tipo no reconocido";
             }
            
                         // Agregar fila a la tabla
             Object[] fila = {
                 prestamo.getId(),
                 formatoFecha.format(prestamo.getFechaSolicitud()),
                 formatoFecha.format(prestamo.getFechaDevolucion()),
                 prestamo.getLector().getEmail(),
                 prestamo.getBibliotecario().getEmail(),
                 prestamo.getMaterial().getId(), // Mostrar el id (String) en lugar del idMaterial (Integer)
                 descripcionMaterial,
                 prestamo.getEstado() // Agregar el estado del préstamo
             };
            modeloTabla.addRow(fila);
            
            // Actualizar spinners y combo con valores actuales
            spinnerFechaSolicitud.setValue(prestamo.getFechaSolicitud());
            spinnerFechaDevolucion.setValue(prestamo.getFechaDevolucion());
            comboEstado.setSelectedItem(prestamo.getEstado());
        }
    }
    
    private void actualizarPrestamo() {
        int selectedIndex = comboPrestamos.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= prestamos.size()) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un prestamo valido",
                "Error de Validacion", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            DtPrestamo prestamo = prestamos.get(selectedIndex);
            
            // Obtener nuevos valores
            Date nuevaFechaSolicitud = (Date) spinnerFechaSolicitud.getValue();
            Date nuevaFechaDevolucion = (Date) spinnerFechaDevolucion.getValue();
            EstadoPrestamo nuevoEstado = (EstadoPrestamo) comboEstado.getSelectedItem();
            
            // Validar que la fecha de solicitud sea anterior a la de devolución
            if (nuevaFechaSolicitud.after(nuevaFechaDevolucion)) {
                JOptionPane.showMessageDialog(this, 
                    "La fecha de solicitud debe ser anterior a la fecha de devolucion", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Llamar al controlador para actualizar
            boolean resultado = controlador.ActualizarPrestamo(
                prestamo.getId(), 
                nuevaFechaSolicitud, 
                nuevaFechaDevolucion, 
                nuevoEstado
            );
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Prestamo actualizado exitosamente", 
                    "Actualizacion Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Recargar préstamos y actualizar la vista
                cargarPrestamos();
                mostrarPrestamoSeleccionado();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se pudo actualizar el prestamo", 
                    "Error de Actualizacion", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ActualizarPrestamoExcepcion ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar prestamo: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        modeloTabla.setRowCount(0);
        comboPrestamos.setSelectedIndex(-1);
        
        // Resetear spinners a valores por defecto
        Calendar calSolicitud = Calendar.getInstance();
        spinnerFechaSolicitud.setValue(calSolicitud.getTime());
        
        Calendar calDevolucion = Calendar.getInstance();
        calDevolucion.add(Calendar.DAY_OF_MONTH, 7);
        spinnerFechaDevolucion.setValue(calDevolucion.getTime());
        
        comboEstado.setSelectedIndex(0);
        
        JOptionPane.showMessageDialog(this, 
            "Formulario limpiado", 
            "Informacion", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        Color bg = backgroundColor;
        String label = text == null ? "" : text.toLowerCase();
        if (label.contains("modificar") || label.contains("consultar") || label.contains("actualizar")) {
            bg = new Color(46, 204, 113);
        } else if (label.contains("limpiar") || label.contains("volver")) {
            bg = new Color(52, 152, 219);
        } else if (label.contains("cancelar")) {
            bg = new Color(231, 76, 60);
        } else if (bg == null) {
            bg = new Color(46, 49, 54);
        }
        final Color finalBg = bg;
        button.setBackground(finalBg);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(finalBg.brighter(), 2, true),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        return button;
    }
}
