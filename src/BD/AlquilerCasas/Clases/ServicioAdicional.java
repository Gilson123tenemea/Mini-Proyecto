package BD.AlquilerCasas.Clases;

public class ServicioAdicional {
    private String ID_servicio;
    private String id_casa;
    private String nombre;
    private String descripcionSer;
    private double costoAdicional;

    public ServicioAdicional() {
    }

    public ServicioAdicional(String ID_servicio, String id_casa, String nombre, String descripcionSer, double costoAdicional) {
        this.ID_servicio = ID_servicio;
        this.id_casa = id_casa;
        this.nombre = nombre;
        this.descripcionSer = descripcionSer;
        this.costoAdicional = costoAdicional;
    }

    public String getID_servicio() {
        return ID_servicio;
    }

    public void setID_servicio(String ID_servicio) {
        this.ID_servicio = ID_servicio;
    }

    public String getId_casa() {
        return id_casa;
    }

    public void setId_casa(String id_casa) {
        this.id_casa = id_casa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionSer() {
        return descripcionSer;
    }

    public void setDescripcionSer(String descripcionSer) {
        this.descripcionSer = descripcionSer;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{" + "ID_servicio=" + ID_servicio + ", id_casa=" + id_casa + ", nombre=" + nombre + ", descripcionSer=" + descripcionSer + ", costoAdicional=" + costoAdicional + '}';
    }
    
    
}
