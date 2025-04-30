// FrmMenuAdministrador.java
package gui;

import interfaces.FrmIngresarReporte;
import interfaces.FrmListarUsuarios;
import interfaces.FrmMostrarReportes;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Importar FrmProductos
import gui.FrmProductos;

public class FrmMenuAdministrador {

    public JFrame frame;
    private String usuarioLogueado;
    private String rolUsuarioLogueado;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Para propósitos de prueba, pasar un usuario dummy y rol
                    // En una aplicación real, esto vendría del frame de login
                    FrmMenuAdministrador window = new FrmMenuAdministrador("admin_test_user");
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmMenuAdministrador(String usuario) {
        this.usuarioLogueado = usuario;
        obtenerRolUsuario(); // Obtener el rol de la base de datos
        initialize();
    }

    private void obtenerRolUsuario() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT rol FROM usuarios WHERE usuario = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuarioLogueado);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rolUsuarioLogueado = rs.getString("rol");
            } else {
                 // Manejar caso donde el usuario no es encontrado (no debería pasar si el login es correcto)
                 rolUsuarioLogueado = "Desconocido"; // Asignar un rol por defecto o manejar error
                 JOptionPane.showMessageDialog(frame, "Usuario no encontrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al obtener el rol del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            rolUsuarioLogueado = "Error"; // Indicar error
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sistema de Pedidos - Menú Administrador (" + usuarioLogueado + " - " + rolUsuarioLogueado + ")"); // Mostrar usuario y rol
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Arial", Font.PLAIN, 14));
        menuBar.setBackground(new Color(240, 240, 240));
        frame.setJMenuBar(menuBar);

        JMenu mnMantenimiento = new JMenu("Mantenimiento");
        mnMantenimiento.setFont(new Font("Arial", Font.BOLD, 16));
        mnMantenimiento.setForeground(new Color(0, 100, 0));
        menuBar.add(mnMantenimiento);

        JMenuItem mntmClientes = new JMenuItem("Clientes");
        mntmClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Pasar el rol del usuario logueado a FrmClientes
                    FrmClientes clientesForm = new FrmClientes(rolUsuarioLogueado);
                    clientesForm.setVisible(true);
                } catch (Exception ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(frame, "Error al abrir la ventana de Clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mnMantenimiento.add(mntmClientes);

        JMenuItem mntmProductos = new JMenuItem("Productos");
        mntmProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 try {
                     // Crear y mostrar el formulario FrmProductos, pasando el rol del administrador
                     FrmProductos productosForm = new FrmProductos(rolUsuarioLogueado);
                     productosForm.setVisible(true);
                 } catch (Exception ex) {
                      ex.printStackTrace();
                      JOptionPane.showMessageDialog(frame, "Error al abrir la ventana de Productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }
            }
        });
        mnMantenimiento.add(mntmProductos);

        JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
        mntmUsuarios.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmUsuarios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // FrmListarUsuarios ya parece manejar roles basado en su constructor
                FrmListarUsuarios listarUsuariosForm = new FrmListarUsuarios(rolUsuarioLogueado);
                listarUsuariosForm.setVisible(true);
            }
        });
        mnMantenimiento.add(mntmUsuarios);

        JMenu mnOperaciones = new JMenu("Operaciones");
        mnOperaciones.setFont(new Font("Arial", Font.BOLD, 16));
        mnOperaciones.setForeground(new Color(0, 100, 0));
        menuBar.add(mnOperaciones);

        JMenuItem mntmIngresarReporte = new JMenuItem("Ingresar Reporte");
        mntmIngresarReporte.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmIngresarReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Esta verificación ya está presente en el código original, lo cual es bueno.
                // FrmIngresarReporte también debería idealmente recibir el usuario/rol si es necesario internamente.
                if (rolUsuarioLogueado != null && rolUsuarioLogueado.equals("Administrador")) {
                     // Asumiendo que FrmIngresarReporte necesita el usuario logueado
                     FrmIngresarReporte ingresarReporteForm = new FrmIngresarReporte(usuarioLogueado);
                     ingresarReporteForm.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Solo los administradores pueden ingresar reportes.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        mnOperaciones.add(mntmIngresarReporte);

        JMenu mnReportes = new JMenu("Reportes");
        mnReportes.setFont(new Font("Arial", Font.BOLD, 16));
        mnReportes.setForeground(new Color(0, 100, 0));
        menuBar.add(mnReportes);

        JMenuItem mntmMostrarReportes = new JMenuItem("Mostrar Reportes");
        mntmMostrarReportes.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmMostrarReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 try {
                     // FrmMostrarReportes (Consultas) ya parece manejar roles basado en su constructor
                     // Pasar el rol del usuario logueado a FrmMostrarReportes (Consultas)
                     FrmMostrarReportes mostrarReportesForm = new FrmMostrarReportes(rolUsuarioLogueado);
                     mostrarReportesForm.setVisible(true);
                 } catch (Exception ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(frame, "Error al abrir la ventana de Reportes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 }
            }
        });
        mnReportes.add(mntmMostrarReportes);

        JMenu mnAcercaDe = new JMenu("Acerca de");
        mnAcercaDe.setFont(new Font("Arial", Font.BOLD, 16));
        mnAcercaDe.setForeground(new Color(0, 100, 0));
        menuBar.add(mnAcercaDe);

        JMenu mnSalir = new JMenu("Salir");
        mnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        mnSalir.setForeground(new Color(255, 0, 0));
        menuBar.add(mnSalir);

        JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
        mntmCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Asumiendo que FrmLogin existe y tiene un constructor sin argumentos
                FrmLogin loginForm = new FrmLogin();
                loginForm.setVisible(true);
                frame.dispose(); // Cerrar el frame del menú actual
            }
        });
        mnSalir.add(mntmCerrarSesion);
    }
}
