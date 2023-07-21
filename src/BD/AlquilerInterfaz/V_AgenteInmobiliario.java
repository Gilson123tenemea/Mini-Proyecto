package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.AgenteInmobiliario;
import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class V_AgenteInmobiliario extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public V_AgenteInmobiliario(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarCasas();
        cargarTabla();
    }

    public void cargarCasas() {
        cbxCasa.removeAllItems();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);

        ObjectSet<CasaVacacional> casas = query.execute();

        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay casas vacacionales disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                cbxCasa.addItem(casa.getId_casa());
            }
        }
    }

//    private void cargarCasasDelAgente(String cedulaAgente) {
//        Query query = BaseD.query();
//        query.constrain(CasaVacacional.class);
//        query.descend("IDPropietario").constrain(cedulaAgente);
//        ObjectSet<CasaVacacional> casasDelAgente = query.execute();
//
//        cbxCasa.removeAllItems(); // Limpiar los elementos anteriores en el combobox
//
//        for (CasaVacacional casa : casasDelAgente) {
//            cbxCasa.addItem(casa.getId_casa() + " - " + casa.getNombre()); // Asumiendo que getId_casa() devuelve la ID de la casa
//        }
//    }
//
//    private void crearCasa() {
//        try {
//            if (!validarCampos()) {
//                return;
//            }
//            String IDcas = cbxCasa.getSelectedItem().toString();
//            String idagente = txtCedula.getText();
//
//            cargarCasasDelAgente(idagente); // Cargar las casas del agente seleccionado en el combobox
//
//            // Resto del código para crear la casa...
//        } catch (Exception e) {
//            System.out.println("No se ha seleccionado un propietario o un vehiculo del combo box, puede ser que no exista ningun registro");
//        }
//    }
    // Método para validar los campos de la interfaz
    public boolean validarCampos() {
        Validaciones validaciones = new Validaciones();

        // Validar cédula
        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula del cliente");
            return false;
        } else if (!validaciones.validarCedula(txtCedula.getText())) {
            JOptionPane.showMessageDialog(this, "Cédula incorrecta. Ingrese de nuevo");
            return false;
        }

        // Validar nombre
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cliente");
            return false;
        } else if (!validaciones.ValidarNomApe(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Nombre incorrecto. Ingrese de nuevo");
            return false;
        }

        // Validar apellido
        if (txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el apellido del cliente");
            return false;
        } else if (!validaciones.ValidarNomApe(txtApellido.getText())) {
            JOptionPane.showMessageDialog(this, "Apellido incorrecto. Ingrese de nuevo");
            return false;
        }

        // Validar celular
        if (txtCelular.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el celular del cliente");
            return false;
        } else if (!validaciones.validarCedula(txtCelular.getText())) {
            JOptionPane.showMessageDialog(this, "Celular incorrecto. Ingrese de nuevo");
            return false;
        }

        // Validar correo
        if (txtCorreo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el correo del cliente");
            return false;
        } else if (!validaciones.ValidarCorreo(txtCorreo.getText())) {
            JOptionPane.showMessageDialog(this, "Correo incorrecto. Ingrese de nuevo");
            return false;
        }

        // Validar nacionalidad
        if (cbxNacionalidad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese la nacionalidad del cliente");
            return false;
        } else if (!validaciones.ValidarCiudad(cbxNacionalidad.getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(this, "Nacionalidad inválida");
            return false;
        }

        return true;
    }

    // Método para crear un nuevo agente inmobiliario
    private void crearAgenteInmobiliario() {
        try {
            if (!validarCampos()) {
                return;
            }

            String cedula = txtCedula.getText();

            // Consultar si ya existe un propietario con la misma cédula
            ObjectSet<AgenteInmobiliario> result = BaseD.queryByExample(new AgenteInmobiliario(cedula, null, null, null, 0, null, null, null, null, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe un agente inmobiliario con la cédula ingresada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si no existe un agente con la misma cédula, proceder con la creación
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
            //String id_casa = txtIdcasa.getText();
            String casa = cbxCasa.getSelectedItem().toString();

            AgenteInmobiliario agente = new AgenteInmobiliario(cedula, nombre, apellido, genero, edad, telefono, correo, nacionalidad, fechaNacimiento, casa);
            BaseD.store(agente); // Almacenar el objeto en la base de datos

            JOptionPane.showMessageDialog(null, "Agente inmobiliario creado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa vacacional antes de guardar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarAgenteInmobiliario() {
        String cedula = txtCedula.getText();
        AgenteInmobiliario ejemplo = new AgenteInmobiliario(cedula, null, null, null, 0, null, null, null, null, null);
        ObjectSet<AgenteInmobiliario> result = BaseD.queryByExample(ejemplo);

        if (!result.isEmpty()) {
            AgenteInmobiliario agente = result.get(0);
            mostrarAgente(agente);
            txtCedula.setEnabled(false);
            btnCrear.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un agente inmobiliario con la cédula ingresada.");
            limpiarCampos();
        }
    }

    private void modificarAgente() {
        if (!validarCampos()) {
            return;
        }

        String cedula = txtCedula.getText();
        Query query = BaseD.query();
        query.constrain(AgenteInmobiliario.class);
        query.descend("CedulaAgente").constrain(cedula);
        ObjectSet<AgenteInmobiliario> result = query.execute();
        if (!result.isEmpty()) {
            AgenteInmobiliario agente = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            agente.setNombreAgente(txtNombre.getText());
            agente.setApellidoAgente(txtApellido.getText());
            agente.setGeneroAgente(rbtnHombre.isSelected() ? "Hombre" : "Mujer");
            agente.setEdadAgente((int) spnEdad.getValue());
            agente.setCelularAgente(txtCelular.getText());
            agente.setCorreoAgente(txtCorreo.getText());
            agente.setNacionalidadAgente(cbxNacionalidad.getSelectedItem().toString());
            agente.setFecha_NaciAgente(dchFechaNacimiento.getDate());
            agente.setId_casa(cbxCasa.getSelectedItem().toString());

            BaseD.store(agente); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "agente modificado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un agnete con la cédula ingresada.");
        }
    }

    private void eliminarAgente() {
        try {
            String cedula = txtCedula.getText().trim();
            Query query = BaseD.query();
            query.constrain(AgenteInmobiliario.class);
            query.descend("Cedula").constrain(cedula);
            ObjectSet<AgenteInmobiliario> result = query.execute();

            if (!result.isEmpty()) {
                AgenteInmobiliario agente = result.next();

                // Realizar la validación para verificar si el agente está relacionado con la casa seleccionada
                if (agente.getId_casa() == null || agente.getId_casa().isEmpty()) {
                    //JOptionPane.showMessageDialog(null, "No se puede eliminar el agente porque está relacionado con la casa seleccionada.");
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este agente con la cédula: " + cedula + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        BaseD.delete(agente); // Eliminar el objeto de la base de datos
                        JOptionPane.showMessageDialog(null, "Agente eliminado exitosamente.");
                        limpiarCampos();
                        cargarTabla();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No puedes eliminar este agente, ya que esta relacionada con una casa vacacional\n primero elimina la casa correspondiente");

                    BaseD.store(agente); // Actualizar la casa en la base de datos
                    limpiarCampos();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una agente con la cedula ingresada.");

            }
        } catch (Exception e) {
            System.out.println("Error al eliminar porque no hay casas");
            e.printStackTrace();
        }

    }
    // Otros métodos auxiliares

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) jtbTablaInmobiliario.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<AgenteInmobiliario> result = BaseD.queryByExample(AgenteInmobiliario.class);
        while (result.hasNext()) {
            AgenteInmobiliario agente = result.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaNacimiento = dateFormat.format(agente.getFecha_NaciAgente());
            Object[] row = {
                agente.getCedula(),
                agente.getNombreAgente(),
                agente.getApellidoAgente(),
                agente.getEdadAgente(),
                agente.getGeneroAgente(),
                agente.getCelularAgente(),
                agente.getCorreoAgente(),
                agente.getNacionalidadAgente(),
                fechaNacimiento,
                agente.getId_casa()

            };

            model.addRow(row);
        }
    }

    private void cargarDatosDesdeTabla() {
        int filaSeleccionada = jtbTablaInmobiliario.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) jtbTablaInmobiliario.getModel();
            String cedula = model.getValueAt(filaSeleccionada, 0).toString();

            Query query = BaseD.query();
            query.constrain(AgenteInmobiliario.class);
            query.descend("Cedula").constrain(cedula);
            ObjectSet<AgenteInmobiliario> result = query.execute();

            if (!result.isEmpty()) {
                AgenteInmobiliario agente = result.get(0);
                txtCedula.setText(agente.getCedula());
                txtCedula.setEnabled(false);
                btnCrear.setEnabled(false);
                mostrarAgente(agente);
            }
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        rbtnHombre.setSelected(true);
        spnEdad.setValue(1);
        txtCelular.setText("");
        txtCorreo.setText("");
        //cbxNacionalidad.setSelectedIndex(0);
        dchFechaNacimiento.setDate(null);
        //cbxCasa.setSelectedIndex(0);
    }

    private void mostrarAgente(AgenteInmobiliario agente) {
        txtNombre.setText(agente.getNombreAgente());
        txtApellido.setText(agente.getApellidoAgente());
        if (agente.getGeneroAgente().equals("Hombre")) {
            rbtnHombre.setSelected(true);
        } else if (agente.getGeneroAgente().equals("Mujer")) {
            rbtnMujer.setSelected(true);
        }
        spnEdad.setValue(agente.getEdadAgente());
        txtCelular.setText(agente.getCelularAgente());
        txtCorreo.setText(agente.getCorreoAgente());
        cbxNacionalidad.setSelectedItem(agente.getNacionalidadAgente());
        dchFechaNacimiento.setDate(agente.getFecha_NaciAgente());
        cbxCasa.setSelectedItem(agente.getId_casa());
    }

    private void habilitarParametros() {
        txtCedula.setEnabled(true);
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        spnEdad.setEnabled(true);
        rbtnHombre.setEnabled(true);
        rbtnMujer.setEnabled(true);
        txtCelular.setEnabled(true);
        txtCorreo.setEnabled(true);
        cbxNacionalidad.setEnabled(true);
        cbxCasa.setEnabled(true);
    }

    private void deshabilitarParametros() {
        txtCedula.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        spnEdad.setEnabled(false);
        rbtnHombre.setEnabled(false);
        rbtnMujer.setEnabled(false);
        txtCelular.setEnabled(false);
        txtCorreo.setEnabled(false);
        cbxNacionalidad.setEnabled(false);
        cbxCasa.setEnabled(false);
    }

    private void filtrarAgente(String criterio, String valorBusqueda) {
        DefaultTableModel model = (DefaultTableModel) jtbTablaInmobiliario.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<AgenteInmobiliario> result;

        if (criterio.equals("Cedula")) {
            result = BaseD.queryByExample(new AgenteInmobiliario(valorBusqueda, null, null, null, 0, null, null, null, null, null));
        } else if (criterio.equals("Nombre")) {
            result = BaseD.queryByExample(new AgenteInmobiliario(null, valorBusqueda, null, null, 0, null, null, null, null, null));
        } else if (criterio.equals("Genero")) {
            result = BaseD.queryByExample(new AgenteInmobiliario(null, null, null, valorBusqueda.equals("Hombre") ? "Hombre" : "Mujer", 0, null, null, null, null, null));
        } else {
            // Criterio inválido, no se realiza la búsqueda
            return;
        }

        while (result.hasNext()) {
            AgenteInmobiliario agente = result.next();
            String genero = agente.getGeneroAgente().equals("Hombre") ? "Hombre" : "Mujer";

            Object[] row = {
                agente.getCedula(),
                agente.getNombreAgente(),
                agente.getApellidoAgente(),
                agente.getEdadAgente(),
                genero,
                agente.getCelularAgente(),
                agente.getCorreoAgente(),
                agente.getNacionalidadAgente(),
                agente.getFecha_NaciAgente(),
                agente.getId_casa()
            };

            model.addRow(row);
        }
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

        return valorBusqueda;
    }

    public void habilitarCamposBusqueda(String criterioSeleccionado) {
        deshabilitarParametros();
        if (criterioSeleccionado.equals("Cedula")) {
            txtCedula.setEnabled(true);
        } else if (criterioSeleccionado.equals("Nombre")) {
            txtNombre.setEnabled(true);
        } else if (criterioSeleccionado.equals("Genero")) {
            rbtnHombre.setEnabled(true);
            rbtnMujer.setEnabled(true);
        }
    }

    public void cerrarBaseDatos() {
        BaseD.close();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexo = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
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
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbTablaInmobiliario = new javax.swing.JTable();
        ComboBoxFiltro = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnBuscarFiltro = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cbxCasa = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("REGISTRO AGENTE INMOBILIARIO");

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

        cbxNacionalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ecuatoriana", "Estadounidense", "Mexicana", "Colombiana", "Argentina", "Española", "Brasileña", "Canadiense", "Peruana", "Francesa", "Alemana", "Italiana", "Inglesa", "China", "Japonesa", "Coreana", "Australiana", "Chilena", "Venezolana", "Suiza" }));

        jLabel3.setText("Apellido:");

        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        jLabel12.setText("Fecha Nacimiento:");

        jLabel4.setText("Edad:");

        spnEdad.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabel8.setText("Sexo:");

        sexo.add(rbtnHombre);
        rbtnHombre.setText("Hombre");
        rbtnHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnHombreActionPerformed(evt);
            }
        });

        sexo.add(rbtnMujer);
        rbtnMujer.setText("Mujer");
        rbtnMujer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMujerActionPerformed(evt);
            }
        });

        jLabel9.setText("Celular:");

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sav.png"))); // NOI18N
        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
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

        jtbTablaInmobiliario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Cedula", "Nombre", "Apellido", "Edad", "Sexo", "Celular", "Correo", "Nacionalidad", "Fecha Nacimiento", "Nombre casa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbTablaInmobiliario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbTablaInmobiliarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtbTablaInmobiliario);

        ComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cedula", "Nombre", "Genero" }));
        ComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFiltroActionPerformed(evt);
            }
        });

        jLabel10.setText("FILTRO BUSQUEDA");

        btnBuscarFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        btnBuscarFiltro.setText("Buscar");
        btnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarFiltroActionPerformed(evt);
            }
        });

        jLabel11.setText("Nombre casa:");

        cbxCasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel8))))
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(rbtnHombre)
                                                .addGap(3, 3, 3)
                                                .addComponent(rbtnMujer, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btncargardatos)
                                                .addGap(26, 26, 26)
                                                .addComponent(jLabel9))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(jLabel6))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(jLabel5))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jLabel12)))))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(90, 90, 90)
                                        .addComponent(btnBuscarFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(75, 75, 75)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(ComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnModificar)
                                .addGap(41, 41, 41)
                                .addComponent(btnEliminar)
                                .addGap(73, 73, 73)
                                .addComponent(btnReporte)))
                        .addGap(0, 91, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(cbxNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(dchFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel2)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel3)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel4)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(spnEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rbtnHombre)
                                    .addComponent(rbtnMujer)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btncargardatos)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addComponent(jLabel5)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel12)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbxCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(14, 14, 14)
                        .addComponent(ComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnBuscarFiltro)))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCrear)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar)
                    .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void btncargardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncargardatosActionPerformed
        consultarAgenteInmobiliario();
    }//GEN-LAST:event_btncargardatosActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void rbtnHombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnHombreActionPerformed

    }//GEN-LAST:event_rbtnHombreActionPerformed

    private void rbtnMujerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMujerActionPerformed
    }//GEN-LAST:event_rbtnMujerActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearAgenteInmobiliario();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarAgente();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarAgente();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
        btnCrear.setEnabled(true);
    }//GEN-LAST:event_btnReporteActionPerformed

    private void ComboBoxFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFiltroActionPerformed
        // Obtener el criterio seleccionado del JComboBox
        String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

        // Habilitar o deshabilitar los campos de búsqueda según el criterio seleccionado
        habilitarCamposBusqueda(criterioSeleccionado);
    }//GEN-LAST:event_ComboBoxFiltroActionPerformed

    private void btnBuscarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarFiltroActionPerformed
        try {
            // Obtener el criterio seleccionado del JComboBox
            String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

            // Obtener el valor de búsqueda ingresado por el usuario
            String valorBusqueda = obtenerValorBusqueda(criterioSeleccionado);

            // Realizar la búsqueda y cargar los resultados en el JTable
            filtrarAgente(criterioSeleccionado, valorBusqueda);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontro un agente con esos parametros");
        }
    }//GEN-LAST:event_btnBuscarFiltroActionPerformed

    private void jtbTablaInmobiliarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTablaInmobiliarioMouseClicked
        cargarDatosDesdeTabla();
    }//GEN-LAST:event_jtbTablaInmobiliarioMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFiltro;
    private javax.swing.JButton btnBuscarFiltro;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btncargardatos;
    private javax.swing.JComboBox<String> cbxCasa;
    private javax.swing.JComboBox<String> cbxNacionalidad;
    private com.toedter.calendar.JDateChooser dchFechaNacimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbTablaInmobiliario;
    private javax.swing.JRadioButton rbtnHombre;
    private javax.swing.JRadioButton rbtnMujer;
    private javax.swing.ButtonGroup sexo;
    private javax.swing.JSpinner spnEdad;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
