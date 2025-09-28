package sistemregistrocivil.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */

import sistemregistrocivil.model.Persona;
import sistemregistrocivil.model.Ubicacion;
import sistemregistrocivil.model.Sucursal;
import java.util.*;

public class Archivo {
    private Persona persona;
    private Ubicacion direccion;
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
            return false;
        }
        
        if(this.persona == null || pp.getPersona() == null){ //evitar errores
            return false;
        }
        
        if ( this.esCasado() || pp.esCasado() ) //verificar que no este casado ya alguno
            return false;
        
        
        if (this.persona == pp.getPersona()) return false;
        
        
        
        if (!this.setConyuge(pp) || 
            !pp.setConyuge(this)) //asignar conyuge aqui
            return false;
        
        
        if(!persona.setEstadoCivil("Casado/a") ||
           !(pp.getPersona()).setEstadoCivil("Casado/a") ) //establecer casado
            return false;
        
        
        if(!this.setFechaCasamiento(ff) ||
           !pp.setFechaCasamiento(ff)) //asignamos fecha de casamiento a ambos
            return false;
        
        
        //Se caso correctamente
        return true;
    }
    
    public boolean divorcio(){
        
        Fecha vacia = new Fecha(0,0,0);
        
        
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
    
    public CertificadoNacimiento generarNacimiento(){
        return new 
            CertificadoNacimiento(persona.getNombre(),
            persona.getRut(), persona.getNacimiento());
    }
    
    public CertificadoMatrimonio generarMatrimonio(){
        if (fechaCasamiento  == null || conyuge == null) return null;
        return new CertificadoMatrimonio(persona.getNombre(), persona.getRut(), 
        conyuge.getPersona().getNombre(), conyuge.getPersona().getRut(), fechaCasamiento);
    }
    
    public CertificadoDefusion generarDefusion(){
        if (persona.getDefuncion() == null) return null;
        return new CertificadoDefusion(persona.getNombre(), persona.getRut(), 
                                        persona.getDefuncion());
    }
}