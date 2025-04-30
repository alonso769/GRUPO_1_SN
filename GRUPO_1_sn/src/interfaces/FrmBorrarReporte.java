// interfaces/FrmBorrarReporte.java
package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrmBorrarReporte extends JFrame {

    private JLabel lblMensaje;
    private JButton btnBorrarConfirmar;
    private JButton btnCancelar;
    private int reporteId;
    private FrmMostrarReportes ventanaListadoReportes;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba"; // Reemplaza con tu URL
    private static final String DB_USER = "root";       // Reemplaza con tu usuario
    private static final String DB_PASSWORD = "1234";   // Reemplaza con tu contraseña

    public FrmBorrarReporte(int idReporte, FrmMostrarReportes ventanaListadoReportes) {
        this.reporteId = idReporte;
        this.ventanaListadoReportes = ventanaListadoReportes;
        setTitle("Confirmar Borrar Reporte");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        lblMensaje = new JLabel("¿Está seguro de que desea borrar el reporte con ID: " + reporteId + "?");
        btnBorrarConfirmar = new JButton("Borrar");
        btnCancelar = new JButton("Cancelar");

        add(lblMensaje);
        add(btnBorrarConfirmar);
        add(btnCancelar);

        btnBorrarConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrarReporte(reporteId);
                ventanaListadoReportes.cargarReportes(); // Recargar la lista de reportes
                dispose(); // Cerrar la ventana de confirmación
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana de confirmación
            }
        });
    }

    private void borrarReporte(int idReporte) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "DELETE FROM reporte WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idReporte);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Reporte borrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo borrar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al borrar reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Para probar, necesitarías una instancia de FrmMostrarReportes
                FrmMostrarReportes listaReportes = new FrmMostrarReportes("Administrador"); // Ejemplo con rol
                FrmBorrarReporte frame = new FrmBorrarReporte(1, listaReportes); // Ejemplo con ID 1
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
