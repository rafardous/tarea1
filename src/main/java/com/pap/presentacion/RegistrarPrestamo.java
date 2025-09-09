package com.pap.presentacion;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoPrestamo;

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
import java.util.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;



public class RegistrarPrestamo extends JPanel {
    
    private IControlador controlador;
    
    private JTextField txtEmailLector;
    private JTextField txtEmailBibliotecario;
    private JTextField txtIdMaterial;
    private JSpinner spinnerFechaDev;
    private JComboBox<EstadoPrestamo> cmbEstadoPrestamo;
    
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistrarPrestamo(IControlador controlador) {
        this.controlador = controlador;
        initialize();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Registro de Nuevo Prestamo");
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
        
        // Email del lector
        JLabel lblLector = new JLabel("Email Lector:");
        lblLector.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLector.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2, 0, 10, 10); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblLector, gbc);
        
        txtEmailLector = createStyledTextField();
        txtEmailLector.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to match label
        formContainerPanel.add(txtEmailLector, gbc);
        
        // Email del bibliotecario
        JLabel lblBibliotecario = new JLabel("Email Bibliotecario:");
        lblBibliotecario.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBibliotecario.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblBibliotecario, gbc);
        
        txtEmailBibliotecario = createStyledTextField();
        txtEmailBibliotecario.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtEmailBibliotecario, gbc);
        
        // ID del material
        JLabel lblMaterial = new JLabel("ID Material:");
        lblMaterial.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMaterial.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblMaterial, gbc);
        
        txtIdMaterial = createStyledTextField();
        txtIdMaterial.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtIdMaterial, gbc);
        
        // Fecha de devolución
        JLabel lblFechaDev = new JLabel("Fecha Devolucion:");
        lblFechaDev.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFechaDev.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblFechaDev, gbc);
        
        Calendar calFin = Calendar.getInstance();
        calFin.add(Calendar.DAY_OF_MONTH, 7); // Por defecto 7 días
        SpinnerDateModel modelFin = new SpinnerDateModel(calFin.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaDev = new JSpinner(modelFin);
        spinnerFechaDev.setEditor(new JSpinner.DateEditor(spinnerFechaDev, "dd/MM/yyyy"));
        spinnerFechaDev.setPreferredSize(new Dimension(200, 30));
        spinnerFechaDev.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(spinnerFechaDev, gbc);
        
        // Estado del préstamo
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblEstado, gbc);
        
        cmbEstadoPrestamo = new JComboBox<>(EstadoPrestamo.values());
        cmbEstadoPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbEstadoPrestamo.setPreferredSize(new Dimension(200, 30));
        cmbEstadoPrestamo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(cmbEstadoPrestamo, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setPreferredSize(new Dimension(120, 35));
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarPrestamo();
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
                com.pap.presentacion.Principal.getInstance().irASubmenuPrestamos();
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
        button.setForeground(Color.WHITE); // White text for dark theme
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
    
    private void registrarPrestamo() {
        try {
            String emailLector = txtEmailLector.getText().trim();
            String emailBibliotecario = txtEmailBibliotecario.getText().trim();
            String idMaterial = txtIdMaterial.getText().trim();
            
            // Validación básica
            if (emailLector.isEmpty() || emailBibliotecario.isEmpty() || idMaterial.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener fecha de devolución del spinner
            Date fechaDevolucion = (Date) spinnerFechaDev.getValue();
            
            // Obtener estado seleccionado
            EstadoPrestamo estado = (EstadoPrestamo) cmbEstadoPrestamo.getSelectedItem();
            
            if (estado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un estado", 
                    "Error de Validacion", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }


            boolean resultado = controlador.RegistrarPrestamo(
                new Date(), 
                fechaDevolucion,
                estado, 
                emailLector,
                emailBibliotecario,
                idMaterial
            );
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Prestamo registrado exitosamente", 
                    "Exito", 
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
        txtEmailLector.setText("");
        txtEmailBibliotecario.setText("");
        txtIdMaterial.setText("");
        cmbEstadoPrestamo.setSelectedIndex(0);
        // Resetear fecha de devolución a 7 días desde hoy
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7);
        spinnerFechaDev.setValue(cal.getTime());
    }
}