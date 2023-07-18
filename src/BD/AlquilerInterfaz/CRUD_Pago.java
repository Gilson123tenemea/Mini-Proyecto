/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerInterfaz;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import BD.AlquilerCasas.Clases.CalendarioDisponibilidad;
import BD.AlquilerCasas.Clases.Pago;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.lang.Integer;
import java.text.ParseException;

public class CRUD_Pago extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Pago() {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();
    }

    /// metodo para crear pagos
    public void crearPago() {
        if (!validarCampos()) {
            return;
        }
        String ID_pago = txtIDPago.getText();
        // Consultar si ya existe un cliente con el mismo pago
        ObjectSet<Pago> result = BaseD.queryByExample(new Pago(ID_pago, null, 0, null, 0));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un pago con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si no existe un con la misma codigo, proceder con la creación
        String id_reservacion = CboxIDReservacion.getSelectedItem().toString();
        double monto = Double.parseDouble(txtMonto.getText());
        int estado_pago = Integer.parseInt(txtEstadoPago.getText());
        String estadopagString = Integer.toString(estado_pago);

        Date fecha_pago = jcalendarFechaPago.getDate();

        Pago mipago = new Pago(ID_pago, id_reservacion, monto, fecha_pago, estado_pago);
        BaseD.store(mipago); // Almacenar el objeto en la base de datos
        JOptionPane.showMessageDialog(null, "Pago creado exitosamente.");
        limpiarCampos();
        cargarTabla();
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
            pago.setId_reservacion(CboxIDReservacion.getSelectedItem().toString());
            pago.setMonto(Double.parseDouble(txtMonto.getText()));
            pago.setEstado_pago(Integer.parseInt(txtEstadoPago.getText()));

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
            JOptionPane.showMessageDialog(this, "Codigo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (CboxIDReservacion.getSelectedItem() == null || CboxIDReservacion.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una reservacion");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxIDReservacion.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de reservacion no válida");
                ban_confirmar = false;
            }
        }
        if (txtMonto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.validarDouble(txtMonto.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (txtEstadoPago.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarEntero(txtEstadoPago.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;

    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDPago.setText("");
        CboxIDReservacion.setSelectedIndex(0);
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
        CboxIDReservacion = new javax.swing.JComboBox<>();

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

        btnEliminar.setText("ELIMINAR");

        btnModificar.setText("MODIFICAR");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setText("PAGO");

        btnBuscar.setText("BUSCAR");

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

        CboxIDReservacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                            .addComponent(CboxIDReservacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEstadoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcalendarFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(CboxIDReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
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
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxIDReservacion;
    private javax.swing.JTable TablaPago;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
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
