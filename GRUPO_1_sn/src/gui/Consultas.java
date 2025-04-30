// Consultas.java
package gui;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Consultas {

    JFrame frame;
    private JTextField txtBuscarNombreDescripcion;
    private JTable tablaResultados;
    private DefaultTableModel modeloTablaResultados;
    private List<Object[]> listaProductosRegistrados;
    private JComboBox<String> comboCategorias;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Consultas window = new Consultas(new ArrayList<>());
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Consultas(List<Object[]> productosRegistrados) {
        this.listaProductosRegistrados = productosRegistrados;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 750, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Consultas de Productos");

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 2));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("BÚSQUEDA DE PRODUCTOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(250, 30, 260, 30);
        lblTitulo.setForeground(new Color(0, 100, 0));
        contentPane.add(lblTitulo);

        JLabel lblBuscarNombreDescripcion = new JLabel("Buscar por Nombre:");
        lblBuscarNombreDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscarNombreDescripcion.setBounds(30, 80, 150, 20);
        lblBuscarNombreDescripcion.setForeground(new Color(70, 130, 180));
        contentPane.add(lblBuscarNombreDescripcion);

        txtBuscarNombreDescripcion = new JTextField();
        txtBuscarNombreDescripcion.setBounds(190, 77, 250, 25);
        contentPane.add(txtBuscarNombreDescripcion);
        txtBuscarNombreDescripcion.setColumns(10);
        txtBuscarNombreDescripcion.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel lblBuscarCategoria = new JLabel("Buscar por Categoría:");
        lblBuscarCategoria.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBuscarCategoria.setBounds(30, 120, 150, 20);
        lblBuscarCategoria.setForeground(new Color(70, 130, 180));
        contentPane.add(lblBuscarCategoria);

        comboCategorias = new JComboBox<>();
        comboCategorias.setModel(new DefaultComboBoxModel<>(obtenerCategorias()));
        comboCategorias.setBounds(190, 117, 150, 25);
        contentPane.add(comboCategorias);
        comboCategorias.insertItemAt("Todas", 0);
        comboCategorias.setSelectedIndex(0);

        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String criterioNombre = txtBuscarNombreDescripcion.getText().toLowerCase();
                String categoriaSeleccionada = (String) comboCategorias.getSelectedItem();

                modeloTablaResultados.setRowCount(0);

                for (Object[] producto : listaProductosRegistrados) {
                    String nombre = ((String) producto[0]).toLowerCase();
                    String categoriaProducto = ((String) producto[1]);
                    String precio = ((String) producto[2]);
                    String cantidad = ((String) producto[3]);

                    boolean coincideNombre = nombre.contains(criterioNombre);
                    boolean coincideCategoria = categoriaSeleccionada.equals("Todas") || categoriaProducto.equals(categoriaSeleccionada);

                    if (coincideNombre && coincideCategoria) {
                        modeloTablaResultados.addRow(new Object[]{nombre, categoriaProducto, precio, cantidad});
                    }
                }
            }
        });
        btnBuscar.setBounds(460, 77, 120, 25);
        btnBuscar.setBackground(new Color(173, 216, 230));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setFocusPainted(false);
        contentPane.add(btnBuscar);

        modeloTablaResultados = new DefaultTableModel();
        modeloTablaResultados.addColumn("Producto");
        modeloTablaResultados.addColumn("Categoría");
        modeloTablaResultados.addColumn("Precio");
        modeloTablaResultados.addColumn("Cantidad");

        tablaResultados = new JTable(modeloTablaResultados);
        tablaResultados.setBackground(new Color(255, 255, 240));
        tablaResultados.setForeground(Color.BLACK);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaResultados.setSelectionBackground(new Color(173, 216, 230));
        tablaResultados.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPaneResultados = new JScrollPane(tablaResultados);
        scrollPaneResultados.setBounds(30, 160, 680, 280);
        contentPane.add(scrollPaneResultados);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmProductos productosForm = new FrmProductos();
                productosForm.setVisible(true);
                frame.dispose(); // Cierra el JFrame de Consultas directamente
            }
        });
        btnRegresar.setBounds(580, 450, 120, 30);
        btnRegresar.setBackground(new Color(255, 182, 193));
        btnRegresar.setForeground(Color.BLACK);
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegresar.setFocusPainted(false);
        contentPane.add(btnRegresar);
    }

    private String[] obtenerCategorias() {
        List<String> categorias = new ArrayList<>();
        for (Object[] producto : listaProductosRegistrados) {
            String categoria = (String) producto[1];
            if (!categorias.contains(categoria)) {
                categorias.add(categoria);
            }
        }
        return categorias.toArray(new String[0]);
    }
}