package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.excepciones.RegistroDonacionExcepcion;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.JTableHeader;
import java.util.Comparator;

public class ConsultarDonacionesPorFecha extends JPanel {
    
    private IControlador controlador;
    
    private JSpinner spinnerFechaInicio;
    private JSpinner spinnerFechaFin;
    private JTable tablaDonaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnCerrar;
    
    // Formato para fechas
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultarDonacionesPorFecha(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Consultar Donaciones por Rango de Fechas");
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
        JLabel lblSubtitulo = new JLabel("Selecciona un rango de fechas para consultar las donaciones registradas");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(200, 200, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblSubtitulo, gbc);
        
        // Panel de selección de fechas
        crearPanelFechas(formContainerPanel, gbc);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaDonaciones);
        scrollPane.setPreferredSize(new Dimension(800, 300));
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
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setPreferredSize(new Dimension(120, 35));
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarDonacionesPorFecha();
            }
        });
        buttonPanel.add(btnConsultar);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setPreferredSize(new Dimension(120, 35));
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
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
                com.pap.presentacion.Principal.getInstance().irASubmenuMateriales();
            }
        });
        buttonPanel.add(btnVolver);
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0; // Don't expand buttons vertically
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Don't fill buttons
        gbc.insets = new Insets(15, 0, 0, 0); // Increased spacing above buttons
        formContainerPanel.add(buttonPanel, gbc);

        add(formContainerPanel, BorderLayout.CENTER);
    }
    
    private void crearPanelFechas(JPanel parent, GridBagConstraints gbc) {
        // Panel de fechas con borde
        JPanel panelFechas = new JPanel();
        panelFechas.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFechas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelFechas.setBackground(new Color(84, 86, 91));
        
        // Fecha de inicio
        JLabel lblFechaInicio = new JLabel("Fecha de Inicio:");
        lblFechaInicio.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFechaInicio.setForeground(Color.WHITE);
        panelFechas.add(lblFechaInicio);
        
        // Spinner para fecha de inicio (últimos 30 días por defecto)
        Calendar calInicio = Calendar.getInstance();
        calInicio.add(Calendar.DAY_OF_MONTH, -30);
        SpinnerDateModel modelInicio = new SpinnerDateModel(calInicio.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaInicio = new JSpinner(modelInicio);
        spinnerFechaInicio.setEditor(new JSpinner.DateEditor(spinnerFechaInicio, "dd/MM/yyyy"));
        spinnerFechaInicio.setPreferredSize(new Dimension(120, 30));
        spinnerFechaInicio.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelFechas.add(spinnerFechaInicio);
        
        // Fecha de fin
        JLabel lblFechaFin = new JLabel("Fecha de Fin:");
        lblFechaFin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFechaFin.setForeground(Color.WHITE);
        panelFechas.add(lblFechaFin);
        
        // Spinner para fecha de fin (hoy por defecto)
        Calendar calFin = Calendar.getInstance();
        SpinnerDateModel modelFin = new SpinnerDateModel(calFin.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaFin = new JSpinner(modelFin);
        spinnerFechaFin.setEditor(new JSpinner.DateEditor(spinnerFechaFin, "dd/MM/yyyy"));
        spinnerFechaFin.setPreferredSize(new Dimension(120, 30));
        spinnerFechaFin.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelFechas.add(spinnerFechaFin);
        
        // Información adicional
        JLabel lblInfo = new JLabel("Selecciona el rango de fechas y haz clic en 'Consultar' para ver las donaciones");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(200, 200, 200));
        panelFechas.add(lblInfo);
        
        // Add to parent with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // Don't expand vertically
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        parent.add(panelFechas, gbc);
    }
    
    private void crearTabla() {
        // Definir columnas de la tabla unificada
        String[] columnas = {
            "ID", "Tipo", "Titulo/Descripcion", "Detalles", "Fecha Ingreso"
        };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablaDonaciones = new JTable(modeloTabla);
        tablaDonaciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaDonaciones.setRowHeight(30);
        tablaDonaciones.setGridColor(new Color(52, 152, 219));
        tablaDonaciones.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaDonaciones.setSelectionForeground(Color.BLACK);
        tablaDonaciones.setBackground(new Color(74, 76, 81));
        tablaDonaciones.setForeground(Color.WHITE);
        
        // Style table header
        JTableHeader header = tablaDonaciones.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(new Color(52, 73, 94));
        header.setBackground(new Color(200, 200, 200));
        
        // Configurar el ordenador de filas para permitir ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        
        // Configurar comparador personalizado para la columna de fechas (índice 4)
        sorter.setComparator(4, new Comparator<String>() {
            @Override
            public int compare(String fecha1, String fecha2) {
                try {
                    Date d1 = formatoFecha.parse(fecha1);
                    Date d2 = formatoFecha.parse(fecha2);
                    return d1.compareTo(d2);
                } catch (Exception e) {
                    // Si hay error al parsear, usar comparación alfabética como fallback
                    return fecha1.compareTo(fecha2);
                }
            }
        });
        
        tablaDonaciones.setRowSorter(sorter);
        
        // Configurar anchos de columna
        tablaDonaciones.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaDonaciones.getColumnModel().getColumn(1).setPreferredWidth(100); // Tipo
        tablaDonaciones.getColumnModel().getColumn(2).setPreferredWidth(250); // Título/Descripción
        tablaDonaciones.getColumnModel().getColumn(3).setPreferredWidth(200); // Detalles
        tablaDonaciones.getColumnModel().getColumn(4).setPreferredWidth(120); // Fecha
    }
    
    private void consultarDonacionesPorFecha() {
        try {
            // Obtener fechas seleccionadas
            Date fechaInicio = (Date) spinnerFechaInicio.getValue();
            Date fechaFin = (Date) spinnerFechaFin.getValue();
            
            // Validar que la fecha de inicio sea anterior a la de fin
            if (fechaInicio.after(fechaFin)) {
                JOptionPane.showMessageDialog(this, 
                    "La fecha de inicio debe ser anterior a la fecha de fin", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Normalizar fechas para comparar solo por día (sin hora)
            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(fechaInicio);
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Date fechaInicioNormalizada = calInicio.getTime();
            
            Calendar calFin = Calendar.getInstance();
            calFin.setTime(fechaFin);
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.SECOND, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Date fechaFinNormalizada = calFin.getTime();
            
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            int totalDonaciones = 0;
            
            // Cargar libros en el rango de fechas
            ArrayList<DtLibro> libros = controlador.RegistroDonacionLibro();
            for (DtLibro libro : libros) {
                Date fechaLibro = libro.getFechaIngreso();
                if (fechaLibro.compareTo(fechaInicioNormalizada) >= 0 && fechaLibro.compareTo(fechaFinNormalizada) <= 0) {
                    Object[] fila = {
                        libro.getId(),
                        "Libro",
                        libro.getTitulo(),
                        libro.getCantidadPaginas() + " paginas",
                        formatoFecha.format(libro.getFechaIngreso())
                    };
                    modeloTabla.addRow(fila);
                    totalDonaciones++;
                }
            }
            
            // Cargar artículos en el rango de fechas
            ArrayList<DtArticulo> articulos = controlador.RegistroDonacionArticulo();
            for (DtArticulo articulo : articulos) {
                Date fechaArticulo = articulo.getFechaIngreso();
                if (fechaArticulo.compareTo(fechaInicioNormalizada) >= 0 && fechaArticulo.compareTo(fechaFinNormalizada) <= 0) {
                    Object[] fila = {
                        articulo.getId(),
                        "Articulo",
                        articulo.getDescripcion(),
                        articulo.getPesoKg() + " kg, " + articulo.getDimensiones(),
                        formatoFecha.format(articulo.getFechaIngreso())
                    };
                    modeloTabla.addRow(fila);
                    totalDonaciones++;
                }
            }
            
            // Mostrar resultado de la consulta
            if (totalDonaciones > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Se encontraron " + totalDonaciones + " donaciones entre " + 
                    formatoFecha.format(fechaInicio) + " y " + formatoFecha.format(fechaFin), 
                    "Consulta Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron donaciones en el rango de fechas seleccionado", 
                    "Sin Resultados", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (RegistroDonacionExcepcion ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al consultar donaciones: " + ex.getMessage(),
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
        JOptionPane.showMessageDialog(this, 
            "Tabla limpiada", 
            "Informacion", 
            JOptionPane.INFORMATION_MESSAGE);
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
