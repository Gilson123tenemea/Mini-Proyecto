package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Actividades;
import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_Actividades extends javax.swing.JPanel {

    private ObjectContainer BaseD;
//    String ID_actividades = "";
//    //String casa = "";
//    String tipoActividad = "";
//    double costoAdicional = 0;
//    Date fechaHora = null;

    public CRUD_Actividades(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarCasas();
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
            //System.out.println("Casas registradas:");
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                cbxCasas.addItem(casa.getId_casa());
            }
        }
    }

    private void crearActividades() {
        try {
            if (!validarCampos()) {
                return;
            }
            String ID_actividades = txtidActividades.getText();

            ObjectSet<Actividades> result = BaseD.queryByExample(new Actividades(ID_actividades, null, null, 0, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe una actividad con el id ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String casa1 = cbxCasas.getSelectedItem().toString();
            String tipoActividad = CboxTipoActividades.getSelectedItem().toString();
            double costoAdicional = (double) spnCosotosAdicionales.getValue();
            Date fechaHora = DateFechaActiviti.getDate();

            Actividades misacti = new Actividades();

            misacti.setID_actividades(ID_actividades);
            misacti.setCasa(casa1);
            misacti.setTipoActividad(tipoActividad);
            misacti.setCostoAdicional(costoAdicional);
            misacti.setFechaHora(fechaHora);

            BaseD.store(misacti); // Almacenar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Actividad creado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa vacacional antes de guardar", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void consultarActtividad() {

        String ID_actividades = txtidActividades.getText();
        Query query = BaseD.query();
        query.constrain(Actividades.class);
        query.descend("ID_actividades").constrain(ID_actividades); // Cedula que esta en la clase
        ObjectSet<Actividades> result = query.execute();
        if (!result.isEmpty()) {
            Actividades activi = result.next();
            mostrarActivifafes(activi);

        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
            limpiarCampos();
        }
    }

    private void modificarActividad() {
        if (!validarCampos()) {
            return;
        }
        String id = txtidActividades.getText();
        Query query = BaseD.query();
        query.constrain(Actividades.class);
        query.descend("ID_actividades").constrain(id);
        ObjectSet<Actividades> result = query.execute();
        if (!result.isEmpty()) {
            Actividades acti = result.next();
            acti.setID_actividades(txtidActividades.getText());
            acti.setCasa(cbxCasas.getSelectedItem().toString());
            acti.setCostoAdicional((double) spnCosotosAdicionales.getValue());
            acti.setFechaHora(DateFechaActiviti.getDate());
            acti.setTipoActividad(CboxTipoActividades.getSelectedItem().toString());

            BaseD.store(acti);
            JOptionPane.showMessageDialog(null, "Actividad modificado exitosamente.");
            limpiarCampos();
            cargarTabla();
        }

    }

    private void mostrarActivifafes(Actividades activi) {

        txtidActividades.setText(activi.getID_actividades());
        cbxCasas.setSelectedItem(activi.getCasa());
        DateFechaActiviti.setDate(activi.getFechaHora());
        CboxTipoActividades.setSelectedItem(activi.getTipoActividad());
        spnCosotosAdicionales.setValue(activi.getCostoAdicional());

    }

    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;
        if (txtidActividades.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese ei id de las actividades");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtidActividades.getText())) {
            JOptionPane.showMessageDialog(this, "Cédula incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (CboxTipoActividades.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese el tipo actividad");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxTipoActividades.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Actividad inválida");
                ban_confirmar = false;
            }
        }

        return ban_confirmar;

    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaactivi.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
        ObjectSet<Actividades> result = BaseD.queryByExample(Actividades.class);
        while (result.hasNext()) {
            Actividades acti = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                acti.getID_actividades(),
                acti.getCasa(),
                acti.getTipoActividad(),
                acti.getCostoAdicional(),
                sdf.format(acti.getFechaHora())
            };
            model.addRow(row);
        }

    }

    private void eliminaractividades() {
        String ID_actividades = txtidActividades.getText();
        Query query = BaseD.query();
        query.constrain(Actividades.class);
        query.descend("ID_actividades").constrain(ID_actividades); // Cedula que esta en la clase
        ObjectSet<Actividades> result = query.execute();
        if (!result.isEmpty()) {
            Actividades act = result.next();
            BaseD.delete(act); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "Actividad eliminado exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una actividad con el id ingresada.");
        }

    }

    public void habilitarParametros() {
        txtidActividades.setEnabled(true);
        cbxCasas.setEnabled(true);
        CboxTipoActividades.setEnabled(true);
        spnCosotosAdicionales.setEnabled(true);
        DateFechaActiviti.setEnabled(true);
    }

    public void deshabilitarParametros() {
        txtidActividades.setEnabled(false);
        cbxCasas.setEnabled(false);
        CboxTipoActividades.setEnabled(false);
        spnCosotosAdicionales.setEnabled(false);
        DateFechaActiviti.setEnabled(false);
    }

    private void limpiarCampos() {
        txtidActividades.setText("");
        //cbxCasas.setSelectedIndex(0);
        CboxTipoActividades.setSelectedIndex(0);
        spnCosotosAdicionales.setValue(1);
        DateFechaActiviti.setDate(null);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidActividades = new javax.swing.JTextField();
        CboxTipoActividades = new javax.swing.JComboBox<>();
        spnCosotosAdicionales = new javax.swing.JSpinner();
        DateFechaActiviti = new com.toedter.calendar.JDateChooser();
        ComboHora = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnreporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaactivi = new javax.swing.JTable();
        cbxCasas = new javax.swing.JComboBox<>();
        cargardatos = new javax.swing.JButton();
        btnVerCasa = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("REGUISTRO DE LAS ACTIVIDADES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel2.setText("ID Actividades:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel3.setText("Casas Vacacionales: ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, -1));

        jLabel4.setText("Tipo de Actividades: ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));

        jLabel5.setText("Costos Adicionales: ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, -1, -1));

        jLabel6.setText("Fecha:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 40, -1));
        jPanel1.add(txtidActividades, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 140, -1));

        CboxTipoActividades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Descanso y Relajacion", "Asados y Comidas", "Senderismos y Actividades", "Observacion de la Naturaleza", "Pesca", "Juegos de mesa", "Excursiones Locales", "Actividades acuaticas", "Fotograficas", "Yoja y meditacion", "Jardineria y cuidado ", "Lectura y Escritura", "Tareas creativas", "Natacion", " " }));
        jPanel1.add(CboxTipoActividades, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 200, -1));

        spnCosotosAdicionales.setModel(new javax.swing.SpinnerNumberModel(10.0d, 5.0d, 100.0d, 5.0d));
        jPanel1.add(spnCosotosAdicionales, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 80, -1));
        jPanel1.add(DateFechaActiviti, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 140, -1));

        ComboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM 12:00", "AM 01:00", "AM 02:00", "AM 03:00", "AM 04:00", "AM 05:00", "AM 06:00", "AM 07:00", "AM 08:00", "AM 09:00", "AM 10:00", "AM 11:00", "PM 12:00", "PM 01:00", "PM 02:00", "PM 03:00", "PM 04:00", "PM 05:00", "PM 06:00", "PM 07:00", "PM 08:00", "PM 09:00", "PM 10:00", "PM 11:00" }));
        ComboHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboHoraActionPerformed(evt);
            }
        });
        jPanel1.add(ComboHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, -1, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 120, 40));

        btnmodificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnmodificar.setText("MODIFICAR");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnmodificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, -1, 40));

        btneliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btneliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 120, 40));

        btnreporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnreporte.setText("REPORTE");
        btnreporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreporteActionPerformed(evt);
            }
        });
        jPanel1.add(btnreporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 220, -1, 40));

        tablaactivi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Actividades", "Casas ", "Tipo Actividades", "Costos Adicionales", "Fecha"
            }
        ));
        jScrollPane1.setViewportView(tablaactivi);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 780, 290));

        cbxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cbxCasas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 140, -1));

        cargardatos.setBackground(new java.awt.Color(255, 255, 255));
        cargardatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        cargardatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargardatosActionPerformed(evt);
            }
        });
        jPanel1.add(cargardatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, -1, -1));

        btnVerCasa.setText("VER");
        btnVerCasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCasaActionPerformed(evt);
            }
        });
        jPanel1.add(btnVerCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnreporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
        btnGuardar.setEnabled(true);
    }//GEN-LAST:event_btnreporteActionPerformed

    private void ComboHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboHoraActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        crearActividades();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        modificarActividad();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        eliminaractividades();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void cargardatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargardatosActionPerformed
        txtidActividades.setEnabled(false);
        btnGuardar.setEnabled(false);
        consultarActtividad();
    }//GEN-LAST:event_cargardatosActionPerformed

    private void btnVerCasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCasaActionPerformed
        // TODO add your handling code here:
        mostrarDatosCasaSeleccionada();
    }//GEN-LAST:event_btnVerCasaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxTipoActividades;
    private javax.swing.JComboBox<String> ComboHora;
    private com.toedter.calendar.JDateChooser DateFechaActiviti;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVerCasa;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnreporte;
    private javax.swing.JButton cargardatos;
    private javax.swing.JComboBox<String> cbxCasas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnCosotosAdicionales;
    private javax.swing.JTable tablaactivi;
    private javax.swing.JTextField txtidActividades;
    // End of variables declaration//GEN-END:variables
}
