package com.pap.presentacion;

import com.pap.interfaces.IControlador;
import com.pap.excepciones.RegistrarDonacionArticuloExcepcion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Registro de Donacion de Articulo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        add(headerPanel, BorderLayout.NORTH);
        
        // Form Container Panel using GridBagLayout for precise control
        JPanel formContainerPanel = new JPanel();
        formContainerPanel.setLayout(new GridBagLayout());
        formContainerPanel.setOpaque(false);
        formContainerPanel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Reduced top margin to bring form closer to title
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Vertical spacing: 10px top, 10px bottom
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ID del Articulo
        JLabel lblIdArticulo = new JLabel("ID del Articulo:");
        lblIdArticulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIdArticulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2, 0, 10, 10); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblIdArticulo, gbc);
        
        txtIdArticulo = createStyledTextField();
        txtIdArticulo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to match label
        formContainerPanel.add(txtIdArticulo, gbc);
        
        // Descripcion del Articulo
        JLabel lblDescripcion = new JLabel("Descripcion:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDescripcion.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblDescripcion, gbc);
        
        txtDescripcion = createStyledTextField();
        txtDescripcion.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtDescripcion, gbc);
        
        // Peso en Kg
        JLabel lblPesoKg = new JLabel("Peso (Kg):");
        lblPesoKg.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPesoKg.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblPesoKg, gbc);
        
        txtPesoKg = createStyledTextField();
        txtPesoKg.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtPesoKg, gbc);
        
        // Dimensiones
        JLabel lblDimensiones = new JLabel("Dimensiones:");
        lblDimensiones.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDimensiones.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblDimensiones, gbc);
        
        txtDimensiones = createStyledTextField();
        txtDimensiones.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtDimensiones, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarDonacionArticulo();
            }
        });
        buttonPanel.add(btnRegistrar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        buttonPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuMateriales();
            }
        });
        buttonPanel.add(btnVolver);
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 0, 0); // Increased spacing above buttons
        formContainerPanel.add(buttonPanel, gbc);

        add(formContainerPanel, BorderLayout.CENTER);
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
