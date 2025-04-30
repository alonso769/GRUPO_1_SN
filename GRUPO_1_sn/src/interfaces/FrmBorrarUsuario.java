// interfaces/FrmBorrarUsuario.java
package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrmBorrarUsuario extends JFrame {

    private JLabel lblMensaje;
    private JButton btnBorrarConfirmar;
    private JButton btnCancelar;
    private int usuarioId;
    private FrmListarUsuarios ventanaListado;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public FrmBorrarUsuario(int idUsuario, FrmListarUsuarios ventanaListado) {
        this.usuarioId = idUsuario;
        this.ventanaListado = ventanaListado;
        setTitle("Confirmar Borrar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        lblMensaje = new JLabel("¿Está seguro de que desea borrar el usuario con ID: " + usuarioId + "?");
        btnBorrarConfirmar = new JButton("Borrar");
        btnCancelar = new JButton("Cancelar");

        add(lblMensaje);
        add(btnBorrarConfirmar);
        add(btnCancelar);

        btnBorrarConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                borrarUsuario(usuarioId);
                ventanaListado.cargarUsuarios(); // Recargar la lista
                dispose(); // Cerrar la ventana de confirmación
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar la ventana de confirmación
            }
        });
    }

    private void borrarUsuario(int idUsuario) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "DELETE FROM usuarios WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Usuario borrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo borrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al borrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                // Para probar, necesitarías una instancia de FrmListarUsuarios
                FrmListarUsuarios listaUsuarios = new FrmListarUsuarios("Administrador"); // Ejemplo con rol
                FrmBorrarUsuario frame = new FrmBorrarUsuario(1, listaUsuarios); // Ejemplo con ID 1
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}