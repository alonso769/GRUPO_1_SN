// FrmLogin.java
package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FrmLogin extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmLogin frame = new FrmLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmLogin() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300));
        setMinimumSize(new Dimension(400, 300));
        setMaximumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(100, 30, 200, 30);
        contentPane.add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setBounds(80, 80, 70, 20);
        contentPane.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(160, 77, 160, 25);
        contentPane.add(txtUsuario);
        txtUsuario.setColumns(10);

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblContrasena.setBounds(80, 120, 80, 20);
        contentPane.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(160, 117, 160, 25);
        contentPane.add(txtContrasena);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(173, 216, 230));
        btnIngresar.setBounds(140, 170, 120, 30);
        btnIngresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                    String sql = "SELECT * FROM usuarios WHERE usuario = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, usuario);
                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String storedPassword = rs.getString("contrasena");
                        String rol = rs.getString("rol");

                        if (contrasena.equals(storedPassword)) {
                            JOptionPane.showMessageDialog(null, "Acceso concedido como " + rol + ".");
                            if (rol.equals("Administrador")) {
                                FrmMenuAdministrador menuAdmin = new FrmMenuAdministrador(usuario);
                                menuAdmin.frame.setVisible(true);
                            } else if (rol.equals("Invitado")) {
                                FrmMenuInvitado menuInvitado = new FrmMenuInvitado();
                                menuInvitado.frame.setVisible(true);
                            } else { // Asumimos que cualquier otro rol es "Regular"
                                FrmMenuPrincipal menuPrincipal = new FrmMenuPrincipal();
                                menuPrincipal.frame.setVisible(true);
                            }
                            FrmLogin.this.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try { if (rs != null) rs.close(); } catch (SQLException ex) {}
                    try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
                    try { if (conn != null) conn.close(); } catch (SQLException ex) {}
                }
            }
        });
        contentPane.add(btnIngresar);

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegistrarse.setBounds(140, 210, 120, 25);
        btnRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmRegistrarUsuario registrarForm = new FrmRegistrarUsuario();
                registrarForm.setVisible(true);
                FrmLogin.this.dispose();
            }
        });
        contentPane.add(btnRegistrarse);
    }
}