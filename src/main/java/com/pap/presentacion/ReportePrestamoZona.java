package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.Zona;
import com.pap.excepciones.ReportePrestamoZonaExcepcion;

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
import java.util.Comparator;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class ReportePrestamoZona extends JPanel {

    private IControlador controlador;

    private JComboBox<Zona> cmbZona;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private JButton btnConsultar;
    private JButton btnLimpiar;
    private JButton btnCerrar;

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public ReportePrestamoZona(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark theme background
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        headerPanel.setBackground(new Color(74, 76, 81));
        
        JLabel lblTitulo = new JLabel("Reporte de Prestamos por Zona");
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
        scrollPane.setPreferredSize(new Dimension(800, 300));
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
                consultarReporteZona();
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
                com.pap.presentacion.Principal.getInstance().irASubmenuPrestamos();
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

    private JPanel crearPanelConsulta() {
        JPanel panelConsulta = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelConsulta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelConsulta.setBackground(new Color(255, 255, 255, 200));

        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblZona.setForeground(new Color(52, 73, 94));
        panelConsulta.add(lblZona);

        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setPreferredSize(new Dimension(200, 25));
        cmbZona.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelConsulta.add(cmbZona);

        JLabel lblInfo = new JLabel("Seleccione la zona y haga clic en 'Consultar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        panelConsulta.add(lblInfo);

        return panelConsulta;
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

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.setGridColor(new Color(52, 152, 219));
        tablaPrestamos.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaPrestamos.setSelectionForeground(Color.BLACK);
        tablaPrestamos.setBackground(new Color(74, 76, 81));
        tablaPrestamos.setForeground(Color.WHITE);

        // Style table header
        JTableHeader header = tablaPrestamos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(new Color(52, 73, 94));
        header.setBackground(new Color(200, 200, 200));

        // Anchos de columna (3 columnas)
        tablaPrestamos.getColumnModel().getColumn(0).setPreferredWidth(120); // ID Material
        tablaPrestamos.getColumnModel().getColumn(1).setPreferredWidth(350); // Nombre/Descripcion
        tablaPrestamos.getColumnModel().getColumn(2).setPreferredWidth(180); // Cantidad
    }

    private Comparator<String> crearComparadorFechas() {
        return new Comparator<String>() {
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
        };
    }

    private void consultarReporteZona() {
        try {
            Zona zona = (Zona) cmbZona.getSelectedItem();

            if (zona == null) {
                JOptionPane.showMessageDialog(this,
                    "Debe seleccionar una zona",
                    "Error de Validacion",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            modeloTabla.setRowCount(0);

            ArrayList<DtPrestamo> prestamos = controlador.ReportePrestamoZona(zona);

            if (prestamos != null && !prestamos.isEmpty()) {
                for (DtPrestamo prestamo : prestamos) {
                    String descripcionMaterial = "";
                    if (prestamo.getMaterial() instanceof DtLibro) {
                        descripcionMaterial = ((DtLibro) prestamo.getMaterial()).getTitulo();
                    } else if (prestamo.getMaterial() instanceof DtArticulo) {
                        descripcionMaterial = ((DtArticulo) prestamo.getMaterial()).getDescripcion();
                    } else {
                        descripcionMaterial = "Material no identificado";
                    }

                    Object[] fila = {
                        prestamo.getId(),
                        descripcionMaterial,
                        "1" // Cantidad de prestamos pendientes
                    };
                    modeloTabla.addRow(fila);
                }

                JOptionPane.showMessageDialog(this,
                    "Se encontraron " + prestamos.size() + " prestamos en la zona: " + zona,
                    "Consulta Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron prestamos en la zona: " + zona,
                    "Sin Resultados",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (ReportePrestamoZonaExcepcion ex) {
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
        cmbZona.setSelectedIndex(-1);
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
