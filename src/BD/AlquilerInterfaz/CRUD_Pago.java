package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Pago;
import BD.AlquilerCasas.Clases.Reservacion;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Pago extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Pago(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarReservaciones();
        cargarTabla();
    }

    public void cargarReservaciones() {
        cbxReservaciones.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Reservacion.class);

        ObjectSet<Reservacion> reserv = query.execute();

        if (reserv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay reservaciones ingresadas", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (reserv.hasNext()) {
                Reservacion res = reserv.next();
                cbxReservaciones.addItem(res.getId_reservacion());
            }
        }
    }

    /// metodo para crear pagos
    public void crearPago() {
        try {
            if (!validarCampos()) {
                return;
            }
            String ID_pago = txtIDPago.getText();
            // Consultar si ya existe un cliente con el mismo pago
            ObjectSet<Pago> result = BaseD.queryByExample(new Pago(ID_pago, null, 0, null, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe un pago con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Si no existe un con la misma codigo, proceder con la creación
            String id_reservacion = cbxReservaciones.getSelectedItem().toString();
            double monto = Double.parseDouble(txtMonto.getText());
            String estado_pago = txtEstadoPago.getText();

            Date fecha_pago = jcalendarFechaPago.getDate();

            Pago mipago = new Pago(ID_pago, id_reservacion, monto, fecha_pago, estado_pago);
            BaseD.store(mipago); // Almacenar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Pago creado exitosamente.");
            cargarTabla();
            limpiarCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Se debe escoger una id de reservacion antes de crear.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarPago() {
        String cedula = txtIDPago.getText();
        Query query = BaseD.query();
        query.constrain(Pago.class);
        query.descend("ID_pago").constrain(cedula);
        ObjectSet<Pago> result = query.execute();

        if (!result.isEmpty()) {
            Pago pago = result.get(0);
            mostrarDatos(pago);
            txtIDPago.setEnabled(false);
            btnCrear.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un pago con la idingresada.");
            limpiarCampos();
        }
    }

    private void mostrarDatos(Pago pago) {// Método para mostrar los datos de un propietario en los campos de la interfaz
        txtIDPago.setText(pago.getID_pago());
        cbxReservaciones.setSelectedItem(pago.getId_reservacion());
        double monto = pago.getMonto();
        String montoStr = String.valueOf(monto);
        txtMonto.setText(montoStr);

        txtEstadoPago.setText(pago.getEstado_pago());
        jcalendarFechaPago.setDate(pago.getFecha_pago());
    }

    /////// busca / consultar por ID
    private void buscarPorId(String id) {
        Query query = BaseD.query();
        query.constrain(Pago.class);
        query.descend("ID_pago").constrain(id);
        ObjectSet<Pago> result = query.execute();

        if (!result.isEmpty()) {
            Pago pago = result.next();
            DefaultTableModel model = (DefaultTableModel) TablaPago.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                pago.getID_pago(),
                pago.getId_reservacion(),
                pago.getMonto(),
                sdf.format(pago.getFecha_pago()),
                pago.getEstado_pago(),};
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el pago a buscar.");
            limpiarCampos();
        }
    }

    // Método para modificar un pago existente
    private void modificarPago() {
        if (!validarCampos()) {
            return;
        }

        String ID_pago = txtIDPago.getText();
        Query query = BaseD.query();
        query.constrain(Pago.class);
        query.descend("ID_pago").constrain(ID_pago);  //ID_promocion ---> es el nombre de la variable de la clase
        ObjectSet<Pago> result = query.execute();

        if (!result.isEmpty()) {
            Pago pago = result.next();
            // Actualizar los campos del pago con los valores ingresados en la interfaz
            pago.setId_reservacion(cbxReservaciones.getSelectedItem().toString());
            pago.setMonto(Double.parseDouble(txtMonto.getText()));
            pago.setEstado_pago(txtEstadoPago.getText());

            Date fecha_pago = jcalendarFechaPago.getDate();

            pago.setFecha_pago(fecha_pago);
            BaseD.store(pago); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "El pago se modifico exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de pago con el ID ingresado.");
        }
    }

// Método para eliminar un pago existente
    private void eliminarPago() {
        String ID_pago = txtIDPago.getText();
        Query query = BaseD.query();
        query.constrain(Pago.class);
        query.descend("ID_pago").constrain(ID_pago);
        ObjectSet<Pago> result = query.execute();
        if (!result.isEmpty()) {
            Pago pago = result.next();
            BaseD.delete(pago); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "El registro del pago se elimino exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de pago con el ID ingresado.");
        }
    }
//metodo para verifcar campos

    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIDPago.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIDPago.getText())) {
            JOptionPane.showMessageDialog(this, "ID incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (cbxReservaciones.getSelectedItem() == null || cbxReservaciones.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una reservacion");
            ban_confirmar = false;
        }
        if (txtMonto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido");
            ban_confirmar = false;
        } else if (!miValidaciones.validarDouble(txtMonto.getText())) {
            JOptionPane.showMessageDialog(this, "Monto incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (txtEstadoPago.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarCiudad(txtEstadoPago.getText())) {
            JOptionPane.showMessageDialog(this, "Estado de pago incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;

    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDPago.setText("");
        //cbxReservaciones.setSelectedIndex(0);
        txtMonto.setText("");
        txtEstadoPago.setText("");
        jcalendarFechaPago.setDate(null);
    }

    // Método para cargar la tabla con los pagos existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaPago.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Pago> result = BaseD.queryByExample(Pago.class);
        while (result.hasNext()) {
            Pago pago = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                pago.getID_pago(),
                pago.getId_reservacion(),
                pago.getMonto(),
                sdf.format(pago.getFecha_pago()), //fechaFormateada
                pago.getEstado_pago(),};
            model.addRow(row);
        }
    }

    private void mostrarDatosReservacionSeleccionada() {
        String idSeleccionado = cbxReservaciones.getSelectedItem().toString();
        Query query = BaseD.query();
        query.constrain(Reservacion.class);
        query.descend("id_reservacion").constrain(idSeleccionado);
        ObjectSet<Reservacion> result = query.execute();

        if (!result.isEmpty()) {
            Reservacion reserv = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String mensaje = "ID: " + reserv.getId_reservacion() + "\n"
                    + "ID Casa: " + reserv.getId_casa() + "\n"
                    + "Cedula Cliente: " + reserv.getIDCliente() + "\n"
                    + "Fecha De Inicio: " + sdf.format(reserv.getFecha_inicio()) + "\n"
                    + "Fecha Fin: " + sdf.format(reserv.getFecha_fin());

            JOptionPane.showMessageDialog(this, mensaje, "Datos de la Reservacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la reservacion con el id seleccionado.", "Reservacion no encontrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtIDPago = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jcalendarFechaPago = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtEstadoPago = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPago = new javax.swing.JTable();
        cbxReservaciones = new javax.swing.JComboBox<>();
        btnVerReservacion = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btncargardatos = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ID DEL PAGO:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 124, -1, -1));
        add(txtIDPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 118, -1));

        jLabel2.setText("ID DE RESERVACION:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 186, -1, -1));

        jLabel3.setText("MONTO:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 248, -1, -1));

        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });
        add(txtMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 118, -1));

        jLabel4.setText("FECHA DE PAGO:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 316, -1, -1));
        add(jcalendarFechaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 102, -1));

        jLabel5.setText("ESTADO DE PAGO:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 380, -1, -1));
        add(txtEstadoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 117, -1));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, 50));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, -1, 50));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 480, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("PAGO");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnBuscar.setText("CONSULTAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, -1, 50));

        TablaPago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID DEL PAGO", "ID DE RESERVACION", "MONTO", "FECHA DE PAGO", "ESTADO DE PAGO"
            }
        ));
        jScrollPane1.setViewportView(TablaPago);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(359, 116, 470, 350));

        cbxReservaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Itemuno", "Itemdos", "Itemtres", "Itemcuatro" }));
        add(cbxReservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 118, -1));

        btnVerReservacion.setText("VER");
        btnVerReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerReservacionActionPerformed(evt);
            }
        });
        add(btnVerReservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 180, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        jButton1.setText("REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 480, -1, 50));

        btncargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargardatosActionPerformed(evt);
            }
        });
        add(btncargardatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 40, 40));
    }// </editor-fold>//GEN-END:initComponents

    private void txtMontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoActionPerformed

    }//GEN-LAST:event_txtMontoActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearPago();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String id = txtIDPago.getText();
        buscarPorId(id);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarPago();
        limpiarCampos();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarPago();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnVerReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerReservacionActionPerformed
        mostrarDatosReservacionSeleccionada();
    }//GEN-LAST:event_btnVerReservacionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cargarTabla();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btncargardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargardatosActionPerformed
        consultarPago();
    }//GEN-LAST:event_btncargardatosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPago;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVerReservacion;
    private javax.swing.JButton btncargardatos;
    private javax.swing.JComboBox<String> cbxReservaciones;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jcalendarFechaPago;
    private javax.swing.JTextField txtEstadoPago;
    private javax.swing.JTextField txtIDPago;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
