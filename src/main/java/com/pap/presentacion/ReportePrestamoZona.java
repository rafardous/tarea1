package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.datatypes.Zona;
import com.pap.excepciones.ReportePrestamoZonaExcepcion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

        // Título
        JLabel lblTitulo = new JLabel("Reporte de Prestamos por Zona");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(280, 20, 500, 30);
        contentPanel.add(lblTitulo);

        // Panel de consulta
        crearPanelConsulta(contentPanel);

        // Crear la tabla
        crearTabla();

        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos);
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
                consultarReporteZona();
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
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        contentPanel.add(btnCerrar);
    }

    private void crearPanelConsulta(JPanel parent) {
        JPanel panelConsulta = new JPanel();
        panelConsulta.setLayout(null);
        panelConsulta.setBounds(30, 90, 940, 50);
        panelConsulta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelConsulta.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelConsulta);

        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblZona.setForeground(Color.WHITE);
        lblZona.setBounds(20, 15, 80, 20);
        panelConsulta.add(lblZona);

        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setBounds(100, 15, 200, 25);
        panelConsulta.add(cmbZona);

        JLabel lblInfo = new JLabel("Seleccione la zona y haga clic en 'Consultar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(320, 15, 400, 20);
        panelConsulta.add(lblInfo);
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

        // Header styling: bold + greyish blue text
        java.awt.Color headerColor = new java.awt.Color(52, 73, 94);
        javax.swing.table.JTableHeader header = tablaPrestamos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setForeground(headerColor);
        javax.swing.table.TableCellRenderer baseHeaderRenderer = header.getDefaultRenderer();
        header.setDefaultRenderer((table, value, isSelected, hasFocus, row, col) -> {
            java.awt.Component c = baseHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            c.setFont(new Font("Segoe UI", Font.BOLD, 12));
            c.setForeground(headerColor);
            return c;
        });

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
                        formatoFecha.format(prestamo.getFechaSolicitud()),
                        formatoFecha.format(prestamo.getFechaDevolucion()),
                        descripcionMaterial,
                        prestamo.getEstado().toString()
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
