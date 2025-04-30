// GuardarUsuarioEnBaseDeDatos.java
package gui;

import interfaces.GuardarUsuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class GuardarUsuarioEnBaseDeDatos implements GuardarUsuario {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba"; // Conectándose a la base de datos 'prueba'
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234"; // 

    @Override
    public boolean guardarUsuario(String usuario, String contrasena, String rol) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean resultado = false;

        try {
            // Intenta cargar el driver directamente a través del DriverManager (implícito en versiones recientes)
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO usuarios (usuario, contrasena, rol) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);
            pstmt.setString(3, rol);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente.");
                resultado = true;
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException se2) {}
            try { if (conn != null) conn.close(); } catch (SQLException se) {}
        }
        return resultado;
    }
}