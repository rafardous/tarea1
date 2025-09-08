package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.excepciones.RegistrarDonacionArticuloExcepcion;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegistrarDonacionArticulo extends JPanel {
    
    private IControlador controlador;
    
    private JTextField txtIdArticulo;
    private JTextField txtDescripcion;
    private JTextField txtPesoKg;
    private JTextField txtDimensiones;
    
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistrarDonacionArticulo(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(null);
        setBounds(0, 0, 1200, 800);
        setBackground(new Color(74, 76, 81)); // Slightly lighter grey
        
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
        
        // Titulo con estilo moderno
        JLabel lblTitulo = new JLabel("Registro de Donacion de Articulo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(100, 20, 300, 30);
        contentPanel.add(lblTitulo);
        
        // ID del Articulo
        JLabel lblIdArticulo = new JLabel("ID del Articulo:");
        lblIdArticulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblIdArticulo.setForeground(Color.WHITE);
        lblIdArticulo.setBounds(30, 80, 100, 20);
        contentPanel.add(lblIdArticulo);
        
        txtIdArticulo = createStyledTextField();
        txtIdArticulo.setBounds(140, 80, 250, 30);
        contentPanel.add(txtIdArticulo);
        
        // Descripcion del Articulo
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setBounds(30, 130, 100, 20);
        contentPanel.add(lblDescripcion);
        
        txtDescripcion = createStyledTextField();
        txtDescripcion.setBounds(140, 130, 250, 30);
        contentPanel.add(txtDescripcion);
        
        // Peso en Kg
        JLabel lblPesoKg = new JLabel("Peso (Kg):");
        lblPesoKg.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPesoKg.setForeground(Color.WHITE);
        lblPesoKg.setBounds(30, 180, 100, 20);
        contentPanel.add(lblPesoKg);
        
        txtPesoKg = createStyledTextField();
        txtPesoKg.setBounds(140, 180, 250, 30);
        contentPanel.add(txtPesoKg);
        
        // Dimensiones
        JLabel lblDimensiones = new JLabel("Dimensiones:");
        lblDimensiones.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDimensiones.setForeground(Color.WHITE);
        lblDimensiones.setBounds(30, 230, 100, 20);
        contentPanel.add(lblDimensiones);
        
        txtDimensiones = createStyledTextField();
        txtDimensiones.setBounds(140, 230, 250, 30);
        contentPanel.add(txtDimensiones);
        
        // Botones modernos
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setBounds(100, 300, 120, 35);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarDonacionArticulo();
            }
        });
        contentPanel.add(btnRegistrar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setBounds(240, 300, 120, 35);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        contentPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setBounds(380, 300, 120, 35);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuMateriales();
            }
        });
        contentPanel.add(btnVolver);
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
    
    private void registrarDonacionArticulo() {
        try {
            String idArticulo = txtIdArticulo.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String pesoKgStr = txtPesoKg.getText().trim();
            String dimensiones = txtDimensiones.getText().trim();
            
            // Validacion basica
            if (idArticulo.isEmpty() || descripcion.isEmpty() || pesoKgStr.isEmpty() || dimensiones.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que el peso sea un numero valido
            float pesoKg;
            try {
                pesoKg = Float.parseFloat(pesoKgStr);
                if (pesoKg <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "El peso debe ser un numero mayor a 0", 
                        "Error de Validacion", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "El peso debe ser un numero valido", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Registrar la donacion del articulo
            controlador.RegistrarDonacionArticulo(idArticulo, descripcion, pesoKg, dimensiones);
            
            JOptionPane.showMessageDialog(this, 
                "Articulo registrado exitosamente", 
                "Exito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            setVisible(false);
            
        } catch (RegistrarDonacionArticuloExcepcion ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(),
                "Error de Registro", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtIdArticulo.setText("");
        txtDescripcion.setText("");
        txtPesoKg.setText("");
        txtDimensiones.setText("");
    }
}
