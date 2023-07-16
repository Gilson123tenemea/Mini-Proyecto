/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Comentario;
import BD.AlquilerCasas.Clases.Promocion;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class CRUD_Comentario extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    /**
     * Creates new form CRUD_Comentario
     */
    public CRUD_Comentario(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();
    }
    /// metodo para crear promociones

    public void crearComentario() {
        if (!validarCampos()) {
            return;
        }
        String ID_comentario = txtIDComen.getText();
        // Consultar si ya existe un cliente con la misma cédula
        ObjectSet<Comentario> result = BaseD.queryByExample(new Comentario(ID_comentario, null, null, null, 0, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un comentario con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si no existe un con la misma codigo, proceder con la creación
        String IDCliente = CboxIDCliente.getSelectedItem().toString();
        String id_casa = CboxCasa.getSelectedItem().toString();
        String contenido = txtContenido.getText();
        int puntuacion = Integer.parseInt(txtPuntuacion.getText());
        String puntuacionString = Integer.toString(puntuacion);

        Date fecha_comentario = jcalendarFechaComentario.getDate();

        Comentario micomen = new Comentario(ID_comentario, IDCliente, id_casa, contenido, puntuacion, fecha_comentario);
        BaseD.store(micomen); // Almacenar el objeto en la base de datos
        JOptionPane.showMessageDialog(null, "Comentario creado exitosamente.");
        limpiarCampos();
        cargarTabla();
    }

    /////// busca / consultar por ID
    private void buscarPorId(String id) {
        Query query = BaseD.query();
        query.constrain(Comentario.class);
        query.descend("ID_comentario").constrain(id);
        ObjectSet<Comentario> result = query.execute();

        if (!result.isEmpty()) {
            Comentario comen = result.next();
            DefaultTableModel model = (DefaultTableModel) TablaComentario.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                comen.getID_comentario(),
                comen.getIDCliente(),
                comen.getId_casa(),
                comen.getContenido(),
                comen.getPuntuacion(),
                sdf.format(comen.getFecha_comentario()), //fechaFormateada
            };
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el comentario disponible.");
            limpiarCampos();
        }
    }
// Método para modificar un comentario existente

    private void modificarComentario() {
        if (!validarCampos()) {
            return;
        }
        String ID_comentario = txtIDComen.getText();
        Query query = BaseD.query();
        query.constrain(Comentario.class);
        query.descend("ID_comentario").constrain(ID_comentario);  //ID_promocion ---> es el nombre de la variable de la clase
        ObjectSet<Comentario> result = query.execute();

        if (!result.isEmpty()) {
            Comentario comentario = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            comentario.setIDCliente(CboxIDCliente.getSelectedItem().toString());
            comentario.setId_casa(CboxCasa.getSelectedItem().toString());
            comentario.setContenido(txtContenido.getText());
            comentario.setPuntuacion(Integer.parseInt(txtPuntuacion.getText()));
            Date fecha_comentario = jcalendarFechaComentario.getDate();

            comentario.setFecha_comentario(fecha_comentario);
            BaseD.store(comentario); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "El comentario se modifico exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de comentario con el ID ingresado.");
        }
    }
// Método para eliminar un comentario existente

    private void eliminarComentario() {
        String ID_comentario = txtIDComen.getText();
        Query query = BaseD.query();
        query.constrain(Comentario.class);
        query.descend("ID_comentario").constrain(ID_comentario);
        ObjectSet<Comentario> result = query.execute();
        if (!result.isEmpty()) {
            Comentario comentario = result.next();
            BaseD.delete(comentario); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "El registro del comentario se elimino exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningun comentario con el ID ingresado.");
        }
    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDComen.setText("");
        CboxIDCliente.setSelectedIndex(0);
        CboxCasa.setSelectedIndex(0);
        txtContenido.setText("");
        txtPuntuacion.setText("");
        jcalendarFechaComentario.setDate(null);
    }

// Método para cargar la tabla con las casas promos existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaComentario.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Comentario> result = BaseD.queryByExample(Comentario.class);
        while (result.hasNext()) {
            Comentario comen = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                comen.getID_comentario(),
                comen.getIDCliente(),
                comen.getId_casa(),
                comen.getContenido(),
                comen.getPuntuacion(),
                sdf.format(comen.getFecha_comentario()), //fechaFormateada
            };
            model.addRow(row);
        }
    }

    //metodo para verifcar campos
    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIDComen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIDComen.getText())) {
            JOptionPane.showMessageDialog(this, "Codigo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (CboxIDCliente.getSelectedItem() == null || CboxIDCliente.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxIDCliente.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de cliente no válida");
                ban_confirmar = false;
            }
        }
        if (CboxCasa.getSelectedItem() == null || CboxCasa.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxCasa.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de casa no válida");
                ban_confirmar = false;
            }
        }
        if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarEntero(txtContenido.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (txtPuntuacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarEntero(txtPuntuacion.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        txtIDComen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcalendarFechaComentario = new com.toedter.calendar.JDateChooser();
        txtContenido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaComentario = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        CboxIDCliente = new javax.swing.JComboBox<>();
        CboxCasa = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtPuntuacion = new javax.swing.JTextField();

        jLabel1.setText("ID COMENTARIO:");

        jLabel2.setText("ID DEL CLIENTE:");

        jLabel3.setText("ID CASA:");

        jLabel4.setText("CONTENIDO:");

        jLabel5.setText("PUNTUACION:");

        jLabel6.setText("FECHA DEL COMENTARIO:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel7.setText("COMENTARIO");

        TablaComentario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID COMENTARIO", "ID DEL CLIENTE", "ID CASA", "CONTENIDO", "PUNTUACION", "FECHA DEL COMENTARIO"
            }
        ));
        jScrollPane1.setViewportView(TablaComentario);

        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        CboxIDCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));

        CboxCasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));

        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(30, 30, 30)
                                    .addComponent(txtIDComen, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                    .addGap(36, 36, 36)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtContenido)
                                        .addComponent(CboxIDCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(CboxCasa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtPuntuacion)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 49, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConsultar)
                                .addGap(55, 55, 55)
                                .addComponent(btnModificar)
                                .addGap(40, 40, 40)
                                .addComponent(btnEliminar))
                            .addComponent(jcalendarFechaComentario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnRegresar)
                        .addGap(193, 193, 193)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(btnCrear)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(btnRegresar))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIDComen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(CboxIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(CboxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jcalendarFechaComentario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnConsultar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addGap(57, 57, 57))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearComentario();
        limpiarCampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        String id = txtIDComen.getText();
        buscarPorId(id);        // TODO add your handling code here:
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarComentario();
        limpiarCampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarComentario();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
       
    }//GEN-LAST:event_btnRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxCasa;
    private javax.swing.JComboBox<String> CboxIDCliente;
    private javax.swing.JTable TablaComentario;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jcalendarFechaComentario;
    private javax.swing.JTextField txtContenido;
    private javax.swing.JTextField txtIDComen;
    private javax.swing.JTextField txtPuntuacion;
    // End of variables declaration//GEN-END:variables
}
