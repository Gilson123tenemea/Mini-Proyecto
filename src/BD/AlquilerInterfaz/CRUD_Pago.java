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
                pago.getEstado_pago(),
                sdf.format(pago.getFecha_pago()), //fechaFormateada
            };
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

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("ID DEL PAGO:");

        jLabel2.setText("ID DE RESERVACION:");

        jLabel3.setText("MONTO:");

        txtMonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoActionPerformed(evt);
            }
        });

        jLabel4.setText("FECHA DE PAGO:");

        jLabel5.setText("ESTADO DE PAGO:");

        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("PAGO");

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

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

        cbxReservaciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Itemuno", "Itemdos", "Itemtres", "Itemcuatro" }));

        btnVerReservacion.setText("VER");
        btnVerReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerReservacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIDPago, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(txtMonto, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(cbxReservaciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnVerReservacion))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEstadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcalendarFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addGap(63, 63, 63))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(btnCrear)
                        .addGap(59, 59, 59)
                        .addComponent(btnBuscar)
                        .addGap(60, 60, 60)
                        .addComponent(btnModificar)
                        .addGap(65, 65, 65)
                        .addComponent(btnEliminar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(395, 395, 395)
                        .addComponent(jLabel6)))
                .addContainerGap(338, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbxReservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVerReservacion))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jcalendarFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtEstadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnBuscar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCrear))
                .addGap(51, 51, 51))
        );
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
        // TODO add your handling code here:
        mostrarDatosReservacionSeleccionada();
    }//GEN-LAST:event_btnVerReservacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPago;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVerReservacion;
    private javax.swing.JComboBox<String> cbxReservaciones;
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
