package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Promocion;
import BD.AlquilerCasas.Clases.Validaciones;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class CRUD_Promociones extends javax.swing.JPanel {

    private ObjectContainer BaseD;
    public CRUD_Promociones(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarCasas();
        cargarTabla();
    }
    public void cargarCasas() {
        CboxCasas.removeAllItems();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);

        ObjectSet<CasaVacacional> casas = query.execute();

        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay casas vacacionales disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (casas.hasNext()) {
                CasaVacacional casa = casas.next();
                CboxCasas.addItem(casa.getNombre());
            }
        }
    }
/// metodo para crear promociones

    public void crearPromocion() {
        try {
            if (!validarCampos()) {
            return;
        }
        String Id_promo = txtIDpromo.getText();
        // Consultar si ya existe un cliente con la misma cédula
        ObjectSet<Promocion> result = BaseD.queryByExample(new Promocion(Id_promo, null, 0, null, null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe una promocion con ese codigo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Si no existe un con la misma codigo, proceder con la creación
        String casas = CboxCasas.getSelectedItem().toString();
        int descuento = Integer.parseInt(txtDescuento.getText());
        String descuentoString = Integer.toString(descuento);

        Date fechaIni = jcalendarInicio.getDate();
        Date fechaFin = jcalendarFin.getDate();

        Promocion mipromo = new Promocion(Id_promo, casas, descuento, fechaIni, fechaFin);
        BaseD.store(mipromo); // Almacenar el objeto en la base de datos
        JOptionPane.showMessageDialog(null, "Promocion creado exitosamente.");
        limpiarCampos();
        cargarTabla();
        } catch (Exception e) {
            System.out.println("No se ha seleccionado un casa vacacional del combo box, puede ser que no exista ningun registro");
        }
        
    }

    /////// busca / consultar por ID
    private void buscarPorId(String id) {
        Query query = BaseD.query();
        query.constrain(Promocion.class);
        query.descend("ID_promocion").constrain(id);
        ObjectSet<Promocion> result = query.execute();

        if (!result.isEmpty()) {
            Promocion promo = result.next();
            DefaultTableModel model = (DefaultTableModel) TablaDescuento.getModel();
            model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                promo.getID_promocion(),
                promo.getCasa(),
                promo.getDescuento(),
                sdf.format(promo.getFecha_inicio_desc()),
                sdf.format(promo.getFecha_fin_desc())
            };
            model.addRow(row);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontron la promocion disponible.");
            limpiarCampos();
        }
    }

     // Método para modificar un registro existente
    private void modificarPromocion() {
        if (!validarCampos()) {
            return;
        }

        String IDpromo = txtIDpromo.getText();
        Query query = BaseD.query();
        query.constrain(Promocion.class);
        query.descend("ID_promocion").constrain(IDpromo);  //ID_promocion ---> es el nombre de la variable de la clase
        ObjectSet<Promocion> result = query.execute();

        if (!result.isEmpty()) {
            Promocion promocion = result.next();
            // Actualizar los campos del propietario con los valores ingresados en la interfaz
            promocion.setCasa(CboxCasas.getSelectedItem().toString());
            promocion.setDescuento(Integer.parseInt(txtDescuento.getText()));
            Date fechaIni = jcalendarInicio.getDate();
            Date fechaFin = jcalendarFin.getDate();

            promocion.setFecha_inicio_desc(fechaIni);
            promocion.setFecha_fin_desc(fechaFin);
            BaseD.store(promocion); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "La promocion se modifico exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro de promocion con el ID ingresado.");
        }
    }
    
        
