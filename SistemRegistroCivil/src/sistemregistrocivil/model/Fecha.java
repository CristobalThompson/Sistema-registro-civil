package sistemregistrocivil.model;


public class Fecha {
    private int dia;
    private int mes;
    private int año;
    
    /**
     * Crea una instancia de Fecha.
     * @param dia parámetro de entrada.
     * @param mes parámetro de entrada.
     * @param año parámetro de entrada.
     */
    public Fecha(int dia, int mes, int año) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }
    
    // Día actual
    /**
     * Obtiene dia.
     * @return valor resultante de la operación.
     */
    public int getDia() {
        return dia;
    }

    // Mes actual
    /**
     * Obtiene mes.
     * @return valor resultante de la operación.
     */
    public int getMes() {
        return mes;
    }

    // Año actual
    /**
     * Obtiene año.
     * @return valor resultante de la operación.
     */
    public int getAño() {
        return año;
    }

    // Cambia el día
    /**
     * Asigna dia.
     * @param nuevoDia parámetro de entrada.
     */
    public void setDia(int nuevoDia) {
        dia = nuevoDia;
    }

    // Cambia el mes
    /**
     * Asigna mes.
     * @param nuevoMes parámetro de entrada.
     */
    public void setMes(int nuevoMes) {
        mes = nuevoMes;
    }

    // Cambia el año
    /**
     * Asigna año.
     * @param nuevoAño parámetro de entrada.
     */
    public void setAño(int nuevoAño) {
        año = nuevoAño;
    }
}