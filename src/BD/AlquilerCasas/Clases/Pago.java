package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Pago {

    private String ID_pago;
    private String id_reservacion;
    private double monto;
    private Date fecha_pago;
    private String estado_pago;

    public Pago() {
    }

    public Pago(String ID_pago, String id_reservacion, double monto, Date fecha_pago, String estado_pago) {
        this.ID_pago = ID_pago;
        this.id_reservacion = id_reservacion;
        this.monto = monto;
        this.fecha_pago = fecha_pago;
        this.estado_pago = estado_pago;
    }

    public String getID_pago() {
        return ID_pago;
    }

    public void setID_pago(String ID_pago) {
        this.ID_pago = ID_pago;
    }

    public String getId_reservacion() {
        return id_reservacion;
    }

    public void setId_reservacion(String id_reservacion) {
        this.id_reservacion = id_reservacion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    @Override
    public String toString() {
        return "Pago{" + "ID_pago=" + ID_pago + ", id_reservacion=" + id_reservacion + ", monto=" + monto + ", fecha_pago=" + fecha_pago + ", estado_pago=" + estado_pago + '}';
    }

}
