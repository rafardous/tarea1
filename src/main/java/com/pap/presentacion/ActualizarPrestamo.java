package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoPrestamo;
import com.pap.excepciones.ActualizarPrestamoExcepcion;
import com.pap.datatypes.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.table.JTableHeader;

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
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Actualizar Informacion de Prestamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        add(headerPanel, BorderLayout.NORTH);
        
        // Form Container Panel using GridBagLayout for precise control
        JPanel formContainerPanel = new JPanel();
        formContainerPanel.setLayout(new GridBagLayout());
        formContainerPanel.setOpaque(false);
        formContainerPanel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Reduced top margin to bring form closer to title
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Vertical spacing: 10px top, 10px bottom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically for table
        
        // Subtitulo explicativo
        JLabel lblSubtitulo = new JLabel("Selecciona un prestamo del combo y modifica sus datos");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(200, 200, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblSubtitulo, gbc);
        
        // Panel de selección de préstamo
        crearPanelSeleccionPrestamo(formContainerPanel, gbc);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamo);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(scrollPane, gbc);
        
        // Panel de edición
        crearPanelEdicion(formContainerPanel, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnActualizar = createStyledButton("Actualizar", new Color(46, 204, 113));
        btnActualizar.setPreferredSize(new Dimension(120, 35));
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPrestamo();
            }
        });
        buttonPanel.add(btnActualizar);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setPreferredSize(new Dimension(120, 35));
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });
        buttonPanel.add(btnLimpiar);
        
        btnCerrar = createStyledButton("Cerrar", new Color(231, 76, 60));
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        buttonPanel.add(btnCerrar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuPrestamos();
            }
        });
        buttonPanel.add(btnVolver);
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0; // Don't expand buttons vertically
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Don't fill buttons
        gbc.insets = new Insets(15, 0, 0, 0); // Increased spacing above buttons
        formContainerPanel.add(buttonPanel, gbc);

        add(formContainerPanel, BorderLayout.CENTER);
    }
    
    private void crearPanelSeleccionPrestamo(JPanel parent, GridBagConstraints gbc) {
        // Panel de selección con borde
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSeleccion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelSeleccion.setBackground(new Color(84, 86, 91));
        
        // Label para el combo
        JLabel lblPrestamo = new JLabel("Seleccionar Prestamo:");
        lblPrestamo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPrestamo.setForeground(Color.WHITE);
        panelSeleccion.add(lblPrestamo);
        
        // Combo de préstamos
        comboPrestamos = new JComboBox<>();
        comboPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboPrestamos.setPreferredSize(new Dimension(300, 30));
        comboPrestamos.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
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
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(200, 200, 200));
        panelSeleccion.add(lblInfo);
        
        // Add to parent with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Don't expand vertically
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        parent.add(panelSeleccion, gbc);
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
        tablaPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaPrestamo.setRowHeight(30);
        tablaPrestamo.setGridColor(new Color(52, 152, 219));
        tablaPrestamo.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaPrestamo.setSelectionForeground(Color.BLACK);
        tablaPrestamo.setBackground(new Color(74, 76, 81));
        tablaPrestamo.setForeground(Color.WHITE);
        
        // Style table header
        JTableHeader header = tablaPrestamo.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(new Color(52, 73, 94));
        header.setBackground(new Color(200, 200, 200));
        
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
    
    private void crearPanelEdicion(JPanel parent, GridBagConstraints gbc) {
        // Panel de edición con borde
        JPanel panelEdicion = new JPanel();
        panelEdicion.setLayout(new GridBagLayout());
        panelEdicion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        panelEdicion.setBackground(new Color(84, 86, 91));
        
        GridBagConstraints panelGbc = new GridBagConstraints();
        panelGbc.insets = new Insets(5, 5, 5, 5);
        panelGbc.anchor = GridBagConstraints.WEST;
        panelGbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Titulo del panel
        JLabel lblTituloEdicion = new JLabel("Modificar Datos del Prestamo:");
        lblTituloEdicion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTituloEdicion.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 0;
        panelGbc.gridwidth = 2;
        panelGbc.anchor = GridBagConstraints.CENTER;
        panelEdicion.add(lblTituloEdicion, panelGbc);
        
        // Fecha de solicitud
        JLabel lblFechaSolicitud = new JLabel("Nueva Fecha de Solicitud:");
        lblFechaSolicitud.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaSolicitud.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 1;
        panelGbc.gridwidth = 1;
        panelGbc.anchor = GridBagConstraints.WEST;
        panelEdicion.add(lblFechaSolicitud, panelGbc);
        
        // Spinner para fecha de solicitud
        Calendar calSolicitud = Calendar.getInstance();
        SpinnerDateModel modelSolicitud = new SpinnerDateModel(calSolicitud.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaSolicitud = new JSpinner(modelSolicitud);
        spinnerFechaSolicitud.setEditor(new JSpinner.DateEditor(spinnerFechaSolicitud, "dd/MM/yyyy"));
        spinnerFechaSolicitud.setPreferredSize(new Dimension(120, 30));
        spinnerFechaSolicitud.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelGbc.gridx = 1;
        panelGbc.gridy = 1;
        panelEdicion.add(spinnerFechaSolicitud, panelGbc);
        
        // Fecha de devolución
        JLabel lblFechaDevolucion = new JLabel("Nueva Fecha de Devolucion:");
        lblFechaDevolucion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFechaDevolucion.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 2;
        panelEdicion.add(lblFechaDevolucion, panelGbc);
        
        // Spinner para fecha de devolución
        Calendar calDevolucion = Calendar.getInstance();
        calDevolucion.add(Calendar.DAY_OF_MONTH, 7); // Por defecto 7 días
        SpinnerDateModel modelDevolucion = new SpinnerDateModel(calDevolucion.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaDevolucion = new JSpinner(modelDevolucion);
        spinnerFechaDevolucion.setEditor(new JSpinner.DateEditor(spinnerFechaDevolucion, "dd/MM/yyyy"));
        spinnerFechaDevolucion.setPreferredSize(new Dimension(120, 30));
        spinnerFechaDevolucion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelGbc.gridx = 1;
        panelGbc.gridy = 2;
        panelEdicion.add(spinnerFechaDevolucion, panelGbc);
        
        // Estado del préstamo
        JLabel lblEstado = new JLabel("Nuevo Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstado.setForeground(Color.WHITE);
        panelGbc.gridx = 0;
        panelGbc.gridy = 3;
        panelEdicion.add(lblEstado, panelGbc);
        
        // Combo de estados
        comboEstado = new JComboBox<>(EstadoPrestamo.values());
        comboEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboEstado.setPreferredSize(new Dimension(120, 30));
        comboEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelGbc.gridx = 1;
        panelGbc.gridy = 3;
        panelEdicion.add(comboEstado, panelGbc);
        
        // Informacion adicional
        JLabel lblInfo = new JLabel("Modifica los campos necesarios y haz clic en 'Actualizar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(200, 200, 200));
        panelGbc.gridx = 0;
        panelGbc.gridy = 4;
        panelGbc.gridwidth = 2;
        panelGbc.anchor = GridBagConstraints.CENTER;
        panelEdicion.add(lblInfo, panelGbc);
        
        // Add to parent with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Don't expand vertically
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        parent.add(panelEdicion, gbc);
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
