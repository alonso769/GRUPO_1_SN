package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Import Statement class

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel; // Import for table selection listener
import javax.swing.event.ListSelectionEvent; // Import for table selection listener
import javax.swing.event.ListSelectionListener; // Import for table selection listener


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
    private JComboBox<String> cboTipoDocumento;
    private JComboBox<String> cboEstado;
    private JRadioButton rdbtnNatural;
    private JRadioButton rdbtnJuridica;
    private JButton btnAgregar;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private String usuarioRol; // Field to store the user's role

    private static final String DB_URL = "jdbc:mysql://localhost:3306/prueba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // For testing purposes, launch as Administrator by default
                    FrmClientes frame = new FrmClientes("Administrador");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * Added a parameter to receive the user's role.
     */
    public FrmClientes(String userRole) {
        this.usuarioRol = userRole; // Store the passed role

        setTitle("Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.5;

        // Fila 0: Tipo Documento
        JLabel lblTipoDocumento = new JLabel("Tipo Documento:");
        lblTipoDocumento.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(lblTipoDocumento, gbc);

        cboTipoDocumento = new JComboBox<>();
        cboTipoDocumento.setModel(new DefaultComboBoxModel<>(new String[] {"DNI - Documento Nacional", "RUC - Registro Único de Contribuyentes", "Pasaporte"}));
        cboTipoDocumento.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        contentPane.add(cboTipoDocumento, gbc);
        gbc.gridwidth = 1;

        // Fila 1: Nro. Documento
        JLabel lblNroDocumento = new JLabel("Nro. Documento:");
        lblNroDocumento.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(lblNroDocumento, gbc);

        txtNroDocumento = new JTextField();
        txtNroDocumento.setFont(inputFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contentPane.add(txtNroDocumento, gbc);
        gbc.weightx = 0.5;

        // Fila 1: Tipo de Persona
        JLabel lblTipoPersona = new JLabel("Tipo de Persona:");
        lblTipoPersona.setFont(labelFont);
        gbc.gridx = 2;
        contentPane.add(lblTipoPersona, gbc);

        JPanel panelTipoPersona = new JPanel();
        panelTipoPersona.setLayout(new GridBagLayout());
        GridBagConstraints gbcRadio = new GridBagConstraints();
        gbcRadio.insets = new Insets(0, 0, 0, 10);
        gbcRadio.anchor = GridBagConstraints.WEST;

        rdbtnNatural = new JRadioButton("Natural");
        tipoPersonaGroup.add(rdbtnNatural);
        rdbtnNatural.setSelected(true);
        rdbtnNatural.setFont(inputFont);
        gbcRadio.gridx = 0;
        gbcRadio.gridy = 0;
        panelTipoPersona.add(rdbtnNatural, gbcRadio);

        rdbtnJuridica = new JRadioButton("Jurídica");
        tipoPersonaGroup.add(rdbtnJuridica);
        rdbtnJuridica.setFont(inputFont);
        gbcRadio.gridx = 1;
        gbcRadio.gridy = 0;
        panelTipoPersona.add(rdbtnJuridica, gbcRadio);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        contentPane.add(panelTipoPersona, gbc);
        gbc.weightx = 0.5;

        // Fila 2: Nombres
        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(lblNombres, gbc);

        txtNombres = new JTextField();
        txtNombres.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        contentPane.add(txtNombres, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        // Fila 3: Apellidos
        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(lblApellidos, gbc);

        txtApellidos = new JTextField();
        txtApellidos.setFont(inputFont);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        contentPane.add(txtApellidos, gbc);
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        // Fila 4: RUC
        JLabel lblRUC = new JLabel("RUC:");
        lblRUC.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(lblRUC, gbc);

        txtRUC = new JTextField();
        txtRUC.setFont(inputFont);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contentPane.add(txtRUC, gbc);
        gbc.weightx = 0.5;

        // Fila 4: Estado actual
        JLabel lblEstado = new JLabel("Estado actual:");
        lblEstado.setFont(labelFont);
        gbc.gridx = 2;
        contentPane.add(lblEstado, gbc);

        cboEstado = new JComboBox<>();
        cboEstado.setModel(new DefaultComboBoxModel<>(new String[] {"Activo", "Inactivo"}));
        cboEstado.setFont(inputFont);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        contentPane.add(cboEstado, gbc);
        gbc.weightx = 0.5;

        // Fila 5: Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridBagLayout());
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.insets = new Insets(10, 5, 10, 5);
        gbcBtn.fill = GridBagConstraints.HORIZONTAL;
        gbcBtn.weightx = 0.5;

        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(buttonFont);
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 0;
        panelBotones.add(btnAgregar, gbcBtn);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(buttonFont);
        gbcBtn.gridx = 1;
        gbcBtn.gridy = 0;
        panelBotones.add(btnBuscar, gbcBtn);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(buttonFont);
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 1;
        panelBotones.add(btnActualizar, gbcBtn);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(buttonFont);
        gbcBtn.gridx = 1;
        gbcBtn.gridy = 1;
        panelBotones.add(btnEliminar, gbcBtn);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Changed back to HORIZONTAL for button panel
        contentPane.add(panelBotones, gbc);


        // Fila 6: Tabla de Clientes
        JScrollPane scrollPane = new JScrollPane();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.weighty = 1.0; // Give vertical space to the table
        gbc.fill = GridBagConstraints.BOTH; // Make table fill available space
        contentPane.add(scrollPane, gbc);

        tblClientes = new JTable();
        modeloTablaClientes = new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "Tipo Doc.", "Nro. Doc.", "Tipo Persona", "Nombres", "Apellidos", "RUC", "Estado"
                }
        ) {
            // Make table non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblClientes.setModel(modeloTablaClientes);
        tblClientes.setFont(inputFont);
        tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection
        scrollPane.setViewportView(tblClientes);

        // Configure button visibility based on role AFTER they are created
        configureButtonsByRole();

        // Load existing clients into the table when the form opens
        cargarClientes();

        // Add ActionListeners to buttons
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        // Add a listener to the table to populate fields when a row is selected
        tblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Avoid processing intermediate selection events
                if (!e.getValueIsAdjusting()) {
                    llenarCamposDesdeTabla();
                }
            }
        });
    }

    private void configureButtonsByRole() {
        // If the user is NOT an Administrator, hide the modification buttons
        if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
            btnAgregar.setVisible(false);
            btnBuscar.setVisible(false);
            btnActualizar.setVisible(false);
            btnEliminar.setVisible(false);

            // Optionally disable fields for Regular users if you want them to see the data but not modify it
            txtNroDocumento.setEditable(false);
            txtNombres.setEditable(false);
            txtApellidos.setEditable(false);
            txtRUC.setEditable(false);
            cboTipoDocumento.setEnabled(false);
            rdbtnNatural.setEnabled(false);
            rdbtnJuridica.setEnabled(false);
            cboEstado.setEnabled(false);

        }
        // If it's an Administrator, buttons and fields remain visible/editable by default
    }

    private void cargarClientes() {
        // Clear existing data from the table model
        modeloTablaClientes.setRowCount(0);

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT tipo_documento, nro_documento, tipo_persona, nombres, apellidos, ruc, estado FROM clientes";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Populate the table model with data from the database
            while (rs.next()) {
                modeloTablaClientes.addRow(new Object[]{
                        rs.getString("tipo_documento"),
                        rs.getString("nro_documento"),
                        rs.getString("tipo_persona"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("ruc"),
                        rs.getString("estado")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }


    private void registrarCliente() {
        if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
             // This check is also done by hiding the button, but good for safety
             JOptionPane.showMessageDialog(this, "Solo los administradores pueden registrar clientes.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String tipoDocumento = (String) cboTipoDocumento.getSelectedItem();
        String nroDocumento = txtNroDocumento.getText().trim(); // Trim whitespace
        String tipoPersona = rdbtnNatural.isSelected() ? "Natural" : "Jurídica";
        String nombres = txtNombres.getText().trim(); // Trim whitespace
        String apellidos = txtApellidos.getText().trim(); // Trim whitespace
        String ruc = txtRUC.getText().trim(); // Trim whitespace
        String estado = (String) cboEstado.getSelectedItem();

        // Validar que los campos obligatorios no estén vacíos
        if (nroDocumento.isEmpty() || nombres.isEmpty() || (tipoPersona.equals("Natural") && apellidos.isEmpty())) {
            JOptionPane.showMessageDialog(this, "Por favor, complete los campos obligatorios (Nro. Documento, Nombres, y Apellidos para persona Natural).", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
         // Basic validation for RUC if type is Jurídica
         if (tipoPersona.equals("Jurídica") && ruc.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Por favor, complete el campo RUC para persona Jurídica.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
         }


        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Optional: Check if client already exists based on document type and number or RUC
             if (clienteExiste(conn, tipoDocumento, nroDocumento, ruc, tipoPersona)) {
                 JOptionPane.showMessageDialog(this, "Ya existe un cliente con este número de documento o RUC.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                 return;
             }

            String sql = "INSERT INTO clientes (tipo_documento, nro_documento, tipo_persona, nombres, apellidos, ruc, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tipoDocumento);
            pstmt.setString(2, nroDocumento);
            pstmt.setString(3, tipoPersona);
            pstmt.setString(4, nombres);
            pstmt.setString(5, apellidos);
            pstmt.setString(6, ruc);
            pstmt.setString(7, estado);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Add the new row to the table model immediately
                modeloTablaClientes.addRow(new Object[]{
                        tipoDocumento,
                        nroDocumento,
                        tipoPersona,
                        nombres,
                        apellidos,
                        ruc,
                        estado
                });
                limpiarCampos(); // Clear fields after successful add
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
             if (ex.getSQLState() != null && ex.getSQLState().startsWith("23")) { // SQLState for Integrity Constraint Violation
                  JOptionPane.showMessageDialog(this, "Error al registrar cliente: El número de documento o RUC ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
             } else {
                  JOptionPane.showMessageDialog(this, "Error al registrar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    // Helper method to check if a client already exists
    private boolean clienteExiste(Connection conn, String tipoDocumento, String nroDocumento, String ruc, String tipoPersona) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            String sql = "SELECT COUNT(*) FROM clientes WHERE (tipo_documento = ? AND nro_documento = ?)";
            // If juridical, also check RUC
            if (tipoPersona.equals("Jurídica") && !ruc.isEmpty()) {
                 sql += " OR (ruc = ?)";
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tipoDocumento);
            pstmt.setString(2, nroDocumento);
            if (tipoPersona.equals("Jurídica") && !ruc.isEmpty()) {
                pstmt.setString(3, ruc);
            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
        }
        return exists;
    }


    private void buscarCliente() {
         if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
             JOptionPane.showMessageDialog(this, "Solo los administradores pueden buscar clientes.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String tipoDocumento = (String) cboTipoDocumento.getSelectedItem();
        String nroDocumento = txtNroDocumento.getText().trim();
        String ruc = txtRUC.getText().trim(); // Allow searching by RUC too

        if (nroDocumento.isEmpty() && ruc.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Por favor, ingrese un Nro. Documento o RUC para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
        }

        // Clear existing data from the table model before showing search results
        modeloTablaClientes.setRowCount(0);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT tipo_documento, nro_documento, tipo_persona, nombres, apellidos, ruc, estado FROM clientes WHERE 1=1"; // Start with a true condition
            boolean addedCondition = false;

            if (!nroDocumento.isEmpty()) {
                sql += " AND tipo_documento = ? AND nro_documento = ?";
                addedCondition = true;
            }
            if (!ruc.isEmpty()) {
                 if (addedCondition) {
                     sql += " OR ruc = ?"; // Use OR if also searching by doc
                 } else {
                      sql += " AND ruc = ?"; // Use AND if only searching by RUC
                 }
                 addedCondition = true;
            }

            if (!addedCondition) {
                 // This case should be handled by the initial check, but as a fallback
                 cargarClientes(); // Load all clients if no search criteria are entered
                 return;
            }


            pstmt = conn.prepareStatement(sql);
            int paramIndex = 1;
             if (!nroDocumento.isEmpty()) {
                 pstmt.setString(paramIndex++, getShortTipoDocumento(tipoDocumento)); // Use simplified type for DB search
                 pstmt.setString(paramIndex++, nroDocumento);
             }
            if (!ruc.isEmpty()) {
                pstmt.setString(paramIndex++, ruc);
            }


            rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                modeloTablaClientes.addRow(new Object[]{
                        rs.getString("tipo_documento"),
                        rs.getString("nro_documento"),
                        rs.getString("tipo_persona"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("ruc"),
                        rs.getString("estado")
                });
                found = true;
            }

            if (!found) {
                 JOptionPane.showMessageDialog(this, "No se encontraron clientes con los criterios especificados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                 cargarClientes(); // Optionally reload all clients if search finds none
            } else {
                // Select the first row if found
                if (modeloTablaClientes.getRowCount() > 0) {
                    tblClientes.setRowSelectionInterval(0, 0);
                }
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             cargarClientes(); // Reload all clients on error
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
             cargarClientes(); // Reload all clients on error
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

    private void llenarCamposDesdeTabla() {
        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow >= 0) { // Check if a row is actually selected
            // Get data from the selected row
            String tipoDoc = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
            String nroDoc = (String) modeloTablaClientes.getValueAt(selectedRow, 1);
            String tipoPersona = (String) modeloTablaClientes.getValueAt(selectedRow, 2);
            String nombres = (String) modeloTablaClientes.getValueAt(selectedRow, 3);
            String apellidos = (String) modeloTablaClientes.getValueAt(selectedRow, 4);
            String ruc = (String) modeloTablaClientes.getValueAt(selectedRow, 5);
            String estado = (String) modeloTablaClientes.getValueAt(selectedRow, 6);

            // Populate the input fields
            cboTipoDocumento.setSelectedItem(getFullTipoDocumento(tipoDoc)); // Convert short type back to full
            txtNroDocumento.setText(nroDoc);
            if (tipoPersona.equals("Natural")) {
                rdbtnNatural.setSelected(true);
            } else {
                rdbtnJuridica.setSelected(true);
            }
            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
            txtRUC.setText(ruc);
            cboEstado.setSelectedItem(estado);

            // Hide/Show Apellidos and RUC based on Tipo Persona
            // (This logic might be in an ActionListener for the radio buttons,
            // but also needs to be applied when loading data)
            updateFieldsVisibility(tipoPersona);

        } else {
            // If no row is selected, clear the fields
            limpiarCampos();
        }
    }

     // Helper to get full document type string from short code (if stored short)
     // Assumes the table displays the full name, but DB might store short codes like "DNI", "RUC"
     // If your DB stores the full string like "DNI - Documento Nacional", this is not needed here,
     // but you might need a helper for the opposite direction (Full to Short) for DB operations.
     // Let's assume your DB stores the full string based on your INSERT statement.
     // We will need a helper to get the short code for search/update/delete WHERE clauses.
     private String getFullTipoDocumento(String shortTipoDoc) {
         switch (shortTipoDoc) {
             case "DNI": return "DNI - Documento Nacional";
             case "RUC": return "RUC - Registro Único de Contribuyentes";
             case "Pasaporte": return "Pasaporte";
             default: return shortTipoDoc; // Return as is if not matched
         }
     }

     // Helper to get short document type code for DB operations
     private String getShortTipoDocumento(String fullTipoDoc) {
         if (fullTipoDoc == null) return null;
         if (fullTipoDoc.startsWith("DNI")) return "DNI - Documento Nacional";
         if (fullTipoDoc.startsWith("RUC")) return "RUC - Registro Único de Contribuyentes";
         if (fullTipoDoc.equals("Pasaporte")) return "Pasaporte"; // Assuming "Pasaporte" is stored as is
         return fullTipoDoc; // Fallback
     }


     // Helper to update visibility/editability of Apellidos and RUC based on Tipo Persona selection
     private void updateFieldsVisibility(String tipoPersona) {
         if (tipoPersona.equals("Natural")) {
             txtApellidos.setEditable(true);
             txtApellidos.setEnabled(true);
             txtRUC.setText(""); // Clear RUC for Natural
             txtRUC.setEditable(false);
             txtRUC.setEnabled(false);
         } else { // Jurídica
             txtApellidos.setText(""); // Clear Apellidos for Juridica
             txtApellidos.setEditable(false);
             txtApellidos.setEnabled(false);
             txtRUC.setEditable(true);
             txtRUC.setEnabled(true);
         }
     }


    private void actualizarCliente() {
        if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
             JOptionPane.showMessageDialog(this, "Solo los administradores pueden actualizar clientes.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the ORIGINAL document type and number from the selected row
        // These are used to find the record in the database to update
        String originalTipoDocumento = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
        String originalNroDocumento = (String) modeloTablaClientes.getValueAt(selectedRow, 1);


        // Get the UPDATED data from the input fields
        String nuevoTipoDocumento = (String) cboTipoDocumento.getSelectedItem();
        String nuevoNroDocumento = txtNroDocumento.getText().trim();
        String nuevoTipoPersona = rdbtnNatural.isSelected() ? "Natural" : "Jurídica";
        String nuevosNombres = txtNombres.getText().trim();
        String nuevosApellidos = txtApellidos.getText().trim();
        String nuevoRuc = txtRUC.getText().trim();
        String nuevoEstado = (String) cboEstado.getSelectedItem();

         // Basic validation for updated fields
         if (nuevoNroDocumento.isEmpty() || nuevosNombres.isEmpty() || (nuevoTipoPersona.equals("Natural") && nuevosApellidos.isEmpty())) {
             JOptionPane.showMessageDialog(this, "Por favor, complete los campos obligatorios para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
             return;
         }
          if (nuevoTipoPersona.equals("Jurídica") && nuevoRuc.isEmpty()) {
              JOptionPane.showMessageDialog(this, "Por favor, complete el campo RUC para persona Jurídica.", "Advertencia", JOptionPane.WARNING_MESSAGE);
              return;
          }


        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if the NEW document number/type or RUC already exists for *another* client
             if (!originalNroDocumento.equals(nuevoNroDocumento) || !originalTipoDocumento.equals(nuevoTipoDocumento) || (nuevoTipoPersona.equals("Jurídica") && !nuevoRuc.isEmpty() && !((String) modeloTablaClientes.getValueAt(selectedRow, 5)).equals(nuevoRuc)) ) {
                if (clienteExisteParaOtro(conn, nuevoTipoDocumento, nuevoNroDocumento, nuevoRuc, nuevoTipoPersona, originalTipoDocumento, originalNroDocumento)) {
                    JOptionPane.showMessageDialog(this, "El nuevo número de documento o RUC ya está registrado para otro cliente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
             }


            String sql = "UPDATE clientes SET tipo_documento = ?, nro_documento = ?, tipo_persona = ?, nombres = ?, apellidos = ?, ruc = ?, estado = ? WHERE tipo_documento = ? AND nro_documento = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevoTipoDocumento);
            pstmt.setString(2, nuevoNroDocumento);
            pstmt.setString(3, nuevoTipoPersona);
            pstmt.setString(4, nuevosNombres);
            pstmt.setString(5, nuevosApellidos);
            pstmt.setString(6, nuevoRuc);
            pstmt.setString(7, nuevoEstado);
            pstmt.setString(8, originalTipoDocumento); // WHERE clause
            pstmt.setString(9, originalNroDocumento); // WHERE clause

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Update the row in the table model
                modeloTablaClientes.setValueAt(nuevoTipoDocumento, selectedRow, 0);
                modeloTablaClientes.setValueAt(nuevoNroDocumento, selectedRow, 1);
                modeloTablaClientes.setValueAt(nuevoTipoPersona, selectedRow, 2);
                modeloTablaClientes.setValueAt(nuevosNombres, selectedRow, 3);
                modeloTablaClientes.setValueAt(nuevosApellidos, selectedRow, 4);
                modeloTablaClientes.setValueAt(nuevoRuc, selectedRow, 5);
                modeloTablaClientes.setValueAt(nuevoEstado, selectedRow, 6);

                // Optionally clear fields or keep them populated
                // limpiarCampos(); // Uncomment to clear fields after update
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente (puede que no haya cambios o el cliente original ya no exista).", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            if (ex.getSQLState() != null && ex.getSQLState().startsWith("23")) { // SQLState for Integrity Constraint Violation
                 JOptionPane.showMessageDialog(this, "Error al actualizar cliente: El número de documento o RUC ya existe para otro cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
            try { if (conn != null) conn.close(); } catch (SQLException ex) {}
        }
    }

     // Helper method to check if document/ruc exists for *another* client
     private boolean clienteExisteParaOtro(Connection conn, String nuevoTipoDocumento, String nuevoNroDocumento, String nuevoRuc, String nuevoTipoPersona, String originalTipoDocumento, String originalNroDocumento) throws SQLException {
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         boolean exists = false;

         try {
             String sql = "SELECT COUNT(*) FROM clientes WHERE (tipo_documento = ? AND nro_documento = ?)";
             // If juridical, also check RUC
             if (nuevoTipoPersona.equals("Jurídica") && !nuevoRuc.isEmpty()) {
                  sql += " OR (ruc = ?)";
             }
             sql += " AND NOT (tipo_documento = ? AND nro_documento = ?)"; // Exclude the original client

             pstmt = conn.prepareStatement(sql);
             int paramIndex = 1;
             pstmt.setString(paramIndex++, nuevoTipoDocumento);
             pstmt.setString(paramIndex++, nuevoNroDocumento);
             if (nuevoTipoPersona.equals("Jurídica") && !nuevoRuc.isEmpty()) {
                 pstmt.setString(paramIndex++, nuevoRuc);
             }
             pstmt.setString(paramIndex++, originalTipoDocumento);
             pstmt.setString(paramIndex++, originalNroDocumento);


             rs = pstmt.executeQuery();
             if (rs.next()) {
                 exists = rs.getInt(1) > 0;
             }
         } finally {
             try { if (rs != null) rs.close(); } catch (SQLException ex) {}
             try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
         }
         return exists;
     }


    private void eliminarCliente() {
        if (this.usuarioRol == null || !this.usuarioRol.equals("Administrador")) {
             JOptionPane.showMessageDialog(this, "Solo los administradores pueden eliminar clientes.", "Acceso Denegado", JOptionPane.WARNING_MESSAGE);
             return;
        }

        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get the document type and number from the selected row
        String tipoDocumento = (String) modeloTablaClientes.getValueAt(selectedRow, 0);
        String nroDocumento = (String) modeloTablaClientes.getValueAt(selectedRow, 1);
        String nombres = (String) modeloTablaClientes.getValueAt(selectedRow, 3); // For confirmation message

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar al cliente '" + nombres + "' con Nro. Doc. '" + nroDocumento + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String sql = "DELETE FROM clientes WHERE tipo_documento = ? AND nro_documento = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, tipoDocumento);
                pstmt.setString(2, nroDocumento);

                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Remove the row from the table model immediately
                    modeloTablaClientes.removeRow(selectedRow);
                    limpiarCampos(); // Clear fields after deletion
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                // Consider foreign key constraint errors if other tables reference clients
                if (ex.getSQLState() != null && ex.getSQLState().startsWith("23")) { // SQLState for Integrity Constraint Violation
                    JOptionPane.showMessageDialog(this, "No se puede eliminar el cliente porque tiene registros relacionados en otras tablas.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Driver de base de datos no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try { if (pstmt != null) pstmt.close(); } catch (SQLException ex) {}
                try { if (conn != null) conn.close(); } catch (SQLException ex) {}
            }
        }
    }


    private void limpiarCampos() {
        cboTipoDocumento.setSelectedIndex(0);
        txtNroDocumento.setText("");
        rdbtnNatural.setSelected(true); // Reset to Natural
        txtNombres.setText("");
        txtApellidos.setText("");
        txtRUC.setText("");
        cboEstado.setSelectedIndex(0);
         updateFieldsVisibility("Natural"); // Reset field visibility
         tblClientes.clearSelection(); // Clear table selection
    }

    // Removed the placeholder obtenerRolUsuario() method as role is now passed
}