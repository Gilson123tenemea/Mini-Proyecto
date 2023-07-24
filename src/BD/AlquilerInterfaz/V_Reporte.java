package BD.AlquilerInterfaz;

import BD.AlquilerCasas.Clases.CasaVacacional;
import BD.AlquilerCasas.Clases.Cliente;
import BD.AlquilerCasas.Clases.Factura;
import BD.AlquilerCasas.Clases.Reporte;
import BD.AlquilerCasas.Clases.Reservacion;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class V_Reporte extends javax.swing.JPanel {

    private ObjectContainer BaseD;
    private static int idReporteIncremental = 1; // Variable estática para el ID incremental del reporte

    public V_Reporte(ObjectContainer BaseD) {
        this.BaseD = BaseD;
        initComponents();
        cargarTablaReporte();
    }

    private void cargarTablaReporte() {
        DefaultTableModel model = (DefaultTableModel) tblReporte.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        ObjectSet<Factura> resultFacturas = BaseD.queryByExample(Factura.class);

        // Crear una lista para almacenar los objetos Reporte
        List<Reporte> reportes = new ArrayList<>();

        while (resultFacturas.hasNext()) {
            Factura factura = resultFacturas.next();

            // Obtener el nombre y apellido del cliente
            String nombreCliente = obtenerNombreCliente(factura.getIDCliente());
            String apellidoCliente = obtenerApellidoCliente(factura.getIDCliente());

            // Obtener la reservación asociada a la factura
            Reservacion reservacion = obtenerReservacion(factura.getId_reservacion());

            // Obtener el nombre de la casa vacacional desde la reservación
            String nombreCasa = (reservacion != null) ? reservacion.getId_casa(): "Nombre de Casa Vacacional no encontrado";

            // Crear un objeto Reporte con los datos obtenidos
            Reporte reporte = new Reporte(String.valueOf(idReporteIncremental++), nombreCliente, apellidoCliente, nombreCasa, factura.getTotalPago());
            reportes.add(reporte);
        }

        // Agregar los objetos Reporte a la tabla
        for (Reporte reporte : reportes) {
            Object[] row = {
                reporte.getID_reporte(),
                reporte.getNombrecliente() + " " + reporte.getApellidocliente(),
                reporte.getNombreCasa(),
                reporte.getTotalFactura()
            };
            model.addRow(row);
        }
    }

    private String obtenerNombreCliente(String Cedula) {
        // Lógica para obtener el nombre del cliente a partir del IDCliente
        // Suponiendo que Client es una entidad de la base de datos
        ObjectSet<Cliente> result = BaseD.queryByExample(new Cliente(Cedula, null, null, null, 0, null, null, null, null));
        if (result.hasNext()) {
            Cliente cliente = result.next();
            return cliente.getNombreCliente();
        }
        return "Nombre no encontrado";
    }

    private String obtenerApellidoCliente(String Cedula) {
        // Lógica para obtener el apellido del cliente a partir del IDCliente
        // Suponiendo que Client es una entidad de la base de datos
        ObjectSet<Cliente> result = BaseD.queryByExample(new Cliente(Cedula, null, null, null, 0, null, null, null, null));
        if (result.hasNext()) {
            Cliente cliente = result.next();
            return cliente.getApellidoCliente();
        }
        return "Apellido no encontrado";
    }

    private String obtenerNombreCasaVacacional(String id_casa) {
        // Lógica para obtener el nombre de la casa vacacional a partir del idReservacion
        // Suponiendo que CasaVacacional es una entidad de la base de datos
        ObjectSet<CasaVacacional> result2 = BaseD.queryByExample(new CasaVacacional(id_casa, null, null, null, 0, 0, 0, 0, null, null, null, null, null, null, null));
        if (result2.hasNext()) {
            CasaVacacional casa = result2.next();
            return casa.getNombre();
        }
        return "Nombre de Casa Vacacional no encontrado";
    }
    private Reservacion obtenerReservacion(String idReservacion) {
    ObjectSet<Reservacion> result = BaseD.queryByExample(new Reservacion(idReservacion, null, null, null, null));
    if (result.hasNext()) {
        return result.next();
    }
    return null; // Si no se encontró la Reservacion, retornamos null
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReporte = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dubai", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Reporte Genereal");

        tblReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID REPORTE", "NOMBRE Y APELLIDO CLIENTE", "CASA VACACIONAL", "TOTAL FACTURA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblReporte);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReporte;
    // End of variables declaration//GEN-END:variables
}
