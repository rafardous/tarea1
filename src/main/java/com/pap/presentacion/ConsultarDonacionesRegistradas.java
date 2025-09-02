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

public class ConsultarDonacionesRegistradas extends JInternalFrame {
    
    private IControlador controlador;
    
    private JTable tablaDonaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnCerrar;
    
    // Formato para fechas
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public ConsultarDonacionesRegistradas(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarDonaciones();
    }

    private void initialize() {
        setTitle("Consultar Donaciones Registradas");
        setBounds(0, 0, 900, 600);
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
        JLabel lblTitulo = new JLabel("Donaciones Registradas en el Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(250, 20, 400, 30);
        contentPanel.add(lblTitulo);
        
        // Subtitulo explicativo
        JLabel lblSubtitulo = new JLabel("Lista completa de libros y articulos donados");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setBounds(300, 50, 300, 20);
        contentPanel.add(lblSubtitulo);
        
        // Crear la tabla
        crearTabla();
        
        // Panel con scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaDonaciones);
        scrollPane.setBounds(30, 100, 840, 400);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        contentPanel.add(scrollPane);
        
        // Botones modernos
        btnActualizar = createStyledButton("Actualizar", new Color(46, 204, 113));
        btnActualizar.setBounds(300, 520, 120, 35);
        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarDonaciones();
            }
        });
        contentPanel.add(btnActualizar);
        
        btnCerrar = createStyledButton("Cerrar", new Color(231, 76, 60));
        btnCerrar.setBounds(480, 520, 120, 35);
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        contentPanel.add(btnCerrar);
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
        tablaDonaciones.setRowSorter(sorter);
        
        // Configurar anchos de columna
        tablaDonaciones.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
        tablaDonaciones.getColumnModel().getColumn(1).setPreferredWidth(100); // Tipo
        tablaDonaciones.getColumnModel().getColumn(2).setPreferredWidth(250); // Título/Descripción
        tablaDonaciones.getColumnModel().getColumn(3).setPreferredWidth(200); // Detalles
        tablaDonaciones.getColumnModel().getColumn(4).setPreferredWidth(120); // Fecha
    }
    
    private void cargarDonaciones() {
        try {
            // Limpiar tabla
            modeloTabla.setRowCount(0);
            
            // Cargar libros
            ArrayList<DtLibro> libros = controlador.RegistroDonacionLibro();
            for (DtLibro libro : libros) {
                Object[] fila = {
                    libro.getId(),
                    "Libro",
                    libro.getTitulo(),
                    libro.getCantidadPaginas() + " paginas",
                    formatoFecha.format(libro.getFechaIngreso())
                };
                modeloTabla.addRow(fila);
            }
            
            // Cargar artículos
            ArrayList<DtArticulo> articulos = controlador.RegistroDonacionArticulo();
            for (DtArticulo articulo : articulos) {
                Object[] fila = {
                    articulo.getId(),
                    "Articulo",
                    articulo.getDescripcion(),
                    articulo.getPesoKg() + " kg, " + articulo.getDimensiones(),
                    formatoFecha.format(articulo.getFechaIngreso())
                };
                modeloTabla.addRow(fila);
            }
            
            // Mostrar mensaje de éxito solo si hay donaciones
            if (modeloTabla.getRowCount() > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Se cargaron " + modeloTabla.getRowCount() + " donaciones exitosamente", 
                    "Exito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (RegistroDonacionExcepcion ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar donaciones: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
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
