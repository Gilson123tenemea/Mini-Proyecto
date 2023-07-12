/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerCasas.Clases;

import java.util.Date;

/**
 *
 * @author USER
 */
public class CalendarioDisponibilidad {
    private String ID_calendario;
    private String id_casa;
    private Date fechas_disponibles;

    public CalendarioDisponibilidad() {
    }

    public CalendarioDisponibilidad(String ID_calendario, String id_casa, Date fechas_disponibles) {
        this.ID_calendario = ID_calendario;
        this.id_casa = id_casa;
        this.fechas_disponibles = fechas_disponibles;
    }

    public String getID_calendario() {
        return ID_calendario;
    }

    public void setID_calendario(String ID_calendario) {
        this.ID_calendario = ID_calendario;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
    }

    public Date getFechas_disponibles() {
        return fechas_disponibles;
    }

    public void setFechas_disponibles(Date fechas_disponibles) {
        this.fechas_disponibles = fechas_disponibles;
    }

    @Override
    public String toString() {
        return "CalendarioDisponibilidad{" + "ID_calendario=" + ID_calendario + ", id_casa=" + id_casa + ", fechas_disponibles=" + fechas_disponibles + '}';
    }
    
    
}
