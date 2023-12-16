package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Comentario {

    private String ID_comentario;
    private String IDCliente;
    private String id_casa;
    private String contenido;
    private int puntuacion;
    private Date fecha_comentario;

    public Comentario() {
    }

    public Comentario(String ID_comentario, String IDCliente, String id_casa, String contenido, int puntuacion, Date fecha_comentario) {
        this.ID_comentario = ID_comentario;
        this.IDCliente = IDCliente;
        this.id_casa = id_casa;
        this.contenido = contenido;
        this.puntuacion = puntuacion;
        this.fecha_comentario = fecha_comentario;
    }

    public String getID_comentario() {
        return ID_comentario;
    }

    public void setID_comentario(String ID_comentario) {
        this.ID_comentario = ID_comentario;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Date getFecha_comentario() {
        return fecha_comentario;
    }

    public void setFecha_comentario(Date fecha_comentario) {
        this.fecha_comentario = fecha_comentario;
    }

    @Override
    public String toString() {
        return "Comentario{" + "ID_comentario=" + ID_comentario + ", IDCliente=" + IDCliente + ", id_casa=" + id_casa + ", contenido=" + contenido + ", puntuacion=" + puntuacion + ", fecha_comentario=" + fecha_comentario + '}';
    }

}
