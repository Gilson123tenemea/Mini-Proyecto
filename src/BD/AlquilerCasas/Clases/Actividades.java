/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerCasas.Clases;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */
public class Actividades {
    private String ID_actividades;
    private CasaVacacional casa;
    private String tipoActividad;
    private double costoAdicional;
    private LocalDateTime fechaHora;

    public Actividades() {
    }

    public Actividades(String ID_actividades, CasaVacacional casa, String tipoActividad, double costoAdicional, LocalDateTime fechaHora) {
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

    public CasaVacacional getCasa() {
        return casa;
    }

    public void setCasa(CasaVacacional casa) {
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Actividades{" + "ID_actividades=" + ID_actividades + ", casa=" + casa + ", tipoActividad=" + tipoActividad + ", costoAdicional=" + costoAdicional + ", fechaHora=" + fechaHora + '}';
    }
    
    
}
