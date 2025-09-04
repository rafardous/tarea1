package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtPrestamo;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.excepciones.ListarPrestamoLectorExcepcion;

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
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class HistorialPrestamosLector extends JInternalFrame {
    
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
        setTitle("Historial de Prestamos por Lector");
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
        
        // Titulo con estilo moderno
        JLabel lblTitulo = new JLabel("Historial de Prestamos por Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(250, 20, 500, 30);
        contentPanel.add(lblTitulo);
        
        // Subtitulo explicativo
        JLabel lblSubtitulo = new JLabel("Consulta el historial de prestamos de un lector especifico");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setBounds(300, 50, 400, 20);
        contentPanel.add(lblSubtitulo);
        
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
        
        // Botones modernos
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setBounds(200, 570, 120, 35);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarHistorialLector();
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
    
    private void crearPanelConsulta(JPanel parent) {
        // Panel de consulta con borde
        JPanel panelConsulta = new JPanel();
        panelConsulta.setLayout(null);
        panelConsulta.setBounds(30, 90, 940, 50);
        panelConsulta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelConsulta.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelConsulta);
        
        // Label para el email del lector
        JLabel lblLector = new JLabel("Lector a consultar:");
        lblLector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLector.setForeground(new Color(52, 73, 94));
        lblLector.setBounds(20, 15, 120, 20);
        panelConsulta.add(lblLector);
        
        // Campo de texto para el email
        txtEmailLector = createStyledTextField();
        txtEmailLector.setBounds(150, 15, 300, 25);
        panelConsulta.add(txtEmailLector);
        
        // Informacion adicional
        JLabel lblInfo = new JLabel("Ingresa el email del lector y haz clic en 'Consultar'");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(470, 15, 400, 20);
        panelConsulta.add(lblInfo);
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
        button.setForeground(Color.BLACK);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        return button;
    }
}
