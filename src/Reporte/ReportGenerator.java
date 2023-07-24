package Reporte;

import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class ReportGenerator {

    public static JasperPrint generateReport(Object[][] data) {
        try {
            // Cargar el archivo .jasper desde el paquete Reporte
            String reportPath = "/Reporte/sistema.jasper";
            JasperPrint jasperPrint = JasperFillManager.fillReport(ReportGenerator.class.getResourceAsStream(reportPath), null, new FormatoReporte(data));

            // Retornar el JasperPrint que contiene los datos del reporte
            return jasperPrint;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
