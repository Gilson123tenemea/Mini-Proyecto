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
            System.out.println("Se debe escoger una id de cliente, o de una reservacion");
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
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        CboxClientes = new javax.swing.JComboBox<>();
        cbxReservaciones = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("FACTURA");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 0, -1, -1));

        jLabel2.setText("ID DE LA FACTURA:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 85, -1, -1));
        add(txtIDFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 81, 140, -1));

        jLabel3.setText("ID DEL CLIENTE:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 151, -1, -1));

        jLabel4.setText("ID DE RESERVACION:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 212, -1, -1));

        jLabel5.setText("FECHA DE EMISION:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 286, -1, -1));
        add(jcalendarEmision, new org.netbeans.lib.awtextra.AbsoluteConstraints(169, 273, 150, -1));

        jLabel6.setText("TOTAL DE PAGO:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 354, -1, 20));
        add(txtPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 352, 140, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 81, -1, 380));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 490, -1, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 490, -1, 50));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, -1, 50));

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(CboxClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 141, 140, -1));

        cbxReservaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cbxReservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 207, 140, -1));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 490, -1, 50));

        jLabel7.setText("$");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearFactura();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarPorId(txtIDFactura.getText());
        limpiarCampos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarFactura();
        limpiarCampos();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarFactura();
        limpiarCampos();
    }//GEN-LAST:event_btnModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JTable TablaFactura;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxReservaciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jcalendarEmision;
    private javax.swing.JTextField txtIDFactura;
    private javax.swing.JTextField txtPago;
    // End of variables declaration//GEN-END:variables
}
