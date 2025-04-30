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
import javax.swing.JOptionPane;

// Importar la clase Consultas
import gui.Consultas;


public class FrmProductos extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombreProducto;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cmbCategoria;
    private JButton btnRegistrar;
    private JButton btnConsultarProductos;

    // Ya no necesitamos esta lista para pasarla a Consultas
    // private List<Object[]> listaProductos = new ArrayList<>();

    private String usuarioRol;

    private static final long serialVersionUID = 1L;

    // TODO: Agregar constantes de conexión a BD si registrarProducto() las necesita
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    // private static final String DB_USER = "root";
    // private static final String DB_PASSWORD = "1234";


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmProductos frame = new FrmProductos("Administrador");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public FrmProductos(String userRole) {
        this.usuarioRol = userRole;

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

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarProducto();
            }
        });
        btnRegistrar.setBackground(new Color(144, 238, 144));
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBounds(190, 240, 120, 30);
        contentPane.add(btnRegistrar);

        btnConsultarProductos = new JButton("Consultar Productos");
        btnConsultarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear y mostrar el formulario Consultas, pasando el rol
                    // Consultas ahora cargará los datos directamente de la BD
                    Consultas consultasForm = new Consultas(usuarioRol);
                    consultasForm.frame.setVisible(true);
                    // Opcional: Ocultar o cerrar FrmProductos si no quieres tener ambas abiertas
                    // FrmProductos.this.setVisible(false);
                    // FrmProductos.this.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                     JOptionPane.showMessageDialog(FrmProductos.this, "Error al abrir la ventana de Consultas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos.setModel(modeloTabla);
        scrollPane.setViewportView(tablaProductos);

        configureButtonsByRole();

        // TODO: Implementar la carga de productos existentes desde base de datos para esta tabla si se desea mostrar
        // cargarProductosEnTablaFrmProductos(); // Llamar a esto si se quiere mostrar la lista aquí también

    }

    private void configureButtonsByRole() {
        if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
            btnRegistrar.setVisible(false);
            // Mantener los campos de entrada editables/habilitados para usuarios regulares
            txtNombreProducto.setEditable(true);
            cmbCategoria.setEnabled(true);
            txtPrecio.setEditable(true);
            txtCantidad.setEditable(true);

            // El botón Consultar Productos permanece visible para todos los roles

        }
    }

    private void registrarProducto() {
         if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
              JOptionPane.showMessageDialog(this, "Solo los administradores pueden registrar productos.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
              return;
         }

         String nombre = txtNombreProducto.getText().trim();
         String categoria = (String) cmbCategoria.getSelectedItem();
         String precioStr = txtPrecio.getText().trim();
         String cantidadStr = txtCantidad.getText().trim();

         if (nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
         }

         try {
             double precio = Double.parseDouble(precioStr);
             int cantidad = Integer.parseInt(cantidadStr);

             // TODO: Implementar el guardado en la base de datos aquí
             // Esto implicaría pasos similares a los de FrmClientes:
             // 1. Obtener conexión a la base de datos
             // 2. Preparar la sentencia INSERT
             // 3. Establecer parámetros
             // 4. Ejecutar la actualización
             // 5. Manejar éxito/fracaso y cerrar recursos

             // --- Lógica temporal en memoria (Eliminar o adaptar si se usa BD) ---
             // Si se guarda en BD, la tabla en FrmProductos debería recargarse desde BD
             // o actualizarse individualmente si se desea mostrar aquí también.
             // Por ahora, solo agregamos a la tabla local para visualización inmediata
             modeloTabla.addRow(new Object[]{nombre, categoria, precio, cantidad});
             // la listaProductos en memoria ya no se usa para pasar a Consultas
             // listaProductos.add(new Object[]{nombre, categoria, precio, cantidad});
             // ---------------------------------------------------------------------


             JOptionPane.showMessageDialog(this, "Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

             txtNombreProducto.setText("");
             txtPrecio.setText("");
             txtCantidad.setText("");
             cmbCategoria.setSelectedIndex(0);

         } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Precio y Cantidad deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
         }
    }

    // TODO: Implementar métodos para cargar, buscar, actualizar, eliminar productos de la base de datos
    // private void cargarProductosEnTablaFrmProductos() { ... } // Para mostrar en esta tabla
    // private void buscarProductoEnFrmProductos() { ... } // Si se añade búsqueda aquí
    // private void actualizarProductoEnBD() { ... }
    // private void eliminarProductoDeBD() { ... }
}
