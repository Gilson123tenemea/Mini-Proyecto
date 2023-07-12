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
public class AgenteInmobiliario {

    private String Cedula;
    private String NombreAgente;
    private String ApellidoAgente;
    private char GeneroAgente;
    private int EdadAgente;
    private String correoAgente;
    private String celularAgente;
    private String nacionalidadAgente;
    private Date fecha_NaciAgente;
    private String id_casa;

    public AgenteInmobiliario() {
    }

    public AgenteInmobiliario(String Cedula, String NombreAgente, String ApellidoAgente, char GeneroAgente, int EdadAgente, String correoAgente, String celularAgente, String nacionalidadAgente, Date fecha_NaciAgente, String id_casa) {
        this.Cedula = Cedula;
        this.NombreAgente = NombreAgente;
        this.ApellidoAgente = ApellidoAgente;
        this.GeneroAgente = GeneroAgente;
        this.EdadAgente = EdadAgente;
        this.correoAgente = correoAgente;
        this.celularAgente = celularAgente;
        this.nacionalidadAgente = nacionalidadAgente;
        this.fecha_NaciAgente = fecha_NaciAgente;
        this.id_casa = id_casa;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombreAgente() {
        return NombreAgente;
    }

    public void setNombreAgente(String NombreAgente) {
        this.NombreAgente = NombreAgente;
    }

    public String getApellidoAgente() {
        return ApellidoAgente;
    }

    public void setApellidoAgente(String ApellidoAgente) {
        this.ApellidoAgente = ApellidoAgente;
    }

    public char getGeneroAgente() {
        return GeneroAgente;
    }

    public void setGeneroAgente(char GeneroAgente) {
        this.GeneroAgente = GeneroAgente;
    }

    public int getEdadAgente() {
        return EdadAgente;
    }

    public void setEdadAgente(int EdadAgente) {
        this.EdadAgente = EdadAgente;
    }

    public String getCorreoAgente() {
        return correoAgente;
    }

    public void setCorreoAgente(String correoAgente) {
        this.correoAgente = correoAgente;
    }

    public String getCelularAgente() {
        return celularAgente;
    }

    public void setCelularAgente(String celularAgente) {
        this.celularAgente = celularAgente;
    }

    public String getNacionalidadAgente() {
        return nacionalidadAgente;
    }

    public void setNacionalidadAgente(String nacionalidadAgente) {
        this.nacionalidadAgente = nacionalidadAgente;
    }

    public Date getFecha_NaciAgente() {
        return fecha_NaciAgente;
    }

    public void setFecha_NaciAgente(Date fecha_NaciAgente) {
        this.fecha_NaciAgente = fecha_NaciAgente;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
    }

    @Override
    public String toString() {
        return "AgenteInmobiliario{" + "Cedula=" + Cedula + ", NombreAgente=" + NombreAgente + ", ApellidoAgente=" + ApellidoAgente + ", GeneroAgente=" + GeneroAgente + ", EdadAgente=" + EdadAgente + ", correoAgente=" + correoAgente + ", celularAgente=" + celularAgente + ", nacionalidadAgente=" + nacionalidadAgente + ", fecha_NaciAgente=" + fecha_NaciAgente + ", id_casa=" + id_casa + '}';
    }

    

}
