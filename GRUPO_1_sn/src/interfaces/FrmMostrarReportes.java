// interfaces/FrmMostrarReportes.java
package interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FrmMostrarReportes extends JFrame {

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;
    private JButton btnBorrarReporte;
    private int reporteSeleccionadoId = -1;
    private String usuarioRol; // Para almacenar el rol del usuario actual

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba"; // Reemplaza con tu URL si es diferente
    private static final String DB_USER = "root";       // Reemplaza con tu usuario si es diferente
    private static final String DB_PASSWORD = "1234";   // Reemplaza con tu contraseña si es diferente

    public FrmMostrarReportes(String rolUsuario) {
        this.usuarioRol = rolUsuario;
        setTitle("Reportes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650); // Aumentamos el tamaño para el botón
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre Producto", "Categoría", "Precio", "Cantidad", "Fecha Ingreso", "Fecha Caducidad", "Conservar", "Empresa", "Código Barras", "Personal Registro"}, 0);
        tablaReportes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaReportes);

        JPanel panelBotones = new JPanel();
        btnBorrarReporte = new JButton("Borrar Reporte");
        panelBotones.add(btnBorrarReporte);

        cargarReportes();

        // Solo mostrar el botón de borrar si el usuario es administrador
        if (!usuarioRol.equals("Administrador")) {
            btnBorrarReporte.setVisible(false);
        } else {
            tablaReportes.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && tablaReportes.getSelectedRow() != -1) {
                    int filaSeleccionada = tablaReportes.getSelectedRow();
                    reporteSeleccionadoId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                    btnBorrarReporte.setEnabled(true);
                } else {
                    reporteSeleccionadoId = -1;
                    btnBorrarReporte.setEnabled(false);
                }
            });

            btnBorrarReporte.setEnabled(false);
            btnBorrarReporte.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (reporteSeleccionadoId != -1) {
                        FrmBorrarReporte borrarReporteForm = new FrmBorrarReporte(reporteSeleccionadoId, FrmMostrarReportes.this);
                        borrarReporteForm.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(FrmMostrarReportes.this, "Seleccione un reporte para borrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
        }

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void cargarReportes() {
        modeloTabla.setRowCount(0);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM reporte";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreProducto = rs.getString("nombre_producto");
                String categoria = rs.getString("categoria");
                double precio = rs.getDouble("precio");
                int cantidad = rs.getInt("cantidad");
                Date fechaIngreso = rs.getDate("fecha_ingreso");
                Date fechaCaducidad = rs.getDate("fecha_caducidad");
                String conservar = rs.getString("conservar");
                String empresa = rs.getString("empresa");
                String codigoBarras = rs.getString("codigo_barras");
                String personalRegistro = rs.getString("personal_registro");
                modeloTabla.addRow(new Object[]{id, nombreProducto, categoria, precio, cantidad, fechaIngreso, fechaCaducidad, conservar, empresa, codigoBarras, personalRegistro});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar reportes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                FrmMostrarReportes frame = new FrmMostrarReportes("Regular"); // Ejemplo con rol regular
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}