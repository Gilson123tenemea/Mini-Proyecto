package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Validaciones;
import BD.AlquilerCasas.Clases.Vehiculo;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import javax.swing.JOptionPane;
import com.toedter.calendar.JCalendar;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN_01
 */
public class CRUD_Vehiculo extends javax.swing.JPanel {
//FJ
    private ObjectContainer BaseD;
    String ID_carro = "";
    String marca = "";
    String modelo = "";
    String anio = "";
    String tipoVehiculo = "";
    String casa = "";
    
    
    public CRUD_Vehiculo(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTabla();
        
    }
    
    private void crearVehiculo() {
        if (!validarCampos()) {
            return;
        }

        String ID_carro = txtplacaCarro.getText();
        
        ObjectSet<Vehiculo> result = BaseD.queryByExample(new Vehiculo(ID_carro, null, null, 0, null,null));
        if (!result.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ya existe un Vehiculo con el numero de placa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String modelo = txtmodelo.getText();
        String marca = txtMarca.getText();
        int anio = AnioVehi.getValue();
        String tipo = cboxTipoVehiculo.getSelectedItem().toString();
        String tipocasa = CboxCasa.getSelectedItem().toString();
        
        Vehiculo micarro = new Vehiculo ();
        micarro.setID_carro(ID_carro);
        micarro.setModelo(modelo);
        micarro.setMarca(marca);
        micarro.setAnio(anio);
        micarro.setTipoVehiculo(tipo);
        micarro.setCasa(tipocasa);
        
        BaseD.store(micarro);
        
        JOptionPane.showMessageDialog(null, "Vehiculo creado exitosamente.");
        limpiarCampos();
        cargarTabla();
    }
    
     private void consultarVehiculo() {
        String ID_carro = txtplacaCarro.getText();
        Query query = BaseD.query();
        query.constrain(Vehiculo.class);
        query.descend("ID_carro").constrain(ID_carro); // Cedula que esta en la clase
        ObjectSet<Vehiculo> result = query.execute();
        if (!result.isEmpty()) {
            Vehiculo carro = result.next();
            mostrarVehiculo(carro);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
            limpiarCampos();
        }
        

     }
     
     private void modificarVehiculo() {
          if (!validarCampos()) {
            return;
        }

       String ID_carro = txtplacaCarro.getText();
       Query query = BaseD.query();
        query.constrain(Vehiculo.class);
        query.descend("ID_carro").constrain(ID_carro);
        ObjectSet<Vehiculo> result = query.execute();
          if (!result.isEmpty()) {
             Vehiculo micar = result.next();
             micar.setMarca(txtMarca.getText());
             micar.setModelo(txtmodelo.getText());
             micar.setAnio(AnioVehi.getYear());
             micar.setTipoVehiculo(cboxTipoVehiculo.getSelectedItem().toString());
             micar.setCasa(CboxCasa.getSelectedItem().toString());
             BaseD.store(micar);
             JOptionPane.showMessageDialog(null, "Vehiculo modificado exitosamente.");
             limpiarCampos();
            cargarTabla();
             
          }else {
            JOptionPane.showMessageDialog(null, "No se encontró un Vehiculo con la placa ingresada.");
        }
     }
    private void mostrarVehiculo(Vehiculo carro) {
        txtplacaCarro.setText(carro.getID_carro());
        txtMarca.setText(carro.getMarca());
        txtmodelo.setText(carro.getModelo());
        AnioVehi.setYear(carro.getAnio());
        cboxTipoVehiculo.setSelectedItem(carro.getTipoVehiculo());
        CboxCasa.setSelectedItem(carro.getCasa());
        
        
    }
 
    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;
        
         if (txtplacaCarro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la placa del Vehiculo");
            ban_confirmar = false;
        } else if (!miValidaciones.validarPlaca(txtplacaCarro.getText())) {
            JOptionPane.showMessageDialog(this, "Placa incorrecta. Ingrese de nuevo");
            ban_confirmar = false;
        }
        
        if (txtMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la marca del Vehiculo");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarNomApe(txtMarca.getText())) {
            JOptionPane.showMessageDialog(this, "Marca incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
         
        if (txtmodelo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el modelo del Vehiculo");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarNomApe(txtmodelo.getText())) {
            JOptionPane.showMessageDialog(this, "Modelo incorrecto. Ingrese de nuevo");
            ban_confirmar = false;
        }
        
        
        return ban_confirmar;
     }
    
     private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaVehiculo.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
        ObjectSet<Vehiculo> result = BaseD.queryByExample(Vehiculo.class);
        while (result.hasNext()) {
            Vehiculo carro = result.next();
             Object[] row = {
              carro.getID_carro(),
              carro.getModelo(),
              carro.getMarca(),
              carro.getAnio(),
              carro.getTipoVehiculo(),
              carro.getCasa()   
             };
            model.addRow(row);
        }

     }
     
     
     private void eliminarVehiculo() {
         String ID_carro = txtplacaCarro.getText();
         Query query = BaseD.query();
        query.constrain(Vehiculo.class);
        query.descend("Cedula").constrain(ID_carro);
        ObjectSet<Vehiculo> result = query.execute();
         if (!result.isEmpty()) {
             Vehiculo carro = result.next();
             BaseD.delete(carro); // Eliminar el objeto de la base de datos
             JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.");
            limpiarCampos();
            cargarTabla();
         }else {
            JOptionPane.showMessageDialog(null, "No se encontró un cliente con la cédula ingresada.");
        }
     }
     
     public void habilitarParametros() {
         txtplacaCarro.setEnabled(true);
         txtMarca.setEnabled(true);
         txtmodelo.setEnabled(true);
         AnioVehi.setEnabled(true);
         cboxTipoVehiculo.setEnabled(true);
         CboxCasa.setEnabled(true);
     }
     
     public void deshabilitarParametros() {
         txtplacaCarro.setEnabled(false);
         txtMarca.setEnabled(false);
         txtmodelo.setEnabled(false);
         AnioVehi.setEnabled(false);
         cboxTipoVehiculo.setEnabled(false);
         CboxCasa.setEnabled(false);
     }
     
     private void limpiarCampos() {
          txtplacaCarro.setText("");
          txtMarca.setText("");
          txtmodelo.setText("");
          AnioVehi.setYear(0);
          cboxTipoVehiculo.setSelectedIndex(0);
          CboxCasa.setSelectedIndex(0);
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        jLabel7 = new javax.swing.JLabel();
        txtplacaCarro = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtmodelo = new javax.swing.JTextField();
        AnioVehi = new com.toedter.calendar.JYearChooser();
        cboxTipoVehiculo = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        tbnReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVehiculo = new javax.swing.JTable();
        CboxCasa = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("REGISTRO DE VEHICULO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Placa del Carro: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Marca:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Modelo:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Año:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tipo de Vehiculo: ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Casa: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, -1, 20));
        jPanel1.add(txtplacaCarro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 130, -1));
        jPanel1.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 130, -1));
        jPanel1.add(txtmodelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, 130, -1));

        AnioVehi.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(AnioVehi, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 60, -1));

        cboxTipoVehiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Carros Deportivos", "Camiones", "Motocicletas ", "Carros pequeños", " ", " " }));
        cboxTipoVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboxTipoVehiculoActionPerformed(evt);
            }
        });
        jPanel1.add(cboxTipoVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 130, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sav.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 120, 40));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, -1, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 310, 120, 40));

        tbnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        tbnReporte.setText("REPORTE");
        tbnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnReporteActionPerformed(evt);
            }
        });
        jPanel1.add(tbnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 310, 120, 40));

        tablaVehiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Placa", "Marca", "Modelo", "Año", "Tipo Vehiculo", "Casa"
            }
        ));
        jScrollPane1.setViewportView(tablaVehiculo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 770, 110));

        CboxCasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Casa Verano", "Casa Invierno", "Casa Playa", "Casa Campo" }));
        jPanel1.add(CboxCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 130, -1));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnReporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
    }//GEN-LAST:event_tbnReporteActionPerformed

    private void cboxTipoVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboxTipoVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboxTipoVehiculoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        crearVehiculo();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarVehiculo();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       eliminarVehiculo();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtplacaCarro.setEnabled(false);
        btnGuardar.setEnabled(false);
        consultarVehiculo();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser AnioVehi;
    private javax.swing.JComboBox<String> CboxCasa;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox<String> cboxTipoVehiculo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaVehiculo;
    private javax.swing.JButton tbnReporte;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtmodelo;
    private javax.swing.JTextField txtplacaCarro;
    // End of variables declaration//GEN-END:variables
}
