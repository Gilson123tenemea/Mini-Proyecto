package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.ServicioAdicional;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class V_ServicioAdicional extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public V_ServicioAdicional(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();

    }

    // Métodos auxiliares
    private void crearServicioAdicional() {
        if (!validarCampos()) {
            return; // Si hay campos vacíos, se detiene la creación del servicio adicional
        }

        if (!validarCostoAdicional()) {
            return; // Si el costo adicional no es válido, se detiene la creación del servicio adicional
        }

        String idServicio = txtIdServicio.getText();
        String idCasa = txtIdCasa.getText();
        String nombre = txtNombreServicio.getText();
        String descripcion = jtpDescripcionServicio.getText();
        double costoAdicional = Double.parseDouble(txtCostoAdicional.getText());

        // Verificar si ya existe un servicio adicional con la misma ID
        ObjectSet<ServicioAdicional> resultado = BaseD.queryByExample(new ServicioAdicional(idServicio, null, null, null, 0));
        if (resultado.hasNext()) {
            JOptionPane.showMessageDialog(this, "Ya existe un servicio adicional con la misma ID.");
            return; // Se detiene la creación del servicio adicional
        }

        ServicioAdicional servicioAdicional = new ServicioAdicional(idServicio, idCasa, nombre, descripcion, costoAdicional);
        BaseD.store(servicioAdicional);

        JOptionPane.showMessageDialog(this, "Se ha creado el servicio adicional correctamente.");

        limpiarCampos();
        cargarTabla();
    }

    private boolean validarCostoAdicional() {
        String costoAdicionalText = txtCostoAdicional.getText();
        try {
            double costoAdicional = Double.parseDouble(costoAdicionalText);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo de costo adicional debe ser un valor numérico válido.");
            return false;
        }
    }

    private boolean validarCampos() {
        if (txtIdServicio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID del servicio.");
            return false;
        }

        if (txtIdCasa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el ID de la casa.");
            return false;
        }

        if (txtNombreServicio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del servicio.");
            return false;
        }

        if (jtpDescripcionServicio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese la descripción del servicio.");
            return false;
        }

        if (txtCostoAdicional.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el costo adicional.");
            return false;
        }

        return true;
    }

    private void eliminarServicioAdicional() {
        int filaSeleccionada = tblServicioAdicional.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio adicional de la tabla.");
            return;
        }

        String idServicio = tblServicioAdicional.getValueAt(filaSeleccionada, 0).toString();

        ServicioAdicional servicioAdicional = new ServicioAdicional();
        servicioAdicional.setID_servicio(idServicio);

        ObjectSet<ServicioAdicional> result = BaseD.queryByExample(servicioAdicional);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio adicional seleccionado en la base de datos.");
            return;
        }

        ServicioAdicional servicioEliminar = result.next();
        BaseD.delete(servicioEliminar);

        JOptionPane.showMessageDialog(this, "Servicio adicional eliminado correctamente.");

        cargarTabla();
    }

    private void modificarServicioAdicional() {
        int filaSeleccionada = tblServicioAdicional.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio adicional de la tabla.");
            return;
        }

        String idServicio = tblServicioAdicional.getValueAt(filaSeleccionada, 0).toString();

        ServicioAdicional servicioAdicional = new ServicioAdicional();
        servicioAdicional.setID_servicio(idServicio);

        ObjectSet<ServicioAdicional> result = BaseD.queryByExample(servicioAdicional);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio adicional seleccionado en la base de datos.");
            return;
        }

        ServicioAdicional servicioModificar = result.next();

        // Actualizar los valores del servicio adicional con los campos del formulario
        servicioModificar.setId_casa(txtIdCasa.getText());
        servicioModificar.setNombre(txtNombreServicio.getText());
        servicioModificar.setDescripcionSer(jtpDescripcionServicio.getText());
        servicioModificar.setCostoAdicional(Double.parseDouble(txtCostoAdicional.getText()));

        BaseD.store(servicioModificar);

        JOptionPane.showMessageDialog(this, "Servicio adicional modificado correctamente.");

        cargarTabla();
    }

    private void consultarServicioAdicional() {
        String idServicio = txtIdServicio.getText();

        ServicioAdicional servicioAdicional = new ServicioAdicional();
        servicioAdicional.setID_servicio(idServicio);

        ObjectSet<ServicioAdicional> result = BaseD.queryByExample(servicioAdicional);

        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio adicional en la base de datos.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblServicioAdicional.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        while (result.hasNext()) {
            ServicioAdicional servicio = result.next();

            Object[] row = {
                servicio.getID_servicio(),
                servicio.getId_casa(),
                servicio.getNombre(),
                servicio.getDescripcionSer(),
                servicio.getCostoAdicional()
            };

            model.addRow(row);
        }
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblServicioAdicional.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<ServicioAdicional> result = BaseD.queryByExample(ServicioAdicional.class);
        while (result.hasNext()) {
            ServicioAdicional servicioAdicional = result.next();

            Object[] row = {
                servicioAdicional.getID_servicio(),
                servicioAdicional.getId_casa(),
                servicioAdicional.getNombre(),
                servicioAdicional.getDescripcionSer(),
                servicioAdicional.getCostoAdicional()
            };

            model.addRow(row);
        }
    }

    private void limpiarCampos() {
        txtIdServicio.setText("");
        txtIdCasa.setText("");
        txtNombreServicio.setText("");
        jtpDescripcionServicio.setText("");
        txtCostoAdicional.setText("");
    }

    private void filtrarServicioAdicional(String criterio, String valorBusqueda) {
        DefaultTableModel model = (DefaultTableModel) tblServicioAdicional.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<ServicioAdicional> result;

        if (criterio.equals("ID Servicio")) {
            result = BaseD.queryByExample(new ServicioAdicional(valorBusqueda, null, null, null, 0));
        } else if (criterio.equals("ID Casa")) {
            result = BaseD.queryByExample(new ServicioAdicional(null, valorBusqueda, null, null, 0));
        } else if (criterio.equals("Nombre Servicio")) {
            result = BaseD.queryByExample(new ServicioAdicional(null, null, valorBusqueda, null, 0));
        } else {
            // Criterio inválido, no se realiza la búsqueda
            return;
        }

        while (result.hasNext()) {
            ServicioAdicional servicioAdicional = result.next();

            Object[] row = {
                servicioAdicional.getID_servicio(),
                servicioAdicional.getId_casa(),
                servicioAdicional.getNombre(),
                servicioAdicional.getDescripcionSer(),
                servicioAdicional.getCostoAdicional()
            };

            model.addRow(row);
        }
    }

    private String obtenerValorBusqueda(String criterioSeleccionado) {
        String valorBusqueda = "";

        // Obtener el valor de búsqueda según el criterio seleccionado
        if (criterioSeleccionado.equals("ID Servicio")) {
            valorBusqueda = txtIdServicio.getText();
        } else if (criterioSeleccionado.equals("ID Casa")) {
            valorBusqueda = txtIdCasa.getText();
        } else if (criterioSeleccionado.equals("Nombre Servicio")) {
            valorBusqueda = txtNombreServicio.getText();
        }

        return valorBusqueda;
    }

    public void habilitarCamposBusqueda(String criterioSeleccionado) {
        deshabilitarCampos();

        if (criterioSeleccionado.equals("ID Servicio")) {
            txtIdServicio.setEnabled(true);
        } else if (criterioSeleccionado.equals("ID Casa")) {
            txtIdCasa.setEnabled(true);
        } else if (criterioSeleccionado.equals("Nombre Servicio")) {
            txtNombreServicio.setEnabled(true);
        }
    }

    public void deshabilitarCampos() {
        txtIdServicio.setEnabled(false);
        txtIdCasa.setEnabled(false);
        txtNombreServicio.setEnabled(false);
    }

    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tblServicioAdicional.getSelectedRow();
        if (filaSeleccionada != -1) {
            String idServicio = tblServicioAdicional.getValueAt(filaSeleccionada, 0).toString();
            String idCasa = tblServicioAdicional.getValueAt(filaSeleccionada, 1).toString();
            String nombreServicio = tblServicioAdicional.getValueAt(filaSeleccionada, 2).toString();
            String descripcionServicio = tblServicioAdicional.getValueAt(filaSeleccionada, 3).toString();
            String costoAdicional = tblServicioAdicional.getValueAt(filaSeleccionada, 4).toString();

            txtIdServicio.setText(idServicio);
            txtIdCasa.setText(idCasa);
            txtNombreServicio.setText(nombreServicio);
            jtpDescripcionServicio.setText(descripcionServicio);
            txtCostoAdicional.setText(costoAdicional);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreServicio = new javax.swing.JTextField();
        txtIdServicio = new javax.swing.JTextField();
        txtIdCasa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpDescripcionServicio = new javax.swing.JTextPane();
        txtCostoAdicional = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblServicioAdicional = new javax.swing.JTable();
        ComboBoxFiltro = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("REGISTRO DE SERVICIO ADICIONAL");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 15, -1, -1));

        jLabel2.setText("ID SERVICIO:");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, 30));

        jLabel3.setText("ID CASA:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, 20));

        jLabel4.setText("NOMBRE DEL SERVICIO:");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel5.setText("DESCRIPCION DEL SERVICIO:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel6.setText("COSTO ADICIONAL:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        txtNombreServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreServicioActionPerformed(evt);
            }
        });
        add(txtNombreServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 190, 30));
        add(txtIdServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 150, -1));
        add(txtIdCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 150, -1));

        jScrollPane1.setViewportView(jtpDescripcionServicio);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 330, 90));
        add(txtCostoAdicional, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 120, -1));

        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 120, -1));

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 120, -1));

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 120, -1));

        btnConsultar.setText("CONSULTAR");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 120, -1));

        tblServicioAdicional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID SERVICIO", "ID CASA", "NOMBRE SERVICIO", "DESCRIPCION SERVICIO", "COSTO ADICIONAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblServicioAdicional.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblServicioAdicionalMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblServicioAdicional);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 760, 200));

        ComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID Servicio", "ID Casa", "Nombre Servicio" }));
        ComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxFiltroActionPerformed(evt);
            }
        });
        add(ComboBoxFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 120, 30));

        jLabel7.setText("Filtro de busqueda");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, -1, -1));

        jButton1.setText("REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 120, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreServicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreServicioActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearServicioAdicional();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarServicioAdicional();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarServicioAdicional();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        try {
            // Obtener el criterio seleccionado del JComboBox
            String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

            // Obtener el valor de búsqueda ingresado por el usuario
            String valorBusqueda = obtenerValorBusqueda(criterioSeleccionado);

            // Realizar la búsqueda y cargar los resultados en el JTable
            filtrarServicioAdicional(criterioSeleccionado, valorBusqueda);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontró un servicio adicional con esos parámetros.");
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void ComboBoxFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFiltroActionPerformed
        // Obtener el criterio seleccionado del JComboBox
        String criterioSeleccionado = ComboBoxFiltro.getSelectedItem().toString();

        // Habilitar o deshabilitar los campos de búsqueda según el criterio seleccionado
        habilitarCamposBusqueda(criterioSeleccionado);
    }//GEN-LAST:event_ComboBoxFiltroActionPerformed

    private void tblServicioAdicionalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblServicioAdicionalMouseClicked
        cargarDatosSeleccionados();
    }//GEN-LAST:event_tblServicioAdicionalMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cargarTabla();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxFiltro;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jtpDescripcionServicio;
    private javax.swing.JTable tblServicioAdicional;
    private javax.swing.JTextField txtCostoAdicional;
    private javax.swing.JTextField txtIdCasa;
    private javax.swing.JTextField txtIdServicio;
    private javax.swing.JTextField txtNombreServicio;
    // End of variables declaration//GEN-END:variables
}