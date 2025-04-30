package gui;

import java.awt.EventQueue;
import java.awt.Font;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel; // Importar DefaultComboBoxModel

public class FrmClientes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNroDocumento;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtRUC;
    private JTable tblClientes;
    private DefaultTableModel modeloTablaClientes;
    private final ButtonGroup tipoPersonaGroup = new ButtonGroup();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmClientes frame = new FrmClientes();
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
    public FrmClientes() {
        setTitle("Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTipoDocumento = new JLabel("Tipo Documento:");
        lblTipoDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTipoDocumento.setBounds(20, 20, 120, 20);
        contentPane.add(lblTipoDocumento);

        JComboBox<String> cboTipoDocumento = new JComboBox<>();
        cboTipoDocumento.setModel(new DefaultComboBoxModel<>(new String[] {"DNI - Documento Nacional", "RUC - Registro Único de Contribuyentes", "Pasaporte"}));
        cboTipoDocumento.setBounds(150, 20, 200, 22);
        contentPane.add(cboTipoDocumento);

        JLabel lblNroDocumento = new JLabel("Nro. Documento:");
        lblNroDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNroDocumento.setBounds(20, 50, 120, 20);
        contentPane.add(lblNroDocumento);

        txtNroDocumento = new JTextField();
        txtNroDocumento.setBounds(150, 50, 150, 22);
        contentPane.add(txtNroDocumento);
        txtNroDocumento.setColumns(10);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNombres.setBounds(20, 80, 120, 20);
        contentPane.add(lblNombres);

        txtNombres = new JTextField();
        txtNombres.setBounds(150, 80, 250, 22);
        contentPane.add(txtNombres);
        txtNombres.setColumns(10);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblApellidos.setBounds(20, 110, 120, 20);
        contentPane.add(lblApellidos);

        txtApellidos = new JTextField();
        txtApellidos.setBounds(150, 110, 250, 22);
        contentPane.add(txtApellidos);
        txtApellidos.setColumns(10);

        JLabel lblTipoPersona = new JLabel("Tipo de Persona:");
        lblTipoPersona.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblTipoPersona.setBounds(380, 20, 100, 20);
        contentPane.add(lblTipoPersona);

        JRadioButton rdbtnNatural = new JRadioButton("Natural");
        tipoPersonaGroup.add(rdbtnNatural);
        rdbtnNatural.setSelected(true);
        rdbtnNatural.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnNatural.setBounds(490, 20, 80, 21);
        contentPane.add(rdbtnNatural);

        JRadioButton rdbtnJuridica = new JRadioButton("Jurídica");
        tipoPersonaGroup.add(rdbtnJuridica);
        rdbtnJuridica.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnJuridica.setBounds(570, 20, 80, 21);
        contentPane.add(rdbtnJuridica);

        JLabel lblRUC = new JLabel("RUC:");
        lblRUC.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblRUC.setBounds(380, 50, 100, 20);
        contentPane.add(lblRUC);

        txtRUC = new JTextField();
        txtRUC.setBounds(490, 50, 150, 22);
        contentPane.add(txtRUC);
        txtRUC.setColumns(10);

        JLabel lblEstado = new JLabel("Estado actual:");
        lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblEstado.setBounds(380, 80, 100, 20);
        contentPane.add(lblEstado);

        JComboBox<String> cboEstado = new JComboBox<>();
        cboEstado.setModel(new DefaultComboBoxModel<>(new String[] {"Activo", "Inactivo"}));
        cboEstado.setBounds(490, 80, 100, 22);
        contentPane.add(cboEstado);

        JLabel lblActivo = new JLabel("Activo:"); // Esto parece redundante con "Estado actual"
        lblActivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblActivo.setBounds(380, 110, 100, 20);
        contentPane.add(lblActivo);

        JRadioButton rdbtnActivo = new JRadioButton("Sí");
        rdbtnActivo.setSelected(true);
        rdbtnActivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnActivo.setBounds(490, 110, 50, 21);
        contentPane.add(rdbtnActivo);

        JRadioButton rdbtnInactivo = new JRadioButton("No");
        rdbtnInactivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rdbtnInactivo.setBounds(540, 110, 50, 21);
        contentPane.add(rdbtnInactivo);

        ButtonGroup estadoGroup = new ButtonGroup();
        estadoGroup.add(rdbtnActivo);
        estadoGroup.add(rdbtnInactivo);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAgregar.setBounds(450, 150, 100, 25);
        contentPane.add(btnAgregar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnBuscar.setBounds(560, 150, 100, 25);
        contentPane.add(btnBuscar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnActualizar.setBounds(450, 185, 100, 25);
        contentPane.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnEliminar.setBounds(560, 185, 100, 25);
        contentPane.add(btnEliminar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 220, 640, 200);
        contentPane.add(scrollPane);

        tblClientes = new JTable();
        modeloTablaClientes = new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "Nombre", "Apellido", "Tipo Doc.", "Nro. Doc.", "Tipo Persona", "RUC", "Estado"
            }
        );
        tblClientes.setModel(modeloTablaClientes);
        scrollPane.setViewportView(tblClientes);
    }
}
