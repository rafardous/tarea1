package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.excepciones.ListarPrestamoLectorExcepcion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class HistorialPrestamosLector extends JPanel {
    
    private IControlador controlador;
    
    private JTextField txtEmailLector;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnCerrar;
    
    // Formato para fechas
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public HistorialPrestamosLector(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark theme background
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        headerPanel.setBackground(new Color(74, 76, 81));
        
        JLabel lblTitulo = new JLabel("Historial de Prestamos por Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitulo);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel formContainerPanel = new JPanel(new GridBagLayout());
        formContainerPanel.setBackground(new Color(74, 76, 81));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // Panel de consulta
        JPanel panelConsulta = crearPanelConsulta();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formContainerPanel.add(panelConsulta, gbc);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
        scrollPane.setPreferredSize(new Dimension(800, 200));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        formContainerPanel.add(scrollPane, gbc);
        
        add(formContainerPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(74, 76, 81));
        
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarHistorialLector();
            }
        });
        buttonPanel.add(btnConsultar);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
            }
        });
        buttonPanel.add(btnLimpiar);
        
        btnCerrar = createStyledButton("Volver", new Color(52, 152, 219));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuPrestamos();
            }
        });
        buttonPanel.add(btnCerrar);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelConsulta() {
        JPanel panelConsulta = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelConsulta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelConsulta.setBackground(new Color(255, 255, 255, 200));
        
        // Label para el email del lector
        JLabel lblLector = new JLabel("Lector a consultar:");
        lblLector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLector.setForeground(Color.WHITE);
        panelConsulta.add(lblLector);
        
        // Campo de texto para el email
        txtEmailLector = createStyledTextField();
        txtEmailLector.setPreferredSize(new Dimension(300, 25));
        panelConsulta.add(txtEmailLector);
        
        // Informacion adicional
        JLabel lblInfo = new JLabel("Ingresa el email del lector y haz clic en 'Consultar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        panelConsulta.add(lblInfo);
        
        return panelConsulta;
    }
    
    private void crearTabla() {
        // Definir columnas de la tabla
        String[] columnas = {
            "ID Prestamo", "Fecha Solicitud", "Fecha Devolucion", "Nombre/Descripcion Material", "Estado", "ATRASADO"
        };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.setGridColor(new Color(52, 152, 219));
        tablaPrestamos.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaPrestamos.setSelectionForeground(Color.BLACK);
        tablaPrestamos.setBackground(new Color(74, 76, 81));
        tablaPrestamos.setForeground(Color.WHITE);
        
        // Configurar el ordenador de filas para permitir ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        
        // Configurar comparador personalizado para las columnas de fechas (índices 1 y 2)
        sorter.setComparator(1, new Comparator<String>() {
            @Override
            public int compare(String fecha1, String fecha2) {
                try {
                    Date d1 = formatoFecha.parse(fecha1);
                    Date d2 = formatoFecha.parse(fecha2);
                    return d1.compareTo(d2);
                } catch (Exception e) {
                    return fecha1.compareTo(fecha2);
                }
            }
        });
        
        sorter.setComparator(2, new Comparator<String>() {
            @Override
            public int compare(String fecha1, String fecha2) {
                try {
                    Date d1 = formatoFecha.parse(fecha1);
                    Date d2 = formatoFecha.parse(fecha2);
                    return d1.compareTo(d2);
                } catch (Exception e) {
                    return fecha1.compareTo(fecha2);
                }
            }
        });
        
        tablaPrestamos.setRowSorter(sorter);
        
        // Configurar anchos de columna
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID Prestamo
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(120);  // Fecha Solicitud
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(120);  // Fecha Devolucion
        tablaPrestamos.getColumnModel().getColumn(3).setPreferredWidth(300);  // Nombre/Descripcion Material
        tablaPrestamos.getColumnModel().getColumn(4).setPreferredWidth(100);  // Estado
        tablaPrestamos.getColumnModel().getColumn(5).setPreferredWidth(100);  // ATRASADO
        
        // Style table header
        JTableHeader header = tablaPrestamos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(new Color(52, 73, 94));
        header.setBackground(new Color(200, 200, 200));
    }
    
    private void consultarHistorialLector() {
        try {
            String emailLector = txtEmailLector.getText().trim();
            
            // Validacion basica
            if (emailLector.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe ingresar el email del lector", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            // Obtener prestamos del lector
            ArrayList<DtPrestamo> prestamos = controlador.ListarPrestamoLector(emailLector);
            
            if (prestamos != null && !prestamos.isEmpty()) {
                Date fechaActual = new Date();
                
                for (DtPrestamo prestamo : prestamos) {
                    // Obtener descripción del material
                    String descripcionMaterial = "";
                    if (prestamo.getMaterial() instanceof DtLibro) {
                        descripcionMaterial = ((DtLibro) prestamo.getMaterial()).getTitulo();
                    } else if (prestamo.getMaterial() instanceof DtArticulo) {
                        descripcionMaterial = ((DtArticulo) prestamo.getMaterial()).getDescripcion();
                    } else {
                        descripcionMaterial = "Material no identificado";
                    }
                    
                    // Verificar si está atrasado
                    String estadoAtrasado = "";
                    if (prestamo.getEstado().toString().equals("PENDIENTE") && 
                        fechaActual.after(prestamo.getFechaDevolucion())) {
                        estadoAtrasado = "ATRASADO";
                    }
                    
                    // Agregar fila a la tabla
                    Object[] fila = {
                        prestamo.getId(),
                        formatoFecha.format(prestamo.getFechaSolicitud()),
                        formatoFecha.format(prestamo.getFechaDevolucion()),
                        descripcionMaterial,
                        prestamo.getEstado().toString(),
                        estadoAtrasado
                    };
                    modeloTabla.addRow(fila);
                }
                
                // Mostrar mensaje de exito
                JOptionPane.showMessageDialog(this, 
                    "Se encontraron " + prestamos.size() + " prestamos para el lector: " + emailLector, 
                    "Consulta Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron prestamos para el lector: " + emailLector, 
                    "Sin Resultados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (ListarPrestamoLectorExcepcion ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al consultar prestamos: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(),
                "Error", 
                            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
        txtEmailLector.setText("");
        JOptionPane.showMessageDialog(this, 
            "Tabla limpiada", 
            "Informacion", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        Color bg = backgroundColor;
        String label = text == null ? "" : text.toLowerCase();
        if (label.contains("modificar") || label.contains("consultar")) {
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
