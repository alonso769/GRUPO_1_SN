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
import javax.swing.JOptionPane;

import java.sql.Connection; // Importar clases de SQL
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Consultas {

    JFrame frame;
    private JTextField txtBuscarNombreDescripcion;
    private JTable tablaResultados;
    private DefaultTableModel modeloTablaResultados;
    private JComboBox<String> comboCategorias;

    private String usuarioRol;

    private static final long serialVersionUID = 1L;

    // Constantes de conexión a la base de datos (asumimos las mismas que en FrmClientes)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Para propósitos de prueba, pasar un rol por defecto
                    // Ahora Consultas cargará los datos directamente de la BD
                    Consultas window = new Consultas("Administrador"); // Pasar un rol de ejemplo
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Constructor de Consultas.
     * Ahora solo recibe el rol del usuario.
     */
    public Consultas(String userRole) {
        this.usuarioRol = userRole; // Almacenar el rol
        initialize();
        cargarCategoriasDesdeBD(); // Cargar categorías desde la BD primero
        cargarProductosDesdeBD("", "Todas"); // Cargar todos los productos al iniciar (filtros vacíos)
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
        // Las categorías se cargarán desde la BD después de initialize()
        comboCategorias.setBounds(190, 117, 150, 25);
        contentPane.add(comboCategorias);


        JButton btnBuscar = new JButton("BUSCAR");
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Al buscar, pasamos el texto del campo y la categoría seleccionada
                buscarProductosDesdeBD(txtBuscarNombreDescripcion.getText().trim(), (String) comboCategorias.getSelectedItem());
            }
        });
        btnBuscar.setBounds(460, 77, 120, 25);
        btnBuscar.setBackground(new Color(173, 216, 230));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setFocusPainted(false);
        contentPane.add(btnBuscar);

        modeloTablaResultados = new DefaultTableModel();
        modeloTablaResultados.addColumn("Nombre"); // Cambiado de "Producto" a "Nombre" para coincidir con FrmProductos
        modeloTablaResultados.addColumn("Categoría");
        modeloTablaResultados.addColumn("Precio");
        modeloTablaResultados.addColumn("Cantidad");

        tablaResultados = new JTable(modeloTablaResultados);
        tablaResultados.setBackground(new Color(255, 255, 240));
        tablaResultados.setForeground(Color.BLACK);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaResultados.setSelectionBackground(new Color(173, 216, 230));
        tablaResultados.setSelectionForeground(Color.BLACK);
         tablaResultados.setDefaultEditor(Object.class, null);


        JScrollPane scrollPaneResultados = new JScrollPane(tablaResultados);
        scrollPaneResultados.setBounds(30, 160, 680, 280);
        contentPane.add(scrollPaneResultados);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Al regresar, pasamos el rol almacenado
                FrmProductos productosForm = new FrmProductos(usuarioRol);
                productosForm.setVisible(true);
                frame.dispose();
            }
        });
        btnRegresar.setBounds(580, 450, 120, 30);
        btnRegresar.setBackground(new Color(255, 182, 193));
        btnRegresar.setForeground(Color.BLACK);
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegresar.setFocusPainted(false);
        contentPane.add(btnRegresar);
    }

    /**
     * Carga productos desde la base de datos aplicando filtros opcionales.
     */
    private void cargarProductosDesdeBD(String nombreFiltro, String categoriaFiltro) {
         modeloTablaResultados.setRowCount(0); // Limpiar tabla

         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;

         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             String sql = "SELECT nombre, categoria, precio, cantidad FROM productos WHERE 1=1"; // Condición base siempre verdadera
             List<Object> params = new ArrayList<>();

             // Si hay filtro por nombre, añadir a la consulta SQL
             if (nombreFiltro != null && !nombreFiltro.isEmpty()) {
                 sql += " AND LOWER(nombre) LIKE ?";
                 params.add("%" + nombreFiltro.toLowerCase() + "%");
             }

             // Si hay filtro por categoría (y no es "Todas")
             if (categoriaFiltro != null && !categoriaFiltro.equals("Todas")) {
                  sql += " AND categoria = ?";
                  params.add(categoriaFiltro);
             }


             pstmt = conn.prepareStatement(sql);

             // Establecer los parámetros de la consulta
             for (int i = 0; i < params.size(); i++) {
                 pstmt.setObject(i + 1, params.get(i));
             }


             rs = pstmt.executeQuery();

             // Llenar la tabla con los resultados
             boolean found = false;
             while (rs.next()) {
                 modeloTablaResultados.addRow(new Object[]{
                         rs.getString("nombre"),
                         rs.getString("categoria"),
                         rs.getDouble("precio"), // Asumiendo que precio es DOUBLE/DECIMAL en BD
                         rs.getInt("cantidad")    // Asumiendo que cantidad es INT en BD
                 });
                 found = true;
             }

              if (!found && (nombreFiltro != null && !nombreFiltro.isEmpty() || (categoriaFiltro != null && !categoriaFiltro.equals("Todas")))) {
                   // Mostrar mensaje solo si se aplicaron filtros y no hubo resultados
                   JOptionPane.showMessageDialog(frame, "No se encontraron productos con los criterios especificados.", "Información", JOptionPane.INFORMATION_MESSAGE);
              }


         } catch (SQLException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(frame, "Error al cargar productos desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         } catch (ClassNotFoundException ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(frame, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
         } finally {
             try { if (rs != null) rs.close(); } catch (SQLException ex) {}
             try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
             try { if (conn != null) conn.close(); } catch (SQLException ex) {}
         }
    }

    /**
     * Realiza la búsqueda de productos en la base de datos.
     */
    private void buscarProductosDesdeBD(String criterioNombre, String categoriaSeleccionada) {
        // Simplemente llamamos a cargarProductosDesdeBD con los filtros
        cargarProductosDesdeBD(criterioNombre, categoriaSeleccionada);
    }


    /**
     * Obtiene las categorías únicas de la base de datos.
     */
    private void cargarCategoriasDesdeBD() {
        comboCategorias.removeAllItems(); // Limpiar items existentes
        comboCategorias.addItem("Todas"); // Agregar opción "Todas"

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Seleccionar categorías distintas de la tabla de productos
            String sql = "SELECT DISTINCT categoria FROM productos WHERE categoria IS NOT NULL AND categoria != '' ORDER BY categoria";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Agregar categorías al JComboBox
            while (rs.next()) {
                comboCategorias.addItem(rs.getString("categoria"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al cargar categorías desde la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }
}
