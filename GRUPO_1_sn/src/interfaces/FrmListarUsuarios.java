// interfaces/FrmListarUsuarios.java
package interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import gui.FrmClientes;
import gui.FrmProductos;

public class FrmListarUsuarios extends JFrame {

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnEditar;
    private JButton btnBorrar;
    private int usuarioSeleccionadoId = -1;
    private String usuarioRolLogueado; // Para almacenar el rol del usuario actual

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public FrmListarUsuarios(String rolUsuario) {
        this.usuarioRolLogueado = rolUsuario;
        setTitle("Listado de Usuarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Usuario", "Rol"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        JPanel panelBotones = new JPanel();
        btnEditar = new JButton("Editar");
        btnBorrar = new JButton("Borrar");
        panelBotones.add(btnEditar);

        // Solo agregar el botón de borrar si el usuario es administrador
        if (usuarioRolLogueado != null && usuarioRolLogueado.equals("Administrador")) {
            panelBotones.add(btnBorrar);
        }

        cargarUsuarios();

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaUsuarios.getSelectedRow() != -1) {
                int filaSeleccionada = tablaUsuarios.getSelectedRow();
                usuarioSeleccionadoId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                btnEditar.setEnabled(true);
                // Solo habilitar el botón de borrar si el usuario es administrador
                btnBorrar.setEnabled(usuarioRolLogueado != null && usuarioRolLogueado.equals("Administrador"));
            } else {
                usuarioSeleccionadoId = -1;
                btnEditar.setEnabled(false);
                btnBorrar.setEnabled(false);
            }
        });

        btnEditar.setEnabled(false);
        btnBorrar.setEnabled(false);

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarioSeleccionadoId != -1) {
                    FrmEditarUsuario editarUsuarioForm = new FrmEditarUsuario(usuarioSeleccionadoId, usuarioRolLogueado);
                    editarUsuarioForm.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(FrmListarUsuarios.this, "Seleccione un usuario para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarioSeleccionadoId != -1) {
                    FrmBorrarUsuario borrarUsuarioForm = new FrmBorrarUsuario(usuarioSeleccionadoId, FrmListarUsuarios.this);
                    borrarUsuarioForm.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(FrmListarUsuarios.this, "Seleccione un usuario para borrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT id, usuario, rol FROM usuarios";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String usuario = rs.getString("usuario");
                String rol = rs.getString("rol");
                modeloTabla.addRow(new Object[]{id, usuario, rol});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmListarUsuarios frame = new FrmListarUsuarios("Administrador"); // Ejemplo con rol
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}