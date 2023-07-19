/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerCasas.Clases;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author USER
 */
public class Actividades {
    private String ID_actividades;
    private String casa;
    private String tipoActividad;
    private double costoAdicional;
    private Date fechaHora;

    public Actividades() {
    }

    public Actividades(String ID_actividades, String casa, String tipoActividad, double costoAdicional, Date fechaHora) {
        this.ID_actividades = ID_actividades;
        this.casa = casa;
        this.tipoActividad = tipoActividad;
        this.costoAdicional = costoAdicional;
        this.fechaHora = fechaHora;
    }

    

    public String getID_actividades() {
        return ID_actividades;
    }

    public void setID_actividades(String ID_actividades) {
        this.ID_actividades = ID_actividades;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

   

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    

    @Override
    public String toString() {
        return "Actividades{" + "ID_actividades=" + ID_actividades + ", casa=" + casa + ", tipoActividad=" + tipoActividad + ", costoAdicional=" + costoAdicional + ", fechaHora=" + fechaHora + '}';
    }
    
    
}
