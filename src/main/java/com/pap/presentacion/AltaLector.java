package com.pap.presentacion;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BoxLayout;
import javax.swing.Box;
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
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Ingrese los datos del lector");
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
        gbc.insets = new Insets(10, 0, 10, 0); // Doubled vertical spacing: 10px top, 10px bottom
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
        
        // Dirección
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDireccion.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblDireccion, gbc);
        
        txtDireccion = createStyledTextField();
        txtDireccion.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtDireccion, gbc);
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblEstado, gbc);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbEstado.setPreferredSize(new Dimension(200, 30));
        cmbEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(cmbEstado, gbc);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblZona.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblZona, gbc);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbZona.setPreferredSize(new Dimension(200, 30));
        cmbZona.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(cmbZona, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
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
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 0, 0); // Increased spacing above buttons
        formContainerPanel.add(buttonPanel, gbc);

        add(formContainerPanel, BorderLayout.CENTER);
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
