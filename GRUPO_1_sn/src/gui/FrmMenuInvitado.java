// FrmMenuInvitado.java
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
import javax.swing.JOptionPane; // Importar JOptionPane para mensajes de error

// Importar FrmProductos
import gui.FrmProductos;

public class FrmMenuInvitado {

    public JFrame frame;

    /**
     * Lanza la aplicación.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmMenuInvitado window = new FrmMenuInvitado();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Crea la aplicación.
     */
    public FrmMenuInvitado() {
        initialize();
    }

    /**
     * Inicializa el contenido del frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sistema de Pedidos - Menú Invitado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Arial", Font.PLAIN, 14));
        menuBar.setBackground(new Color(240, 240, 240));
        frame.setJMenuBar(menuBar);

        JMenu mnVerProductos = new JMenu("Ver Productos");
        mnVerProductos.setFont(new Font("Arial", Font.BOLD, 16));
        mnVerProductos.setForeground(new Color(0, 0, 128));
        menuBar.add(mnVerProductos);

        JMenuItem mntmListaProductos = new JMenuItem("Lista de Productos");
        mntmListaProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        mntmListaProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Pasar el rol "Invitado" al constructor de FrmProductos
                    FrmProductos productosForm = new FrmProductos("Invitado");
                    productosForm.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al abrir la ventana de Productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mnVerProductos.add(mntmListaProductos);

        JMenu mnInformacion = new JMenu("Información");
        mnInformacion.setFont(new Font("Arial", Font.BOLD, 16));
        mnInformacion.setForeground(new Color(0, 0, 128));
        menuBar.add(mnInformacion);

        JMenuItem mntmContacto = new JMenuItem("Contacto");
        mntmContacto.setFont(new Font("Arial", Font.PLAIN, 14));
        mnInformacion.add(mntmContacto);

        JMenuItem mntmAcerca = new JMenuItem("Acerca del Sistema");
        mntmAcerca.setFont(new Font("Arial", Font.PLAIN, 14));
        mnInformacion.add(mntmAcerca);
    }
}
