package sistemregistrocivil.model;

import sistemregistrocivil.model.Archivo;
import sistemregistrocivil.model.Persona;
import java.time.LocalDate;


public abstract class Certificado {
    private String nombreCertificado;
    private String nombrePersona;
    private String rut;
    private Fecha emision;
    
    public Certificado(String nombre, String nombrePer, String rut){
        nombreCertificado = nombre;
        nombrePersona = nombrePer;
        this.rut = rut;
        LocalDate fechaActual = LocalDate.now();
        emision = new Fecha(fechaActual.getDayOfMonth(), fechaActual.getMonthValue(),
        fechaActual.getYear());
    }
    
    public void setEmision(Fecha emi){
        emision = emi;
    }

    public Fecha getEmision() {
        return emision;
    }
    
    public String getNombreCertificado(){
        return nombreCertificado;
    }
    
    public String getNombrePersona(){
        return nombrePersona;
    }
    
    public String getRut(){
        return rut;
    }
    
    public abstract String generarLinea();
}
