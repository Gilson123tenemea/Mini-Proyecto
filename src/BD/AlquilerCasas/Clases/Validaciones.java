/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD.AlquilerCasas.Clases;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author USER
 */
public class Validaciones {

    public static boolean ValidarCedula(String cadena) {
        cadena = cadena.trim();
        return cadena.matches("[0-9]{10}");
    }

    public boolean validarCedula(String cadena) {
        cadena = cadena.trim();
        boolean ban = false;
        if (cadena.matches("[0-9]{10}")) {
            ban = true;
        }
        return ban;
    }

    public boolean ValidarNomApe(String cadena) {
        cadena = cadena.trim();
        return cadena.matches("[A-Za-z\\s]{3,30}");
    }

    public boolean ValidarId(String cadena) {
        return cadena.matches("[A-Z]{2}-\\d{4}"); // Ejemplo: AS-1234
    }

    public boolean ValidarCiudad(String ciudad) {
        ciudad = ciudad.trim();
        return ciudad.matches("[A-Za-z\\s]*");
    }

    public boolean ValidarCorreo(String mail) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,30})$");
        Matcher matcher = pattern.matcher(mail);
        return matcher.find();
    }

    public boolean ValidarFechaNacimiento(int dia, int mes, int anio) {
        Date fechaActual = new Date();
        int anioActual = fechaActual.getYear() + 1900;

        if (mes >= 1 && mes <= 12) {
            if (mes == 2) {
                if ((anio % 4 == 0 && anio % 100 != 0) || anio % 400 == 0) {
                    return dia >= 1 && dia <= 29;
                } else {
                    return dia >= 1 && dia <= 28;
                }
            } else {
                if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                    return dia >= 1 && dia <= 30;
                } else {
                    return dia >= 1 && dia <= 31;
                }
            }
        }

        return false;
    }

    public boolean ValidarEntero(String numero) {
        numero = numero.replaceAll("\\s", "");
        return numero.matches("[0-9]+");
    }

    public boolean ValidarGenero(String genero) {
        genero = genero.trim();
        return genero.equalsIgnoreCase("H") || genero.equalsIgnoreCase("M");
    }

    public boolean ValidarMarca(String marca) {
        marca = marca.trim();
        return marca.matches("[A-Za-z\\s]{1,50}");
    }

    public boolean ValidarModelo(String modelo) {
        modelo = modelo.trim();
        return modelo.matches("[A-Za-z0-9\\s]{1,50}");
    }

    public boolean ValidarAnio(String anio) {
        anio = anio.trim();
        return anio.matches("\\d{4}");
    }

    public boolean ValidarTipoVehiculo(String tipoVehiculo) {
        tipoVehiculo = tipoVehiculo.trim();
        return tipoVehiculo.matches("[A-Za-z\\s]{1,50}");
    }

    public boolean ValidarCasaVacacional(CasaVacacional casaVacacional) {
        return casaVacacional != null; // Verifica si la casaVacacional es diferente de nulo
    }

    public boolean ValidarContenido(String cadena) {
        return cadena.matches("^[A-Za-z0-9\\s]+$");
    }

    public boolean ValidarPuntuacion(String cadena) {
        return cadena.matches("^(?:100|[0-9]{1,2})$");
    }

    public boolean validarDouble(String numero) {
        numero = numero.replaceAll("\\s", "");
        return numero.matches("[0-9]+(\\.[0-9]+)?");
    }

}
