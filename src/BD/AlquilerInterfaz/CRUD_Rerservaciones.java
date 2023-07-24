package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Reservacion;
import BD.AlquilerCasas.Clases.Validaciones;
import Reporte.ReportFrame;
import Reporte.ReportGenerator;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class CRUD_Rerservaciones extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CRUD_Rerservaciones(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();
        cargarCasas();
        cargarClientes();
    }

    private void crearReservacion() {
        try {
            if (!validarCampos()) {
                return;
            }

            String id = txt_id.getText();

            // Consultar si ya existe una reservacion con el mismo id
            ObjectSet<Reservacion> result = BaseD.queryByExample(new Reservacion(id, null, null, null, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe una reservacion con el id ingresado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Si no existe una reservacion con el mismo id , proceder con la creación
            String casas = cbxCasas.getSelectedItem().toString();
            String clientes = CboxClientes.getSelectedItem().toString();
            Date fechaLlegada = jDateLlegada.getDate();
            Date fechaSalida = jDateSalida.getDate();

            Reservacion mi_reserv = new Reservacion(id, casas, clientes, fechaLlegada, fechaSalida);
            BaseD.store(mi_reserv); // Almacenar el objeto en la base de datos

            JOptionPane.showMessageDialog(null, "Reservacion creada exitosamente.");
            limpiarCampos();
            cargarTabla();
        } catch (Exception e) {
            System.out.println("Error: No se puede crear una reservacion, puede que no existas casas vacacionales\n o puede que tambien no exista clientes");
        }

    }

    private void consultarReservaciones() {
        String id = txt_id.getText();
        Query query = BaseD.query();
        query.constrain(Reservacion.class);
        query.descend("id_reservacion").constrain(id); // id que esta en la clase
        ObjectSet<Reservacion> result = query.execute();
        if (!result.isEmpty()) {
            Reservacion reservacion = result.next();
            mostrarReservacion(reservacion);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una reservacion con el id ingresado.");
            limpiarCampos();
        }
    }

    private void mostrarReservacion(Reservacion reservacion) {
        txt_id.setText(reservacion.getId_reservacion());
        cbxCasas.setSelectedItem(reservacion.getId_casa());
        CboxClientes.setSelectedItem(reservacion.getIDCliente());
        jDateLlegada.setDate(reservacion.getFecha_inicio());
        jDateSalida.setDate(reservacion.getFecha_fin());
    }

    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txt_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el id de la Reservacion");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txt_id.getText())) {
            JOptionPane.showMessageDialog(this, "ID incorrecta. Ingrese de nuevo");
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

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) jTableReservaciones.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Reservacion> result = BaseD.queryByExample(Reservacion.class);
        while (result.hasNext()) {
            Reservacion reservacion = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                reservacion.getId_reservacion(),
                reservacion.getId_casa(),
                reservacion.getIDCliente(),
                sdf.format(reservacion.getFecha_inicio()),
                sdf.format(reservacion.getFecha_fin()),};
            model.addRow(row);
        }
    }

    private void modificarReservacion() {
        if (!validarCampos()) {
            return;
        }

        String id = txt_id.getText();
        Query query = BaseD.query();
        query.constrain(Reservacion.class);
        query.descend("ID").constrain(id);
        ObjectSet<Reservacion> result = query.execute();
        if (!result.isEmpty()) {
            Reservacion reservacion = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            reservacion.setId_casa(cbxCasas.getSelectedItem().toString());
            reservacion.setIDCliente(CboxClientes.getSelectedItem().toString());
            reservacion.setFecha_inicio(jDateLlegada.getDate());
            reservacion.setFecha_fin(jDateSalida.getDate());

            BaseD.store(reservacion); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Reservacion modificada exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una reservacion con el id ingresado.");
        }
    }

    private void limpiarCampos() {

        txt_id.setText("");
        jDateLlegada.setDate(null);
        jDateSalida.setDate(null);
    }

    private void eliminarReservacion() {
        try {
            String id = txt_id.getText();
            Query query = BaseD.query();
            query.constrain(Reservacion.class);
            query.descend("id_reservacion").constrain(id);//El id_reservacion deber ser igual al de la variable de la clase Reservacion
            ObjectSet<Reservacion> result = query.execute();
            if (!result.isEmpty()) {

                Reservacion reservacion = result.next();

                if (reservacion.getIDCliente() == null || reservacion.getIDCliente().isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta reservacion conn la id: " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        BaseD.delete(reservacion); // Eliminar el objeto de la base de datos
                        JOptionPane.showMessageDialog(null, "Reservacion eliminado exitosamente.");
                        limpiarCampos();
                        cargarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes eliminar esta reservacion, ya que esta relacionada con un cliente\n primero elimina el cliente");
                    BaseD.store(reservacion); // Actualizar la casa en la base de datos
                    limpiarCampos();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una reservacion con la id ingresada.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar una reservacion, porque no hay clientes");
            e.printStackTrace();
        }

    }

    public void habilitarParametros() {
        txt_id.setEnabled(true);
        cbxCasas.setEnabled(true);
        CboxClientes.setEnabled(true);
        jDateLlegada.setEnabled(true);
        jDateSalida.setEnabled(true);
    }

    public void deshabilitarParametros() {
        txt_id.setEnabled(false);
        cbxCasas.setEnabled(false);
        CboxClientes.setEnabled(false);
        jDateLlegada.setEnabled(false);
        jDateSalida.setEnabled(false);
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
            JOptionPane.showMessageDialog(this, "No hay clientes ingresados", "Error", JOptionPane.ERROR_MESSAGE);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        cbxCasas = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CboxClientes = new javax.swing.JComboBox<>();
        btnIngresar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDateLlegada = new com.toedter.calendar.JDateChooser();
        jDateSalida = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableReservaciones = new javax.swing.JTable();
        btnReporte = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btnVerCasa = new javax.swing.JButton();
        btnVerCliente = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("panelReporte"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("INGRESO RESERVACIONES");

        jLabel2.setText("ID RESERVACION:");

        cbxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("ID CASA:");

        jLabel4.setText("CEDULA CLIENTE :");

        CboxClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CboxClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboxClientesActionPerformed(evt);
            }
        });

        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnIngresar.setText("INGRESAR");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        jLabel5.setText("FECHA DE LLEGADA:");

        jLabel6.setText("FECHA DE SALIDA:");

        jTableReservaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID RESEVACION", "NOMBRE CASA ", "CEDULA CLIENTE", "FECHA DE LLEGADA", "FECHA DE SALIDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableReservaciones);

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reservacion.png"))); // NOI18N

        btnVerCasa.setText("VER");
        btnVerCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCasaActionPerformed(evt);
            }
        });

        btnVerCliente.setText("VER");
        btnVerCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(cbxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnVerCasa))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(8, 8, 8))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(btnEliminar)
                                            .addGap(16, 16, 16)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnModificar)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(44, 44, 44)
                                            .addComponent(jLabel5)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVerCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(jLabel1)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 141, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnReporte)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(btnVerCasa)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(CboxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(btnVerCliente))
                    .addComponent(jDateSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        crearReservacion();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarReservacion();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void CboxClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboxClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CboxClientesActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarReservacion();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed

DefaultTableModel model = (DefaultTableModel) jTableReservaciones.getModel();
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        Object[][] data = new Object[rowCount][columnCount];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Definir los índices de las columnas de fecha
        int fechaInicioColumnIndex = 3; // Por ejemplo, si la columna de fecha de inicio es la 3
        int fechaFinColumnIndex = 4; // Por ejemplo, si la columna de fecha de fin es la 4

        for (int i = 0; i < rowCount; i++) {
            boolean isValidRow = true; // Bandera para verificar si la fila es válida
            for (int j = 0; j < columnCount; j++) {
                if (j == fechaInicioColumnIndex || j == fechaFinColumnIndex) {
                    try {
                        data[i][j] = sdf.parse((String) model.getValueAt(i, j));
                    } catch (ParseException e) {
                        // Si hay un error al parsear la fecha, marca la fila como no válida
                        isValidRow = false;
                        break; // No es necesario seguir verificando el resto de las celdas
                    }
                } else {
                    data[i][j] = model.getValueAt(i, j);
                }
            }
            if (!isValidRow) {
                // Si la fila no es válida, no se agrega a la matriz data
                data[i] = null;
            }
        }

        // Generar el informe utilizando la clase ReportGenerator
        JasperPrint reportViewer = ReportGenerator.generateReport(data);

// Crear una instancia de JRViewer y agregar reportViewer como contenido
        JRViewer jrViewer = new JRViewer(reportViewer);

// Crear una instancia de ReportFrame y mostrar el informe en un nuevo JFrame
        ReportFrame reportFrame = new ReportFrame(jrViewer);
        reportFrame.setVisible(true);

    }//GEN-LAST:event_btnReporteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        consultarReservaciones();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnVerCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCasaActionPerformed
        // TODO add your handling code here:
        mostrarDatosCasaSeleccionada();
    }//GEN-LAST:event_btnVerCasaActionPerformed

    private void btnVerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerClienteActionPerformed
        // TODO add your handling code here:
        mostrarDatosClienteSeleccionado();
    }//GEN-LAST:event_btnVerClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxClientes;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVerCasa;
    private javax.swing.JButton btnVerCliente;
    private javax.swing.JComboBox<String> cbxCasas;
    private javax.swing.JButton jButton2;
    private com.toedter.calendar.JDateChooser jDateLlegada;
    private com.toedter.calendar.JDateChooser jDateSalida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableReservaciones;
    private javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}

