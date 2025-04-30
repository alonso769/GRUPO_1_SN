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

public class FrmLogin extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    
        private static final long serialVersionUID = 1L;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmLogin frame = new FrmLogin();
                    frame.setVisible(true); // El login se muestra primero
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FrmLogin() {
        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 300)); // Tamaño preferido
        setMinimumSize(new Dimension(400, 300));
        setMaximumSize(new Dimension(400, 300));
        setLocationRelativeTo(null); // Centrar en pantalla
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
                // Simulación de acceso concedido (sin validación de base de datos)
                JOptionPane.showMessageDialog(null, "Acceso concedido.");
                FrmMenuPrincipal menu = new FrmMenuPrincipal();
                menu.frame.setVisible(true); // Acceder al frame público y hacerlo visible
                FrmLogin.this.dispose(); // Cierra el formulario de login
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
                FrmLogin.this.dispose(); // Cierra el login al ir a registrarse (opcional)
            }
        });
        contentPane.add(btnRegistrarse);
    }
}