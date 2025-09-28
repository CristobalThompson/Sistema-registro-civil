package sistemregistrocivil.model;


/**
 * Representa a una persona dentro del sistema.
 * Contiene datos y operaciones asociadas al modelo.
 */
public class Persona {
    private String rut = null;
    private String nombre;
    private Fecha fechaNacimiento = null;
    private String estadoCivil;
    private Fecha defuncion = null;
    
    /**
    * @param rut parámetro de entrada.
    * @param nombre parámetro de entrada.
    * @param nac parámetro de entrada.
    */
    public Persona(String rut, String nombre, Fecha nac){
        this.rut = rut;
        this.nombre = nombre;
        fechaNacimiento = nac;
        estadoCivil = "Soltero/a";
    }
    
    /**
    
     * Asigna rut.
     * @param rut parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setRut(String rut){
        if (this.rut != null ||rut == null) return false; //rut no se cambia
        this.rut = rut;
        return true;
    }
    
    /**
     * Asigna nombre.
     * @param nombre parámetro de entrada.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    /**
     * Asigna fecha n.
     * @param fecha parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setFechaN(Fecha fecha){
        if (fechaNacimiento != null || fecha == null) return false; //nacimiento no se cambia
        fechaNacimiento = fecha;
        return true;
    }
    
    /**
     * Asigna estado civil.
     * @param estado parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setEstadoCivil(String estado){
        if (estado == null) return false;
        estadoCivil = estado;
        return true;
    }
    
    /**
     * Asigna fecha defuncion.
     * @param fd parámetro de entrada.
     * @return valor resultante de la operación.
     */
    public boolean setFechaDefuncion(Fecha fd){
        if (fd == null || defuncion != null) return false; //fallecimiento no se cambia
        defuncion = fd;
        return true;
    }
    
    /**
     * Obtiene rut.
     * @return valor resultante de la operación.
     */
    public String getRut(){
        return rut;
    }
    
    /**
     * Obtiene nombre.
     * @return valor resultante de la operación.
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Obtiene nacimiento.
     * @return valor resultante de la operación.
     */
    public Fecha getNacimiento(){
        return fechaNacimiento;
    }
    
    /**
     * Obtiene civil.
     * @return valor resultante de la operación.
     */
    public String getCivil(){
        return estadoCivil;
    }
    
    /**
     * Obtiene defuncion.
     * @return valor resultante de la operación.
     */
    public Fecha getDefuncion(){
        return defuncion; 
    }
}
