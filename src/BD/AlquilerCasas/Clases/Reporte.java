package BD.AlquilerCasas.Clases;


public class Reporte {

    private String ID_reporte;
    private String nombrecliente;
    private String Apellidocliente;
    private String nombreCasa;
    private double totalFactura;

    public Reporte() {
    }

    public Reporte(String ID_reporte, String nombrecliente, String Apellidocliente, String nombreCasa, double totalFactura) {
        this.ID_reporte = ID_reporte;
        this.nombrecliente = nombrecliente;
        this.Apellidocliente = Apellidocliente;
        this.nombreCasa = nombreCasa;
        this.totalFactura = totalFactura;
    }

    public String getID_reporte() {
        return ID_reporte;
    }

    public void setID_reporte(String ID_reporte) {
        this.ID_reporte = ID_reporte;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getApellidocliente() {
        return Apellidocliente;
    }

    public void setApellidocliente(String Apellidocliente) {
        this.Apellidocliente = Apellidocliente;
    }

    public String getNombreCasa() {
        return nombreCasa;
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    @Override
    public String toString() {
        return "Reporte{" + "ID_reporte=" + ID_reporte + ", nombrecliente=" + nombrecliente + ", Apellidocliente=" + Apellidocliente + ", nombreCasa=" + nombreCasa + ", totalFactura=" + totalFactura + '}';
    }

}
