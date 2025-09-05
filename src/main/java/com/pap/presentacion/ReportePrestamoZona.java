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

public class ReportePrestamoZona extends JInternalFrame{
    private static final long serialVersionUID = 1L;

	private IControlador icon;
    private JComboBox<String> comboZona;
    private JTable tabla;
    private JScrollPane scrollPane;

	public ReportePrestamoZona(IControlador icon) {
		this.icon = icon;
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Reporte de Prestamos por Zona");
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(null);

        JLabel lblZona = new JLabel("Zona:");
        lblZona.setBounds(20, 20, 120, 25);
        getContentPane().add(lblZona);

        comboZona = new JComboBox<>(new String[]{
            "BIBLIOTECA_CENTRAL",
            "SUCURSAL_ESTE",
            "SUCURSAL_OESTE",
            "BIBLIOTECA_INFANTIL",
            "ARCHIVO_GENERAL"
        });
        comboZona.setBounds(150, 20, 200, 25);
        getContentPane().add(comboZona);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ReportePrestamoZonaAceptarActionPerformed(arg0);
            }
        });
        btnAceptar.setBounds(100, 60, 100, 30);
        getContentPane().add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ReportePrestamoZonaCancelarActionPerformed(arg0);
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

    protected void ReportePrestamoZonaAceptarActionPerformed(ActionEvent arg0) {
        String zona = (String) comboZona.getSelectedItem();
        
        if(checkFormulario()){
            try {
                
                ArrayList<DtPrestamo> prestamos = icon.ReportePrestamoZona(zona, false);

                String[] columnas = {"ID Prestamo", "Email Lector", "Email Bibliotecario", "Material", "Fecha Solicitud", "Fecha Devolucion", "Estado"};
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
                    datos[i][6] = p.getEstado();
                }
                
                javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(datos, columnas);
                tabla.setModel(modelo);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Reporte de Prestamos por Zona", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void ReportePrestamoZonaCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }

    private boolean checkFormulario() {
        String zonaSeleccionada = (String) comboZona.getSelectedItem();

        if (zonaSeleccionada == null || zonaSeleccionada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una zona", "Reporte de Prestamos por Zona", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        tabla.setModel(new javax.swing.table.DefaultTableModel());
    }
}
