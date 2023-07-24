package Reporte;

import javax.swing.JFrame;
import net.sf.jasperreports.swing.JRViewer;

public class ReportFrame extends JFrame {

    public ReportFrame(JRViewer viewer) {
        super("Reporte de reservaciones"); // Establece el título del JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra el JFrame al presionar la "X"
        setSize(800, 600); // Establece el tamaño del JFrame (ajústalo según tus necesidades)
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        getContentPane().add(viewer); // Agrega el JRViewer al contenido del JFrame
    }
}
