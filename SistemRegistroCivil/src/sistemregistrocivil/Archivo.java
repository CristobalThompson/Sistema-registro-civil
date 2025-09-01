package sistemregistrocivil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */

import java.util.*;

public class Archivo {
    private Persona persona;
    private Ubicacion direccion;
    private ArrayList<Certificado> certificados = new ArrayList<>();
    private Sucursal sucursal;
    private Fecha fechaCasamiento;
    private Archivo conyuge;
    
    //----------------setters-------------------
    public boolean setFechaCasamiento(Fecha fc){
        if (fc == null) return false;
        
        fechaCasamiento = fc;
        return true;
    }
    
    public boolean setPersona(Persona p){
        if (p == null) return false;
        persona = p;
        return true;
    }
    
    public boolean setUbicacion(Ubicacion direccion){
        if (direccion == null) return false;
        this.direccion = direccion;
        return true;
    }
    
    public boolean setSucursal(Sucursal sucur){
        if(sucur == null) return false;
        sucursal = sucur;
        return true;
    }
    
    public boolean setConyuge(Archivo conyuge){
        if (conyuge == null) return false;
        if (conyuge.getPersona() == null) return false;
        if(conyuge == this) return false;
        this.conyuge = conyuge;
        return true;
    }
    //------------------------------------------
    
    //----------------getters-------------------
    public Fecha getFechaCasamiento(){
        return fechaCasamiento;
    }
    
    public Persona getPersona(){
        return persona;
    }
    
    public Archivo getConyuge(){
        return conyuge;
    }
    
    public Ubicacion getUbicacion(){
        return direccion;
    }
    
    public Sucursal getSucursal(){
        return sucursal;
    }
    //-----------------------------------------
    
    private boolean esCasado(){
        if (persona.getCivil() == null) return false;
        if ((persona.getCivil()).equals("Casado/a")) return true;
        
        return false;
        
    }
    
    public boolean casarse(Archivo pp, Fecha ff){
        
        if (pp == null || ff == null){ //verificar parametros sean utiles
            System.out.println("Parametros invalidos (null)");
            return false;
        }
        
        if(this.persona == null || pp.getPersona() == null){ //evitar errores
            System.out.println("Algun archivo no tiene registro de la persona");
            return false;
        }
        
        if ( this.esCasado() || pp.esCasado() ){ //verificar que no este casado ya alguno
            
            System.out.println("Ya se encuentra casado alguno!");
            return false;
        }
        
        if (this.persona == pp.getPersona()){
            System.out.println("No puedes casarte contigo mismo");
            return false;
        }
        
        
        if (!this.setConyuge(pp) || 
            !pp.setConyuge(this)){ //asignar conyuge aqui
            
            System.out.println("No se pudo asignar conyuge a alguno");
            return false;
        }
        
        if(!persona.setEstadoCivil("Casado/a") ||
           !(pp.getPersona()).setEstadoCivil("Casado/a") ){ //establecer casado
            System.out.println("No se pudo asignar el estado civil a alguno");
            return false;
        }
        
        
        if(!this.setFechaCasamiento(ff) ||
           !pp.setFechaCasamiento(ff)){ //asignamos fecha de casamiento a ambos
            System.out.println("No se pudo asignar fecha de casamiento a alguno");
            return false;
        }
        
        //Se caso correctamente
        return true;
    }
    
    public boolean divorcio(){
        
        Fecha vacia = new Fecha();
        vacia.setDia(0);
        vacia.setMes(0);
        vacia.setAño(0);
        
        Archivo otro = this.conyuge;
        
        if (this.persona != null)
            persona.setEstadoCivil("Soltero/a");
        
        this.setFechaCasamiento(vacia);
        this.conyuge = null;
        
        if (otro != null){
            
            otro.setFechaCasamiento(vacia);
            
            if (conyuge.getPersona() != null)
                (conyuge.getPersona()).setEstadoCivil("Soltero/a");
            if (otro.getConyuge() == this) otro.conyuge = null;
            
        }
        
        return true;
    }
    
    
    public Certificado generarCertificado(byte opcion){
        
        if (opcion != 1 && opcion != 2 && opcion != 3){
            System.out.println("Opcion invalida, retornando null");
            return null;
        }
        
        if (opcion == 1 && !this.esCasado()){
            System.out.println("No puedes generar un certificado de matrimonio");
            System.out.println("sin casarte primero");
            return null;
        }
        
        
        
        Certificado certificado = new Certificado();
        
        certificado.setPersona(this.persona);
        certificado.setConyuge(this.conyuge);
        certificado.setFechaC(this.fechaCasamiento);
        
        if (certificado.crearCertificado(opcion)){
            certificados.add(certificado);
            System.out.println("Certificado generado correctamente");
            return certificado;
        }
        System.out.println("No se pudo generar el certificado");
        return null;
        
    }
    
    public void listarCertificado(){
        if(certificados.isEmpty()){
            System.out.println("No hay certificados para mostrar");
            return;
        }
        
        System.out.println("---------------------------------------------");
        System.out.println("            Listas de certificados");
        System.out.println("---------------------------------------------\n");
        for(int k = 0; k < certificados.size(); ++k){
            Certificado c = certificados.get(k);
            System.out.println("certificado " + (k+1) + ": " + c.getNombre());
        }
    }
}

