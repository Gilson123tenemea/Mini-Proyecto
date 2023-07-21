package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Promocion {

    private String ID_promocion;
    private String casa;
    private int descuento;
    private Date fecha_inicio_desc;
    private Date fecha_fin_desc;

    public Promocion() {
    }

    public Promocion(String ID_promocion, String casa, int descuento, Date fecha_inicio_desc, Date fecha_fin_desc) {
        this.ID_promocion = ID_promocion;
        this.casa = casa;
        this.descuento = descuento;
        this.fecha_inicio_desc = fecha_inicio_desc;
        this.fecha_fin_desc = fecha_fin_desc;
    }

    public String getID_promocion() {
        return ID_promocion;
    }

    public void setID_promocion(String ID_promocion) {
        this.ID_promocion = ID_promocion;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Date getFecha_inicio_desc() {
        return fecha_inicio_desc;
    }

    public void setFecha_inicio_desc(Date fecha_inicio_desc) {
        this.fecha_inicio_desc = fecha_inicio_desc;
    }

    public Date getFecha_fin_desc() {
        return fecha_fin_desc;
    }

    public void setFecha_fin_desc(Date fecha_fin_desc) {
        this.fecha_fin_desc = fecha_fin_desc;
    }

    @Override
    public String toString() {
        return "Promocion{" + "ID_promocion=" + ID_promocion + ", casa=" + casa + ", descuento=" + descuento + ", fecha_inicio_desc=" + fecha_inicio_desc + ", fecha_fin_desc=" + fecha_fin_desc + '}';
    }

}
