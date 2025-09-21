package sistemregistrocivil;

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
    int getDia() {
        return dia;
    }

    // Mes actual
    int getMes() {
        return mes;
    }

    // Año actual
    int getAño() {
        return año;
    }

    // Cambia el día
    void setDia(int nuevoDia) {
        dia = nuevoDia;
    }

    // Cambia el mes
    void setMes(int nuevoMes) {
        mes = nuevoMes;
    }

    // Cambia el año
    void setAño(int nuevoAño) {
        año = nuevoAño;
    }
}