package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CalendarioDisponibilidad;
import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.db4o.query.Query;

public class CalendarioDispo extends javax.swing.JPanel {

    private ObjectContainer BaseD;

    public CalendarioDispo(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarCasas();
        cargarTabla();
        cargarComboFechas(); // Llamada al método para cargar las fechas en el combobox
    }

    public void cargarCasas() {
        CBoxCasas.removeAllItems();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);

        ObjectSet<CasaVacacional> casas = query.execute();

        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay casas vacacionales");
        } else {
            //System.out.println("Casas registradas:");
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                CBoxCasas.addItem(casa.getNombre());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdcale = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CBoxCasas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        CboxFechasDispo = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaDisponi = new javax.swing.JTable();
        btncrear = new javax.swing.JButton();
        btnConsul = new javax.swing.JButton();
        btnModi = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        btnTodos = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(810, 589));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("CALENDARIO");

        jLabel2.setText("ID CALENDARIO");

        txtIdcale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdcaleActionPerformed(evt);
            }
        });

        jLabel3.setText("ID CASA:");

        CBoxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Casa Verano", "Casa Invierno", "Casa Playa", "Casa Campo" }));

        jLabel4.setText("FECHAS DISPONIBLES:");

        TablaDisponi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID CALENDARIO", "ID CASA VACACIONAL", "FECHA DISPONIBLE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaDisponi);

        btncrear.setText("CREAR");
        btncrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearActionPerformed(evt);
            }
        });

        btnConsul.setText("CONSULTAR");
        btnConsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulActionPerformed(evt);
            }
        });

        btnModi.setText("MODIFICAR");
        btnModi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModiActionPerformed(evt);
            }
        });

        btnDelete.setText("ELIMINAR");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jCalendar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jCalendar1MousePressed(evt);
            }
        });
        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });

        btnTodos.setText("VER TODOS");
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/24-horas.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(11, 11, 11)
                                .addComponent(txtIdcale, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(CBoxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CboxFechasDispo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)))))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btncrear)
                .addGap(23, 23, 23)
                .addComponent(btnConsul)
                .addGap(17, 17, 17)
                .addComponent(btnModi)
                .addGap(29, 29, 29)
                .addComponent(btnDelete)
                .addGap(19, 19, 19)
                .addComponent(btnTodos))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(8, 8, 8)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdcale, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBoxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))))
                        .addGap(22, 22, 22)
                        .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4)
                        .addGap(6, 6, 6)
                        .addComponent(CboxFechasDispo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncrear)
                    .addComponent(btnConsul)
                    .addComponent(btnModi)
                    .addComponent(btnDelete)
                    .addComponent(btnTodos))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdcaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdcaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdcaleActionPerformed

    private void btncrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearActionPerformed
        // TODO add your handling code here:
        crearDisponibilidad();
        limpiarCampos();
    }//GEN-LAST:event_btncrearActionPerformed

    private void jCalendar1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCalendar1MousePressed

    }//GEN-LAST:event_jCalendar1MousePressed

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void btnConsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulActionPerformed
        // TODO add your handling code here:
        String id = txtIdcale.getText();
        buscarPorId(id);
    }//GEN-LAST:event_btnConsulActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void btnModiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModiActionPerformed
        // TODO add your handling code here:
        modificarDisponibilidad();
    }//GEN-LAST:event_btnModiActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        eliminarDisponibilidad();
    }//GEN-LAST:event_btnDeleteActionPerformed
    /// crear disponibilidad
    public void crearDisponibilidad() {
        if (!validarCampos()) {
            return;
        }
        String Id_calen = txtIdcale.getText();
        // Consultar si ya existe un cliente con la misma cédula
        ObjectSet<CalendarioDisponibilidad> result = BaseD.queryByExample(new CalendarioDisponibilidad(Id_calen, null, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe una fecha disponible con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si no existe un con la misma codigo, proceder con la creación
        String casas = CBoxCasas.getSelectedItem().toString();
        String fechaDispo = CboxFechasDispo.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;

        try {
            fecha = dateFormat.parse(fechaDispo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CalendarioDisponibilidad midispo = new CalendarioDisponibilidad(Id_calen, casas, fecha);
        BaseD.store(midispo); // Almacenar el objeto en la base de datos
        JOptionPane.showMessageDialog(null, "Casas disponible creado exitosamente.");
        limpiarCampos();
        cargarTabla();
    }

    // Método para consultar un propietario por su cédula
    private void consultarDisponibilidad() {
        String Id_dispo = txtIdcale.getText();
        Query query = BaseD.query();
        query.constrain(CalendarioDisponibilidad.class);
        query.descend("ID_calendario").constrain(Id_dispo);
        ObjectSet<CalendarioDisponibilidad> result = query.execute();
        if (!result.isEmpty()) {
            CalendarioDisponibilidad calendario = result.next();
            mostrarDisponibilidad(calendario);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la fecha disponible.");
            limpiarCampos();
        }
    }
/////// busca por ID

    private void buscarPorId(String id) {
        Query query = BaseD.query();
        query.constrain(CalendarioDisponibilidad.class);
        query.descend("ID_calendario").constrain(id);
        ObjectSet<CalendarioDisponibilidad> result = query.execute();

        if (!result.isEmpty()) {
            CalendarioDisponibilidad calendario = result.next();
            DefaultTableModel model = (DefaultTableModel) TablaDisponi.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                calendario.getID_calendario(),
                calendario.getId_casa(),
                sdf.format(calendario.getFechas_disponibles())
            };
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la fecha disponible.");
            limpiarCampos();
        }
    }

    // Método para mostrar los datos de un propietario en los campos de la interfaz
    private void mostrarDisponibilidad(CalendarioDisponibilidad dispo) {
        txtIdcale.setText(dispo.getID_calendario());
        CBoxCasas.setSelectedItem(dispo.getId_casa());
        CboxFechasDispo.setSelectedItem(dispo.getFechas_disponibles());

    }

    // Método para modificar un registro existente
    private void modificarDisponibilidad() {
        if (!validarCampos()) {
            return;
        }

        String IDdispo = txtIdcale.getText();
        Query query = BaseD.query();
        query.constrain(CalendarioDisponibilidad.class);
        query.descend("ID_calendario").constrain(IDdispo);
        ObjectSet<CalendarioDisponibilidad> result = query.execute();

        if (!result.isEmpty()) {
            CalendarioDisponibilidad disponibilidad = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            disponibilidad.setId_casa(CBoxCasas.getSelectedItem().toString());

            String fechaDispo = CboxFechasDispo.getSelectedItem().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = null;

            try {
                fecha = dateFormat.parse(fechaDispo);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            disponibilidad.setFechas_disponibles(fecha);
            BaseD.store(disponibilidad); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "El registro de disponibilidad se modifico exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de disponibilidad con el ID ingresado.");
        }
    }

// Método para eliminar un registro existente
    private void eliminarDisponibilidad() {
        String E_dispo = txtIdcale.getText();
        Query query = BaseD.query();
        query.constrain(CalendarioDisponibilidad.class);
        query.descend("ID_calendario").constrain(E_dispo);
        ObjectSet<CalendarioDisponibilidad> result = query.execute();
        if (!result.isEmpty()) {
            CalendarioDisponibilidad disponibilidad = result.next();
            BaseD.delete(disponibilidad); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "El registro de disponibilidad se elimino exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro con el ID ingresado.");
        }
    }

    // Método para cargar la tabla con las casas dispo existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaDisponi.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<CalendarioDisponibilidad> result = BaseD.queryByExample(CalendarioDisponibilidad.class);
        while (result.hasNext()) {
            CalendarioDisponibilidad dispo = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                dispo.getID_calendario(),
                dispo.getId_casa(),
                sdf.format(dispo.getFechas_disponibles())
            };
            model.addRow(row);
        }
    }
    // Validar otros campos aquí...

    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIdcale.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIdcale.getText())) {
            JOptionPane.showMessageDialog(this, "Codigo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (CBoxCasas.getSelectedItem() == null || CBoxCasas.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CBoxCasas.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de casa no válida");
                ban_confirmar = false;
            }
        }

        return ban_confirmar;
    }

    public void cargarComboFechas() {

        // Agregar un PropertyChangeListener al JCalendar
        jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Obtener la fecha seleccionada del JCalendar
                Date fechaSeleccionada = jCalendar1.getDate();

                // Obtener el formato deseado para la fecha (en este caso: dd/MM/yyyy)
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String fechaFormateada = dateFormat.format(fechaSeleccionada);

                // Agregar la fecha al JComboBox
                CboxFechasDispo.addItem(fechaFormateada);
            }
        });
    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIdcale.setText("");
        CBoxCasas.setSelectedIndex(0);
        CboxFechasDispo.setSelectedIndex(0);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBoxCasas;
    private javax.swing.JComboBox<String> CboxFechasDispo;
    private javax.swing.JTable TablaDisponi;
    private javax.swing.JButton btnConsul;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModi;
    private javax.swing.JButton btnTodos;
    private javax.swing.JButton btncrear;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtIdcale;
    // End of variables declaration//GEN-END:variables
}
