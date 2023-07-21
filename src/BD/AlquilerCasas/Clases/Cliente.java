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

    public void cargarCiudad() {
        listaCiudades.add("Guayaquil"
                + "Quito"
                + "Cuenca"
                + "Santo Domingo"
                + "Machala"
                + "Durán"
                + "Manta"
                + "Portoviejo"
                + "Loja"
                + "Ambato"
                + "Esmeraldas"
                + "Quevedo\n"
                + "Riobamba\n"
                + "Milagro\n"
                + "Ibarra\n"
                + "La Libertad\n"
                + "Babahoyo\n"
                + "Sangolquí\n"
                + "Daule\n"
                + "Latacunga\n"
                + "Tulcán\n"
                + "Chone\n"
                + "Pasaje\n"
                + "Santa Rosa\n"
                + "Nueva Loja\n"
                + "Huaquillas\n"
                + "El Carmen\n"
                + "Montecristi\n"
                + "Samborondón\n"
                + "Puerto Francisco de Orellana\n"
                + "Jipijapa\n"
                + "Santa Elena\n"
                + "Otavalo\n"
                + "Cayambe\n"
                + "Buena Fe\n"
                + "Ventanas\n"
                + "Velasco Ibarra\n"
                + "La Troncal\n"
                + "El Triunfo\n"
                + "Salinas\n"
                + "General Villamil\n"
                + "Azogues\n"
                + "Puyo\n"
                + "Vinces\n"
                + "La Concordia\n"
                + "Rosa Zárate\n"
                + "Balzar\n"
                + "Naranjal\n"
                + "Guaranda\n"
                + "La Maná\n"
                + "Tena\n"
                + "San Lorenzo\n"
                + "Catamayo\n"
                + "El Guabo\n"
                + "Pedernales\n"
                + "Atuntaqui\n"
                + "Bahía de Caráquez\n"
                + "Pedro Carbo\n"
                + "Macas\n"
                + "Yaguachi\n"
                + "Calceta\n"
                + "Arenillas\n"
                + "Jaramijó\n"
                + "Valencia\n"
                + "Machachi\n"
                + "Shushufindi\n"
                + "Atacames\n"
                + "Piñas\n"
                + "San Gabriel\n"
                + "Gualaceo\n"
                + "Cañar\n"
                + "Cariamanga\n"
                + "Baños de Agua Santa\n"
                + "Montalvo\n"
                + "Macará\n"
                + "San Miguel de Salcedo\n"
                + "Zamora\n"
                + "Puerto Ayora\n"
                + "La Joya de los Sachas\n"
                + "Tosagua\n"
                + "Pelileo\n"
                + "Puerto López\n"
                + "San Vicente\n"
                + "Santa Ana\n"
                + "Zaruma\n"
                + "Rocafuerte\n"
                + "Cotacachi\n"
                + "Santa Lucía\n"
                + "Puebloviejo\n"
                + "Portovelo\n"
                + "Sucúa\n"
                + "Simón Bolívar\n"
                + "Gualaquiza\n"
                + "Paute\n"
                + "San Miguel\n"
                + "Puerto Baquerizo Moreno\n"
                + "Catacocha\n"
                + "Palenque\n"
                + "Alausí\n"
                + "Santa Isabel\n"
                + "Biblián\n"
                + "Valdez (Limones)\n"
                + "El Tambo\n"
                + "Quinsaloma\n"
                + "El Ángel\n"
                + "Chordeleg\n"
                + "Saraguro\n"
                + "Girón\n"
                + "Pichincha\n"
                + "Sigsig\n"
                + "Loreto\n"
                + "Rioverde\n"
                + "Zumba\n"
                + "Bolívar\n"
                + "Sucre\n"
                + "Guamote\n"
                + "Cevallos\n"
                + "San Fernando\n"
                + "Santa Clara\n"
                + "Nabón\n"
                + "La Victoria\n"
                + "Guachapala\n"
                + "Santiago\n"
                + "Chaguarpamba\n"
                + "Pucará\n"
                + "Oña\n"
                + "Sevilla de Oro\n"
                + "Olmedo\n"
                + "Déleg\n"
                + "El Pan");

    }
}
