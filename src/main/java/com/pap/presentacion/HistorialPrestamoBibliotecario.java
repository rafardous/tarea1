package com.pap.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.pap.datatypes.DtPrestamo;
import com.pap.interfaces.IControlador;

public class HistorialPrestamoBibliotecario extends JInternalFrame{
    private static final long serialVersionUID = 1L;

	private IControlador icon;
    private JTextField txtNumBibliotecario;
    private JTable tabla;
    private JScrollPane scrollPane;

	public HistorialPrestamoBibliotecario(IControlador icon) {
		this.icon = icon;
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Historial de Prestamos de un Bibliotecario");
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(null);

        JLabel lblNumBiblio = new JLabel("N° Bibliotecario:");
        lblNumBiblio.setBounds(20, 20, 120, 25);
        getContentPane().add(lblNumBiblio);

        txtNumBibliotecario = new JTextField();
        txtNumBibliotecario.setBounds(150, 20, 200, 25);
        getContentPane().add(txtNumBibliotecario);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                HistorialPrestamoBibliotecarioAceptarActionPerformed(arg0);
            }
        });
        btnAceptar.setBounds(100, 60, 100, 30);
        getContentPane().add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                HistorialPrestamoBibliotecarioCancelarActionPerformed(arg0);
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

    protected void HistorialPrestamoBibliotecarioAceptarActionPerformed(ActionEvent arg0) {
        String numeroEmpleado = this.txtNumBibliotecario.getText();
        
        if(checkFormulario()){
            try {

                ArrayList<DtPrestamo> prestamos = icon.HistorialPrestamoBibliotecario(numeroEmpleado, true);

                String[] columnas = {"ID Prestamo", "Email Lector", "Email Bibliotecario", "Fecha Solicitud", "Fecha Devolucion", "Estado"};
                Object[][] datos = new Object[prestamos.size()][columnas.length];
                
                for (int i = 0; i < prestamos.size(); i++) {
                    DtPrestamo p = prestamos.get(i);
                    datos[i][0] = p.getId();
                    datos[i][1] = p.getLector().getEmail();
                    datos[i][2] = p.getBibliotecario().getEmail();
                    datos[i][3] = p.getFechaSolicitud();
                    datos[i][4] = p.getFechaDevolucion();
                    datos[i][5] = p.getEstado();
                }
                
                javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(datos, columnas);
                tabla.setModel(modelo);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Historial de Prestamos de un Bibliotecario", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void HistorialPrestamoBibliotecarioCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }

    private boolean checkFormulario() {
        String numeroEmpleado = this.txtNumBibliotecario.getText();

        if (numeroEmpleado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Historial de Prestamos de un Bibliotecario", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        txtNumBibliotecario.setText("");
    }

}
