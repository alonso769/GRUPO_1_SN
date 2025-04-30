// FrmMenuPrincipal.java
package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import interfaces.FrmMostrarReportes; // Import FrmMostrarReportes
import javax.swing.JOptionPane;

import gui.FrmProductos; // Import FrmProductos (Assuming it's in the gui package)


// NOTE: This class assumes it's opened for a "Regular" user.
// If your application's login process passes the user/role to this menu,
// you should modify this class similarly to FrmMenuAdministrador
// to fetch and store the specific logged-in user's role.
// For this example, we assume anyone seeing this menu is "Regular".

public class FrmMenuPrincipal {

    public JFrame frame;
    // Fields for user/role if you want to pass them dynamically like Admin menu
    // private String usuarioLogueado;
    // private String rolUsuarioLogueado = "Regular"; // Assume default role

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                     // For testing the menu directly, simulate opening for a Regular user
                     // If your login passes a user, you would modify this.
                     FrmMenuPrincipal window = new FrmMenuPrincipal();
                     window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FrmMenuPrincipal() {
        // If user/role were passed from login, this constructor would take them.
        // E.g., public FrmMenuPrincipal(String user, String role) { ... }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame(); // Inicialización de frame aquí
        frame.setTitle("Sistema de Pedidos - Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Arial", Font.PLAIN, 14));
        menuBar.setBackground(new Color(240, 240, 240));
        frame.setJMenuBar(menuBar);

        JMenu mnMantenimiento = new JMenu("Mantenimiento");
        mnMantenimiento.setFont(new Font("Arial", Font.BOLD, 16));
        mnMantenimiento.setForeground(new Color(0, 100, 0));
        menuBar.add(mnMantenimiento);

        JMenuItem mntmClientes = new JMenuItem("Clientes");
        mntmClientes.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Pass the role "Regular" to FrmClientes when opened from this menu
                // If this menu got the role dynamically, pass that variable instead.
                FrmClientes clientesForm = new FrmClientes("Regular");
                clientesForm.setVisible(true);
            }
        });
        mnMantenimiento.add(mntmClientes);

        JMenuItem mntmProductos = new JMenuItem("Productos");
        mntmProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the FrmProductos form
                // Pass the role "Regular" to FrmProductos (assuming its constructor accepts it)
                try {
                    FrmProductos productosForm = new FrmProductos("Regular"); // Pass the role
                    productosForm.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al abrir la ventana de Productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mnMantenimiento.add(mntmProductos);

        // Remove the "Usuarios" menu item as it's typically admin-only
        // JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
        // mntmUsuarios.setFont(new Font("Arial", Font.PLAIN, 14));
        // mnMantenimiento.add(mntmUsuarios);


        JMenu mnOperaciones = new JMenu("Operaciones");
        mnOperaciones.setFont(new Font("Arial", Font.BOLD, 16));
        mnOperaciones.setForeground(new Color(0, 100, 0));
        menuBar.add(mnOperaciones);

        // Remove "Ingresar Reporte" as it was restricted to Admin
        // JMenuItem mntmIngresarReporte = new JMenuItem("Ingresar Reporte");
        // mntmIngresarReporte.setFont(new Font("Arial", Font.PLAIN, 14));
        // mnOperaciones.add(mntmIngresarReporte);


        JMenu mnReportes = new JMenu("Reportes");
        mnReportes.setFont(new Font("Arial", Font.BOLD, 16));
        mnReportes.setForeground(new Color(0, 100, 0));
        menuBar.add(mnReportes);

        JMenuItem mntmMostrarReportes = new JMenuItem("Mostrar Reportes");
        mntmMostrarReportes.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmMostrarReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // FrmMostrarReportes already seems to handle roles based on its constructor
                // Pass "Regular" role to FrmMostrarReportes
                FrmMostrarReportes mostrarReportesForm = new FrmMostrarReportes("Regular");
                mostrarReportesForm.setVisible(true);
            }
        });
        mnReportes.add(mntmMostrarReportes);


        JMenu mnAcercaDe = new JMenu("Acerca de");
        mnAcercaDe.setFont(new Font("Arial", Font.BOLD, 16));
        mnAcercaDe.setForeground(new Color(0, 100, 0));
        menuBar.add(mnAcercaDe);

        JMenu mnSalir = new JMenu("Salir");
        mnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        mnSalir.setForeground(new Color(255, 0, 0));
        menuBar.add(mnSalir);

        JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar Sesión");
        mntmCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmLogin loginForm = new FrmLogin();
                loginForm.setVisible(true);
                frame.dispose(); // Close the current menu frame
            }
        });
        mnSalir.add(mntmCerrarSesion);
    }
}