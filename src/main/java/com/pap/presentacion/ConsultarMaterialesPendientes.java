package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.EstadoPrestamo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ConsultarMaterialesPendientes extends JInternalFrame {

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
        setTitle("Consultar Prestamos pendientes por Material");
        setBounds(0, 0, 1000, 650);
        setLayout(null);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Panel de fondo con gradiente
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(248, 249, 250),
                    getWidth(), getHeight(), new Color(233, 236, 239)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };
        contentPanel.setLayout(null);
        contentPanel.setBounds(0, 0, getWidth(), getHeight());
        add(contentPanel);

        // Título
        JLabel lblTitulo = new JLabel("Materiales con Prestamos Pendientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(280, 20, 500, 30);
        contentPanel.add(lblTitulo);

        // Panel de información
        crearPanelInformacion(contentPanel);

        // Crear la tabla
        crearTabla();

        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaMateriales);
        scrollPane.setBounds(30, 150, 940, 400);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        contentPanel.add(scrollPane);

        // Botones
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setBounds(200, 570, 120, 35);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarMaterialesPendientes();
            }
        });
        contentPanel.add(btnConsultar);

        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setBounds(350, 570, 120, 35);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
            }
        });
        contentPanel.add(btnLimpiar);

        btnCerrar = createStyledButton("Cerrar", new Color(231, 76, 60));
        btnCerrar.setBounds(500, 570, 120, 35);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPanel.add(btnCerrar);
    }

    private void crearPanelInformacion(JPanel parent) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);
        panelInfo.setBounds(30, 90, 940, 50);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelInfo.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelInfo);

        JLabel lblInfo = new JLabel("Esta consulta muestra los materiales que tienen prestamos en estado PENDIENTE");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblInfo.setForeground(new Color(52, 73, 94));
        lblInfo.setBounds(20, 15, 600, 20);
        panelInfo.add(lblInfo);

        JLabel lblInfo2 = new JLabel("Haga clic en 'Consultar' para ver los resultados");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(20, 30, 400, 20);
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

        tablaMateriales = new JTable(modeloTabla);
        tablaMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaMateriales.setRowHeight(25);
        tablaMateriales.setGridColor(new Color(52, 152, 219));
        tablaMateriales.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaMateriales.setSelectionForeground(Color.BLACK);

        // Ordenamiento por cantidad de préstamos pendientes (columna 2)
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

        // Anchos de columna
        tablaMateriales.getColumnModel().getColumn(0).setPreferredWidth(120);
        tablaMateriales.getColumnModel().getColumn(1).setPreferredWidth(500);
        tablaMateriales.getColumnModel().getColumn(2).setPreferredWidth(200);
    }

    private void consultarMaterialesPendientes() {
        try {
            modeloTabla.setRowCount(0);

            // Obtener todos los préstamos
            ArrayList<DtPrestamo> todosLosPrestamos = controlador.listarTodosLosPrestamos();

            if (todosLosPrestamos == null || todosLosPrestamos.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron prestamos en el sistema",
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Filtrar solo los préstamos pendientes y contar por material
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.BLACK);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
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
