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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pap.interfaces.IControlador;
import com.pap.datatypes.DtBibliotecario;

public class AltaBibliotecario extends JInternalFrame {
    
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
        setTitle("Registrar Nuevo Bibliotecario");
        setBounds(0, 0, 450, 350);
        setLayout(null);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
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
        
        // Título con estilo moderno
        JLabel lblTitulo = new JLabel("Registro de Nuevo Bibliotecario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(100, 20, 300, 30);
        contentPanel.add(lblTitulo);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombre.setForeground(new Color(52, 73, 94));
        lblNombre.setBounds(30, 80, 100, 20);
        contentPanel.add(lblNombre);
        
        txtNombre = createStyledTextField();
        txtNombre.setBounds(140, 80, 250, 30);
        contentPanel.add(txtNombre);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(new Color(52, 73, 94));
        lblEmail.setBounds(30, 130, 100, 20);
        contentPanel.add(lblEmail);
        
        txtEmail = createStyledTextField();
        txtEmail.setBounds(140, 130, 250, 30);
        contentPanel.add(txtEmail);
        
        // Número de Empleado
        JLabel lblNumeroEmpleado = new JLabel("Numero de Empleado:");
        lblNumeroEmpleado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNumeroEmpleado.setForeground(new Color(52, 73, 94));
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
                setVisible(false);
            }
        });
        contentPanel.add(btnCancelar);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182)),
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