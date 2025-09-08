package com.pap.presentacion;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



public class AltaLector extends JPanel {
    
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
        
        // Título con estilo moderno - Centered at top
        JLabel lblTitulo = new JLabel("Registro de Nuevo Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 30, getWidth(), 40);
        contentPanel.add(lblTitulo);
        
        // Create main form panel - centered
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setOpaque(false);
        formPanel.setBounds((getWidth() - 400) / 2, 100, 400, 300);
        contentPanel.add(formPanel);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(20, 20, 100, 25);
        formPanel.add(lblNombre);
        
        txtNombre = createStyledTextField();
        txtNombre.setBounds(130, 20, 250, 30);
        formPanel.add(txtNombre);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(20, 70, 100, 25);
        formPanel.add(lblEmail);
        
        txtEmail = createStyledTextField();
        txtEmail.setBounds(130, 70, 250, 30);
        formPanel.add(txtEmail);
        
        // Dirección
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDireccion.setForeground(Color.WHITE);
        lblDireccion.setBounds(20, 120, 100, 25);
        formPanel.add(lblDireccion);
        
        txtDireccion = createStyledTextField();
        txtDireccion.setBounds(130, 120, 250, 30);
        formPanel.add(txtDireccion);
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setBounds(20, 170, 100, 25);
        formPanel.add(lblEstado);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstado.setBounds(130, 170, 250, 30);
        cmbEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(cmbEstado);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblZona.setForeground(Color.WHITE);
        lblZona.setBounds(20, 220, 100, 25);
        formPanel.add(lblZona);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbZona.setBounds(130, 220, 250, 30);
        cmbZona.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(cmbZona);
        
        // Botones - Centered at bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 450, getWidth(), 50);
        contentPanel.add(buttonPanel);
        
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarLector();
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
        // Determine background color by label (overrides passed color for consistency)
        Color bg = backgroundColor;
        String label = text == null ? "" : text.toLowerCase();
        if (label.contains("modificar") || label.contains("consultar")) {
            bg = new Color(46, 204, 113); // green
        } else if (label.contains("limpiar") || label.contains("volver")) {
            bg = new Color(52, 152, 219); // greyish blue
        } else if (label.contains("cancelar")) {
            bg = new Color(231, 76, 60); // red
        } else if (bg == null) {
            bg = new Color(46, 49, 54); // fallback dark grey
        }
        final Color finalBg = bg;
        button.setBackground(finalBg);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        // Visible outline border
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(finalBg.brighter(), 2, true),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
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
