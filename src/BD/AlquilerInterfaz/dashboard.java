package BD.AlquilerInterfaz;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class dashboard extends javax.swing.JFrame {

    //Direccion de la base de datos
    //public static String direccionBD = "C:\\Users\\USER\\Documents\\GitHub\\AlquilerInterfaz.yap"; // Bryan farez
    //public static String direccionBD = "C:\\Users\\David\\Downloads\\BASEMiniProyecto\\BASEMiniProyecto.yap";//Isaac Villa
    //public static String direccionBD = "C:\\Users\\HP\\Documents\\GitHub\\AlquilerInterfaz.yap"; // Stiven Carpio
    //public static String direccionBD = "C:\\Users\\PC01\\Documents\\GitHub\\AlquilerInterfaz.yap";
    public static String direccionBD = "C:\\Users\\ADMIN_01\\Documents\\MINI PROYECTO BDOO\\AlquilerInterfaz.yap";

    ObjectContainer baseD = Db4o.openFile(direccionBD);

    public dashboard() {

        initComponents();
        this.setTitle("Base De Datos Orientada a Objetos");
        this.setLocationRelativeTo(null);
        cerrarBaseVentana();
        ShowJPanel(new Inicio());
    }

    private void cerrarBaseVentana() {
        // Cerrar la base de datos cuando se presiona la x de le ventana principal
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                baseD.close();
            }
        });
    }

    public void cerrarBaseDeDatos() {
        baseD.close(); // Cierra la conexión con la base de datos
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        jLabelCasa = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btncliente = new javax.swing.JButton();
        btnpropi = new javax.swing.JButton();
        btnadmi = new javax.swing.JButton();
        btncasa = new javax.swing.JButton();
        btnreservar = new javax.swing.JButton();
        btnpago = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnFactura = new javax.swing.JButton();
        btnPromo = new javax.swing.JButton();
        btnCalendario = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        Contenido = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        Bg.setBackground(new java.awt.Color(255, 255, 255));
        Bg.setForeground(new java.awt.Color(255, 255, 255));

        menu.setBackground(new java.awt.Color(0, 102, 153));
        menu.setForeground(new java.awt.Color(255, 255, 255));

        jLabelCasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/casa.png"))); // NOI18N

        btncliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/users-altV.png"))); // NOI18N
        btncliente.setText("CLIENTES");
        btncliente.setBorder(null);
        btncliente.setBorderPainted(false);
        btncliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclienteActionPerformed(evt);
            }
        });

        btnpropi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/users-altV.png"))); // NOI18N
        btnpropi.setText("PROPIETARIO");
        btnpropi.setBorder(null);
        btnpropi.setBorderPainted(false);
        btnpropi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnpropi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpropiActionPerformed(evt);
            }
        });

        btnadmi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8_Contacts_25px.png"))); // NOI18N
        btnadmi.setText("AGENTE INMOBILIARIO");
        btnadmi.setBorder(null);
        btnadmi.setBorderPainted(false);
        btnadmi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnadmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnadmiActionPerformed(evt);
            }
        });

        btncasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8-casa-32.png"))); // NOI18N
        btncasa.setText("CASAS VACACIONALES");
        btncasa.setBorder(null);
        btncasa.setBorderPainted(false);
        btncasa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncasaActionPerformed(evt);
            }
        });

        btnreservar.setText("RESERVACION");
        btnreservar.setBorder(null);
        btnreservar.setBorderPainted(false);
        btnreservar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnreservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreservarActionPerformed(evt);
            }
        });

        btnpago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/billete-de-banco.png"))); // NOI18N
        btnpago.setText("PAGO");
        btnpago.setBorder(null);
        btnpago.setBorderPainted(false);
        btnpago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnpago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpagoActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/camion1.png"))); // NOI18N
        jButton2.setText("VEHICULO");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ACTIVIDADES");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/google_forms_24px.png"))); // NOI18N
        btnFactura.setText("FACTURA");
        btnFactura.setBorder(null);
        btnFactura.setBorderPainted(false);
        btnFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturaActionPerformed(evt);
            }
        });

        btnPromo.setText("PROMOCIONES");
        btnPromo.setBorder(null);
        btnPromo.setBorderPainted(false);
        btnPromo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPromoActionPerformed(evt);
            }
        });

        btnCalendario.setText("CALENDARIO");
        btnCalendario.setBorder(null);
        btnCalendario.setBorderPainted(false);
        btnCalendario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarioActionPerformed(evt);
            }
        });

        jButton6.setText("CONTRATO");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/account_24px.png"))); // NOI18N
        jButton7.setText("REPORTE");
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("COMENTARIO");
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("SERVICIO ADICIONAL");
        jButton9.setBorder(null);
        jButton9.setBorderPainted(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        btnsalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/icons8_Exit_25px.png"))); // NOI18N
        btnsalir.setText("SALIR");
        btnsalir.setBorder(null);
        btnsalir.setBorderPainted(false);
        btnsalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPromo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnreservar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnpago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(menuLayout.createSequentialGroup()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabelCasa))
                    .addGroup(menuLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnpropi, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnadmi, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncasa, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabelCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btncliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnpropi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnadmi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncasa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPromo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnreservar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpago, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        header.setBackground(new java.awt.Color(0, 153, 204));
        header.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        titulo.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        titulo.setText("ALQUILER DE CASAS VACACIONALES \"M2B KAME HOUSE\"");

        Contenido.setBackground(new java.awt.Color(255, 255, 255));
        Contenido.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ContenidoLayout = new javax.swing.GroupLayout(Contenido);
        Contenido.setLayout(ContenidoLayout);
        ContenidoLayout.setHorizontalGroup(
            ContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ContenidoLayout.setVerticalGroup(
            ContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BgLayout = new javax.swing.GroupLayout(Bg);
        Bg.setLayout(BgLayout);
        BgLayout.setHorizontalGroup(
            BgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BgLayout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(BgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BgLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(BgLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 157, Short.MAX_VALUE))))
            .addGroup(BgLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BgLayout.setVerticalGroup(
            BgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(BgLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Contenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
        cerrarBaseDeDatos(); // Cierra la base de datos
        dispose(); // Cierra la ventana
    }//GEN-LAST:event_btnsalirActionPerformed
    public void ShowJPanel(JPanel p) {
        p.setSize(830, 601);
        p.setLocation(0, 0);

        Contenido.removeAll();
        Contenido.add(p, BorderLayout.CENTER);
        Contenido.revalidate();
        Contenido.repaint();
    }
    private void btnclienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclienteActionPerformed
        ShowJPanel(new CRUD_Clientes(baseD));
    }//GEN-LAST:event_btnclienteActionPerformed

    private void btnpropiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpropiActionPerformed
        ShowJPanel(new CRUD_Propietario(baseD));
    }//GEN-LAST:event_btnpropiActionPerformed

    private void btnCalendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarioActionPerformed
        ShowJPanel(new CalendarioDispo(baseD));
    }//GEN-LAST:event_btnCalendarioActionPerformed

    private void btnPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromoActionPerformed
        ShowJPanel(new CRUD_Promociones(baseD));
    }//GEN-LAST:event_btnPromoActionPerformed

    private void btnadmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnadmiActionPerformed
        ShowJPanel(new V_AgenteInmobiliario(baseD));
    }//GEN-LAST:event_btnadmiActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        ShowJPanel(new V_ServicioAdicional(baseD));
    }//GEN-LAST:event_jButton9ActionPerformed

    private void btncasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncasaActionPerformed
        ShowJPanel(new CRUD_CasaVacacional11(baseD));
    }//GEN-LAST:event_btncasaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ShowJPanel(new CRUD_Vehiculo(baseD));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ShowJPanel(new CRUD_Actividades(baseD));
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        ShowJPanel(new CRUD_Comentario(baseD));
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnpagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpagoActionPerformed
        ShowJPanel(new CRUD_Pago(baseD));
    }//GEN-LAST:event_btnpagoActionPerformed

    private void btnFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturaActionPerformed
        ShowJPanel(new CRUD_Factura(baseD));
    }//GEN-LAST:event_btnFacturaActionPerformed

    private void btnreservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreservarActionPerformed
        ShowJPanel(new CRUD_Rerservaciones(baseD));
    }//GEN-LAST:event_btnreservarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ShowJPanel(new CRUD_Contrato(baseD));
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        ShowJPanel(new V_Reporte(baseD));
    }//GEN-LAST:event_jButton7ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Bg;
    private javax.swing.JPanel Contenido;
    private javax.swing.JButton btnCalendario;
    private javax.swing.JButton btnFactura;
    private javax.swing.JButton btnPromo;
    private javax.swing.JButton btnadmi;
    private javax.swing.JButton btncasa;
    private javax.swing.JButton btncliente;
    private javax.swing.JButton btnpago;
    private javax.swing.JButton btnpropi;
    private javax.swing.JButton btnreservar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabelCasa;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
