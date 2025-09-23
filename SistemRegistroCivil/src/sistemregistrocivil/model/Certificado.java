package sistemregistrocivil.model;

import sistemregistrocivil.model.Archivo;
import sistemregistrocivil.model.Persona;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */
public class Certificado {
    private Persona persona;
    private Archivo conyuge;
    private Fecha fechaCasamiento;
    private byte tipoCertificado;
    private String nombreCertificado;
    
    public boolean setPersona(Persona p){
        if (p == null) return false;
        persona = p;
        return true;
    }
    
    public boolean setFechaC(Fecha fc){
        if(fc == null) return false;
        fechaCasamiento = fc;
        return true;
    }
    
    public boolean setConyuge(Archivo c){
        if (c == null) return false;
        
        conyuge = c;
        return true;
    }
    
    public String getNombreCertificado(){
        return nombreCertificado;
    }
    
    public boolean crearCertificado(byte opcion){
        tipoCertificado = opcion;
        if (opcion == 1)
            nombreCertificado = "Certificado de matrimonio";
        else if (opcion == 2)
            nombreCertificado = "Certificado de nacimiento";
        else nombreCertificado = "Certificado de difusión";
        return true;
    }
    
    public void mostrarCertificado(){
        if (tipoCertificado == 1) mostrarMatrimonio();
        else if (tipoCertificado == 2) mostrarNacimiento();
        else mostrarDifusion();
    }
    
    private void mostrarMatrimonio(){
        System.out.println("matrimonio"); //pendiente
    }
    
    private void mostrarNacimiento(){
        System.out.println("nacimiento"); //pendiente
    }
    
    private void mostrarDifusion(){
        System.out.println("Difusión"); //pendiente
    }
    
}
