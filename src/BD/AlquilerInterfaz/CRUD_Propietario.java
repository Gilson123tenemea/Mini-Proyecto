package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Propietario;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Propietario extends javax.swing.JPanel {

    public static ObjectContainer BaseD = Db4o.openFile(dashboard.direccionBD);

    public CRUD_Propietario() {
        initComponents();
        cargarTabla();
        // Cerrar la base de datos cuando se presiona la x
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                cerrarBaseDatos();
            }
        });
    }

    // Método para validar los campos de la interfaz
    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.validarCedula(txtCedula.getText())) {
            JOptionPane.showMessageDialog(this, "Cédula incorrecta. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarNomApe(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Nombre incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el apellido del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarNomApe(txtApellido.getText())) {
            JOptionPane.showMessageDialog(this, "Apellido incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }

        // Validar otros campos aquí...
        if (txtCelular.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el celular del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.validarCedula(txtCelular.getText())) {
            JOptionPane.showMessageDialog(this, "Celular incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el correo del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarCorreo(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Correo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (cbxNacionalidad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese la nacionalidad del cliente");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(cbxNacionalidad.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Nacionalidad inválida");
                ban_confirmar = false;
            }
        }
        return ban_confirmar;
    }

    // Método para crear un nuevo propietario
    private void crearPropietario() {
        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText();

        // Consultar si ya existe un propietario con la misma cédula
        ObjectSet<Propietario> result = BaseD.queryByExample(new Propietario(cedula, null, null, null, 0, null, null, null, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un propietario con la cédula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si no existe un propietario con la misma cédula, proceder con la creación
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String genero = rbtnHombre.isSelected() ? "H" : "M";
        int edad = (int) spnEdad.getValue();
        String telefono = txtCelular.getText();
        String correo = txtCorreo.getText();
        String nacionalidad = cbxNacionalidad.getSelectedItem().toString();
        Date fechaNacimiento = dchFechaNacimiento.getDate();

        Propietario propietario = new Propietario(cedula, nombre, apellido, genero, edad, telefono, correo, nacionalidad, fechaNacimiento);
        BaseD.store(propietario); // Almacenar el objeto en la base de datos

        JOptionPane.showMessageDialog(null, "Propietario creado exitosamente.");
        limpiarCampos();
        cargarTabla();
    }

    // Método para consultar un propietario por su cédula
    private void consultarPropietario() {
        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(Propietario.class);
        query.descend("CedulaPropietario").constrain(cedula);
        ObjectSet<Propietario> result = query.execute();
        if (!result.isEmpty()) {
            Propietario propietario = result.next();
            mostrarPropietario(propietario);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un propietario con la cédula ingresada.");
            limpiarCampos();
        }
    }

    // Método para modificar un propietario existente
    private void modificarPropietario() {
        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(Propietario.class);
        query.descend("CedulaPropietario").constrain(cedula);
        ObjectSet<Propietario> result = query.execute();
        if (!result.isEmpty()) {
            Propietario propietario = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            propietario.setNombrePropietario(txtNombre.getText());
            propietario.setApellidoPropietario(txtApellido.getText());
            propietario.setGeneroPropietario(rbtnHombre.isSelected() ? "H" : "M");
            propietario.setEdadPropietario((int) spnEdad.getValue());
            propietario.setTelfPropietario(txtCelular.getText());
            propietario.setCorreo_propi(txtCorreo.getText());
            propietario.setNacionalidad_propi(cbxNacionalidad.getSelectedItem().toString());
            propietario.setFecha_Naci(dchFechaNacimiento.getDate());

            BaseD.store(propietario); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Propietario modificado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un propietario con la cédula ingresada.");
        }
    }

    // Método para eliminar un propietario existente
    private void eliminarPropietario() {
        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(Propietario.class);
        query.descend("CedulaPropietario").constrain(cedula);
        ObjectSet<Propietario> result = query.execute();
        if (!result.isEmpty()) {
            Propietario propietario = result.next();
            BaseD.delete(propietario); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "Propietario eliminado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un propietario con la cédula ingresada.");
        }
    }

    // Método para generar el reporte de propietarios
    private void generarReporte() {
        // Lógica para generar el reporte
        //JOptionPane.showMessageDialog(null, "Reporte generado exitosamente.");
    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        rbtnHombre.setSelected(true);
        spnEdad.setValue(1);
        txtCelular.setText("");
        txtCorreo.setText("");
        cbxNacionalidad.setSelectedIndex(0);
        dchFechaNacimiento.setDate(null);
    }

    // Método para cargar la tabla con los propietarios existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaPropietarios.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Propietario> result = BaseD.queryByExample(Propietario.class);
        while (result.hasNext()) {
            Propietario propietario = result.next();
            Object[] row = {
                propietario.getCedulaPropietario(),
                propietario.getNombrePropietario(),
                propietario.getApellidoPropietario(),
                propietario.getEdadPropietario(),
                propietario.getGeneroPropietario(),
                propietario.getTelfPropietario(),
                propietario.getCorreo_propi(),
                propietario.getNacionalidad_propi(),
                propietario.getFecha_Naci()
            };
            model.addRow(row);
        }
    }

    // Método para mostrar los datos de un propietario en los campos de la interfaz
    private void mostrarPropietario(Propietario propietario) {
        txtNombre.setText(propietario.getNombrePropietario());
        txtApellido.setText(propietario.getApellidoPropietario());
        rbtnHombre.setSelected(propietario.getGeneroPropietario() == "H");
        rbtnMujer.setSelected(propietario.getGeneroPropietario() == "M");
        spnEdad.setValue(propietario.getEdadPropietario());
        txtCelular.setText(propietario.getTelfPropietario());
        txtCorreo.setText(propietario.getCorreo_propi());
        cbxNacionalidad.setSelectedItem(propietario.getNacionalidad_propi());
        dchFechaNacimiento.setDate(propietario.getFecha_Naci());
    }

    public static void cerrarBaseDatos() {
        BaseD.close(); // Cerrar la base de datos
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrupSexo = new javax.swing.ButtonGroup();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        btncargardatos = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbxNacionalidad = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        dchFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        spnEdad = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        rbtnHombre = new javax.swing.JRadioButton();
        rbtnMujer = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPropietarios = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("REGISTRO PROPIETARIOS");

        jLabel1.setText("Cedula:");

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });

        btncargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargardatosActionPerformed(evt);
            }
        });

        jLabel6.setText("Correo:");

        jLabel2.setText("Nombre:");

        jLabel5.setText("Nacionalidad:");

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ecuatoriano", "Mexicano", "Canadiense", "Brasileño", "Ucraniana", "Británica", "Escocesa", "Finlandesa", "Austriaca", "Rusa", "Española" }));

        jLabel3.setText("Apellido:");

        jLabel12.setText("Fecha Nacimiento:");

        jLabel4.setText("Edad:");

        spnEdad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel8.setText("Sexo:");

        btngrupSexo.add(rbtnHombre);
        rbtnHombre.setText("Hombre");
        rbtnHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHombreActionPerformed(evt);
            }
        });

        btngrupSexo.add(rbtnMujer);
        rbtnMujer.setText("Mujer");

        jLabel9.setText("Celular:");

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sav.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        TablaPropietarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Sexo", "Celular", "Correo", "Nacionalidad", "Fecha Nacimiento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaPropietarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(4, 4, 4)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbtnHombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnMujer))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12)
                                            .addGap(18, 18, 18)
                                            .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(cbxNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(28, 28, 28)
                                                    .addComponent(btncargardatos)
                                                    .addGap(102, 102, 102)
                                                    .addComponent(jLabel6)
                                                    .addGap(23, 23, 23)
                                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(btnCrear)
                                .addGap(18, 18, 18)
                                .addComponent(btnConsultar)
                                .addGap(18, 18, 18)
                                .addComponent(btnModificar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReporte)))
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(btncargardatos)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(38, 38, 38)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jLabel12)
                    .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(rbtnHombre)
                        .addComponent(rbtnMujer))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnConsultar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnReporte))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btncargardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargardatosActionPerformed
        txtCedula.setEnabled(false);
        btnCrear.setEnabled(false);
        consultarPropietario();
    }//GEN-LAST:event_btncargardatosActionPerformed

    private void rbtnHombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnHombreActionPerformed

    }//GEN-LAST:event_rbtnHombreActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearPropietario();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        //consultarPropietario();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarPropietario();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarPropietario();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        cargarTabla();
    }//GEN-LAST:event_btnReporteActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPropietarios;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btncargardatos;
    private javax.swing.ButtonGroup btngrupSexo;
    private javax.swing.JComboBox<String> cbxNacionalidad;
    private com.toedter.calendar.JDateChooser dchFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtnHombre;
    private javax.swing.JRadioButton rbtnMujer;
    private javax.swing.JSpinner spnEdad;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
