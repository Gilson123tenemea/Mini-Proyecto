package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Reservacion;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Clientes extends javax.swing.JPanel {

    //public static ObjectContainer BaseD = Db4o.openFile(dashboard.direccionBD);
    private ObjectContainer BaseD;
    String CedulaCli = "";
    String NombreCli = "";
    String ApellidoCli = "";
    int EdadCli = 0;
    String GeneroCli = "";
    String CelularCli = "";
    String correoCli = "";
    String NacionalidadCli = "";
    Date fechaNaciCli = null;

    public CRUD_Clientes(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrupSexo = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rbtnHombre = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rbtnMujer = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        cbxNacionalidad = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        dchFechaNacimiento = new com.toedter.calendar.JDateChooser();
        btnCrear = new javax.swing.JButton();
        btnmod = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePersona = new javax.swing.JTable();
        btnreport = new javax.swing.JButton();
        spnEdad = new javax.swing.JSpinner();
        txtcelu = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        btncargardatos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ComboBoxFiltro = new javax.swing.JComboBox<>();
        btnBuscarFiltro = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Cedula:");

        jLabel2.setText("Nombre:");

        jLabel6.setText("Correo:");

        jLabel3.setText("Apellido:");

        jLabel4.setText("Edad:");

        jLabel8.setText("Sexo:");

        btngrupSexo.add(rbtnHombre);
        rbtnHombre.setText("Hombre");
        rbtnHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHombreActionPerformed(evt);
            }
        });

        jLabel9.setText("Celular:");

        btngrupSexo.add(rbtnMujer);
        rbtnMujer.setText("mujer");

        jLabel5.setText("Nacionalidad:");

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ecuatoriano", "Mexicano", "Canadiense", "Brasileño", "Ucraniana", "Británica", "Escocesa", "Finlandesa", "Austriaca", "Rusa", "Española" }));

        jLabel12.setText("Fecha Nacimiento:");

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sav.png"))); // NOI18N
        btnCrear.setText("GUARDAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnmod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnmod.setText("MODIFICAR");
        btnmod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodActionPerformed(evt);
            }
        });

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        jTablePersona.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTablePersona);

        btnreport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnreport.setText("REPORTE");
        btnreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportActionPerformed(evt);
            }
        });

        spnEdad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        btncargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargardatosActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("REGISTRO DE CLIENTE");

        ComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cedula", "Nombre", "Genero" }));
        ComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFiltroActionPerformed(evt);
            }
        });

        btnBuscarFiltro.setText("Buscar");
        btnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltroActionPerformed(evt);
            }
        });

        jLabel10.setText("FILTRO BUSQUEDA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(23, 23, 23)
                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(247, 247, 247))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btncargardatos)))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbxNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnCrear)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbtnHombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbtnMujer)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(393, 393, 393)
                                        .addComponent(btnBuscarFiltro))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btnmod)
                                        .addGap(18, 18, 18)
                                        .addComponent(btneliminar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnreport)
                                        .addGap(44, 44, 44))))
                            .addComponent(spnEdad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcelu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(64, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btncargardatos, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addGap(10, 10, 10)
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
                        .addComponent(jLabel4)
                        .addComponent(jLabel12))
                    .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(rbtnHombre)
                        .addComponent(rbtnMujer)
                        .addComponent(ComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(btnBuscarFiltro))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcelu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnmod, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneliminar)
                    .addComponent(btnreport, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnHombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnHombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnHombreActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        crearCliente();
    }//GEN-LAST:event_btnCrearActionPerformed

    // Método para crear un nuevo propietario
    private void crearCliente() {
        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText();

        // Consultar si ya existe un cliente con la misma cédula
        ObjectSet<Cliente> result = BaseD.queryByExample(new Cliente(cedula, null, null, null, 0, null, null, null, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un cliente con la cédula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si no existe un cliente con la misma cédula, proceder con la creación
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String genero = rbtnHombre.isSelected() ? "Hombre" : "Mujer";
        int edad = (int) spnEdad.getValue();
        String telefono = txtcelu.getText();
        String correo = txtCorreo.getText();
        String nacionalidad = cbxNacionalidad.getSelectedItem().toString();
        Date fechaNacimiento = dchFechaNacimiento.getDate();

        Cliente mi_cli = new Cliente(cedula, nombre, apellido, genero, edad, telefono, correo, nacionalidad, fechaNacimiento);
        BaseD.store(mi_cli); // Almacenar el objeto en la base de datos

        JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.");
        limpiarCampos();
        cargarTabla();
    }

    // Método para consultar un propietario por su cédula
    private void consultarCliente() {
        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(Cliente.class);
        query.descend("Cedula").constrain(cedula); // Cedula que esta en la clase
        ObjectSet<Cliente> result = query.execute();
        if (!result.isEmpty()) {
            Cliente cliente = result.next();
            mostrarCliente(cliente);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
            limpiarCampos();
        }
    }

    // Método para modificar un propietario existente
    private void modificarCliente() {
        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(Cliente.class);
        query.descend("Cedula").constrain(cedula);
        ObjectSet<Cliente> result = query.execute();
        if (!result.isEmpty()) {
            Cliente cliente = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            cliente.setNombreCliente(txtNombre.getText());
            cliente.setApellidoCliente(txtApellido.getText());
            cliente.setGeneroCliente(rbtnHombre.isSelected() ? "Hombre" : "Mujer");
            cliente.setEdadCliente((int) spnEdad.getValue());
            cliente.setCelular(txtcelu.getText());
            cliente.setCorreo(txtCorreo.getText());
            cliente.setNacionalidad(cbxNacionalidad.getSelectedItem().toString());
            cliente.setFecha_Naci(dchFechaNacimiento.getDate());

            BaseD.store(cliente); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Cliente modificado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
        }
    }

    // Método para mostrar los datos de un propietario en los campos de la interfaz
    private void mostrarCliente(Cliente cliente) {
        txtNombre.setText(cliente.getNombreCliente());
        txtApellido.setText(cliente.getApellidoCliente());
        rbtnHombre.setSelected(cliente.getGeneroCliente() == "H");
        rbtnMujer.setSelected(cliente.getGeneroCliente() == "M");
        spnEdad.setValue(cliente.getEdadCliente());
        txtcelu.setText(cliente.getCelular());
        txtCorreo.setText(cliente.getCorreo());
        cbxNacionalidad.setSelectedItem(cliente.getNacionalidad());
        dchFechaNacimiento.setDate(cliente.getFecha_Naci());
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
        if (txtcelu.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el celular del cliente");
            ban_confirmar = false;
        } else if (!miValidaciones.validarCedula(txtcelu.getText())) {
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


    private void btnmodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodActionPerformed
        modificarCliente();
    }//GEN-LAST:event_btnmodActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminarCliente();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreportActionPerformed
        // TODO add your handling code here:
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
    }//GEN-LAST:event_btnreportActionPerformed
    // Método para cargar la tabla con los propietarios existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) jTablePersona.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Cliente> result = BaseD.queryByExample(Cliente.class);
        while (result.hasNext()) {
            Cliente cliente = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                cliente.getCedula(),
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                cliente.getEdadCliente(),
                cliente.getGeneroCliente(),
                cliente.getCelular(),
                cliente.getCorreo(),
                cliente.getNacionalidad(),
                sdf.format(cliente.getFecha_Naci())

            };
            model.addRow(row);
        }
    }
    //////////////////////////////////// filtra clientes ///

    private void filtrarClientes(String criterio, String valorBusqueda) {
        DefaultTableModel model = (DefaultTableModel) jTablePersona.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Cliente> result;

        if (criterio.equals("Cedula")) {
            result = BaseD.queryByExample(new Cliente(valorBusqueda, null, null, null, 0, null, null, null, null));
        } else if (criterio.equals("Nombre")) {
            result = BaseD.queryByExample(new Cliente(null, valorBusqueda, null, null, 0, null, null, null, null));
        } else if (criterio.equals("Genero")) {
            Cliente filtro = new Cliente(null, null, null, valorBusqueda, 0, null, null, null, null);
            result = BaseD.queryByExample(filtro);
        } else {
            // Criterio inválido, no se realiza la búsqueda
            return;
        }

        while (result.hasNext()) {
            Cliente cliente = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                cliente.getCedula(),
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                cliente.getEdadCliente(),
                cliente.getGeneroCliente(),
                cliente.getCelular(),
                cliente.getCorreo(),
                cliente.getNacionalidad(),
                sdf.format(cliente.getFecha_Naci())

            };
            model.addRow(row);
        }
    }


    private void btncargardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargardatosActionPerformed
        txtCedula.setEnabled(false);
        btnCrear.setEnabled(false);
        consultarCliente();

    }//GEN-LAST:event_btncargardatosActionPerformed

    private void ComboBoxFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFiltroActionPerformed
        // Obtener el criterio seleccionado del JComboBox
        String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

        // Habilitar o deshabilitar los campos de búsqueda según el criterio seleccionado
        habilitarCamposBusqueda(criterioSeleccionado);
    }//GEN-LAST:event_ComboBoxFiltroActionPerformed

    private void btnBuscarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFiltroActionPerformed
        // TODO add your handling code here:
        try {
            // Obtener el criterio seleccionado del JComboBox
            String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

            // Obtener el valor de búsqueda ingresado por el usuario
            String valorBusqueda = obtenerValorBusqueda(criterioSeleccionado);

            // Realizar la búsqueda y cargar los resultados en el JTable
            filtrarClientes(criterioSeleccionado, valorBusqueda);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro clientes con esos parametros");
        }

    }//GEN-LAST:event_btnBuscarFiltroActionPerformed
    private void habilitarCamposBusqueda(String criterioSeleccionado) {
        // Deshabilitar todos los campos de búsqueda
        deshabilitarParametros();
        // ...

        // Habilitar el campo de búsqueda correspondiente al criterio seleccionado
        if (criterioSeleccionado.equals("Cedula")) {
            txtCedula.setEnabled(true);
        } else if (criterioSeleccionado.equals("Nombre")) {
            txtNombre.setEnabled(true);
        } else if (criterioSeleccionado.equals("Genero")) {
            rbtnHombre.setEnabled(true);
            rbtnMujer.setEnabled(true);
        }
        // ...
    }

    private String obtenerValorBusqueda(String criterioSeleccionado) {
        String valorBusqueda = "";

        // Obtener el valor de búsqueda según el criterio seleccionado
        if (criterioSeleccionado.equals("Cedula")) {
            valorBusqueda = txtCedula.getText();
        } else if (criterioSeleccionado.equals("Nombre")) {
            valorBusqueda = txtNombre.getText();
        } else if (criterioSeleccionado.equals("Genero")) {
            if (rbtnHombre.isSelected()) {
                valorBusqueda = "Hombre";
            } else if (rbtnMujer.isSelected()) {
                valorBusqueda = "Mujer";
            }
        }
        // ...

        return valorBusqueda;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Método para eliminar un cliente existente

    private void eliminarCliente() {
        String cedula = txtCedula.getText().trim();
        Query query = BaseD.query();
        query.constrain(Cliente.class);
        query.descend("Cedula").constrain(cedula);
        ObjectSet<Cliente> result = query.execute();

        if (!result.isEmpty()) {
            Cliente cliente = result.next();

            // Primero, eliminamos la relación con las reservaciones
            Query queryReservacion = BaseD.query();
            queryReservacion.constrain(Reservacion.class);
            queryReservacion.descend("IDCliente").constrain(cedula);
            ObjectSet<Reservacion> resultReservacion = queryReservacion.execute();
            while (resultReservacion.hasNext()) {
                Reservacion reservacion = resultReservacion.next();
                reservacion.setIDCliente(null); // Eliminamos la referencia del cliente en la reservacion
                BaseD.store(reservacion);
            }

            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este cliente con la cédula: " + cedula + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                BaseD.delete(cliente); // Eliminar el objeto de la base de datos
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
                limpiarCampos();
                cargarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
        }
    }

    public void habilitarParametros() {
        txtCedula.setEnabled(true);
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        spnEdad.setEnabled(true);
        rbtnHombre.setEnabled(true);
        rbtnMujer.setEnabled(true);
        txtcelu.setEnabled(true);
        txtCorreo.setEnabled(true);
        cbxNacionalidad.setEnabled(true);

    }

    public void deshabilitarParametros() {
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        spnEdad.setEnabled(false);
        rbtnHombre.setEnabled(false);
        rbtnMujer.setEnabled(false);
        txtcelu.setEnabled(false);
        txtCorreo.setEnabled(false);
        cbxNacionalidad.setEnabled(false);

    }
// Método para limpiar los campos de la interfaz

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        rbtnHombre.setSelected(true);
        spnEdad.setValue(1);
        txtcelu.setText("");
        txtCorreo.setText("");
        cbxNacionalidad.setSelectedIndex(0);
        dchFechaNacimiento.setDate(null);
    }

    public static void cerrarBaseDatos() {
        //BaseD.close(); // Cerrar la base de datos
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFiltro;
    private javax.swing.JButton btnBuscarFiltro;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btncargardatos;
    private javax.swing.JButton btneliminar;
    private javax.swing.ButtonGroup btngrupSexo;
    private javax.swing.JButton btnmod;
    private javax.swing.JButton btnreport;
    private javax.swing.JComboBox<String> cbxNacionalidad;
    private com.toedter.calendar.JDateChooser dchFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTable jTablePersona;
    private javax.swing.JRadioButton rbtnHombre;
    private javax.swing.JRadioButton rbtnMujer;
    private javax.swing.JSpinner spnEdad;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtcelu;
    // End of variables declaration//GEN-END:variables
}
