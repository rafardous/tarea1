package com.pap.presentacion;

import com.pap.datatypes.DtLector;
import com.pap.datatypes.Zona;
import com.pap.interfaces.IControlador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CambiarZonaLector extends JPanel {
    
    private IControlador controlador;
    
    // Componentes de seleccion de lector
    private JComboBox<DtLector> cmbLector;
    
    // Campos de datos del lector (no editables)
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JTextField txtFechaRegistro;
    private JTextField txtEstadoActual;
    private JTextField txtZona;
    
    // Componentes para cambio de zona
    private JComboBox<Zona> cmbZona;
    private JButton btnModificar;
    private JButton btnCancelar;
    
    // Formato de fecha
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CambiarZonaLector(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarLectores();
    }

    private void initialize() {
        setLayout(null);
        setBounds(0, 0, 1200, 800);
        setBackground(new Color(74, 76, 81)); // Slightly lighter grey
        
        // Panel de fondo con gradiente
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(74, 76, 81),
                    getWidth(), getHeight(), new Color(84, 86, 91)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        contentPanel.setLayout(null);
        contentPanel.setBounds(0, 0, getWidth(), getHeight());
        add(contentPanel);
        
        // Titulo con estilo moderno
        JLabel lblTitulo = new JLabel("Cambiar Zona de Lector");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(120, 20, 300, 30);
        contentPanel.add(lblTitulo);
        
        // Seccion de seleccion de lector
        JLabel lblSeleccionarLector = new JLabel("Seleccionar Lector:");
        lblSeleccionarLector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSeleccionarLector.setForeground(Color.WHITE);
        lblSeleccionarLector.setBounds(30, 70, 120, 20);
        contentPanel.add(lblSeleccionarLector);
        
        cmbLector = new JComboBox<>();
        cmbLector.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbLector.setBounds(160, 70, 280, 25);
        
        // Renderer personalizado para mostrar toda la información del lector sin etiquetas
        cmbLector.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value == null) {
                    setText("Seleccione un lector...");
                } else if (value instanceof DtLector) {
                    DtLector lector = (DtLector) value;
                    String infoLector = String.format("%s | %s | %s",
                        lector.getNombre(),
                        lector.getEmail(),
                        lector.getEstado().toString()
                        );
                    setText(infoLector);
                } else {
                    setText(value.toString());
                }
                
                return this;
            }
        });
        
        cmbLector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cargarDatosLector();
                }
            }
        });
        contentPanel.add(cmbLector);
        
        // Seccion de datos del lector
        JLabel lblDatosLector = new JLabel("Datos del Lector:");
        lblDatosLector.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDatosLector.setForeground(Color.WHITE);
        lblDatosLector.setBounds(30, 110, 150, 20);
        contentPanel.add(lblDatosLector);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(30, 140, 100, 20);
        contentPanel.add(lblNombre);
        
        txtNombre = createStyledTextField(false);
        txtNombre.setBounds(140, 140, 300, 25);
        contentPanel.add(txtNombre);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBounds(30, 175, 100, 20);
        contentPanel.add(lblEmail);
        
        txtEmail = createStyledTextField(false);
        txtEmail.setBounds(140, 175, 300, 25);
        contentPanel.add(txtEmail);
        
        // Direccion
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDireccion.setForeground(Color.WHITE);
        lblDireccion.setBounds(30, 210, 100, 20);
        contentPanel.add(lblDireccion);
        
        txtDireccion = createStyledTextField(false);
        txtDireccion.setBounds(140, 210, 300, 25);
        contentPanel.add(txtDireccion);
        
        // Fecha de Registro
        JLabel lblFechaRegistro = new JLabel("Fecha de Registro:");
        lblFechaRegistro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblFechaRegistro.setForeground(Color.WHITE);
        lblFechaRegistro.setBounds(30, 245, 120, 20);
        contentPanel.add(lblFechaRegistro);
        
        txtFechaRegistro = createStyledTextField(false);
        txtFechaRegistro.setBounds(160, 245, 280, 25);
        contentPanel.add(txtFechaRegistro);
        
        // Estado Actual
        JLabel lblEstadoActual = new JLabel("Estado Actual:");
        lblEstadoActual.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadoActual.setForeground(Color.WHITE);
        lblEstadoActual.setBounds(30, 280, 100, 20);
        contentPanel.add(lblEstadoActual);
        
        txtEstadoActual = createStyledTextField(false);
        txtEstadoActual.setBounds(140, 280, 300, 25);
        contentPanel.add(txtEstadoActual);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblZona.setForeground(Color.WHITE);
        lblZona.setBounds(30, 315, 100, 20);
        contentPanel.add(lblZona);
        
        txtZona = createStyledTextField(false);
        txtZona.setBounds(140, 315, 300, 25);
        contentPanel.add(txtZona);
        
        // Seccion de cambio de zona
        JLabel lblCambiarZona = new JLabel("Cambiar zona a:");
        lblCambiarZona.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCambiarZona.setForeground(Color.WHITE);
        lblCambiarZona.setBounds(30, 360, 130, 20);
        contentPanel.add(lblCambiarZona);
        
        cmbZona = new JComboBox<>(Zona.values());
        cmbZona.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbZona.setBounds(170, 360, 270, 25);
        // Inicialmente sin seleccion
        cmbZona.setSelectedItem(null);
        contentPanel.add(cmbZona);
        
        // Botones
        btnModificar = createStyledButton("Modificar", new Color(52, 152, 219));
        btnModificar.setBounds(150, 400, 100, 35);
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarZona();
            }
        });
        contentPanel.add(btnModificar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setBounds(270, 400, 100, 35);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        contentPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setBounds(390, 400, 100, 35);
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuUsuarios();
            }
        });
        contentPanel.add(btnVolver);
        
        // Inicialmente deshabilitar campos de datos hasta que se seleccione un lector
        setCamposDatosEnabled(false);
    }
    
    public void cargarLectores() {
        try {
            List<DtLector> lectores = controlador.listarLectores();
            
            // Ordenar por nombre
            lectores = lectores.stream()
                .sorted((l1, l2) -> l1.getNombre().compareToIgnoreCase(l2.getNombre()))
                .collect(Collectors.toList());
            
            cmbLector.removeAllItems();
            for (DtLector lector : lectores) {
                cmbLector.addItem(lector);
            }
            
            // Agregar item vacio al inicio
            cmbLector.insertItemAt(null, 0);
            cmbLector.setSelectedIndex(0);
            
        } catch (Exception e) {
            System.err.println("Error al cargar lectores: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error al cargar la lista de lectores", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDatosLector() {
        DtLector lectorSeleccionado = (DtLector) cmbLector.getSelectedItem();
        
        if (lectorSeleccionado == null) {
            setCamposDatosEnabled(false);
            limpiarCamposDatos();
            return;
        }
        
        // Llenar campos con datos del lector
        txtNombre.setText(lectorSeleccionado.getNombre());
        txtEmail.setText(lectorSeleccionado.getEmail());
        txtDireccion.setText(lectorSeleccionado.getDireccion());
        txtFechaRegistro.setText(dateFormat.format(lectorSeleccionado.getFechaRegistro()));
        txtEstadoActual.setText(lectorSeleccionado.getEstado().toString());
        txtZona.setText(lectorSeleccionado.getZona().toString());
        
        setCamposDatosEnabled(true);
    }
    
    private void modificarZona() {
        DtLector lectorSeleccionado = (DtLector) cmbLector.getSelectedItem();
        Zona nuevaZona = (Zona) cmbZona.getSelectedItem();
        
        // Validaciones
        if (lectorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (nuevaZona == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar una zona", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si la zona es diferente
        if (lectorSeleccionado.getZona() == nuevaZona) {
            JOptionPane.showMessageDialog(this, 
                "El lector ya tiene la zona seleccionada", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Llamar al controlador para modificar la zona
            boolean exito = controlador.cambiarZona(lectorSeleccionado.getEmail(), nuevaZona);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Zona modificada exitosamente", 
                    "Exito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar la lista de lectores
                cargarLectores();
                
                // Limpiar seleccion de zona
                cmbZona.setSelectedItem(null);
                
                // Cerrar la ventana
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al modificar la zona", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("Error al modificar zona: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error al modificar la zona: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setCamposDatosEnabled(boolean enabled) {
        txtNombre.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
        txtDireccion.setEnabled(enabled);
        txtFechaRegistro.setEnabled(enabled);
        txtEstadoActual.setEnabled(enabled);
        txtZona.setEnabled(enabled);
    }
    
    private void limpiarCamposDatos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtFechaRegistro.setText("");
        txtEstadoActual.setText("");
        txtZona.setText("");
    }
    
    private JTextField createStyledTextField(boolean editable) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.WHITE);
        textField.setEditable(editable);
        return textField;
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
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
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(finalBg.brighter(), 2, true),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        // Efecto hover
        final Color hover = finalBg.darker();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(finalBg);
            }
        });
        
        return button;
    }
}