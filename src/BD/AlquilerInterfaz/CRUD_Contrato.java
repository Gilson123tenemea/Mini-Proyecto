package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Contrato;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Contrato extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Contrato(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();
        cargarCasas();
        cargarClientes();
    }

    private void crearContrato() {
        try {
            if (!validarCampos()) {
                return;
            }

            String id = txtID.getText();

            // Consultar si ya existe un contrato con el mismo id
            ObjectSet<Contrato> result = BaseD.queryByExample(new Contrato(id, null, null, null, null, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe un contrato con el id ingresado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si no existe un contrato con el mismo id , proceder con la creación
            String clientes = CboxClientes.getSelectedItem().toString();
            String casas = cbxCasas.getSelectedItem().toString();
            Date fechaLlegada = jDateLlegada.getDate();
            Date fechaSalida = jDateSalida.getDate();
            String condiciones = txareaTeryCon.getText();

            Contrato mi_cont = new Contrato(id, clientes, casas, fechaLlegada, fechaSalida, condiciones);
            BaseD.store(mi_cont); // Almacenar el objeto en la base de datos

            JOptionPane.showMessageDialog(null, "Contrato creado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } catch (Exception e) {
            System.out.println("Error: No se puede crear un contrato, puede que no exista un cliente o una casa vacacional");
        }

    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) jTableContrato.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Contrato> result = BaseD.queryByExample(Contrato.class);
        while (result.hasNext()) {
            Contrato contrato = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                contrato.getID_contrato(),
                contrato.getIDCliente(),
                contrato.getId_casa(),
                sdf.format(contrato.getFecha_inicio()),
                sdf.format(contrato.getFecha_fin()),
                contrato.getTerminosCondiciones()

            };
            model.addRow(row);
        }
    }

    private void consultarContarto() {
        String id = txtID.getText();
        Query query = BaseD.query();
        query.constrain(Contrato.class);
        query.descend("ID_contrato").constrain(id); // id que esta en la clase
        ObjectSet<Contrato> result = query.execute();
        if (!result.isEmpty()) {
            Contrato contrato = result.next();
            mostrarContrato(contrato);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un contrato con el id ingresado.");
            limpiarCampos();
        }
    }

    private void mostrarContrato(Contrato contrato) {
        txtID.setText(contrato.getID_contrato());
        CboxClientes.setSelectedItem(contrato.getIDCliente());
        cbxCasas.setSelectedItem(contrato.getId_casa());
        jDateLlegada.setDate(contrato.getFecha_inicio());
        jDateSalida.setDate(contrato.getFecha_fin());
        txareaTeryCon.setText(contrato.getTerminosCondiciones());
    }

    private void modificarContrato() {
        if (!validarCampos()) {
            return;
        }

        String id = txtID.getText();
        Query query = BaseD.query();
        query.constrain(Contrato.class);
        query.descend("ID").constrain(id);
        ObjectSet<Contrato> result = query.execute();
        if (!result.isEmpty()) {
            Contrato contrato = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            contrato.setId_casa(cbxCasas.getSelectedItem().toString());
            contrato.setIDCliente(CboxClientes.getSelectedItem().toString());
            contrato.setFecha_inicio(jDateLlegada.getDate());
            contrato.setFecha_fin(jDateSalida.getDate());
            contrato.setTerminosCondiciones(txareaTeryCon.getText());

            BaseD.store(contrato); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Contrato modificado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un contrato con el id ingresado.");
        }
    }

    private void eliminarContrato() {
        try {
            String id = txtID.getText();
            Query query = BaseD.query();
            query.constrain(Contrato.class);
            query.descend("ID_contrato").constrain(id);
            ObjectSet<Contrato> result = query.execute();
            if (!result.isEmpty()) {

                Contrato contrato = result.next();

                if (contrato.getIDCliente() == null || contrato.getIDCliente().isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este contrato con la id: " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        BaseD.delete(contrato); // Eliminar el objeto de la base de datos
                        JOptionPane.showMessageDialog(null, "Contrato eliminado exitosamente.");
                        limpiarCampos();
                        cargarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes eliminar esta contrato, ya que esta relacionada con un cliente\n primero elimina el cliente");
                    BaseD.store(contrato); // Actualizar el contrato en la base de datos
                    limpiarCampos();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un contrato con el id ingresado.");
            }
        } catch (Exception e) {
            System.out.println("Error: No se puede eliminar el contrato ya que puede que no exista clientes");
            e.printStackTrace();
        }

    }

    public boolean validarCampos() {// Método para validar los campos de la interfaz
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del contrato");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtID.getText())) {
            JOptionPane.showMessageDialog(this, "ID incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (cbxCasas.getSelectedItem() == null || cbxCasas.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        }

        if (CboxClientes.getSelectedItem() == null || CboxClientes.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            ban_confirmar = false;
        }

        if (jDateLlegada.getDate() == null || jDateLlegada.getDate().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha");
            ban_confirmar = false;
        }

        if (jDateSalida.getDate() == null || jDateSalida.getDate().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha");
            ban_confirmar = false;
        }

        return ban_confirmar;
    }

    public void habilitarParametros() {
        txtID.setEnabled(true);
        cbxCasas.setEnabled(true);
        CboxClientes.setEnabled(true);
        jDateLlegada.setEnabled(true);
        jDateSalida.setEnabled(true);
        txareaTeryCon.setEnabled(true);
    }

    public void deshabilitarParametros() {
        txtID.setEnabled(false);
        cbxCasas.setEnabled(false);
        CboxClientes.setEnabled(false);
        jDateLlegada.setEnabled(false);
        jDateSalida.setEnabled(false);
        txareaTeryCon.setEnabled(false);
    }

    private void limpiarCampos() {

        txtID.setText("");
        //cbxCasas.setSelectedIndex(0);
        //CboxClientes.setSelectedIndex(0);
        jDateLlegada.setDate(null);
        jDateSalida.setDate(null);
        txareaTeryCon.setText("");
    }

    public void cargarCasas() {
        cbxCasas.removeAllItems();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);

        ObjectSet<CasaVacacional> casas = query.execute();

        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay casas vacacionales disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //System.out.println("Casas registradas:");
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                cbxCasas.addItem(casa.getId_casa());
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
                CboxClientes.addItem(cli.getCedula());
            }
        }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableContrato = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        CboxClientes = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbxCasas = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jDateLlegada = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jDateSalida = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txareaTeryCon = new javax.swing.JTextArea();
        btnIngresar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnVerCliente = new javax.swing.JButton();
        btnVerCasa = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setBackground(new java.awt.Color(255, 255, 255));

        jTableContrato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID CONTRATO", "CEDULA CLIENTE", "NOMBRE CASA", "FECHA DE LLEGADA", "FECHA DE SALIDA", "TERMINOS Y CONDICIONES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableContrato);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CONTRATO");

        jLabel2.setText("ID CONTRATO:");

        jLabel4.setText("CEDULA CLIENTE :");

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("NOMBRE CASA:");

        cbxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("FECHA DE LLEGADA:");

        jLabel6.setText("FECHA DE SALIDA:");

        jLabel7.setText("TERMINOS Y CONSICIONES:");

        txareaTeryCon.setColumns(20);
        txareaTeryCon.setRows(5);
        jScrollPane1.setViewportView(txareaTeryCon);

        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
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

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contrato.png"))); // NOI18N

        btnVerCliente.setText("VER");
        btnVerCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerClienteActionPerformed(evt);
            }
        });

        btnVerCasa.setText("VER");
        btnVerCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCasaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnVerCasa)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIngresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(btnVerCliente)
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(328, 328, 328)
                                .addComponent(jLabel1)))
                        .addGap(0, 395, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateLlegada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(btnVerCliente)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIngresar)
                            .addComponent(jLabel3)
                            .addComponent(btnVerCasa)))
                    .addComponent(btnConsultar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnModificar))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        crearContrato();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarContrato();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarContrato();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
    }//GEN-LAST:event_btnReporteActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        consultarContarto();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnVerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerClienteActionPerformed
        // TODO add your handling code here:
        mostrarDatosClienteSeleccionado();
    }//GEN-LAST:event_btnVerClienteActionPerformed

    private void btnVerCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCasaActionPerformed
        // TODO add your handling code here:
        mostrarDatosCasaSeleccionada();
    }//GEN-LAST:event_btnVerCasaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVerCasa;
    private javax.swing.JButton btnVerCliente;
    private javax.swing.JComboBox<String> cbxCasas;
    private com.toedter.calendar.JDateChooser jDateLlegada;
    private com.toedter.calendar.JDateChooser jDateSalida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableContrato;
    private javax.swing.JTextArea txareaTeryCon;
    private javax.swing.JTextField txtID;
    // End of variables declaration//GEN-END:variables
}
