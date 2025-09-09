package com.pap.presentacion;

import com.pap.datatypes.DtLector;
import com.pap.datatypes.EstadoLector;
import com.pap.interfaces.IControlador;

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

public class ModificarEstadoLector extends JPanel {
    
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
    
    // Componentes para cambio de estado
    private JComboBox<EstadoLector> cmbEstado;
    private JButton btnModificar;
    private JButton btnCancelar;
    
    // Formato de fecha
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ModificarEstadoLector(IControlador controlador) {
        this.controlador = controlador;
        initialize();
        cargarLectores();
    }

    private void initialize() {
        setLayout(new BorderLayout());
        setBackground(new Color(74, 76, 81)); // Dark mode background
        
        // Título - Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Minimal vertical spacing
        headerPanel.setOpaque(false);
        JLabel lblTitulo = new JLabel("Seleccione un lector para cambiar su estado");
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
        
        // Seccion de seleccion de lector
        JLabel lblSeleccionarLector = new JLabel("Seleccionar Lector:");
        lblSeleccionarLector.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSeleccionarLector.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(2, 0, 10, 10); // Reduced top margin to bring closer to title
        formContainerPanel.add(lblSeleccionarLector, gbc);
        
        cmbLector = new JComboBox<>();
        cmbLector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbLector.setPreferredSize(new Dimension(300, 30));
        cmbLector.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
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
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 10, 0); // Reduced top margin to match label
        formContainerPanel.add(cmbLector, gbc);
        
        // Seccion de datos del lector
        JLabel lblDatosLector = new JLabel("Datos del Lector:");
        lblDatosLector.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblDatosLector.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 10, 0); // Extra spacing for section header
        formContainerPanel.add(lblDatosLector, gbc);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblNombre, gbc);
        
        txtNombre = createStyledTextField(false);
        txtNombre.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtNombre, gbc);
        
        // Email
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEmail.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblEmail, gbc);
        
        txtEmail = createStyledTextField(false);
        txtEmail.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtEmail, gbc);
        
        // Direccion
        JLabel lblDireccion = new JLabel("Direccion:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDireccion.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblDireccion, gbc);
        
        txtDireccion = createStyledTextField(false);
        txtDireccion.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtDireccion, gbc);
        
        // Fecha de Registro
        JLabel lblFechaRegistro = new JLabel("Fecha de Registro:");
        lblFechaRegistro.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFechaRegistro.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblFechaRegistro, gbc);
        
        txtFechaRegistro = createStyledTextField(false);
        txtFechaRegistro.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtFechaRegistro, gbc);
        
        // Estado Actual
        JLabel lblEstadoActual = new JLabel("Estado Actual:");
        lblEstadoActual.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEstadoActual.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblEstadoActual, gbc);
        
        txtEstadoActual = createStyledTextField(false);
        txtEstadoActual.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtEstadoActual, gbc);
        
        // Zona
        JLabel lblZona = new JLabel("Zona:");
        lblZona.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblZona.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 0, 10, 10);
        formContainerPanel.add(lblZona, gbc);
        
        txtZona = createStyledTextField(false);
        txtZona.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        formContainerPanel.add(txtZona, gbc);
        
        // Seccion de cambio de estado
        JLabel lblCambiarEstado = new JLabel("Cambiar estado a:");
        lblCambiarEstado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCambiarEstado.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 10, 10); // Extra spacing for new section
        formContainerPanel.add(lblCambiarEstado, gbc);
        
        cmbEstado = new JComboBox<>(EstadoLector.values());
        cmbEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbEstado.setPreferredSize(new Dimension(200, 30));
        cmbEstado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219)),
            new EmptyBorder(5, 10, 5, 10)
        ));
        // Inicialmente sin seleccion
        cmbEstado.setSelectedItem(null);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 0, 10, 0);
        formContainerPanel.add(cmbEstado, gbc);
        
        // Add buttons with minimal spacing
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        // Add buttons to the button panel
        btnModificar = createStyledButton("Modificar", new Color(46, 204, 113));
        btnModificar.setPreferredSize(new Dimension(120, 35));
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarEstado();
            }
        });
        buttonPanel.add(btnModificar);
        
        btnCancelar = createStyledButton("Cancelar", new Color(231, 76, 60));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().volverAPantallaInicialPublic();
            }
        });
        buttonPanel.add(btnCancelar);
        
        // Add back button
        JButton btnVolver = createStyledButton("Volver", new Color(52, 73, 94));
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                com.pap.presentacion.Principal.getInstance().irASubmenuUsuarios();
            }
        });
        buttonPanel.add(btnVolver);
        
        // Add button panel to form container with minimal spacing
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 0, 0); // Increased spacing above buttons
        formContainerPanel.add(buttonPanel, gbc);

        add(formContainerPanel, BorderLayout.CENTER);
        
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
    
    private void modificarEstado() {
        DtLector lectorSeleccionado = (DtLector) cmbLector.getSelectedItem();
        EstadoLector nuevoEstado = (EstadoLector) cmbEstado.getSelectedItem();
        
        // Validaciones
        if (lectorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un lector", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (nuevoEstado == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un estado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el estado es diferente
        if (lectorSeleccionado.getEstado() == nuevoEstado) {
            JOptionPane.showMessageDialog(this, 
                "El lector ya tiene el estado seleccionado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Llamar al controlador para modificar el estado
            boolean exito = controlador.modificarEstado(lectorSeleccionado.getEmail(), nuevoEstado);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Estado modificado exitosamente", 
                    "Exito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar la lista de lectores
                cargarLectores();
                
                // Limpiar seleccion de estado
                cmbEstado.setSelectedItem(null);
                
                // Cerrar la ventana
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al modificar el estado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("Error al modificar estado: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error al modificar el estado: " + e.getMessage(), 
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
        // Map color by label
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
