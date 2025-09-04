package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtLibro;
import com.pap.datatypes.DtArticulo;
import com.pap.excepciones.RegistroDonacionExcepcion;

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
import javax.swing.JInternalFrame;
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
import java.util.Comparator;

public class ConsultarDonacionesPorFecha extends JInternalFrame {
    
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
        setTitle("Consultar Donaciones por Fecha");
        setBounds(0, 0, 900, 650);
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
        JLabel lblTitulo = new JLabel("Consultar Donaciones por Rango de Fechas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(200, 20, 500, 30);
        contentPanel.add(lblTitulo);
        
        // Subtitulo explicativo
        JLabel lblSubtitulo = new JLabel("Selecciona un rango de fechas para consultar las donaciones registradas");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setBounds(250, 50, 400, 20);
        contentPanel.add(lblSubtitulo);
        
        // Panel de selección de fechas
        crearPanelFechas(contentPanel);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaDonaciones);
        scrollPane.setBounds(30, 180, 840, 350);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        contentPanel.add(scrollPane);
        
        // Botones modernos
        btnConsultar = createStyledButton("Consultar", new Color(46, 204, 113));
        btnConsultar.setBounds(200, 550, 120, 35);
        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consultarDonacionesPorFecha();
            }
        });
        contentPanel.add(btnConsultar);
        
        btnLimpiar = createStyledButton("Limpiar", new Color(52, 152, 219));
        btnLimpiar.setBounds(350, 550, 120, 35);
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
            }
        });
        contentPanel.add(btnLimpiar);
        
        btnCerrar = createStyledButton("Cerrar", new Color(231, 76, 60));
        btnCerrar.setBounds(500, 550, 120, 35);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPanel.add(btnCerrar);
    }
    
    private void crearPanelFechas(JPanel parent) {
        // Panel de fechas con borde
        JPanel panelFechas = new JPanel();
        panelFechas.setLayout(null);
        panelFechas.setBounds(30, 90, 840, 70);
        panelFechas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        panelFechas.setBackground(new Color(255, 255, 255, 200));
        parent.add(panelFechas);
        
        // Fecha de inicio
        JLabel lblFechaInicio = new JLabel("Fecha de Inicio:");
        lblFechaInicio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaInicio.setForeground(new Color(52, 73, 94));
        lblFechaInicio.setBounds(20, 15, 100, 20);
        panelFechas.add(lblFechaInicio);
        
        // Spinner para fecha de inicio (últimos 30 días por defecto)
        Calendar calInicio = Calendar.getInstance();
        calInicio.add(Calendar.DAY_OF_MONTH, -30);
        SpinnerDateModel modelInicio = new SpinnerDateModel(calInicio.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaInicio = new JSpinner(modelInicio);
        spinnerFechaInicio.setEditor(new JSpinner.DateEditor(spinnerFechaInicio, "dd/MM/yyyy"));
        spinnerFechaInicio.setBounds(130, 15, 120, 25);
        panelFechas.add(spinnerFechaInicio);
        
        // Fecha de fin
        JLabel lblFechaFin = new JLabel("Fecha de Fin:");
        lblFechaFin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaFin.setForeground(new Color(52, 73, 94));
        lblFechaFin.setBounds(280, 15, 100, 20);
        panelFechas.add(lblFechaFin);
        
        // Spinner para fecha de fin (hoy por defecto)
        Calendar calFin = Calendar.getInstance();
        SpinnerDateModel modelFin = new SpinnerDateModel(calFin.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaFin = new JSpinner(modelFin);
        spinnerFechaFin.setEditor(new JSpinner.DateEditor(spinnerFechaFin, "dd/MM/yyyy"));
        spinnerFechaFin.setBounds(390, 15, 120, 25);
        panelFechas.add(spinnerFechaFin);
        
        // Información adicional
        JLabel lblInfo = new JLabel("Selecciona el rango de fechas y haz clic en 'Consultar' para ver las donaciones");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(108, 117, 125));
        lblInfo.setBounds(20, 40, 400, 20);
        panelFechas.add(lblInfo);
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
        tablaDonaciones.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaDonaciones.setRowHeight(25);
        tablaDonaciones.setGridColor(new Color(52, 152, 219));
        tablaDonaciones.setSelectionBackground(new Color(52, 152, 219, 100));
        tablaDonaciones.setSelectionForeground(Color.BLACK);
        
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
        button.setForeground(Color.BLACK);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        return button;
    }
}
