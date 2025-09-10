package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.EstadoPrestamo;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

public class ConsultarMaterialesPendientes extends JPanel {

    private IControlador controlador;
    private JTable tablaMateriales;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    public ConsultarMaterialesPendientes(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark theme background
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        headerPanel.setBackground(new Color(74, 76, 81));
        
        JLabel lblTitulo = new JLabel("Consultar Materiales Pendientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitulo);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel formContainerPanel = new JPanel(new GridBagLayout());
        formContainerPanel.setBackground(new Color(74, 76, 81));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 10, 20);
        
        // Panel de información
        JPanel panelInfo = crearPanelInformacion();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formContainerPanel.add(panelInfo, gbc);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaMateriales);
        scrollPane.setPreferredSize(new Dimension(800, 400));
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
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setPreferredSize(new Dimension(120, 35));
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarMaterialesPendientes();
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
        
        btnCerrar = createStyledButton("Volver", new Color(52, 152, 219));
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuControlSeguimiento();
            }
        });
        buttonPanel.add(btnCerrar);
        
        // Add button panel to form container
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(10, 0, 0, 0);
        formContainerPanel.add(buttonPanel, gbc);
        
        add(formContainerPanel, BorderLayout.CENTER);
    }

    private JPanel crearPanelInformacion() {
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 20, 10, 20)
        ));
        panelInfo.setBackground(new Color(255, 255, 255, 200));

        JLabel lblInfo = new JLabel("Materiales que tienen prestamos en estado PENDIENTE");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfo.setForeground(Color.WHITE);
        panelInfo.add(lblInfo);

        JLabel lblInfo2 = new JLabel("Haga clic en 'Consultar' para ver los resultados");
        lblInfo2.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblInfo2.setForeground(new Color(108, 117, 125));
        panelInfo.add(lblInfo2);
        
        return panelInfo;
    }

    private void crearTabla() {
        String[] columnas = {
            "ID Material", "Nombre/Descripcion", "Cantidad Prestamos Pendientes"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMateriales = new JTable(modeloTabla) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Estilo moderno para la tabla
        tablaMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaMateriales.setRowHeight(25);
        tablaMateriales.setGridColor(new Color(52, 152, 219));
        tablaMateriales.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaMateriales.setSelectionForeground(Color.BLACK);
        tablaMateriales.setBackground(new Color(74, 76, 81));
        tablaMateriales.setForeground(Color.WHITE);
        tablaMateriales.setFillsViewportHeight(true);
        tablaMateriales.setShowGrid(true);
        
        // Style table header
        JTableHeader header = tablaMateriales.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(new Color(52, 73, 94));
        header.setBackground(new Color(200, 200, 200));

        // Ordenamiento por cantidad de prestamos pendientes (columna 2)
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        sorter.setComparator(2, (o1, o2) -> {
            try {
                Integer cant1 = Integer.parseInt(o1.toString());
                Integer cant2 = Integer.parseInt(o2.toString());
                return cant2.compareTo(cant1); // Orden descendente (mayor cantidad primero)
            } catch (NumberFormatException e) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        tablaMateriales.setRowSorter(sorter);

        // Anchos de columna optimizados
        tablaMateriales.getColumnModel().getColumn(0).setPreferredWidth(120);
        tablaMateriales.getColumnModel().getColumn(1).setPreferredWidth(500);
        tablaMateriales.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    private void consultarMaterialesPendientes() {
        try {
            modeloTabla.setRowCount(0);

            // Obtener todos los prestamos
            ArrayList<DtPrestamo> todosLosPrestamos = controlador.listarTodosLosPrestamos();

            if (todosLosPrestamos == null || todosLosPrestamos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron prestamos en el sistema",
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Filtrar solo los prestamos pendientes y contar por material
            Map<String, MaterialInfo> materialesPendientes = new HashMap<>();

            for (DtPrestamo prestamo : todosLosPrestamos) {
                if (prestamo.getEstado() == EstadoPrestamo.PENDIENTE) {
                    String idMaterial = prestamo.getMaterial().getId();
                    String nombreDescripcion = obtenerNombreDescripcion(prestamo.getMaterial());
                    
                    if (materialesPendientes.containsKey(idMaterial)) {
                        materialesPendientes.get(idMaterial).incrementarCantidad();
                    } else {
                        materialesPendientes.put(idMaterial, new MaterialInfo(idMaterial, nombreDescripcion));
                    }
                }
            }

            // Agregar resultados a la tabla
            if (!materialesPendientes.isEmpty()) {
                for (MaterialInfo material : materialesPendientes.values()) {
                    Object[] fila = {
                        material.getIdMaterial(),
                        material.getNombreDescripcion(),
                        material.getCantidadPrestamos()
                    };
                    modeloTabla.addRow(fila);
                }

                JOptionPane.showMessageDialog(this,
                    "Se encontraron " + materialesPendientes.size() + " materiales con prestamos pendientes",
                    "Consulta Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron materiales con prestamos pendientes",
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error inesperado: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerNombreDescripcion(com.pap.datatypes.DtMaterial material) {
        if (material instanceof DtLibro) {
            return ((DtLibro) material).getTitulo();
        } else if (material instanceof DtArticulo) {
            return ((DtArticulo) material).getDescripcion();
        } else {
            return "Material no identificado";
        }
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
        JOptionPane.showMessageDialog(this,
            "Tabla limpiada",
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Texto mas grande
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
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    // Clase auxiliar para manejar la información de materiales
    private static class MaterialInfo {
        private String idMaterial;
        private String nombreDescripcion;
        private int cantidadPrestamos;

        public MaterialInfo(String idMaterial, String nombreDescripcion) {
            this.idMaterial = idMaterial;
            this.nombreDescripcion = nombreDescripcion;
            this.cantidadPrestamos = 1;
        }

        public void incrementarCantidad() {
            this.cantidadPrestamos++;
        }

        public String getIdMaterial() {
            return idMaterial;
        }

        public String getNombreDescripcion() {
            return nombreDescripcion;
        }

        public int getCantidadPrestamos() {
            return cantidadPrestamos;
        }
    }
}
