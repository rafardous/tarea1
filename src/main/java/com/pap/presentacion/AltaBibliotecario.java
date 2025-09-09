package com.pap.presentacion;

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

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtBibliotecario;

public class AltaBibliotecario extends JPanel {
    
    private IControlador controlador;
    
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtNumeroEmpleado;
    
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public AltaBibliotecario(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Ingrese los datos del nuevo bibliotecario");
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
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2, 0, 10, 10); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblNombre, gbc);
        
        txtNombre = createStyledTextField();
        txtNombre.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to match label
        formContainerPanel.add(txtNombre, gbc);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEmail.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblEmail, gbc);
        
        txtEmail = createStyledTextField();
        txtEmail.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtEmail, gbc);
        
        // Número de Empleado
        JLabel lblNumeroEmpleado = new JLabel("Numero de Empleado:");
        lblNumeroEmpleado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNumeroEmpleado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblNumeroEmpleado, gbc);
        
        txtNumeroEmpleado = createStyledTextField();
        txtNumeroEmpleado.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtNumeroEmpleado, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarBibliotecario();
            }
        });
        buttonPanel.add(btnRegistrar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        buttonPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuUsuarios();
            }
        });
        buttonPanel.add(btnVolver);
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 3;
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
            bg = new Color(46, 204, 113); // green
        } else if (label.contains("limpiar") || label.contains("volver")) {
            bg = new Color(52, 152, 219); // greyish blue
        } else if (label.contains("cancelar")) {
            bg = new Color(231, 76, 60); // red
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
    
    private void registrarBibliotecario() {
        try {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String numeroEmpleado = txtNumeroEmpleado.getText().trim();
            
            if (nombre.isEmpty() || email.isEmpty() || numeroEmpleado.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el bibliotecario
            DtBibliotecario bibliotecario = new DtBibliotecario(nombre, email, numeroEmpleado);
            
            // Registrar usando el controlador
            boolean resultado = controlador.registrarBibliotecario(bibliotecario);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Bibliotecario registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar el bibliotecario", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtNumeroEmpleado.setText("");
    }
}