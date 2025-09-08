package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.EstadoPrestamo;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        setLayout(null);
        setBackground(new Color(66, 69, 73)); // Dark mode background
        setBounds(0, 0, 1200, 800); // Tamaño fijo para que sea visible

        // Crear contenido directamente en este panel

        // Panel de información
        crearPanelInformacion();

        // Crear la tabla
        crearTabla();

        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaMateriales);
        scrollPane.setBounds(50, 120, getWidth() - 100, getHeight() - 220);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane);

        // Botones con diseño moderno
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setBounds(50, getHeight() - 80, 150, 50);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarMaterialesPendientes();
            }
        });
        add(btnConsultar);

        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setBounds(220, getHeight() - 80, 150, 50);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
            }
        });
        add(btnLimpiar);

        btnCerrar = createStyledButton("Volver", new Color(52, 152, 219));
        btnCerrar.setBounds(390, getHeight() - 80, 150, 50);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuControlSeguimiento();
            }
        });
        add(btnCerrar);
    }

    private void crearPanelInformacion() {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);
        panelInfo.setBounds(50, 20, getWidth() - 100, 80);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        panelInfo.setBackground(new Color(76, 79, 83)); // Dark mode
        add(panelInfo);

        JLabel lblInfo = new JLabel("Esta consulta muestra los materiales que tienen prestamos en estado PENDIENTE");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Texto mas grande
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setBounds(20, 10, panelInfo.getWidth() - 40, 25);
        panelInfo.add(lblInfo);

        JLabel lblInfo2 = new JLabel("Haga clic en 'Consultar' para ver los resultados");
        lblInfo2.setFont(new Font("Segoe UI", Font.ITALIC, 14)); // Texto mas grande
        lblInfo2.setForeground(new Color(200, 200, 200)); // Light gray for dark mode
        lblInfo2.setBounds(20, 35, panelInfo.getWidth() - 40, 25);
        panelInfo.add(lblInfo2);
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
        tablaMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaMateriales.setRowHeight(40); // Filas más altas
        tablaMateriales.setGridColor(new Color(230, 230, 230));
        tablaMateriales.setSelectionBackground(new Color(52, 152, 219));
        tablaMateriales.setSelectionForeground(Color.WHITE);
        tablaMateriales.setBackground(new Color(250, 250, 250));
        tablaMateriales.setFillsViewportHeight(true);
        tablaMateriales.setShowGrid(true);
        tablaMateriales.setIntercellSpacing(new java.awt.Dimension(0, 0));
        
        // Personalizar header de la tabla: negrita + color gris azulado oscuro
        java.awt.Color headerColor = new java.awt.Color(52, 73, 94);
        javax.swing.table.JTableHeader header = tablaMateriales.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setForeground(headerColor);
        javax.swing.table.TableCellRenderer baseHeaderRenderer = header.getDefaultRenderer();
        header.setDefaultRenderer((table, value, isSelected, hasFocus, row, col) -> {
            java.awt.Component c = baseHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            c.setFont(new Font("Segoe UI", Font.BOLD, 14));
            c.setForeground(headerColor);
            return c;
        });
        tablaMateriales.getTableHeader().setForeground(Color.WHITE);
        tablaMateriales.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));

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
