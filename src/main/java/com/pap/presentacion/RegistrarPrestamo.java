package com.pap.presentacion;
import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;
import com.pap.datatypes.EstadoPrestamo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;



public class RegistrarPrestamo extends JInternalFrame {
    
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
        setTitle("Registrar Nuevo Prestamo");
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
        JLabel lblTitulo = new JLabel("Registro de Nuevo Prestamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setBounds(120, 20, 250, 30);
        contentPanel.add(lblTitulo);
        
        // Email del lector
        JLabel lblLector = new JLabel("Email Lector:");
        lblLector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblLector.setForeground(new Color(52, 73, 94));
        lblLector.setBounds(30, 80, 100, 20);
        contentPanel.add(lblLector);
        
        txtEmailLector = createStyledTextField();
        txtEmailLector.setBounds(140, 80, 250, 30);
        contentPanel.add(txtEmailLector);
        
        // Email del bibliotecario
        JLabel lblBibliotecario = new JLabel("Email Bibliotecario:");
        lblBibliotecario.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBibliotecario.setForeground(new Color(52, 73, 94));
        lblBibliotecario.setBounds(30, 130, 100, 20);
        contentPanel.add(lblBibliotecario);
        
        txtEmailBibliotecario = createStyledTextField();
        txtEmailBibliotecario.setBounds(140, 130, 250, 30);
        contentPanel.add(txtEmailBibliotecario);
        
        // ID del material
        JLabel lblMaterial = new JLabel("ID Material:");
        lblMaterial.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMaterial.setForeground(new Color(52, 73, 94));
        lblMaterial.setBounds(30, 180, 100, 20);
        contentPanel.add(lblMaterial);
        
        txtIdMaterial = createStyledTextField();
        txtIdMaterial.setBounds(140, 180, 250, 30);
        contentPanel.add(txtIdMaterial);
        
        // Fecha de devolución
        JLabel lblFechaDev = new JLabel("Fecha Devolucion:");
        lblFechaDev.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaDev.setForeground(new Color(52, 73, 94));
        lblFechaDev.setBounds(30, 230, 100, 20);
        contentPanel.add(lblFechaDev);
        
        Calendar calFin = Calendar.getInstance();
        calFin.add(Calendar.DAY_OF_MONTH, 7); // Por defecto 7 días
        SpinnerDateModel modelFin = new SpinnerDateModel(calFin.getTime(), null, null, Calendar.DAY_OF_MONTH);
        spinnerFechaDev = new JSpinner(modelFin);
        spinnerFechaDev.setEditor(new JSpinner.DateEditor(spinnerFechaDev, "dd/MM/yyyy"));
        spinnerFechaDev.setBounds(140, 230, 250, 30);
        contentPanel.add(spinnerFechaDev);
        
        // Estado del préstamo
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(new Color(52, 73, 94));
        lblEstado.setBounds(30, 280, 100, 20);
        contentPanel.add(lblEstado);
        
        cmbEstadoPrestamo = new JComboBox<>(EstadoPrestamo.values());
        cmbEstadoPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbEstadoPrestamo.setBounds(140, 280, 250, 30);
        cmbEstadoPrestamo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        contentPanel.add(cmbEstadoPrestamo);
        
        // Botones modernos
        btnRegistrar = createStyledButton("Registrar", new Color(46, 204, 113));
        btnRegistrar.setBounds(100, 330, 120, 35);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarPrestamo();
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
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
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