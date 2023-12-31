package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.AgenteInmobiliario;
import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Propietario;
import BD.AlquilerCasas.Clases.Validaciones;
import BD.AlquilerCasas.Clases.Vehiculo;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CRUD_CasaVacacional11 extends javax.swing.JPanel {

    private ObjectContainer BaseD;
    String id_casa = "";
    String IDPropietario = "";
    String nombre = "";
    String carro = "";
    int num_pisos = 0;
    int capacidad_maxima = 0;
    int num_habitaciones = 0;
    int num_banos = 0;
    String tiene_piscina = "";
    String tiene_jardin = "";
    String tiene_wifi = "";
    String tiene_tv = "";
    String tiene_cocina = "";
    String ubicacion = "";
    String otros_detalles = "";

    public CRUD_CasaVacacional11(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarPropietarios();
        cargarVehiculos();
        cargarTabla();

    }

    public void cargarPropietarios() {
        cbxPropietarios.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Propietario.class);

        ObjectSet<Propietario> propi = query.execute();

        if (propi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existen propietarios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (propi.hasNext()) {
                Propietario pro = propi.next();
                cbxPropietarios.addItem(pro.getCedulaPropietario());
            }
        }
    }

    private void mostrarDatosPropietarioSeleccionado() {
        String cedulaSeleccionada = cbxPropietarios.getSelectedItem().toString();
        Query query = BaseD.query();
        query.constrain(Propietario.class);
        query.descend("CedulaPropietario").constrain(cedulaSeleccionada);
        ObjectSet<Propietario> result = query.execute();

        if (!result.isEmpty()) {
            Propietario propietario = result.next();
            String mensaje = "Cédula: " + propietario.getCedulaPropietario() + "\n"
                    + "Nombre: " + propietario.getNombrePropietario() + "\n"
                    + "Apellido: " + propietario.getApellidoPropietario() + "\n"
                    + "Teléfono: " + propietario.getTelfPropietario() + "\n"
                    + "Correo: " + propietario.getCorreo_propi();

            JOptionPane.showMessageDialog(this, mensaje, "Datos del Propietario", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un propietario con la cédula seleccionada.", "Propietario no encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDatosVehiculoSeleccionado() {
        String placaSeleccionada = CboxtipoCarro.getSelectedItem().toString();
        Query query = BaseD.query();
        query.constrain(Vehiculo.class);
        query.descend("ID_carro").constrain(placaSeleccionada);
        ObjectSet<Vehiculo> result = query.execute();

        if (!result.isEmpty()) {
            Vehiculo carro = result.next();
            String mensaje = "Placa: " + carro.getID_carro() + "\n"
                    + "Marca: " + carro.getMarca() + "\n"
                    + "Modelo: " + carro.getModelo() + "\n"
                    + "Año: " + carro.getAnio() + "\n"
                    + "Tipo De Vehiculo: " + carro.getTipoVehiculo();

            JOptionPane.showMessageDialog(this, mensaje, "Datos del Vehiculo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró un vehiculo con la placa seleccionado.", "Vehiculo no encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarVehiculos() {
        CboxtipoCarro.removeAllItems();
        Query query = BaseD.query();
        query.constrain(Vehiculo.class);

        ObjectSet<Vehiculo> vehic = query.execute();

        if (vehic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay vehiculos disponibles", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            while (vehic.hasNext()) {
                Vehiculo veh = vehic.next();
                CboxtipoCarro.addItem(veh.getID_carro());
            }
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtIDCASA = new javax.swing.JTextField();
        spnpisos = new javax.swing.JSpinner();
        spnmaximo = new javax.swing.JSpinner();
        spnhabitaciones = new javax.swing.JSpinner();
        spnbanos = new javax.swing.JSpinner();
        cboxpicina = new javax.swing.JCheckBox();
        cboxjardin = new javax.swing.JCheckBox();
        cboxwifi = new javax.swing.JCheckBox();
        cboxTV = new javax.swing.JCheckBox();
        cbxcocina = new javax.swing.JCheckBox();
        btnGUARDAR = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_casaVacacional = new javax.swing.JTable();
        cbxnacionalidad_crudcasa = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        CbxFiltrarbsuque = new javax.swing.JComboBox<>();
        BtnBuscarFiltro = new javax.swing.JButton();
        CboxtipoCarro = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtnombreCasa = new javax.swing.JTextField();
        cbxPropietarios = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtdetallescasa = new javax.swing.JTextArea();
        btnVerPropietarios = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("REGISTROS DE CASAS DE VACACIONES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jLabel2.setText("ID Casa: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        jLabel3.setText("Propietarios:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabel4.setText("Placa de carro:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Equipamiento de la Casa");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, -1, -1));

        jLabel6.setText("Numero de Pisos:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        jLabel7.setText("Capacidad Maxima: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, -1, -1));

        jLabel8.setText("Numero de Habitaciones:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, -1, -1));

        jLabel9.setText("Numero de Baños:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel10.setText("Tiene Picina:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, 110, 30));

        jLabel11.setText("Tiene Jardin:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 110, 30));

        jLabel12.setText("Tiene WIFI:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 100, 30));

        jLabel13.setText("Tiene TV:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 100, 90, 30));

        jLabel14.setText("Tiene Cocina: ");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 120, 30));

        jLabel15.setText("Ubicacion:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 100, 30));

        jLabel16.setText("Otros Detalles:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 130, 30));
        jPanel1.add(txtIDCASA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 170, -1));

        spnpisos.setModel(new javax.swing.SpinnerNumberModel(2, 1, 4, 1));
        jPanel1.add(spnpisos, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 60, -1));

        spnmaximo.setModel(new javax.swing.SpinnerNumberModel(5, 5, 20, 1));
        jPanel1.add(spnmaximo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 60, -1));

        spnhabitaciones.setModel(new javax.swing.SpinnerNumberModel(2, 2, 8, 1));
        jPanel1.add(spnhabitaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 60, -1));

        spnbanos.setModel(new javax.swing.SpinnerNumberModel(2, 2, 4, 1));
        jPanel1.add(spnbanos, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 60, -1));

        cboxpicina.setBackground(new java.awt.Color(255, 255, 255));
        cboxpicina.setText("SI");
        jPanel1.add(cboxpicina, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 80, 40));

        cboxjardin.setBackground(new java.awt.Color(255, 255, 255));
        cboxjardin.setText("SI");
        jPanel1.add(cboxjardin, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 80, 40));

        cboxwifi.setBackground(new java.awt.Color(255, 255, 255));
        cboxwifi.setText("SI");
        jPanel1.add(cboxwifi, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 80, 40));

        cboxTV.setBackground(new java.awt.Color(255, 255, 255));
        cboxTV.setText("SI");
        jPanel1.add(cboxTV, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, 80, 40));

        cbxcocina.setBackground(new java.awt.Color(255, 255, 255));
        cbxcocina.setText("SI");
        jPanel1.add(cbxcocina, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 80, 40));

        btnGUARDAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/crear.png"))); // NOI18N
        btnGUARDAR.setText("GUARDAR");
        btnGUARDAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGUARDARActionPerformed(evt);
            }
        });
        jPanel1.add(btnGUARDAR, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 120, 40));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mod.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, 140, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 390, 120, 40));

        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/informe.png"))); // NOI18N
        btnReporte.setText("REPORTE");
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        jPanel1.add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 390, 120, 40));

        tabla_casaVacacional.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Casa", "Nombre", "Cedula Propietario", "Tipo Carro", "N° Pisos", "N° Baños", "Capacidad maxima", "N° Habitaciones", "Piscina", "WIFI", "Cocina", "Jardín", "TV", "Ubicación", "Otros Detalles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla_casaVacacional);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 800, 150));

        cbxnacionalidad_crudcasa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Guayaquil", "Quito", "Cuenca", "Santo Domingo", "Machala", "Durán", "Manta", "Portoviejo", "Loja", "Ambato", "Esmeraldas", "Quevedo", "Riobamba", "Milagro", "Ibarra", "La Libertad", "Babahoyo", "Sangolquí", "Daule", "Latacunga", "Tulcán", "Chone", "Pasaje", "Santa Rosa", "Nueva Loja", "Huaquillas", "El Carmen", "Montecristi", "Samborondón", "Puerto Francisco de Orellana", "Jipijapa", "Santa Elena", "Otavalo", "Cayambe", "Buena Fe", "Ventanas", "Velasco Ibarra", "La Troncal", "El Triunfo", "Salinas", "General Villamil", "Azogues", "Puyo", "Vinces", "La Concordia", "Rosa Zárate", "Balzar", "Naranjal", "Guaranda", "La Maná", "Tena", "San Lorenzo", "Catamayo", "El Guabo", "Pedernales", "Atuntaqui", "Bahía de Caráquez", "Pedro Carbo", "Macas", "Yaguachi", "Calceta", "Arenillas", "Jaramijó", "Valencia", "Machachi", "Shushufindi", "Atacames", "Piñas", "San Gabriel", "Gualaceo", "Cañar", "Cariamanga", "Baños de Agua Santa", "Montalvo", "Macará", "San Miguel de Salcedo", "Zamora", "Puerto Ayora", "La Joya de los Sachas", "Tosagua", "Pelileo", "Puerto López", "San Vicente", "Santa Ana", "Zaruma", "Rocafuerte", "Cotacachi", "Santa Lucía", "Puebloviejo", "Portovelo", "Sucúa", "Simón Bolívar", "Gualaquiza", "Paute", "San Miguel", "Puerto Baquerizo Moreno", "Catacocha", "Palenque", "Alausí", "Santa Isabel", "Biblian", "Valdez (Limones)", "El Tambo", "Quinsaloma", "El Ángel\\n", "Chordeleg", "Saraguro", "Girón", "Pichincha", "Sigsig", "Loreto", "Rioverde", "Zumba", "Bolívar", "Sucre", "Guamote", "Cevallos", "San Fernando", "Santa Clara", "Nabón", "La Victoria", "Guachapala", "Santiago", "Chaguarpamba", "Oña", "Sevilla de Oro", "Olmedo", "Déleg", "El Pan" }));
        jPanel1.add(cbxnacionalidad_crudcasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 180, 240, -1));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        CbxFiltrarbsuque.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "idCasa", "cedulaPropietario", "nombre", " " }));
        CbxFiltrarbsuque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxFiltrarbsuqueActionPerformed(evt);
            }
        });
        jPanel1.add(CbxFiltrarbsuque, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 336, 100, 30));

        BtnBuscarFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/busqueda.png"))); // NOI18N
        BtnBuscarFiltro.setText("BUSCAR");
        BtnBuscarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarFiltroActionPerformed(evt);
            }
        });
        jPanel1.add(BtnBuscarFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, 110, -1));

        CboxtipoCarro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deportivo", "Camioneta", "Carro" }));
        CboxtipoCarro.setToolTipText("");
        jPanel1.add(CboxtipoCarro, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 170, -1));

        jLabel17.setText("Nombre:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));
        jPanel1.add(txtnombreCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 170, -1));

        cbxPropietarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0150698017", "0150989755", "0984501255", "0156598521" }));
        jPanel1.add(cbxPropietarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 170, -1));

        jLabel18.setText("Filtro de busqueda:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 340, -1, -1));

        txtdetallescasa.setColumns(20);
        txtdetallescasa.setRows(5);
        jScrollPane2.setViewportView(txtdetallescasa);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 270, 90));

        btnVerPropietarios.setText("VER");
        btnVerPropietarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPropietariosActionPerformed(evt);
            }
        });
        jPanel1.add(btnVerPropietarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, -1, -1));

        jButton2.setText("VER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, -1, -1));

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

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificarCasaVacacional();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGUARDARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGUARDARActionPerformed
        crearCasa();
    }//GEN-LAST:event_btnGUARDARActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarCasas();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        cargarTabla();
        limpiarCampos();
        habilitarParametros();
    }//GEN-LAST:event_btnReporteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        txtIDCASA.setEnabled(false);
        btnGUARDAR.setEnabled(false);
        consultarcasavacaional();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CbxFiltrarbsuqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxFiltrarbsuqueActionPerformed
        String criterioSeleccionado = CbxFiltrarbsuque.getSelectedItem().toString();
        habilitarCamposBusqueda(criterioSeleccionado);
    }//GEN-LAST:event_CbxFiltrarbsuqueActionPerformed

    private void BtnBuscarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarFiltroActionPerformed
        try {
            // Obtener el criterio seleccionado del JComboBox
            String criterioSeleccionado = CbxFiltrarbsuque.getSelectedItem().toString();

            // Obtener el valor de búsqueda ingresado por el usuario
            String valorBusqueda = obtenerValorBusqueda(criterioSeleccionado);

            // Realizar la búsqueda y cargar los resultados en el JTable
            filtrarCasaVacacional(criterioSeleccionado, valorBusqueda);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encontraron casas vacaionales con esos parametros");
        }
    }//GEN-LAST:event_BtnBuscarFiltroActionPerformed

    private void btnVerPropietariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPropietariosActionPerformed
        mostrarDatosPropietarioSeleccionado();
    }//GEN-LAST:event_btnVerPropietariosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        mostrarDatosVehiculoSeleccionado();
    }//GEN-LAST:event_jButton2ActionPerformed
    private void crearCasa() {
        try {
            if (!validarCampos()) {
                return;
            }

            // Obtiene los datos de la interfaz de usuario
            String IDcas = txtIDCASA.getText();
            String Idpropie = cbxPropietarios.getSelectedItem().toString();
            String nombre = txtnombreCasa.getText();
            String carro = CboxtipoCarro.getSelectedItem().toString();
            int pisos = (int) spnpisos.getValue();
            int capacidad = (int) spnmaximo.getValue();
            int habitaciones = (int) spnhabitaciones.getValue();
            int baños = (int) spnbanos.getValue();
            String picina = cboxpicina.isSelected() ? "Sí" : "No";
            String jardin = cboxjardin.isSelected() ? "Sí" : "No";
            String wifi = cboxwifi.isSelected() ? "Sí" : "No";
            String tv = cboxTV.isSelected() ? "Sí" : "No";
            String cocina = cbxcocina.isSelected() ? "Sí" : "No";
            String nacionalidad = cbxnacionalidad_crudcasa.getSelectedItem().toString();
            String detalles = txtdetallescasa.getText();

            // Verificar si ya existe una casa con el mismo ID
            ObjectSet<CasaVacacional> result = BaseD.queryByExample(new CasaVacacional(IDcas, null, null, null, 0, 0, 0, 0, null, null, null, null, null, null, null));
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ya existe una casa vacacional con el id", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear una nueva instancia de CasaVacacional y establecer los datos
            CasaVacacional micasa = new CasaVacacional();
            micasa.setId_casa(IDcas);
            micasa.setIDPropietario(Idpropie);
            micasa.setNombre(nombre);
            micasa.setCarro(carro);
            micasa.setNum_pisos(pisos);
            micasa.setCapacidad_maxima(capacidad);
            micasa.setNum_habitaciones(habitaciones);
            micasa.setNum_banos(baños);
            micasa.setTiene_piscina(picina);
            micasa.setTiene_jardin(jardin);
            micasa.setTiene_wifi(wifi);
            micasa.setTiene_tv(tv);
            micasa.setTiene_cocina(cocina);
            micasa.setUbicacion(nacionalidad);
            micasa.setOtros_detalles(detalles);

            BaseD.store(micasa); // Almacenar el objeto en la base de datos

            JOptionPane.showMessageDialog(null, "Casa vacacional creada exitosamente.");
            limpiarCampos();
            cargarTabla();

        } catch (Exception e) {
            System.out.println("Error al crear la casa.");
            e.printStackTrace();
        }
    }

    private void consultarcasavacaional() {

        String IDcas = txtIDCASA.getText();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);
        query.descend("id_casa").constrain(IDcas);
        ObjectSet<CasaVacacional> result = query.execute();
        if (!result.isEmpty()) {
            CasaVacacional casa = result.next();
            mostrarCasaVacacional(casa);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una Casa Vcacional con el ID ingresada.");
            limpiarCampos();
        }
    }

    private void modificarCasaVacacional() {
        if (!validarCampos()) {
            return;
        }
        String IDcas = txtIDCASA.getText();
        Query query = BaseD.query();
        query.constrain(CasaVacacional.class);
        query.descend("id_casa").constrain(IDcas);
        ObjectSet<CasaVacacional> result = query.execute();
        if (!result.isEmpty()) {
            CasaVacacional casa = new CasaVacacional();
            casa.setIDPropietario(cbxPropietarios.getSelectedItem().toString());
            casa.setNombre(txtnombreCasa.getText());
            casa.setCarro(CboxtipoCarro.getSelectedItem().toString());
            casa.setNum_pisos((int) spnpisos.getValue());
            casa.setCapacidad_maxima((int) spnmaximo.getValue());
            casa.setNum_habitaciones((int) spnhabitaciones.getValue());
            casa.setNum_banos((int) spnbanos.getValue());
            casa.setTiene_piscina(cboxpicina.getSelectedIcon().toString());
            casa.setTiene_jardin(cboxjardin.getSelectedIcon().toString());
            casa.setTiene_wifi(cboxwifi.getSelectedIcon().toString());
            casa.setTiene_tv(cboxTV.getSelectedIcon().toString());
            casa.setTiene_cocina(cbxcocina.getSelectedIcon().toString());
            casa.setUbicacion(cbxnacionalidad_crudcasa.getSelectedItem().toString());
            casa.setOtros_detalles(txtdetallescasa.getText());

            BaseD.store(casa); // Actualizar el objeto en la base de datos
            JOptionPane.showMessageDialog(null, "Casa Vacacional modificado exitosamente.");
            limpiarCampos();
            cargarTabla();

        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una casa vacacional con el ID ingresado.");
        }
    }

    private void mostrarCasaVacacional(CasaVacacional casa) {
        txtIDCASA.setText(casa.getId_casa());
        cbxPropietarios.setSelectedItem(casa.getIDPropietario());
        txtnombreCasa.setText(casa.getNombre());
        CboxtipoCarro.setSelectedItem(casa.getCarro());
        spnpisos.setValue(casa.getNum_pisos());
        spnmaximo.setValue(casa.getCapacidad_maxima());
        spnhabitaciones.setValue(casa.getNum_habitaciones());
        spnbanos.setValue(casa.getNum_banos());
        cboxpicina.setText(casa.getTiene_piscina());
        cboxjardin.setText(casa.getTiene_jardin());
        cboxwifi.setText(casa.getTiene_wifi());
        cboxTV.setText(casa.getTiene_tv());
        cbxcocina.setText(casa.getTiene_cocina());
        cbxnacionalidad_crudcasa.setSelectedItem(casa.getUbicacion());
        txtdetallescasa.setText(casa.getOtros_detalles());

    }

    public boolean validarCampos() {
        Validaciones miValidaciones = new Validaciones();
        boolean ban_confirmar = true;

        if (txtIDCASA.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el id de la casa");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarId(txtIDCASA.getText())) {
            JOptionPane.showMessageDialog(this, "ID incorrecta. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (cbxPropietarios.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Elija la cedula del propietario");
            ban_confirmar = false;

        }
        if (txtnombreCasa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre de la casa");
            ban_confirmar = false;
        } else if (!miValidaciones.ValidarNomApe(txtnombreCasa.getText())) {
            JOptionPane.showMessageDialog(this, "Nombre incorrecta. Ingrese de nuevo");
            ban_confirmar = false;
        }

        if (CboxtipoCarro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Elija un tipo de Vehiculo");
            ban_confirmar = false;
        }

        if (cbxnacionalidad_crudcasa.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Ingrese la ubicacion de la casa");
            ban_confirmar = false;
        } else {
            if (!miValidaciones.ValidarCiudad(cbxnacionalidad_crudcasa.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(this, "Ubicacion inválida");
                ban_confirmar = false;
            }
        }

        return ban_confirmar;

    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) tabla_casaVacacional.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos
        ObjectSet<CasaVacacional> result = BaseD.queryByExample(CasaVacacional.class);
        while (result.hasNext()) {
            CasaVacacional casa = result.next();
            Object[] row = {
                casa.getId_casa(),
                casa.getNombre(),
                casa.getIDPropietario(),
                casa.getCarro(),
                casa.getNum_pisos(),
                casa.getNum_banos(),
                casa.getCapacidad_maxima(),
                casa.getNum_habitaciones(),
                casa.getTiene_piscina(),
                casa.getTiene_wifi(),
                casa.getTiene_cocina(),
                casa.getTiene_jardin(),
                casa.getTiene_tv(),
                casa.getUbicacion(),
                casa.getOtros_detalles()
            };
            model.addRow(row);
        }
    }

    private void filtrarCasaVacacional(String criterio, String valorBusqueda) {
        DefaultTableModel model = (DefaultTableModel) tabla_casaVacacional.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<CasaVacacional> result;
        if (criterio.equals("idCasa")) {
            result = BaseD.queryByExample(new CasaVacacional(valorBusqueda, null, null, null, 0, 0, 0, 0, null, null, null, null, null, null, null));
        } else if (criterio.equals("cedulaPropietario")) {
            result = BaseD.queryByExample(new CasaVacacional(null, valorBusqueda, null, null, 0, 0, 0, 0, null, null, null, null, null, null, null));
        } else if (criterio.equals("nombre")) {
            CasaVacacional filtro = new CasaVacacional(null, null, valorBusqueda, null, 0, 0, 0, 0, null, null, null, null, null, null, null);
            result = BaseD.queryByExample(filtro);
        } else {
            // Criterio inválido, no se realiza la búsqueda
            return;
        }

        while (result.hasNext()) {
            CasaVacacional casa = result.next();
            Object[] row = {
                casa.getId_casa(),
                casa.getIDPropietario(),
                casa.getNombre(),
                casa.getCarro(),
                casa.getNum_pisos(),
                casa.getNum_habitaciones(),
                casa.getNum_banos(),
                casa.getCapacidad_maxima(),
                casa.getTiene_piscina(),
                casa.getTiene_jardin(),
                casa.getTiene_wifi(),
                casa.getTiene_tv(),
                casa.getTiene_cocina(),
                casa.getUbicacion(),
                casa.getOtros_detalles()
            };
            model.addRow(row);
        }
    }

    private void habilitarCamposBusqueda(String criterioSeleccionado) {
        //deshabilitarParametros();
        if (criterioSeleccionado.equals("id_casa")) {
            txtIDCASA.setEnabled(true);
        } else if (criterioSeleccionado.equals("IDPropietario")) {
            cbxPropietarios.setEnabled(true);
        } else if (criterioSeleccionado.equals("nombre")) {
            txtnombreCasa.setEnabled(true);
        }

    }

    private String obtenerValorBusqueda(String criterioSeleccionado) {
        String valorBusqueda = "";
        if (criterioSeleccionado.equals("idCasa")) {
            valorBusqueda = txtIDCASA.getText();
        } else if (criterioSeleccionado.equals("cedulaPropietario")) {
            valorBusqueda = cbxPropietarios.getSelectedItem().toString();
        } else if (criterioSeleccionado.equals("nombre")) {
            valorBusqueda = txtnombreCasa.getText();

        }

        return valorBusqueda;
    }

    private void eliminarCasas() {
        try {
            String idCasa = txtIDCASA.getText().trim();
            Query query = BaseD.query();
            query.constrain(CasaVacacional.class);
            query.descend("id_casa").constrain(idCasa);
            ObjectSet<CasaVacacional> result = query.execute();

            if (!result.isEmpty()) {
                CasaVacacional casa = result.next();

                // Primero, eliminamos la relación con los agentes
                Query queryAgente = BaseD.query();
                queryAgente.constrain(AgenteInmobiliario.class);
                queryAgente.descend("id_casa").constrain(idCasa);
                ObjectSet<AgenteInmobiliario> resultAgentes = queryAgente.execute();
                while (resultAgentes.hasNext()) {
                    AgenteInmobiliario agente = resultAgentes.next();
                    agente.setId_casa(null); // Eliminamos la referencia de la casa en el agente
                    BaseD.store(agente);
                }

                // Verificar si la casa no tiene un propietario asociado
                if (casa.getIDPropietario() == null || casa.getIDPropietario().isEmpty()) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta casa vacacional con la id: " + idCasa + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        BaseD.delete(casa); // Eliminar el objeto de la base de datos
                        JOptionPane.showMessageDialog(null, "Casa vacacional eliminada exitosamente.");
                        limpiarCampos();
                        cargarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No puedes eliminar esta casa, ya que está relacionada con un propietario.\nPrimero elimina el propietario correspondiente.");
                    // No eliminamos la casa, ya que está asociada a un propietario
                    limpiarCampos();
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una casa con el id ingresado.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            e.printStackTrace();
        }
    }

    public void habilitarParametros() {
        txtIDCASA.setEnabled(true);
        cbxPropietarios.setEnabled(true);
        txtnombreCasa.setEnabled(true);
        CboxtipoCarro.setEnabled(true);
        spnpisos.setEnabled(true);
        spnmaximo.setEnabled(true);
        spnhabitaciones.setEnabled(true);
        spnbanos.setEnabled(true);
        cboxpicina.setEnabled(true);
        cboxjardin.setEnabled(true);
        cboxwifi.setEnabled(true);
        cboxTV.setEnabled(true);
        cbxcocina.setEnabled(true);
        cbxnacionalidad_crudcasa.setEnabled(true);
        txtdetallescasa.setEnabled(true);

    }

    public void deshabilitarParametros() {
        txtIDCASA.setEnabled(false);
        cbxPropietarios.setEnabled(false);
        txtnombreCasa.setEnabled(false);
        CboxtipoCarro.setEnabled(false);
        spnpisos.setEnabled(false);
        spnmaximo.setEnabled(false);
        spnhabitaciones.setEnabled(false);
        spnbanos.setEnabled(false);
        cboxpicina.setEnabled(false);
        cboxjardin.setEnabled(false);
        cboxwifi.setEnabled(false);
        cboxTV.setEnabled(false);
        cbxcocina.setEnabled(false);
        cbxnacionalidad_crudcasa.setEnabled(false);
        txtdetallescasa.setEnabled(false);
    }

    private void limpiarCampos() {

        txtIDCASA.setText("");
        //cbxPropietarios.setSelectedIndex(0);
        txtnombreCasa.setText("");
        //CboxtipoCarro.setSelectedIndex(0);
        spnpisos.setValue(1);
        spnmaximo.setValue(1);
        spnhabitaciones.setValue(1);
        spnbanos.setValue(1);
        cboxpicina.setSelected(false);
        cboxjardin.setSelected(false);
        cboxwifi.setSelected(false);
        cboxTV.setSelected(false);
        cbxcocina.setSelected(false);
        cbxnacionalidad_crudcasa.setSelectedIndex(0);
        txtdetallescasa.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBuscarFiltro;
    private javax.swing.JComboBox<String> CboxtipoCarro;
    private javax.swing.JComboBox<String> CbxFiltrarbsuque;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGUARDAR;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnVerPropietarios;
    private javax.swing.JCheckBox cboxTV;
    private javax.swing.JCheckBox cboxjardin;
    private javax.swing.JCheckBox cboxpicina;
    private javax.swing.JCheckBox cboxwifi;
    private javax.swing.JComboBox<String> cbxPropietarios;
    private javax.swing.JCheckBox cbxcocina;
    private javax.swing.JComboBox<String> cbxnacionalidad_crudcasa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spnbanos;
    private javax.swing.JSpinner spnhabitaciones;
    private javax.swing.JSpinner spnmaximo;
    private javax.swing.JSpinner spnpisos;
    private javax.swing.JTable tabla_casaVacacional;
    private javax.swing.JTextField txtIDCASA;
    private javax.swing.JTextArea txtdetallescasa;
    private javax.swing.JTextField txtnombreCasa;
    // End of variables declaration//GEN-END:variables
}
