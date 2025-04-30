// FrmRegistrarUsuario.java
package gui;

import interfaces.GuardarUsuario;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

public class FrmRegistrarUsuario extends JFrame {

    private JPanel contentPane;
    private JTextField txtNuevoUsuario;
    private JPasswordField txtNuevaContrasena;
    private JComboBox<String> comboRol;
    private static final long serialVersionUID = 1L;
    
    private GuardarUsuario guardarUsuarioService;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmRegistrarUsuario frame = new FrmRegistrarUsuario();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FrmRegistrarUsuario() {
        setTitle("Registrar Nuevo Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 350));
        setMinimumSize(new Dimension(400, 350));
        setMaximumSize(new Dimension(400, 350));
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        guardarUsuarioService = new GuardarUsuarioEnBaseDeDatos();

        JLabel lblTitulo = new JLabel("Registrar Nuevo Usuario");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(80, 30, 240, 30);
        contentPane.add(lblTitulo);

        JLabel lblNuevoUsuario = new JLabel("Nuevo Usuario:");
        lblNuevoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNuevoUsuario.setBounds(60, 80, 100, 20);
        contentPane.add(lblNuevoUsuario);

        txtNuevoUsuario = new JTextField();
        txtNuevoUsuario.setBounds(180, 77, 160, 25);
        contentPane.add(txtNuevoUsuario);
        txtNuevoUsuario.setColumns(10);

        JLabel lblNuevaContrasena = new JLabel("Contraseña:");
        lblNuevaContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNuevaContrasena.setBounds(60, 120, 90, 20);
        contentPane.add(lblNuevaContrasena);

        txtNuevaContrasena = new JPasswordField();
        txtNuevaContrasena.setBounds(180, 117, 160, 25);
        contentPane.add(txtNuevaContrasena);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setBounds(60, 160, 40, 20);
        contentPane.add(lblRol);

        comboRol = new JComboBox<>();
        comboRol.setModel(new DefaultComboBoxModel<>(new String[] {"Administrador", "Usuario Regular", "Invitado"}));
        comboRol.setBounds(180, 157, 160, 25);
        contentPane.add(comboRol);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(144, 238, 144));
        btnRegistrar.setBounds(80, 210, 120, 30);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nuevoUsuario = txtNuevoUsuario.getText();
                String nuevaContrasena = new String(txtNuevaContrasena.getPassword());
                String rolSeleccionado = (String) comboRol.getSelectedItem();

                if (guardarUsuarioService.guardarUsuario(nuevoUsuario, nuevaContrasena, rolSeleccionado)) {
                    txtNuevoUsuario.setText("");
                    txtNuevaContrasena.setText("");
                    comboRol.setSelectedIndex(0);
                }
            }
        });
        contentPane.add(btnRegistrar);

        JButton btnVolverLogin = new JButton("Volver a Login");
        btnVolverLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnVolverLogin.setBounds(220, 210, 120, 30);
        btnVolverLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmLogin loginForm = new FrmLogin();
                loginForm.setVisible(true);
                FrmRegistrarUsuario.this.dispose();
            }
        });
        contentPane.add(btnVolverLogin);
    }
}
