package sistemregistrocivil.model;

import java.time.LocalDate;


/**
 * Clase base de certificados dentro del sistema.
 */
public abstract class Certificado {
    private String nombreCertificado;
    private String nombrePersona;
    private String rut;
    private Fecha emision;
    
    /**
     * Crea una instancia de Certificado.
     * @param nombre parámetro de entrada.
     * @param nombrePer parámetro de entrada.
     * @param rut parámetro de entrada.
     */
    public Certificado(String nombre, String nombrePer, String rut){
        nombreCertificado = nombre;
        nombrePersona = nombrePer;
        this.rut = rut;
        LocalDate fechaActual = LocalDate.now();
        emision = new Fecha(fechaActual.getDayOfMonth(), fechaActual.getMonthValue(),
        fechaActual.getYear());
    }
    
    /**
     * Asigna emision.
     * @param emi parámetro de entrada.
     */
    public void setEmision(Fecha emi){
        emision = emi;
    }

    /**
     * Obtiene emision.
     * @return valor resultante de la operación.
     */
    public Fecha getEmision() {
        return emision;
    }
    
    /** 
     * Obtiene nombre certificado.
     * @return valor resultante de la operación.
     */
    public String getNombreCertificado(){
        return nombreCertificado;
    }
    
    /**
     * Obtiene nombre persona.
     * @return valor resultante de la operación.
     */
    public String getNombrePersona(){
        return nombrePersona;
    }
    
    /**
     * Obtiene rut.
     * @return valor resultante de la operación.
     */
    public String getRut(){
        return rut;
    }
    
    /**
     * genera la linea del certificado correspondiente para guardar en csv.
     * @return valor resultante de la operación.
     */
    public abstract String generarLinea();
}
