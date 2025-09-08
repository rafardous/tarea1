package com.pap.presentacion;

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
        setLayout(null);
        setBounds(0, 0, 1200, 800);
        setBackground(new Color(66, 69, 73)); // Dark mode background
        
        // Panel de fondo con gradiente
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(66, 69, 73),
                    getWidth(), getHeight(), new Color(76, 79, 83)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        contentPanel.setLayout(null);
        contentPanel.setBounds(0, 0, getWidth(), getHeight());
        add(contentPanel);
        
        // Título con estilo moderno
        JLabel lblTitulo = new JLabel("Registro de Nuevo Bibliotecario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE); // White text for dark mode
        lblTitulo.setBounds(120, 20, 350, 30);
        contentPanel.add(lblTitulo);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(30, 80, 100, 20);
        contentPanel.add(lblNombre);
        
        txtNombre = createStyledTextField();
        txtNombre.setBounds(140, 80, 250, 30);
        contentPanel.add(txtNombre);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(30, 130, 100, 20);
        contentPanel.add(lblEmail);
        
        txtEmail = createStyledTextField();
        txtEmail.setBounds(140, 130, 250, 30);
        contentPanel.add(txtEmail);
        
        // Número de Empleado
        JLabel lblNumeroEmpleado = new JLabel("Numero de Empleado:");
        lblNumeroEmpleado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNumeroEmpleado.setForeground(Color.WHITE);
        lblNumeroEmpleado.setBounds(30, 180, 100, 20);
        contentPanel.add(lblNumeroEmpleado);
        
        txtNumeroEmpleado = createStyledTextField();
        txtNumeroEmpleado.setBounds(140, 180, 250, 30);
        contentPanel.add(txtNumeroEmpleado);
        
        // Botones modernos
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setBounds(100, 250, 120, 35);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarBibliotecario();
            }
        });
        contentPanel.add(btnRegistrar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setBounds(240, 250, 120, 35);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        contentPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setBounds(380, 250, 120, 35);
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuUsuarios();
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