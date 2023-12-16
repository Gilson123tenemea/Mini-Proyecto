package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Factura {

    private String ID_factura;
    private String IDCliente;
    private String id_reservacion;
    private Date fecha_emision;
    private double totalPago;

    public Factura() {
    }

    public Factura(String ID_factura, String IDCliente, String id_reservacion, Date fecha_emision, double totalPago) {
        this.ID_factura = ID_factura;
        this.IDCliente = IDCliente;
        this.id_reservacion = id_reservacion;
        this.fecha_emision = fecha_emision;
        this.totalPago = totalPago;
    }

    public String getID_factura() {
        return ID_factura;
    }

    public void setID_factura(String ID_factura) {
        this.ID_factura = ID_factura;
    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(String IDCliente) {
        this.IDCliente = IDCliente;
    }

    public String getId_reservacion() {
        return id_reservacion;
    }

    public void setId_reservacion(String id_reservacion) {
        this.id_reservacion = id_reservacion;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    @Override
    public String toString() {
        return "Factura{" + "ID_factura=" + ID_factura + ", IDCliente=" + IDCliente + ", id_reservacion=" + id_reservacion + ", fecha_emision=" + fecha_emision + ", totalPago=" + totalPago + '}';
    }

}
