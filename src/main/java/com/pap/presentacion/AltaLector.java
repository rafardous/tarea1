package com.pap.presentacion;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class AltaLector extends JInternalFrame {
    
    private IControlador controlador;
    
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JComboBox<EstadoLector> cmbEstado;
    private JComboBox<Zona> cmbZona;
    
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public AltaLector(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setTitle("Registrar Nuevo Lector");
        setBounds(0, 0, 450, 400);
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
        
        // Título con estilo moderno
        JLabel lblTitulo = new JLabel("Registro de Nuevo Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(120, 20, 250, 30);
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
        
        // Dirección
        JLabel lblDireccion = new JLabel("Direccio'n:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDireccion.setForeground(new Color(52, 73, 94));
        lblDireccion.setBounds(30, 180, 100, 20);
        contentPanel.add(lblDireccion);
        
        txtDireccion = createStyledTextField();
        txtDireccion.setBounds(140, 180, 250, 30);
        contentPanel.add(txtDireccion);
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(new Color(52, 73, 94));
        lblEstado.setBounds(30, 230, 100, 20);
        contentPanel.add(lblEstado);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstado.setBounds(140, 230, 250, 30);
        cmbEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        contentPanel.add(cmbEstado);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblZona.setForeground(new Color(52, 73, 94));
        lblZona.setBounds(30, 280, 100, 20);
        contentPanel.add(lblZona);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbZona.setBounds(140, 280, 250, 30);
        cmbZona.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        contentPanel.add(cmbZona);
        
        // Botones modernos
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setBounds(100, 330, 120, 35);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarLector();
            }
        });
        contentPanel.add(btnRegistrar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setBounds(240, 330, 120, 35);
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
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
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
    
    private void registrarLector() {
        try {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String direccion = txtDireccion.getText().trim();
            
            // Validación básica
            if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar de enums
            EstadoLector estado = (EstadoLector) cmbEstado.getSelectedItem();
            Zona zona = (Zona) cmbZona.getSelectedItem();
            
            if (estado == null || zona == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un estado y una zona", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el dtlector
            DtLector dtlector = new DtLector(
                nombre, email, direccion, new Date(),
                estado, zona
            );
            
            // Registrar
            boolean resultado = controlador.registrarLector(dtlector);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Lector registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                setVisible(false);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        cmbEstado.setSelectedIndex(0);
        cmbZona.setSelectedIndex(0);
    }
}
