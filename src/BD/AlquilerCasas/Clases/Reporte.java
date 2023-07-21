package BD.AlquilerCasas.Clases;

import java.util.Date;

public class Reporte {
    private String ID_reporte;
    private Date fechaGeneracion;
    private Date fechaInicio;
    private Date fechaFin;
    private Propietario propietario;
    private Cliente cliente;
    private CasaVacacional casa;
    private double precioAlquiler;
    private double pagosRealizados;
    private double saldoPendiente;
    private String estadoCasa;
    private String comentarios;

    public Reporte() {
    }

    public Reporte(String ID_reporte, Date fechaGeneracion, Date fechaInicio, Date fechaFin, Propietario propietario, Cliente cliente, CasaVacacional casa, double precioAlquiler, double pagosRealizados, double saldoPendiente, String estadoCasa, String comentarios) {
        this.ID_reporte = ID_reporte;
        this.fechaGeneracion = fechaGeneracion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.propietario = propietario;
        this.cliente = cliente;
        this.casa = casa;
        this.precioAlquiler = precioAlquiler;
        this.pagosRealizados = pagosRealizados;
        this.saldoPendiente = saldoPendiente;
        this.estadoCasa = estadoCasa;
        this.comentarios = comentarios;
    }

    public String getID_reporte() {
        return ID_reporte;
    }

    public void setID_reporte(String ID_reporte) {
        this.ID_reporte = ID_reporte;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public CasaVacacional getCasa() {
        return casa;
    }

    public void setCasa(CasaVacacional casa) {
        this.casa = casa;
    }

    public double getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(double precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }

    public double getPagosRealizados() {
        return pagosRealizados;
    }

    public void setPagosRealizados(double pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstadoCasa() {
        return estadoCasa;
    }

    public void setEstadoCasa(String estadoCasa) {
        this.estadoCasa = estadoCasa;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Reporte{" + "ID_reporte=" + ID_reporte + ", fechaGeneracion=" + fechaGeneracion + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", propietario=" + propietario + ", cliente=" + cliente + ", casa=" + casa + ", precioAlquiler=" + precioAlquiler + ", pagosRealizados=" + pagosRealizados + ", saldoPendiente=" + saldoPendiente + ", estadoCasa=" + estadoCasa + ", comentarios=" + comentarios + '}';
    }
    
    
}
