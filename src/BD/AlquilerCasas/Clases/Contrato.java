package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Contrato {
    private String ID_contrato;
    private String IDCliente;
    private String id_casa;
    private Date  fecha_inicio;
    private Date fecha_fin;
    private String terminosCondiciones;

    public Contrato() {
    }

    public Contrato(String ID_contrato, String IDCliente, String id_casa, Date fecha_inicio, Date fecha_fin, String terminosCondiciones) {
        this.ID_contrato = ID_contrato;
        this.IDCliente = IDCliente;
        this.id_casa = id_casa;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.terminosCondiciones = terminosCondiciones;
    }

    public String getID_contrato() {
        return ID_contrato;
    }

    public void setID_contrato(String ID_contrato) {
        this.ID_contrato = ID_contrato;
    }

    public String getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(String IDCliente) {
        this.IDCliente = IDCliente;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
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

    public String getTerminosCondiciones() {
        return terminosCondiciones;
    }

    public void setTerminosCondiciones(String terminosCondiciones) {
        this.terminosCondiciones = terminosCondiciones;
    }

    @Override
    public String toString() {
        return "Contrato{" + "ID_contrato=" + ID_contrato + ", IDCliente=" + IDCliente + ", id_casa=" + id_casa + ", fecha_inicio=" + fecha_inicio + ", fecha_fin=" + fecha_fin + ", terminosCondiciones=" + terminosCondiciones + '}';
    }
    
    
}
