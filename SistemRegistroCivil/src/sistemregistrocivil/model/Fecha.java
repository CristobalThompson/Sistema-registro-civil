package sistemregistrocivil.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */
public class Fecha {
    private int dia;
    private int mes;
    private int año;

    public Fecha(int dia, int mes, int año) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }
    
    // Día actual
    public int getDia() {
        return dia;
    }

    // Mes actual
    public int getMes() {
        return mes;
    }

    // Año actual
    public int getAño() {
        return año;
    }

    // Cambia el día
    public void setDia(int nuevoDia) {
        dia = nuevoDia;
    }

    // Cambia el mes
    public void setMes(int nuevoMes) {
        mes = nuevoMes;
    }

    // Cambia el año
    public void setAño(int nuevoAño) {
        año = nuevoAño;
    }
}