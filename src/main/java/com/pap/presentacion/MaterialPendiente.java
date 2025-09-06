package com.pap.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.pap.datatypes.DtPrestamo;
import com.pap.interfaces.IControlador;

public class MaterialPendiente extends JInternalFrame{
    private static final long serialVersionUID = 1L;

	private IControlador icon;
    private JComboBox<String> comboEstado;
    private JTable tabla;
    private JScrollPane scrollPane;

	public MaterialPendiente(IControlador icon) {
		this.icon = icon;
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Materiales Pendientes de Devolucion");
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(null);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(20, 20, 120, 25);
        getContentPane().add(lblEstado);

        comboEstado = new JComboBox<>(new String[]{"PENDIENTE", "EN_CURSO", "DEVUELTO"});
        comboEstado.setBounds(150, 20, 150, 25);
        getContentPane().add(comboEstado);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                MaterialPendienteAceptarActionPerformed(arg0);
            }
        });
        btnAceptar.setBounds(100, 60, 100, 30);
        getContentPane().add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                MaterialPendienteCancelarActionPerformed(arg0);
            }
        });
        btnCancelar.setBounds(250, 60, 100, 30);
        getContentPane().add(btnCancelar);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 120, 650, 200);
        getContentPane().add(scrollPane);

        tabla = new JTable();
        scrollPane.setViewportView(tabla);

    }

    protected void MaterialPendienteAceptarActionPerformed(ActionEvent arg0) {
        String estado = (String) comboEstado.getSelectedItem();

        if(checkFormulario()){
            try {
                ArrayList<DtPrestamo> prestamos = icon.MaterialPendiente(estado);

                String[] columnas = {"ID Prestamo", "Email Lector", "Email Bibliotecario", "Material", "Fecha Solicitud", "Fecha Devolucion"};
                Object[][] datos = new Object[prestamos.size()][columnas.length];

                for (int i = 0; i < prestamos.size(); i++) {
                    DtPrestamo p = prestamos.get(i);
                    datos[i][0] = p.getId();
                    datos[i][1] = p.getLector().getEmail();
                    datos[i][2] = p.getBibliotecario().getEmail();
                    // Mostrar descripcion del material
                    String descripcionMaterial = "";
                    if (p.getMaterial() instanceof com.pap.datatypes.DtLibro) {
                        descripcionMaterial = ((com.pap.datatypes.DtLibro) p.getMaterial()).getTitulo();
                    } else if (p.getMaterial() instanceof com.pap.datatypes.DtArticulo) {
                        descripcionMaterial = ((com.pap.datatypes.DtArticulo) p.getMaterial()).getDescripcion();
                    } else {
                        descripcionMaterial = "Material no identificado";
                    }
                    datos[i][3] = descripcionMaterial;
                    datos[i][4] = p.getFechaSolicitud();
                    datos[i][5] = p.getFechaDevolucion();
                }
                
                javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(datos, columnas);
                tabla.setModel(modelo);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Materiales Pendientes", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void MaterialPendienteCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }

    private boolean checkFormulario() {
        String estadoSeleccionado = (String) comboEstado.getSelectedItem();

        if (estadoSeleccionado == null || estadoSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estado", "Materiales Pendientes", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        tabla.setModel(new javax.swing.table.DefaultTableModel());
    }

}
