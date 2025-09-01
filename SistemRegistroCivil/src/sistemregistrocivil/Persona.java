package sistemregistrocivil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */
public class Persona {
    private String rut = null;
    private String nombre;
    private Fecha fechaNacimiento = null;
    private boolean estadoPersona = true;
    private String estadoCivil;
    private Fecha defuncion = null;
    
    public boolean setRut(String rut){
        if (this.rut != null ||rut == null) return false; //rut no se cambia
        this.rut = rut;
        return true;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public boolean setFechaN(Fecha fecha){
        if (fechaNacimiento != null || fecha == null) return false; //nacimiento no se cambia
        fechaNacimiento = fecha;
        return true;
    }
    
    public void setEstado(boolean estado){
        estadoPersona = estado;
    }
    
    public void setEstadoCivil(String estado){
        estadoCivil = estado;
    }
    
    public boolean setFechaDefuncion(Fecha fd){
        if (fd == null || defuncion != null) return false; //fallecimiento no se cambia
        defuncion = fd;
        estadoPersona = false;
        return true;
    }
    
    public String getRut(){
        return rut;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public Fecha getNacimiento(){
        return fechaNacimiento;
    }
    
    public boolean getEstado(){
    return estadoPersona;
    }
    
    public String getCivil(){
        return estadoCivil;
    }
    
    public Fecha getDefuncion(){
        return defuncion; 
 
    }
    
    public void mostrarDatos(){
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("\nDatos de la persona: ");
        System.out.println("- NOMBRE: " + (nombre != null ? nombre : "No declarado."));
        System.out.println("- RUT: " + (rut != null ? rut : "No declarado."));
        
        if (fechaNacimiento != null){
            System.out.println("- FECHA DE NACIMIENTO: " + 
                fechaNacimiento.getDia() + "/" + fechaNacimiento.getMes() + "/"
                + fechaNacimiento.getAño());
        }
        else{
            System.out.println("- FECHA DE NACIMIENTO: No declarado.");
        }
        if(!estadoPersona && defuncion != null){
        
            System.out.println("- FECHA DE FALLECIMIENTO: " + defuncion.getDia()
                + "/" + defuncion.getMes() + "/" + defuncion.getAño());
        }
        
        System.out.println("- ESTADO CIVIL: " + (estadoCivil != null ? estadoCivil : "No declarado."));
        System.out.println("\n---------------------------------------------------------------");
    }
}
