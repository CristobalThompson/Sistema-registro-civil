package sistemregistrocivil.model;

import sistemregistrocivil.model.Persona;
import sistemregistrocivil.model.Ubicacion;
import sistemregistrocivil.model.Sucursal;

/**
 * Clase principal del módulo Archivo.
 */
public class Archivo {
    private Persona persona;
    private Ubicacion direccion;
    private Sucursal sucursal;
    private Fecha fechaCasamiento;
    private Archivo conyuge;
    
    //----------------setters-------------------
    /**
     * Asigna fecha casamiento.
     * @param fc parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setFechaCasamiento(Fecha fc){
        if (fc == null) return false;
        
        fechaCasamiento = fc;
        return true;
    }
    
    /**
     * Asigna persona.
     * @param p parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setPersona(Persona p){
        if (p == null) return false;
        persona = p;
        return true;
    }
    
    /**
     * Asigna ubicacion.
     * @param direccion parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setUbicacion(Ubicacion direccion){
        if (direccion == null) return false;
        this.direccion = direccion;
        return true;
    }
    
    /**
     * Asigna sucursal.
     * @param sucur parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setSucursal(Sucursal sucur){
        if(sucur == null) return false;
        sucursal = sucur;
        return true;
    }
    
    /**
     * Asigna conyuge.
     * @param conyuge parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setConyuge(Archivo conyuge){
        if (conyuge == null) return false;
        if (conyuge.getPersona() == null) return false;
        if(conyuge == this) return false;
        this.conyuge = conyuge;
        return true;
    }
    //------------------------------------------
    
    //----------------getters-------------------
    /**
     * Obtiene fecha casamiento.
     * @return valor resultante de la operación.
     */
    public Fecha getFechaCasamiento(){
        return fechaCasamiento;
    }
    
    /**
     * Obtiene persona.
     * @return valor resultante de la operación.
     */
    public Persona getPersona(){
        return persona;
    }
    
    /**
     * Obtiene conyuge.
     * @return valor resultante de la operación.
     */
    public Archivo getConyuge(){
        return conyuge;
    }
    
    /**
     * Obtiene ubicacion.
     * @return valor resultante de la operación.
     */
    public Ubicacion getUbicacion(){
        return direccion;
    }
    
    /**
     * Obtiene sucursal.
     * @return valor resultante de la operación.
     */
    public Sucursal getSucursal(){
        return sucursal;
    }
    //-----------------------------------------
    
    /**
     * Realiza la operación «esCasado».
     * @return valor resultante de la operación.
     */
    private boolean esCasado(){
        if (persona.getCivil() == null) return false;
        if ((persona.getCivil()).equals("Casado/a")) return true;
        
        return false;
        
    }
    
    /**
     * Realiza la operación «casarse».
     * @param pp parámetro de entrada.
     * @param ff parámetro de entrada.
     * @return valor resultante de la operación.
     */
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
    
    /**
     * Realiza la operación «divorcio».
     * @return valor resultante de la operación.
     */
    public boolean divorcio(){
     
        Archivo otro = this.conyuge;
        if (otro == null) return false;


        Persona p1 = this.persona;
        Persona p2 = otro.getPersona();


        this.conyuge = null;
        if (otro.getConyuge() == this) {
            otro.conyuge = null; 
        }


        Fecha vacia = new Fecha(0, 0, 0); 
        this.setFechaCasamiento(vacia);
        otro.setFechaCasamiento(vacia);
       
        if (p1 != null) p1.setEstadoCivil("Divorciado/a"); 
        if (p2 != null) p2.setEstadoCivil("Divorciado/a");

        return true;
    }
    
    /**
     * Realiza la operación «fallecimiento».
     * @param ff parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean fallecimiento(Fecha ff){
        if ("Fallecido/a".equalsIgnoreCase(persona.getCivil())) return false;
        persona.setEstadoCivil("Fallecido/a");
        persona.setFechaDefuncion(ff);
        
        Archivo otro = conyuge;
        if (otro != null){
            Persona p2 = otro.getPersona();
            if (p2 != null) p2.setEstadoCivil("Viudo/a");

            if (otro.getConyuge() == this) {
                otro.conyuge = null;
            }
            this.conyuge = null;
        }
        return true;
    }
    
    /**
     * Realiza la operación «generarNacimiento».
     * @return valor resultante de la operación.
     */
    public CertificadoNacimiento generarNacimiento(){
        return new 
            CertificadoNacimiento(persona.getNombre(),
            persona.getRut(), persona.getNacimiento());
    }
    
     /**
     * Realiza la operación «generarMatrimonio».
     * @return valor resultante de la operación.
     */
    public CertificadoMatrimonio generarMatrimonio(){
        if (fechaCasamiento  == null || conyuge == null) return null;
        return new CertificadoMatrimonio(persona.getNombre(), persona.getRut(), 
        conyuge.getPersona().getNombre(), conyuge.getPersona().getRut(), fechaCasamiento);
    }
    
    /**
     * Realiza la operación «generarDefusion».
     * @return valor resultante de la operación.
     */
    public CertificadoDefusion generarDefusion(){
        if (persona.getDefuncion() == null) return null;
        return new CertificadoDefusion(persona.getNombre(), persona.getRut(), 
                                        persona.getDefuncion());
    }
}