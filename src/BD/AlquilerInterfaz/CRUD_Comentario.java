package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Cliente;
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

public class CRUD_Comentario extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Comentario(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarCasas();
        cargarClientes();
        cargarTabla();
    }
    public void cargarCasas() {
        cbxCasas.removeAllItems();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);

        ObjectSet<CasaVacacional> casas = query.execute();

        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay casas vacacionales");
        } else {
            //System.out.println("Casas registradas:");
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                cbxCasas.addItem(casa.getNombre());
            }
        }
    }
    //////////cargar clientes 
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
                CboxClientes.addItem(cli.getNombreCliente()+ " - " + cli.getCedula());
            }
        }
    }
    /// metodo para crear promociones

    public void crearComentario() {
        if (!validarCampos()) {
            return;
        }
        String ID_comentario = txtIDComen.getText();
        // Consultar si ya existe un cliente con el mismo código
        ObjectSet<Comentario> result = BaseD.queryByExample(new Comentario(ID_comentario, null, null, null, 0, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un comentario con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si no existe un comentario con el mismo código, proceder con la creación
        String IDCliente = CboxClientes.getSelectedItem().toString();
        String id_casa = cbxCasas.getSelectedItem().toString();
        String contenido = txtContenido.getText();
        int puntuacion = (int) spnPuntuacion.getValue();
        String puntuacionString = Integer.toString(puntuacion);

        Date fecha_comentario = null;
        if (jcalendarFechaComentario.getDate() != null) {
            fecha_comentario = jcalendarFechaComentario.getDate();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
            comentario.setIDCliente(CboxClientes.getSelectedItem().toString());
            comentario.setId_casa(cbxCasas.getSelectedItem().toString());
            comentario.setContenido(txtContenido.getText());
            comentario.setPuntuacion((int) spnPuntuacion.getValue());
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
        CboxClientes.setSelectedIndex(0);
        cbxCasas.setSelectedIndex(0);
        txtContenido.setText("");
        spnPuntuacion.setValue(0);
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
            String fechaFormateada = "";
            if (comen.getFecha_comentario() != null) {
                fechaFormateada = sdf.format(comen.getFecha_comentario());
            }
            Object[] row = {
                comen.getID_comentario(),
                comen.getIDCliente(),
                comen.getId_casa(),
                comen.getContenido(),
                comen.getPuntuacion(),
                fechaFormateada,};
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
        if (CboxClientes.getSelectedItem() == null || CboxClientes.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxClientes.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de cliente no válida");
                ban_confirmar = false;
            }
        }
        if (cbxCasas.getSelectedItem() == null || cbxCasas.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(cbxCasas.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de casa no válida");
                ban_confirmar = false;
            }
        }
        if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarContenido(txtContenido.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        int puntuacion = (int) spnPuntuacion.getValue();

        if (puntuacion == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarPuntuacion(Integer.toString(puntuacion))) {
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
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaComentario = new javax.swing.JTable();
        CboxClientes = new javax.swing.JComboBox<>();
        cbxCasas = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        spnPuntuacion = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtContenido = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(810, 600));

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

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNO ", "DOS", "TRES" }));

        cbxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNO ", "DOS", "TRES" }));

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

        spnPuntuacion.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        txtContenido.setColumns(20);
        txtContenido.setRows(5);
        jScrollPane3.setViewportView(txtContenido);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(btnCrear)
                .addGap(57, 57, 57)
                .addComponent(btnConsultar)
                .addGap(58, 58, 58)
                .addComponent(btnModificar)
                .addGap(53, 53, 53)
                .addComponent(btnEliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcalendarFechaComentario, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spnPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(261, 261, 261)
                                .addComponent(jLabel7))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtIDComen, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbxCasas, 0, 152, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtIDComen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(cbxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnPuntuacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcalendarFechaComentario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnConsultar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addGap(22, 22, 22))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JTable TablaComentario;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cbxCasas;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JDateChooser jcalendarFechaComentario;
    private javax.swing.JSpinner spnPuntuacion;
    private javax.swing.JTextArea txtContenido;
    private javax.swing.JTextField txtIDComen;
    // End of variables declaration//GEN-END:variables
}
