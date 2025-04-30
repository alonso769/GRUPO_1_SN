// FrmProductos.java
package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

public class FrmProductos extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombreProducto;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cmbCategoria;
    private List<Object[]> listaProductos = new ArrayList<>(); // Inicializar la lista
    private static final long serialVersionUID = 1L;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmProductos frame = new FrmProductos();
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
    public FrmProductos() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 550);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("GESTIÓN DE PRODUCTOS");
        lblTitulo.setForeground(new Color(0, 100, 0));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(250, 30, 250, 20);
        contentPane.add(lblTitulo);

        JLabel lblNombreProducto = new JLabel("Nombre Producto:");
        lblNombreProducto.setForeground(new Color(70, 130, 180));
        lblNombreProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombreProducto.setBounds(50, 80, 130, 20);
        contentPane.add(lblNombreProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtNombreProducto.setBounds(190, 77, 250, 25);
        contentPane.add(txtNombreProducto);
        txtNombreProducto.setColumns(10);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setForeground(new Color(70, 130, 180));
        lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCategoria.setBounds(50, 120, 100, 20);
        contentPane.add(lblCategoria);

        cmbCategoria = new JComboBox<>();
        cmbCategoria.setModel(new DefaultComboBoxModel<>(new String[] {"Electrónicos", "Ropa", "Alimentos", "Libros", "Otros"}));
        cmbCategoria.setBorder(new LineBorder(Color.LIGHT_GRAY));
        cmbCategoria.setBounds(190, 117, 150, 25);
        contentPane.add(cmbCategoria);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setForeground(new Color(70, 130, 180));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPrecio.setBounds(50, 160, 70, 20);
        contentPane.add(lblPrecio);

        txtPrecio = new JTextField();
        txtPrecio.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtPrecio.setBounds(190, 157, 100, 25);
        contentPane.add(txtPrecio);
        txtPrecio.setColumns(10);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setForeground(new Color(70, 130, 180));
        lblCantidad.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCantidad.setBounds(50, 200, 80, 20);
        contentPane.add(lblCantidad);

        txtCantidad = new JTextField();
        txtCantidad.setBorder(new LineBorder(Color.LIGHT_GRAY));
        txtCantidad.setBounds(190, 197, 100, 25);
        contentPane.add(txtCantidad);
        txtCantidad.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombreProducto.getText();
                String categoria = (String) cmbCategoria.getSelectedItem();
                String precioStr = txtPrecio.getText();
                String cantidadStr = txtCantidad.getText();

                modeloTabla.addRow(new Object[]{nombre, categoria, precioStr, cantidadStr});
                listaProductos.add(new Object[]{nombre, categoria, precioStr, cantidadStr});

                // Limpiar campos después de registrar
                txtNombreProducto.setText("");
                txtPrecio.setText("");
                txtCantidad.setText("");
            }
        });
        btnRegistrar.setBackground(new Color(144, 238, 144));
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBounds(190, 240, 120, 30);
        contentPane.add(btnRegistrar);

        JButton btnConsultarProductos = new JButton("Consultar Productos");
        btnConsultarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Consultas consultasForm = new Consultas(listaProductos); // Pasa la lista
                consultasForm.frame.setVisible(true);
                FrmProductos.this.dispose(); // Cierra FrmProductos
            }
        });
        btnConsultarProductos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsultarProductos.setBackground(new Color(173, 216, 230));
        btnConsultarProductos.setBounds(350, 240, 180, 30);
        contentPane.add(btnConsultarProductos);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 290, 650, 200);
        contentPane.add(scrollPane);

        tablaProductos = new JTable();
        modeloTabla = new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "Nombre", "Categoría", "Precio", "Cantidad"
            }
        );
        tablaProductos.setModel(modeloTabla);
        scrollPane.setViewportView(tablaProductos);
    }
}