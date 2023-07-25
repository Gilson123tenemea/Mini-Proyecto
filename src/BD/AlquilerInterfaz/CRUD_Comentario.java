package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Comentario;
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
            JOptionPane.showMessageDialog(this, "No hay casas vacacionales disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                cbxCasas.addItem(casa.getId_casa());
            }
        }
    }

    public void cargarClientes() {
        CboxClientes.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Cliente.class);

        ObjectSet<Cliente> cliente = query.execute();

        if (cliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes ingresados", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //System.out.println("clientes registradas:");
            while (cliente.hasNext()) {
                Cliente cli = cliente.next();
                CboxClientes.addItem(cli.getCedula());
            }
        }
    }
    /// metodo para crear promociones

    public void crearComentario() {
        try {
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
            //String puntuacionString = Integer.toString(puntuacion);

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
        } catch (Exception e) {
            System.out.println("Error: No se ha seleccionado una casa o un cliente, puede ser que no exista ningun registro");

        }
    }

    private void consultarComentario() {
        String ID_comentario = txtIDComen.getText();
        Query query = BaseD.query();
        query.constrain(Comentario.class);
        query.descend("ID_comentario").constrain(ID_comentario);
        ObjectSet<Comentario> result = query.execute();

        if (!result.isEmpty()) {
            Comentario comentario = result.get(0);
            mostrarDatos(comentario);
            txtIDComen.setEnabled(false);
            btnCrear.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un comentario con el id ingresado.");
            limpiarCampos();
        }
    }

    private void mostrarDatos(Comentario comentario) {// Método para mostrar los datos de un propietario en los campos de la interfaz
        txtIDComen.setText(comentario.getID_comentario());
        CboxClientes.setSelectedItem(comentario.getIDCliente());
        cbxCasas.setSelectedItem(comentario.getId_casa());
        txtContenido.setText(comentario.getContenido());
        int puntuacion = comentario.getPuntuacion();
        spnPuntuacion.setValue(puntuacion);

        jcalendarFechaComentario.setDate(comentario.getFecha_comentario());
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
        try {
            String idcomentario = txtIDComen.getText();
            Query query = BaseD.query();
            query.constrain(Comentario.class);
            query.descend("ID_comentario").constrain(idcomentario);
            ObjectSet<Comentario> result = query.execute();
            if (!result.isEmpty()) {

                Comentario comentario = result.next();

                if (comentario.getIDCliente() == null || comentario.getIDCliente().isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este comentario con la id: " + idcomentario + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        BaseD.delete(comentario); // Eliminar el objeto de la base de datos
                        JOptionPane.showMessageDialog(null, "Comentario eliminado exitosamente.");
                        limpiarCampos();
                        cargarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes eliminar este contrato, ya que esta relacionada con un cliente\n primero elimina el cliente");
                    BaseD.store(comentario); // Actualizar la casa en la base de datos
                    limpiarCampos();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningun comentario con el ID ingresado.");
            }
        } catch (Exception e) {
            System.out.println("Error: puede que no existan clientes");
            e.printStackTrace();
        }

    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDComen.setText("");
//        CboxClientes.setSelectedIndex(0);
//        cbxCasas.setSelectedIndex(0);
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
        }
        if (cbxCasas.getSelectedItem() == null || cbxCasas.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        }
        if (txtContenido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor en el contenido valido");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarContenido(txtContenido.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto del contenido. Ingrese de nuevo");
            ban_confirmar = false;
        }
        int puntuacion = (int) spnPuntuacion.getValue();

        if (puntuacion == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor válido");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarPuntuacion(Integer.toString(puntuacion))) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto de la puntuacion. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;

    }

    private void mostrarDatosCasaSeleccionada() {
        String casaSeleccionada = cbxCasas.getSelectedItem().toString();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);
        query.descend("id_casa").constrain(casaSeleccionada);
        ObjectSet<CasaVacacional> result = query.execute();

        if (!result.isEmpty()) {
            CasaVacacional casa = result.next();
            String mensaje = "ID:: " + casa.getId_casa() + "\n"
                    + "Cedula Propietario: " + casa.getIDPropietario() + "\n"
                    + "Nombre: " + casa.getNombre() + "\n"
                    + "Carro: " + casa.getCarro() + "\n"
                    + "Numero De Pisos: " + casa.getNum_pisos() + "\n"
                    + "Capacidad Maxima: " + casa.getCapacidad_maxima() + "\n"
                    + "Numero De Habitaciones: " + casa.getNum_habitaciones() + "\n"
                    + "Numero De Baños: " + casa.getNum_banos() + "\n"
                    + "Piscina: " + casa.getTiene_piscina() + "\n"
                    + "Jardin: " + casa.getTiene_jardin() + "\n"
                    + "Wifi: " + casa.getTiene_wifi() + "\n"
                    + "Tv: " + casa.getTiene_tv() + "\n"
                    + "Cocina: " + casa.getTiene_cocina() + "\n"
                    + "Ubicacion: " + casa.getUbicacion() + "\n"
                    + "Otros Detalles: " + casa.getOtros_detalles();

            JOptionPane.showMessageDialog(this, mensaje, "Datos de La Casa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la casa con el ID seleccionado.", "Casa no encontrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDatosClienteSeleccionado() {
        String cedulaSeleccionada = CboxClientes.getSelectedItem().toString();
        Query query = BaseD.query();
        query.constrain(Cliente.class);
        query.descend("Cedula").constrain(cedulaSeleccionada);
        ObjectSet<Cliente> result = query.execute();

        if (!result.isEmpty()) {
            Cliente cliente = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String mensaje = "Cédula: " + cliente.getCedula() + "\n"
                    + "Nombre: " + cliente.getNombreCliente() + "\n"
                    + "Apellido: " + cliente.getApellidoCliente() + "\n"
                    + "Genero: " + cliente.getGeneroCliente() + "\n"
                    + "Edad: " + cliente.getEdadCliente() + "\n"
                    + "Correo: " + cliente.getCorreo() + "\n"
                    + "Celular: " + cliente.getCelular() + "\n"
                    + "Nacionalidad: " + cliente.getNacionalidad() + "\n"
                    + "Fecha De Nacimiento: " + sdf.format(cliente.getFecha_Naci());

            JOptionPane.showMessageDialog(this, mensaje, "Datos del Cliente", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un cliente con la cédula seleccionada.", "Cliente no encontrado", JOptionPane.ERROR_MESSAGE);
        }
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
        jButton1 = new javax.swing.JButton();
        btnVerCasa = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btncargarrdatos = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(810, 600));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ID COMENTARIO:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 108, -1, -1));
        add(txtIDComen, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 104, 146, -1));

        jLabel2.setText("ID DEL CLIENTE:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 160, -1, -1));

        jLabel3.setText("ID CASA:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 211, -1, 30));

        jLabel4.setText("CONTENIDO:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 272, -1, 20));

        jLabel5.setText("PUNTUACION:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 405, -1, 29));

        jLabel6.setText("FECHA DEL COMENTARIO:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 452, -1, 28));
        add(jcalendarFechaComentario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 452, 155, 28));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel7.setText("COMENTARIO");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 29, -1, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 460, 270));

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNO ", "DOS", "TRES" }));
        add(CboxClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 155, 152, -1));

        cbxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNO ", "DOS", "TRES" }));
        add(cbxCasas, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 214, 152, -1));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 519, 110, 40));

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, -1, 40));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 520, -1, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 520, -1, 40));

        spnPuntuacion.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        add(spnPuntuacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 406, -1, -1));

        txtContenido.setColumns(20);
        txtContenido.setRows(5);
        jScrollPane3.setViewportView(txtContenido);

        add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 272, -1, 108));

        jButton1.setText("VER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 152, -1, -1));

        btnVerCasa.setText("VER");
        btnVerCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCasaActionPerformed(evt);
            }
        });
        add(btnVerCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(291, 211, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        jButton2.setText("REPORTE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 520, -1, 40));

        btncargarrdatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargarrdatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargarrdatosActionPerformed(evt);
            }
        });
        add(btncargarrdatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 40, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed

        crearComentario();
        limpiarCampos();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        String id = txtIDComen.getText();
        buscarPorId(id);
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarComentario();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarComentario();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mostrarDatosClienteSeleccionado();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnVerCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCasaActionPerformed
        mostrarDatosCasaSeleccionada();
    }//GEN-LAST:event_btnVerCasaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cargarTabla();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btncargarrdatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargarrdatosActionPerformed
        consultarComentario();
    }//GEN-LAST:event_btncargarrdatosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JTable TablaComentario;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVerCasa;
    private javax.swing.JButton btncargarrdatos;
    private javax.swing.JComboBox<String> cbxCasas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
