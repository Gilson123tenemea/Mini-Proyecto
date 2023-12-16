package BD.AlquilerCasas.Clases;

import java.util.ArrayList;
import java.util.Date;

public class Cliente {

    private String Cedula;
    private String NombreCliente;
    private String ApellidoCliente;
    private String GeneroCliente;
    private int EdadCliente;
    private String correo;
    private String celular;
    private String nacionalidad;
    private Date fecha_Naci;
    public static ArrayList<String> listaCiudades = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String Cedula, String NombreCliente, String ApellidoCliente, String GeneroCliente, int EdadCliente, String correo, String celular, String nacionalidad, Date fecha_Naci) {
        this.Cedula = Cedula;
        this.NombreCliente = NombreCliente;
        this.ApellidoCliente = ApellidoCliente;
        this.GeneroCliente = GeneroCliente;
        this.EdadCliente = EdadCliente;
        this.correo = correo;
        this.celular = celular;
        this.nacionalidad = nacionalidad;
        this.fecha_Naci = fecha_Naci;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getApellidoCliente() {
        return ApellidoCliente;
    }

    public void setApellidoCliente(String ApellidoCliente) {
        this.ApellidoCliente = ApellidoCliente;
    }

    public String getGeneroCliente() {
        return GeneroCliente;
    }

    public void setGeneroCliente(String GeneroCliente) {
        this.GeneroCliente = GeneroCliente;
    }

    public int getEdadCliente() {
        return EdadCliente;
    }

    public void setEdadCliente(int EdadCliente) {
        this.EdadCliente = EdadCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public static ArrayList<String> getListaCiudades() {
        return listaCiudades;
    }

    public static void setListaCiudades(ArrayList<String> listaCiudades) {
        Cliente.listaCiudades = listaCiudades;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFecha_Naci() {
        return fecha_Naci;
    }

    public void setFecha_Naci(Date fecha_Naci) {
        this.fecha_Naci = fecha_Naci;
    }

    @Override
    public String toString() {
        return "Cliente{" + "Cedula=" + Cedula + ", NombreCliente=" + NombreCliente + ", ApellidoCliente=" + ApellidoCliente + ", GeneroCliente=" + GeneroCliente + ", EdadCliente=" + EdadCliente + ", correo=" + correo + ", celular=" + celular + ", nacionalidad=" + nacionalidad + ", fecha_Naci=" + fecha_Naci + '}';
    }

}
