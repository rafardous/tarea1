package com.pap.java.presentacion;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.pap.java.datatypes.EstadoLector;
import com.pap.java.datatypes.Zona;
import com.pap.java.interfaces.IControlador;
import com.pap.java.logica.Lector;

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
        setBounds(0, 0, 400, 350);
        setLayout(null);
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Nuevo Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setBounds(100, 20, 200, 25);
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
        
        // Dirección
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(30, 150, 80, 20);
        add(lblDireccion);
        
        txtDireccion = new JTextField();
        txtDireccion.setBounds(120, 150, 200, 25);
        add(txtDireccion);
        
        // Estado
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 190, 80, 20);
        add(lblEstado);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setBounds(120, 190, 200, 25);
        add(cmbEstado);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setBounds(30, 230, 80, 20);
        add(lblZona);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setBounds(120, 230, 200, 25);
        add(cmbZona);
        
        // Botones
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(100, 280, 100, 30);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarLector();
            }
        });
        add(btnRegistrar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 280, 100, 30);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        add(btnCancelar);
    }
    
    private void registrarLector() {
        try {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String direccion = txtDireccion.getText().trim();
            
            if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Todos los campos son obligatorios", 
                    "Error de Validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el lector
            Lector lector = new Lector(nombre, email, direccion, new Date(), 
                                     (EstadoLector) cmbEstado.getSelectedItem(), 
                                     (Zona) cmbZona.getSelectedItem());
            
            // Registrar usando el controlador
            boolean resultado = controlador.registrarLector(lector);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Lector registrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar el lector", 
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
        txtDireccion.setText("");
        cmbEstado.setSelectedIndex(0);
        cmbZona.setSelectedIndex(0);
    }
}
