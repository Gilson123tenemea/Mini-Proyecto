package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Comentario;
import BD.AlquilerCasas.Clases.Contrato;
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

public class CRUD_Clientes extends javax.swing.JPanel {

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
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Cedula:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 50, -1, -1));

        jLabel2.setText("Nombre:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 90, -1, -1));

        jLabel6.setText("Correo:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(459, 52, -1, -1));

        jLabel3.setText("Apellido:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 132, -1, -1));

        jLabel4.setText("Edad:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 177, -1, -1));

        jLabel8.setText("Sexo:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        btngrupSexo.add(rbtnHombre);
        rbtnHombre.setText("Hombre");
        rbtnHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHombreActionPerformed(evt);
            }
        });
        add(rbtnHombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, -1, -1));

        jLabel9.setText("Celular:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        btngrupSexo.add(rbtnMujer);
        rbtnMujer.setText("mujer");
        add(rbtnMujer, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, -1));

        jLabel5.setText("Nacionalidad:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, -1, -1));

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ecuatoriano", "Mexicano", "Canadiense", "Brasileño", "Ucraniana", "Británica", "Escocesa", "Finlandesa", "Austriaca", "Rusa", "Española" }));
        add(cbxNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 160, 28));

        jLabel12.setText("Fecha Nacimiento:");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, -1));
        add(dchFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 132, 30));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnCrear.setText("GUARDAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, -1, -1));

        btnmod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnmod.setText("MODIFICAR");
        btnmod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodActionPerformed(evt);
            }
        });
        add(btnmod, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, -1, 40));

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 290, -1, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 352, 814, 240));

        btnreport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnreport.setText("REPORTE");
        btnreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportActionPerformed(evt);
            }
        });
        add(btnreport, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 290, 130, 40));

        spnEdad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        add(spnEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 172, 79, -1));
        add(txtcelu, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 160, 30));
        add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 46, 160, -1));

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 85, 160, 27));
        add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(148, 127, 160, 27));
        add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(524, 46, 160, 29));

        btncargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargardatosActionPerformed(evt);
            }
        });
        add(btncargardatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 70, 40));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("REGISTRO DE CLIENTE");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, -1, -1));

        ComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cedula", "Nombre", "Genero" }));
        ComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFiltroActionPerformed(evt);
            }
        });
        add(ComboBoxFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 123, -1));

        btnBuscarFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnBuscarFiltro.setText("Buscar");
        btnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltroActionPerformed(evt);
            }
        });
        add(btnBuscarFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 230, -1, -1));

        jLabel10.setText("FILTRO BUSQUEDA");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, -1, -1));
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        while (result.hasNext()) {
            Cliente cliente = result.next();

            Object[] row = {
                cliente.getCedula(),
                cliente.getNombreCliente(),
                cliente.getApellidoCliente(),
                cliente.getEdadCliente(),
                cliente.getGeneroCliente(),
                cliente.getCelular(),
                cliente.getCorreo(),
                cliente.getNacionalidad(),
                cliente.getFecha_Naci() != null ? sdf.format(cliente.getFecha_Naci()) : null // Verificar si la fecha no es null antes de formatearla
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

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed
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
            // Segundo, eliminamos la relación con el contrato
            Query queryContrato = BaseD.query();
            queryContrato.constrain(Contrato.class);
            queryContrato.descend("IDCliente").constrain(cedula);
            ObjectSet<Contrato> resultContrato = queryContrato.execute();
            while (resultContrato.hasNext()) {
                Contrato contrato = resultContrato.next();
                contrato.setIDCliente(null); // Eliminamos la referencia del cliente en el contrato
                BaseD.store(contrato);
            }
            // Tercero, eliminamos la relación con el comentario
            Query queryComentario = BaseD.query();
            queryComentario.constrain(Comentario.class);
            queryComentario.descend("IDCliente").constrain(cedula);
            ObjectSet<Comentario> resultComentario = queryComentario.execute();
            while (resultComentario.hasNext()) {
                Comentario comentario = resultComentario.next();
                comentario.setIDCliente(null); // Eliminamos la referencia del cliente en el comentario
                BaseD.store(comentario);
            }
            // Cuarto, eliminamos la relación con la factura
            Query queryFactura = BaseD.query();
            queryFactura.constrain(Factura.class);
            queryFactura.descend("IDCliente").constrain(cedula);
            ObjectSet<Factura> resultFactura = queryFactura.execute();
            while (resultFactura.hasNext()) {
                Factura factura = resultFactura.next();
                factura.setIDCliente(null); // Eliminamos la referencia del cliente en la factura
                BaseD.store(factura);
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
