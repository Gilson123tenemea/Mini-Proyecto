/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerCasas.Clases;

/**
 *
 * @author USER
 */
public class Vehiculo {
    private String ID_carro;
    private String marca;
    private String modelo;
    private int anio;
    private String tipoVehiculo;
    private String casa;

    public Vehiculo() {
    }

    public Vehiculo(String ID_carro, String marca, String modelo, int anio, String tipoVehiculo, String casa) {
        this.ID_carro = ID_carro;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.tipoVehiculo = tipoVehiculo;
        this.casa = casa;
    }

    

    

    public String getID_carro() {
        return ID_carro;
    }

    public void setID_carro(String ID_carro) {
        this.ID_carro = ID_carro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

   

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    

    @Override
    public String toString() {
        return "Vehiculo{" + "ID_carro=" + ID_carro + ", marca=" + marca + ", modelo=" + modelo + ", anio=" + anio + ", tipoVehiculo=" + tipoVehiculo + ", casa=" + casa + '}';
    }
    
    
}
