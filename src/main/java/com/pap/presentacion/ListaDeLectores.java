package com.pap.presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtLector;

public class ListaDeLectores extends JFrame {
    
    private IControlador controlador;
    private JTable tablaLectores;
    
    public ListaDeLectores(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarLectores();
    }
    
    private void initialize() {
        setTitle("Lista de Lectores");
        setBounds(0, 0, 800, 600);
        setLayout(null);
        
        
        // Panel de fondo con gradiente
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
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
        
        // Crear tabla
        String[] columnas = {"Nombre", "Email", "Direccion", "Estado", "Zona", "Fecha de Registro"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        
        tablaLectores = new JTable(modeloTabla);
        tablaLectores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaLectores.setRowHeight(25);
        tablaLectores.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaLectores.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaLectores.getTableHeader().setForeground(Color.BLACK);
        tablaLectores.setGridColor(new Color(52, 152, 219));
        tablaLectores.setSelectionBackground(new Color(70, 130, 180, 150));
        tablaLectores.setSelectionForeground(Color.BLACK);
        
        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaLectores);
        scrollPane.setBounds(20, 20, getWidth() - 60, getHeight() - 60);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219)));
        contentPanel.add(scrollPane);
    }
    
    private void cargarLectores() {
        try {
            // Obtener lista de lectores desde el controlador
            List<DtLector> lectores = controlador.listarLectores();
            
            // Ordenar por nombre
            lectores = lectores.stream()
                .sorted((l1, l2) -> l1.getNombre().compareToIgnoreCase(l2.getNombre()))
                .collect(Collectors.toList());
            
            // Limpiar tabla
            DefaultTableModel model = (DefaultTableModel) tablaLectores.getModel();
            model.setRowCount(0);
            
            // Agregar datos a la tabla
            for (DtLector lector : lectores) {
                Object[] fila = {
                    lector.getNombre(),
                    lector.getEmail(),
                    lector.getDireccion(),
                    lector.getEstado().toString(),
                    lector.getZona().toString(),
                    lector.getFechaRegistro().toString()
                };
                model.addRow(fila);
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar lectores: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
