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
import com.pap.datatypes.DtBibliotecario;

public class ListaDeBibliotecarios extends JFrame {
    
    private IControlador controlador;
    private JTable tablaBibliotecarios;
    
    public ListaDeBibliotecarios(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarBibliotecarios();
    }
    
    private void initialize() {
        setTitle("Lista de Bibliotecarios");
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
        String[] columnas = {"Nombre", "Email", "Numero de Empleado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaBibliotecarios = new JTable(modeloTabla);
        tablaBibliotecarios.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaBibliotecarios.setRowHeight(25);
        tablaBibliotecarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaBibliotecarios.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaBibliotecarios.getTableHeader().setForeground(Color.BLACK);
        tablaBibliotecarios.setGridColor(new Color(52, 152, 219));
        tablaBibliotecarios.setSelectionBackground(new Color(70, 130, 180, 150));
        tablaBibliotecarios.setSelectionForeground(Color.BLACK);
        
        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaBibliotecarios);
        scrollPane.setBounds(20, 20, getWidth() - 60, getHeight() - 60);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219)));
        contentPanel.add(scrollPane);
    }
    
    private void cargarBibliotecarios() {
        try {
            // Obtener lista de bibliotecarios desde el controlador
            List<DtBibliotecario> bibliotecarios = controlador.listaBibliotecarios();
            
            // Ordenar por nombre
            bibliotecarios = bibliotecarios.stream()
                .sorted((l1, l2) -> l1.getNombre().compareToIgnoreCase(l2.getNombre()))
                .collect(Collectors.toList());
            
            // Limpiar tabla
            DefaultTableModel model = (DefaultTableModel) tablaBibliotecarios.getModel();
            model.setRowCount(0);
            
            // Agregar datos a la tabla
            for (DtBibliotecario bibliotecario : bibliotecarios) {
                Object[] fila = {
                    bibliotecario.getNombre(),
                    bibliotecario.getEmail(),
                    bibliotecario.getNumeroEmpleado()
                };
                model.addRow(fila);
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar bibliotecarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
