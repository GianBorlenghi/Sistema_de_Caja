/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fecha {

    private int anio;
    private int dia;
    private int mes;
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public String fechaHoy = formatter.format(date);
    Calendar cal = Calendar.getInstance();

    public Fecha() {
    }

    public Fecha(int anio, int dia, int mes) {
        setAnio(anio);
        setDia(dia);
        setMes(mes);
    }

    public int getAnio() {
        return anio;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public boolean validadFecha() throws ParseException {
        boolean validoFecha = true;
        cal.setTime(formatter.parse(fechaHoy));

        if (dia < 1 || dia > 31 || mes < 1 || mes > 12 || anio < cal.get(Calendar.YEAR)) {
            validoFecha = false;
            return validoFecha;
        }

        return validoFecha;
    }
}
