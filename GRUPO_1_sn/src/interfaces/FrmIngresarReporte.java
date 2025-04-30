// interfaces/FrmIngresarReporte.java
package interfaces;

import javax.swing.*;
import javax.swing.border.EmptyBorder; // Importa EmptyBorder
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmIngresarReporte extends JFrame {

    private JTextField txtNombreProducto;
    private JTextField txtCategoria;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTextField txtFechaIngreso;
    private JTextField txtFechaCaducidad;
    private JTextField txtConservar;
    private JTextField txtEmpresa;
    private JTextField txtCodigoBarras;
    private JTextField txtPersonalRegistro;
    private JButton btnGuardar;
    private JPanel contentPane; // Referencia al panel principal

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba"; // Reemplaza con tu URL si es diferente
    private static final String DB_USER = "root";       // Reemplaza con tu usuario si es diferente
    private static final String DB_PASSWORD = "1234";   // Reemplaza con tu contraseña si es diferente

    public FrmIngresarReporte(String usuarioLogueado) {
        setTitle("Ingresar Reporte");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new GridLayout(11, 2, 10, 10)); // Inicializa el panel con el GridLayout
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20)); // Establece el borde en el contentPane
        setContentPane(contentPane); // Establece el contentPane del JFrame

        txtPersonalRegistro = new JTextField(usuarioLogueado);
        txtPersonalRegistro.setEditable(false); // No permitir editar el nombre del personal

        contentPane.add(new JLabel("Nombre Producto:"));
        txtNombreProducto = new JTextField();
        contentPane.add(txtNombreProducto);

        contentPane.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        contentPane.add(txtCategoria);

        contentPane.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        contentPane.add(txtPrecio);

        contentPane.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        contentPane.add(txtCantidad);

        contentPane.add(new JLabel("Fecha Ingreso (YYYY-MM-DD):"));
        txtFechaIngreso = new JTextField();
        contentPane.add(txtFechaIngreso);

        contentPane.add(new JLabel("Fecha Caducidad (YYYY-MM-DD):"));
        txtFechaCaducidad = new JTextField();
        contentPane.add(txtFechaCaducidad);

        contentPane.add(new JLabel("Conservar:"));
        txtConservar = new JTextField();
        contentPane.add(txtConservar);

        contentPane.add(new JLabel("Empresa:"));
        txtEmpresa = new JTextField();
        contentPane.add(txtEmpresa);

        contentPane.add(new JLabel("Código de Barras:"));
        txtCodigoBarras = new JTextField();
        contentPane.add(txtCodigoBarras);

        contentPane.add(new JLabel("Personal Registro:"));
        contentPane.add(txtPersonalRegistro);

        btnGuardar = new JButton("Guardar Reporte");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarReporte();
            }
        });
        contentPane.add(new JLabel("")); // Espacio
        contentPane.add(btnGuardar);
    }

    private void guardarReporte() {
        String nombreProducto = txtNombreProducto.getText();
        String categoria = txtCategoria.getText();
        String precioStr = txtPrecio.getText();
        String cantidadStr = txtCantidad.getText();
        String fechaIngresoStr = txtFechaIngreso.getText();
        String fechaCaducidadStr = txtFechaCaducidad.getText();
        String conservar = txtConservar.getText();
        String empresa = txtEmpresa.getText();
        String codigoBarras = txtCodigoBarras.getText();
        String personalRegistro = txtPersonalRegistro.getText();

        if (nombreProducto.isEmpty() || categoria.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombre Producto, Categoría, Precio y Cantidad son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            int cantidad = Integer.parseInt(cantidadStr);
            java.sql.Date fechaIngreso = null;
            java.sql.Date fechaCaducidad = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!fechaIngresoStr.isEmpty()) {
                java.util.Date parsedDate = dateFormat.parse(fechaIngresoStr);
                fechaIngreso = new java.sql.Date(parsedDate.getTime());
            }

            if (!fechaCaducidadStr.isEmpty()) {
                java.util.Date parsedDate = dateFormat.parse(fechaCaducidadStr);
                fechaCaducidad = new java.sql.Date(parsedDate.getTime());
            }

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String sql = "INSERT INTO reporte (nombre_producto, categoria, precio, cantidad, fecha_ingreso, fecha_caducidad, conservar, empresa, codigo_barras, personal_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nombreProducto);
                pstmt.setString(2, categoria);
                pstmt.setDouble(3, precio);
                pstmt.setInt(4, cantidad);
                pstmt.setDate(5, fechaIngreso);
                pstmt.setDate(6, fechaCaducidad);
                pstmt.setString(7, conservar);
                pstmt.setString(8, empresa);
                pstmt.setString(9, codigoBarras);
                pstmt.setString(10, personalRegistro);

                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Reporte guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
                try { if (conn != null) conn.close(); } catch (SQLException ex) {}
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio y la cantidad deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "El formato de fecha debe ser YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombreProducto.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtFechaIngreso.setText("");
        txtFechaCaducidad.setText("");
        txtConservar.setText("");
        txtEmpresa.setText("");
        txtCodigoBarras.setText("");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FrmIngresarReporte frame = new FrmIngresarReporte("Nombre de Usuario Prueba"); // Pasar el nombre del usuario
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
