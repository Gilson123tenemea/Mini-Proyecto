package BD.AlquilerInterfaz;
import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Factura;
import BD.AlquilerCasas.Clases.Reservacion;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Factura extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Factura(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarReservaciones();
        cargarClientes();
        cargarTabla();
    }

    public void cargarReservaciones() {
        cbxReservaciones.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Reservacion.class);

        ObjectSet<Reservacion> reserv = query.execute();

        if (reserv.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reservaciones ingresadas");
        } else {
            while (reserv.hasNext()) {
                Reservacion res = reserv.next();
                cbxReservaciones.addItem(res.getId_reservacion());
            }
        }
    }

    public void cargarClientes() {
        CboxClientes.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Cliente.class);

        ObjectSet<Cliente> cliente = query.execute();

        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes ingresados");
        } else {
            //System.out.println("clientes registradas:");
            while (cliente.hasNext()) {
                Cliente cli = cliente.next();
                CboxClientes.addItem(cli.getCedula());
            }
        }
    }

    /// metodo para crear factura
    public void crearFactura() {
        try {
            if (!validarCampos()) {
                return;
            }
            String ID_factura = txtIDFactura.getText();
            // Consultar si ya existe un cliente con la misma factura
            ObjectSet<Factura> result = BaseD.queryByExample(new Factura(ID_factura, null, null, null, 0));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe una factura con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Si no existe un con la misma codigo, proceder con la creación
            String IDCliente = CboxClientes.getSelectedItem().toString();
            String id_reservacion = cbxReservaciones.getSelectedItem().toString();
            double totalPago = Double.parseDouble(txtPago.getText());

            Date fecha_emision = jcalendarEmision.getDate();

            Factura mifac = new Factura(ID_factura, IDCliente, id_reservacion, fecha_emision, totalPago);
            BaseD.store(mifac); // Almacenar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Factura creada exitosamente.");
            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Se debe escoger una id de factura antes de crear.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /////// busca / consultar por ID
    private void buscarPorId(String id) {
        Query query = BaseD.query();
        query.constrain(Factura.class);
        query.descend("ID_factura").constrain(id);
        ObjectSet<Factura> result = query.execute();

        if (!result.isEmpty()) {
            Factura factura = result.next();
            DefaultTableModel model = (DefaultTableModel) TablaFactura.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                factura.getID_factura(),
                factura.getIDCliente(),
                factura.getId_reservacion(),
                sdf.format(factura.getFecha_emision()),
                factura.getTotalPago(),};
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la factura a buscar.");
            limpiarCampos();
        }
    }

    // Método para modificar una factura existente
    private void modificarFactura() {
        if (!validarCampos()) {
            return;
        }

        String ID_factura = txtIDFactura.getText();
        Query query = BaseD.query();
        query.constrain(Factura.class);
        query.descend("ID_factura").constrain(ID_factura);  //ID_promocion ---> es el nombre de la variable de la clase
        ObjectSet<Factura> result = query.execute();

        if (!result.isEmpty()) {
            Factura factura = result.next();
            // Actualizar los campos del pago con los valores ingresados en la interfaz
            factura.setIDCliente(CboxClientes.getSelectedItem().toString());
            factura.setId_reservacion(cbxReservaciones.getSelectedItem().toString());
            factura.setTotalPago(Double.parseDouble(txtPago.getText()));

            Date fecha_emision = jcalendarEmision.getDate();

            factura.setFecha_emision(fecha_emision);
            BaseD.store(factura); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "La Factura se modifico exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de factura con el ID ingresado.");
        }
    }
// Método para eliminar un pago existente

    private void eliminarFactura() {
        String ID_factura = txtIDFactura.getText();
        Query query = BaseD.query();
        query.constrain(Factura.class);
        query.descend("ID_factura").constrain(ID_factura);
        ObjectSet<Factura> result = query.execute();
        if (!result.isEmpty()) {
            Factura factura = result.next();
            BaseD.delete(factura); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "El registro de la factura se elimino exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de factura con el ID ingresado.");
        }
    }

    //metodo para verifcar campos
    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIDFactura.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIDFactura.getText())) {
            JOptionPane.showMessageDialog(this, "ID incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (CboxClientes.getSelectedItem() == null || CboxClientes.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            ban_confirmar = false;
        }
        if (cbxReservaciones.getSelectedItem() == null || cbxReservaciones.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una reservacion");
            ban_confirmar = false;
        }
        if (txtPago.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido");
            ban_confirmar = false;
        } else if (!miValidaciones.validarDouble(txtPago.getText())) {
            JOptionPane.showMessageDialog(this, "Pago incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;

    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDFactura.setText("");
        CboxClientes.setSelectedIndex(0);
        cbxReservaciones.setSelectedIndex(0);
        txtPago.setText("");
        jcalendarEmision.setDate(null);
    }
    // Método para cargar la tabla con las facturas existentes en la base de datos

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaFactura.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Factura> result = BaseD.queryByExample(Factura.class);
        while (result.hasNext()) {
            Factura factura = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                factura.getID_factura(),
                factura.getIDCliente(),
                factura.getId_reservacion(),
                sdf.format(factura.getFecha_emision()),
                factura.getTotalPago(),};
            model.addRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIDFactura = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jcalendarEmision = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaFactura = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        CboxClientes = new javax.swing.JComboBox<>();
        cbxReservaciones = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("FACTURA");

        jLabel2.setText("ID DE LA FACTURA:");

        jLabel3.setText("ID DEL CLIENTE:");

        jLabel4.setText("ID DE RESERVACION:");

        jLabel5.setText("FECHA DE EMISION:");

        jLabel6.setText("TOTAL DE PAGO:");

        TablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID DE LA FACTURA", "ID DEL CLIENTE", "ID DE RESERVACION", "FECHA DE EMISION", "TOTAL DE PAGO"
            }
        ));
        jScrollPane1.setViewportView(TablaFactura);

        jButton1.setText("REGRESAR");

        btnModificar.setText("MODIFICAR");

        btnEliminar.setText("ELIMINAR");

        btnBuscar.setText("BUSCAR");

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxReservaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnCrear.setText("CREAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIDFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(txtPago)
                            .addComponent(jcalendarEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CboxClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxReservaciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(btnCrear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEliminar)
                                .addGap(75, 75, 75)
                                .addComponent(btnModificar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtIDFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3))
                            .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxReservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jcalendarEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnBuscar)
                    .addComponent(btnCrear))
                .addGap(50, 50, 50))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JTable TablaFactura;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxReservaciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jcalendarEmision;
    private javax.swing.JTextField txtIDFactura;
    private javax.swing.JTextField txtPago;
    // End of variables declaration//GEN-END:variables
}