// Método para eliminar un registro existente
    private void eliminarPromocion(){
        String E_promo = txtIDpromo.getText();
        Query query = BaseD.query();
        query.constrain(Promocion.class);
        query.descend("ID_promocion").constrain(E_promo);
        ObjectSet<Promocion> result = query.execute();
        if (!result.isEmpty()) {
            Promocion promocion = result.next();
            BaseD.delete(promocion); // Eliminar el objeto de la base de datos
            JOptionPane.showMessageDialog(null, "El registro de la promocion se elimino exitosamente.");
            limpiarCampos();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro con el ID ingresado.");
        }
    }
    // Método para cargar la tabla con las casas promos existentes en la base de datos
    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) TablaDescuento.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Promocion> result = BaseD.queryByExample(Promocion.class);
        while (result.hasNext()) {
            Promocion promo = result.next();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[] row = {
                promo.getID_promocion(),
                promo.getCasa(),
                promo.getDescuento(),
                sdf.format(promo.getFecha_inicio_desc()),
                sdf.format(promo.getFecha_fin_desc())
            //fechaFormateada
            };
            model.addRow(row);
        }
    }

    //metodo para verifcar campos
    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIDpromo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un codigo valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIDpromo.getText())) {
            JOptionPane.showMessageDialog(this, "Codigo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        if (CboxCasas.getSelectedItem() == null || CboxCasas.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(CboxCasas.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Selección de casa no válida");
                ban_confirmar = false;
            }
        }
        if (txtDescuento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor valido"); // Ejemplo: AS-1234
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarEntero(txtDescuento.getText())) {
            JOptionPane.showMessageDialog(this, "Valor incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        return ban_confirmar;
    }

    // Método para limpiar los campos de la interfaz
    private void limpiarCampos() {

        txtIDpromo.setText("");
//        CboxCasas.setSelectedIndex(0);
        txtDescuento.setText("");
        jcalendarInicio.setDate(null);
        jcalendarFin.setDate(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIDpromo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jcalendarInicio = new com.toedter.calendar.JDateChooser();
        jcalendarFin = new com.toedter.calendar.JDateChooser();
        txtDescuento = new javax.swing.JTextField();
        CboxCasas = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDescuento = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        btnConsul = new javax.swing.JButton();
        btnMod = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(810, 589));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("PROMOCIONES");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/etiqueta-descuento.png"))); // NOI18N

        jLabel3.setText("ID PROMOCION");

        jLabel4.setText("CASA:");

        jLabel5.setText("DESCUENTO:");

        jLabel6.setText("FECHA INICIO:");

        jLabel7.setText("FICHA FIN:");

        CboxCasas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Casa Verano", "Casa Invierno", "Casa Playa", "Casa Campo" }));

        TablaDescuento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID PROMOCION", "CASA", "DESCUENTO", "FECHA INICIO DESCUENTO", "FECHA FIN DESCUENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaDescuento);

        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnConsul.setText("CONSULTAR");
        btnConsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsulActionPerformed(evt);
            }
        });

        btnMod.setText("MODIFICAR");
        btnMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActionPerformed(evt);
            }
        });

        btnDelete.setText("ELIMINAR");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnTodos.setText("VER TODOS");
        btnTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(10, 10, 10)
                                .addComponent(txtIDpromo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel6)
                                .addGap(16, 16, 16)
                                .addComponent(jcalendarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel4)
                        .addGap(40, 40, 40)
                        .addComponent(CboxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel7)
                        .addGap(35, 35, 35)
                        .addComponent(jcalendarFin, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel5)
                        .addGap(23, 23, 23)
                        .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(btnCrear)
                        .addGap(33, 33, 33)
                        .addComponent(btnConsul)
                        .addGap(27, 27, 27)
                        .addComponent(btnMod)
                        .addGap(19, 19, 19)
                        .addComponent(btnDelete)
                        .addGap(29, 29, 29)
                        .addComponent(btnTodos))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3))
                            .addComponent(txtIDpromo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jcalendarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel2))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4))
                    .addComponent(CboxCasas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jcalendarFin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5))
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCrear)
                    .addComponent(btnConsul)
                    .addComponent(btnMod)
                    .addComponent(btnDelete)
                    .addComponent(btnTodos))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        crearPromocion();
        limpiarCampos();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnConsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsulActionPerformed
        // TODO add your handling code here:
        String id = txtIDpromo.getText();
        buscarPorId(id);
    }//GEN-LAST:event_btnConsulActionPerformed

    private void btnTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosActionPerformed
        // TODO add your handling code here:
        cargarTabla();
    }//GEN-LAST:event_btnTodosActionPerformed

    private void btnModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActionPerformed
        // TODO add your handling code here:
        modificarPromocion();
        limpiarCampos();
    }//GEN-LAST:event_btnModActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        eliminarPromocion();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxCasas;
    private javax.swing.JTable TablaDescuento;
    private javax.swing.JButton btnConsul;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMod;
    private javax.swing.JButton btnTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.toedter.calendar.JDateChooser jcalendarFin;
    private com.toedter.calendar.JDateChooser jcalendarInicio;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtIDpromo;
    // End of variables declaration//GEN-END:variables
}
