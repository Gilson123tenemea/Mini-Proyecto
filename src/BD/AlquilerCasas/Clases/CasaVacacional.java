package BD.AlquilerCasas.Clases;

import static BD.AlquilerCasas.Clases.Cliente.listaCiudades;
import java.util.ArrayList;

public class CasaVacacional {

    private String id_casa;
    private String IDPropietario;
    private String nombre;
    private String carro;
    private int num_pisos;
    private int capacidad_maxima;
    private int num_habitaciones;
    private int num_banos;
    private String tiene_piscina;
    private String tiene_jardin;
    private String tiene_wifi;
    private String tiene_tv;
    private String tiene_cocina;
    private String ubicacion;
    private String otros_detalles;
    public static ArrayList<String> listaUbicacion = new ArrayList<>();

    public CasaVacacional() {
    }

    public CasaVacacional(String id_casa, String IDPropietario, String nombre, String carro, int num_pisos, int capacidad_maxima, int num_habitaciones, int num_banos, String tiene_piscina, String tiene_jardin, String tiene_wifi, String tiene_tv, String tiene_cocina, String ubicacion, String otros_detalles) {
        this.id_casa = id_casa;
        this.IDPropietario = IDPropietario;
        this.nombre = nombre;
        this.carro = carro;
        this.num_pisos = num_pisos;
        this.capacidad_maxima = capacidad_maxima;
        this.num_habitaciones = num_habitaciones;
        this.num_banos = num_banos;
        this.tiene_piscina = tiene_piscina;
        this.tiene_jardin = tiene_jardin;
        this.tiene_wifi = tiene_wifi;
        this.tiene_tv = tiene_tv;
        this.tiene_cocina = tiene_cocina;
        this.ubicacion = ubicacion;
        this.otros_detalles = otros_detalles;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
    }

    public String getIDPropietario() {
        return IDPropietario;
    }

    public void setIDPropietario(String IDPropietario) {
        this.IDPropietario = IDPropietario;
    }

    public String getCarro() {
        return carro;
    }

    public void setCarro(String carro) {
        this.carro = carro;
    }

    public int getNum_pisos() {
        return num_pisos;
    }

    public void setNum_pisos(int num_pisos) {
        this.num_pisos = num_pisos;
    }

    public int getCapacidad_maxima() {
        return capacidad_maxima;
    }

    public void setCapacidad_maxima(int capacidad_maxima) {
        this.capacidad_maxima = capacidad_maxima;
    }

    public int getNum_habitaciones() {
        return num_habitaciones;
    }

    public void setNum_habitaciones(int num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
    }

    public int getNum_banos() {
        return num_banos;
    }

    public void setNum_banos(int num_banos) {
        this.num_banos = num_banos;
    }

    public String getTiene_piscina() {
        return tiene_piscina;
    }

    public void setTiene_piscina(String tiene_piscina) {
        this.tiene_piscina = tiene_piscina;
    }

    public String getTiene_jardin() {
        return tiene_jardin;
    }

    public void setTiene_jardin(String tiene_jardin) {
        this.tiene_jardin = tiene_jardin;
    }

    public String getTiene_wifi() {
        return tiene_wifi;
    }

    public void setTiene_wifi(String tiene_wifi) {
        this.tiene_wifi = tiene_wifi;
    }

    public String getTiene_tv() {
        return tiene_tv;
    }

    public void setTiene_tv(String tiene_tv) {
        this.tiene_tv = tiene_tv;
    }

    public String getTiene_cocina() {
        return tiene_cocina;
    }

    public void setTiene_cocina(String tiene_cocina) {
        this.tiene_cocina = tiene_cocina;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getOtros_detalles() {
        return otros_detalles;
    }

    public void setOtros_detalles(String otros_detalles) {
        this.otros_detalles = otros_detalles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CasaVacacional{" + "id_casa=" + id_casa + ", IDPropietario=" + IDPropietario + ", nombre=" + nombre + ", carro=" + carro + ", num_pisos=" + num_pisos + ", capacidad_maxima=" + capacidad_maxima + ", num_habitaciones=" + num_habitaciones + ", num_banos=" + num_banos + ", tiene_piscina=" + tiene_piscina + ", tiene_jardin=" + tiene_jardin + ", tiene_wifi=" + tiene_wifi + ", tiene_tv=" + tiene_tv + ", tiene_cocina=" + tiene_cocina + ", ubicacion=" + ubicacion + ", otros_detalles=" + otros_detalles + '}';
    }
}
