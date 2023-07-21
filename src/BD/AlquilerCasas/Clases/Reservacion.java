package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Reservacion {

    private String id_reservacion;
    private String id_casa;
    private String IDCliente;
    private Date fecha_inicio;
    private Date fecha_fin;

    public Reservacion() {
    }

    public Reservacion(String id_reservacion, String id_casa, String IDCliente, Date fecha_inicio, Date fecha_fin) {
        this.id_reservacion = id_reservacion;
        this.id_casa = id_casa;
        this.IDCliente = IDCliente;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public String getId_reservacion() {
        return id_reservacion;
    }

    public void setId_reservacion(String id_reservacion) {
        this.id_reservacion = id_reservacion;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(String IDCliente) {
        this.IDCliente = IDCliente;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Reservacion{" + "id_reservacion=" + id_reservacion + ", id_casa=" + id_casa + ", IDCliente=" + IDCliente + ", fecha_inicio=" + fecha_inicio + ", fecha_fin=" + fecha_fin + '}';
    }

}
