package com.pap.java.presentacion;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.pap.java.interfaces.IControlador;
import com.pap.java.logica.Bibliotecario;

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
        setBounds(0, 0, 400, 300);
        setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Nuevo Bibliotecario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setBounds(80, 20, 250, 25);
        add(lblTitulo);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 70, 80, 20);
        add(lblNombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 70, 200, 25);
        add(txtNombre);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 110, 80, 20);
        add(lblEmail);
        
        txtEmail = new JTextField();
        txtEmail.setBounds(120, 110, 200, 25);
        add(txtEmail);
        
        // Número de Empleado
        JLabel lblNumeroEmpleado = new JLabel("Nº Empleado:");
        lblNumeroEmpleado.setBounds(30, 150, 80, 20);
        add(lblNumeroEmpleado);
        
        txtNumeroEmpleado = new JTextField();
        txtNumeroEmpleado.setBounds(120, 150, 200, 25);
        add(txtNumeroEmpleado);
        
        // Botones
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(100, 220, 100, 30);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarBibliotecario();
            }
        });
        add(btnRegistrar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 220, 100, 30);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(btnCancelar);
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
            Bibliotecario bibliotecario = new Bibliotecario(nombre, email, numeroEmpleado);
            
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
