package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Propietario {

    private String CedulaPropietario;
    private String NombrePropietario;
    private String ApellidoPropietario;
    private String GeneroPropietario;
    private int EdadPropietario;
    private String TelfPropietario;
    private String correo_propi;
    private String nacionalidad_propi;
    private Date fecha_Naci;

    public Propietario() {
    }

    public Propietario(String CedulaPropietario, String NombrePropietario, String ApellidoPropietario, String GeneroPropietario, int EdadPropietario, String TelfPropietario, String correo_propi, String nacionalidad_propi, Date fecha_Naci) {
        this.CedulaPropietario = CedulaPropietario;
        this.NombrePropietario = NombrePropietario;
        this.ApellidoPropietario = ApellidoPropietario;
        this.GeneroPropietario = GeneroPropietario;
        this.EdadPropietario = EdadPropietario;
        this.TelfPropietario = TelfPropietario;
        this.correo_propi = correo_propi;
        this.nacionalidad_propi = nacionalidad_propi;
        this.fecha_Naci = fecha_Naci;
    }

    public String getCedulaPropietario() {
        return CedulaPropietario;
    }

    public void setCedulaPropietario(String CedulaPropietario) {
        this.CedulaPropietario = CedulaPropietario;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String NombrePropietario) {
        this.NombrePropietario = NombrePropietario;
    }

    public String getApellidoPropietario() {
        return ApellidoPropietario;
    }

    public void setApellidoPropietario(String ApellidoPropietario) {
        this.ApellidoPropietario = ApellidoPropietario;
    }

    public String getGeneroPropietario() {
        return GeneroPropietario;
    }

    public void setGeneroPropietario(String GeneroPropietario) {
        this.GeneroPropietario = GeneroPropietario;
    }

    public int getEdadPropietario() {
        return EdadPropietario;
    }

    public void setEdadPropietario(int EdadPropietario) {
        this.EdadPropietario = EdadPropietario;
    }

    public String getTelfPropietario() {
        return TelfPropietario;
    }

    public void setTelfPropietario(String TelfPropietario) {
        this.TelfPropietario = TelfPropietario;
    }

    public String getCorreo_propi() {
        return correo_propi;
    }

    public void setCorreo_propi(String correo_propi) {
        this.correo_propi = correo_propi;
    }

    public String getNacionalidad_propi() {
        return nacionalidad_propi;
    }

    public void setNacionalidad_propi(String nacionalidad_propi) {
        this.nacionalidad_propi = nacionalidad_propi;
    }

    public Date getFecha_Naci() {
        return fecha_Naci;
    }

    public void setFecha_Naci(Date fecha_Naci) {
        this.fecha_Naci = fecha_Naci;
    }

    @Override
    public String toString() {
        return "Propietario{" + "CedulaPropietario=" + CedulaPropietario + ", NombrePropietario=" + NombrePropietario + ", ApellidoPropietario=" + ApellidoPropietario + ", GeneroPropietario=" + GeneroPropietario + ", EdadPropietario=" + EdadPropietario + ", TelfPropietario=" + TelfPropietario + ", correo_propi=" + correo_propi + ", nacionalidad_propi=" + nacionalidad_propi + ", fecha_Naci=" + fecha_Naci + '}';
    }

}
