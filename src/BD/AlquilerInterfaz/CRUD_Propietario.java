package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Propietario;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Propietario extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Propietario(ObjectContainer BaseD) {
        this.BaseD = BaseD; // Asignar la instancia pasada como parámetro
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
        String genero = rbtnHombre.isSelected() ? "Hombre" : "Mujer";
        if (rbtnHombre.isSelected()) {
            genero = "Hombre";
        } else if (rbtnMujer.isSelected()) {
            genero = "Mujer";
        }
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
            propietario.setGeneroPropietario(rbtnHombre.isSelected() ? "Hombre" : "Mujer");
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
            String genero = propietario.getGeneroPropietario().equals("H") ? "Hombre" : "Mujer";

            Object[] row = {
                propietario.getCedulaPropietario(),
                propietario.getNombrePropietario(),
                propietario.getApellidoPropietario(),
                propietario.getEdadPropietario(),
                genero,
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
        if (propietario.getGeneroPropietario().equals("Hombre")) {
            rbtnHombre.setSelected(true);
        } else if (propietario.getGeneroPropietario().equals("Mujer")) {
            rbtnMujer.setSelected(true);
        }
        spnEdad.setValue(propietario.getEdadPropietario());
        txtCelular.setText(propietario.getTelfPropietario());
        txtCorreo.setText(propietario.getCorreo_propi());
        cbxNacionalidad.setSelectedItem(propietario.getNacionalidad_propi());
        dchFechaNacimiento.setDate(propietario.getFecha_Naci());
    }
    //////////////////////////////////// filtra clientes ///

    private void filtrarPropietriod(String criterio, String valorBusqueda) {
        DefaultTableModel model = (DefaultTableModel) TablaPropietarios.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Propietario> result;

        if (criterio.equals("Cedula")) {
            result = BaseD.queryByExample(new Propietario(valorBusqueda, null, null, null, 0, null, null, null, null));
        } else if (criterio.equals("Nombre")) {
            result = BaseD.queryByExample(new Propietario(null, valorBusqueda, null, null, 0, null, null, null, null));
        } else if (criterio.equals("Genero")) {
            result = BaseD.queryByExample(new Propietario(null, null, null, valorBusqueda.equals("Hombre") ? "H" : "Mujer", 0, null, null, null, null));
        } else {
            // Criterio inválido, no se realiza la búsqueda
            return;
        }

        while (result.hasNext()) {
            Propietario propietario = result.next();
            String genero = propietario.getGeneroPropietario().equals("H") ? "Hombre" : "Mujer";

            Object[] row = {
                propietario.getCedulaPropietario(),
                propietario.getNombrePropietario(),
                propietario.getApellidoPropietario(),
                propietario.getEdadPropietario(),
                genero,
                propietario.getTelfPropietario(),
                propietario.getCorreo_propi(),
                propietario.getNacionalidad_propi(),
                propietario.getFecha_Naci()
            };

            model.addRow(row);
        }
    }

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

    public void habilitarParametros() {
        txtCedula.setEnabled(true);
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        spnEdad.setEnabled(true);
        rbtnHombre.setEnabled(true);
        rbtnMujer.setEnabled(true);
        txtCelular.setEnabled(true);
        txtCorreo.setEnabled(true);
        cbxNacionalidad.setEnabled(true);

    }

    public void deshabilitarParametros() {
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        spnEdad.setEnabled(false);
        rbtnHombre.setEnabled(false);
        rbtnMujer.setEnabled(false);
        txtCelular.setEnabled(false);
        txtCorreo.setEnabled(false);
        cbxNacionalidad.setEnabled(false);

    }

    public static void cerrarBaseDatos() {
        //BaseD.close(); // Cerrar la base de datos
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
        ComboBoxFiltro = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnBuscarFiltro = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("REGISTRO PROPIETARIOS");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel1.setText("Cedula:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 150, -1));

        btncargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btncargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncargardatosActionPerformed(evt);
            }
        });
        add(btncargardatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, -1, -1));

        jLabel6.setText("Correo:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));
        add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 150, 29));

        jLabel2.setText("Nombre:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));
        add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 150, 27));

        jLabel5.setText("Nacionalidad:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ecuatoriano", "Mexicano", "Canadiense", "Brasileño", "Ucraniana", "Británica", "Escocesa", "Finlandesa", "Austriaca", "Rusa", "Española" }));
        add(cbxNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 150, 28));

        jLabel3.setText("Apellido:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 150, 27));

        jLabel12.setText("Fecha Nacimiento:");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, -1, -1));
        add(dchFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 150, 30));

        jLabel4.setText("Edad:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        spnEdad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        add(spnEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 80, -1));

        jLabel8.setText("Sexo:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        btngrupSexo.add(rbtnHombre);
        rbtnHombre.setText("Hombre");
        rbtnHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHombreActionPerformed(evt);
            }
        });
        add(rbtnHombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        btngrupSexo.add(rbtnMujer);
        rbtnMujer.setText("mujer");
        rbtnMujer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMujerActionPerformed(evt);
            }
        });
        add(rbtnMujer, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 70, -1));

        jLabel9.setText("Celular:");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));
        add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 150, 30));

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sav.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, 110, -1));

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 160, -1, -1));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 240, -1, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, -1, -1));

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, -1, 40));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 780, 220));

        ComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cedula", "Nombre", "Genero" }));
        ComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFiltroActionPerformed(evt);
            }
        });
        add(ComboBoxFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 123, -1));

        jLabel10.setText("FILTRO BUSQUEDA");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        btnBuscarFiltro.setText("Buscar");
        btnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltroActionPerformed(evt);
            }
        });
        add(btnBuscarFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 120, -1, -1));
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
        consultarPropietario();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarPropietario();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarPropietario();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
    }//GEN-LAST:event_btnReporteActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

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
            filtrarPropietriod(criterioSeleccionado, valorBusqueda);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro propietarios con esos parametros");
        }
    }//GEN-LAST:event_btnBuscarFiltroActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void rbtnMujerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMujerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnMujerActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFiltro;
    private javax.swing.JTable TablaPropietarios;
    private javax.swing.JButton btnBuscarFiltro;
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
