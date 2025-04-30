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

public class FrmMenuPrincipal {

    public JFrame frame; // Asegúrate de que 'frame' sea public

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
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
                FrmClientes clientesForm = new FrmClientes();
                clientesForm.setVisible(true);
            }
        });
        mnMantenimiento.add(mntmClientes);

        JMenuItem mntmProductos = new JMenuItem("Productos");
        mntmProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FrmProductos productosForm = new FrmProductos();
                productosForm.setVisible(true);
            }
        });
        mnMantenimiento.add(mntmProductos);

        JMenu mnOperaciones = new JMenu("Operaciones");
        mnOperaciones.setFont(new Font("Arial", Font.BOLD, 16));
        mnOperaciones.setForeground(new Color(0, 100, 0));
        menuBar.add(mnOperaciones);

        JMenu mnReportes = new JMenu("Reportes");
        mnReportes.setFont(new Font("Arial", Font.BOLD, 16));
        mnReportes.setForeground(new Color(0, 100, 0));
        menuBar.add(mnReportes);

        JMenu mnAcercaDe = new JMenu("Acerca de");
        mnAcercaDe.setFont(new Font("Arial", Font.BOLD, 16));
        mnAcercaDe.setForeground(new Color(0, 100, 0));
        menuBar.add(mnAcercaDe);
    }
}