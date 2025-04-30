// interfaces/FrmEditarUsuario.java
package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrmEditarUsuario extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<String> comboRol;
    private JButton btnGuardar;
    private int usuarioId;
    private String usuarioRolLogueado; // Para almacenar el rol del usuario actual

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public FrmEditarUsuario(int idUsuario, String rolUsuario) {
        this.usuarioId = idUsuario;
        this.usuarioRolLogueado = rolUsuario;
        setTitle("Editar Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        panel.add(new JLabel("Rol:"));
        comboRol = new JComboBox<>(new String[]{"Administrador", "Usuario Regular", "Invitado"});
        panel.add(comboRol);

        btnGuardar = new JButton("Guardar Cambios");
        panel.add(new JLabel(""));

        // Solo mostrar el botón guardar si el usuario es administrador
        if (usuarioRolLogueado != null && usuarioRolLogueado.equals("Administrador")) {
            panel.add(btnGuardar);
        } else {
            // Si no es administrador, puedes deshabilitar los campos o simplemente no mostrar el botón
            txtUsuario.setEnabled(false);
            txtContrasena.setEnabled(false);
            comboRol.setEnabled(false);
            JLabel lblAccesoDenegado = new JLabel("Solo los administradores pueden editar usuarios.");
            lblAccesoDenegado.setForeground(Color.RED);
            panel.add(lblAccesoDenegado);
        }

        cargarDatosUsuario();

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nuevoUsuario = txtUsuario.getText();
                String nuevaContrasena = new String(txtContrasena.getPassword());
                String nuevoRol = (String) comboRol.getSelectedItem();

                actualizarUsuario(nuevoUsuario, nuevaContrasena, nuevoRol);
            }
        });

        add(panel);
    }

    private void cargarDatosUsuario() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT usuario, contrasena, rol FROM usuarios WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, usuarioId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                txtUsuario.setText(rs.getString("usuario"));
                txtContrasena.setText(rs.getString("contrasena"));
                comboRol.setSelectedItem(rs.getString("rol"));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el usuario con ID: " + usuarioId, "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    private void actualizarUsuario(String usuario, String contrasena, String rol) {
        if (usuarioRolLogueado != null && usuarioRolLogueado.equals("Administrador")) {
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, rol = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, usuario);
                pstmt.setString(2, contrasena);
                pstmt.setString(3, rol);
                pstmt.setInt(4, usuarioId);
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
                try { if (conn != null) conn.close(); } catch (SQLException ex) {}
            }
        } else {
            JOptionPane.showMessageDialog(this, "Solo los administradores pueden editar usuarios.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmEditarUsuario frame = new FrmEditarUsuario(1, "Administrador"); // Ejemplo con ID y rol
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}