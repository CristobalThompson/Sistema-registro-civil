package sistemregistrocivil.model;

import sistemregistrocivil.model.Archivo;
import sistemregistrocivil.model.Persona;
import sistemregistrocivil.model.Sucursal;
import java.util.*;

/**
 * Gestor principal del sistema de Registro Civil.
 * Mantiene el estado y provee operaciones de negocio expuestas por la clase.
 */
public class RegistroCivil {
    
    private HashMap<String, Sucursal> sucursales = new HashMap<>();
    private HashMap<String, Persona> listaPersonas = new HashMap<>();
    private ArrayList<String> clavesSucursales = new ArrayList<>();
    private ArrayList<String> rutsPersonas = new ArrayList<>();
    private ArrayList<Certificado> certificados = new ArrayList<>();

    /**
    * Obtiene total claves.
    * @return valor resultante de la operación.
    */
    public int getTotalClaves(){
        return clavesSucursales.size();
    }
    
    /**
    * Obtiene total personas.
    * @return valor resultante de la operación.
    */
    public int getTotalPersonas(){
        return listaPersonas.size();
    }
    
    /**
    * Obtiene total certificados.
    * @return valor resultante de la operación.
    */
    public int getTotalCertificados(){
        return certificados.size();
    }
    
    /**
    * Obtiene sucursal.
    * @param key parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Sucursal getSucursal(String key){
        return sucursales.get(key);
    }
    
    //sobreCarga
    /**
    * Obtiene sucursal.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Sucursal getSucursal(int i){
        return sucursales.get(getClave(i));
    }
    
    /**
    * Obtiene certificado.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Certificado getCertificado(int i){
        return certificados.get(i);
    }
    
    /**
    * Obtiene persona.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Persona getPersona(int i){
        return listaPersonas.get(getRut(i));
    }
    
    //sobreCarga
    /**
    * Obtiene persona.
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Persona getPersona(String rut){
        return listaPersonas.get(rut);
    }
    
    /**
    * Obtiene mapa sucursales.
    * @return valor resultante de la operación.
    */
    public HashMap<String, Sucursal> getSucursales(){
        return sucursales;
    }
    
    /**
    * Obtiene lista certificados.
    * @return valor resultante de la operación.
    */
    public ArrayList<Certificado> getListaCertificados(){
        return certificados;
    }
    
    /**
    * Obtiene claves sucursales.
    * @return valor resultante de la operación.
    */
    public ArrayList<String> getClavesSucursales(){
        return clavesSucursales;
    }
    
    /**
    * Obtiene clave.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public String getClave(int i){
        return clavesSucursales.get(i);
    }
    
    /**
    * Obtiene rut.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public String getRut(int i){
        return rutsPersonas.get(i);
    }
    
    /**
    * Realiza la operación «validarRut».
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean validarRut(String rut) {
        return listaPersonas.containsKey(rut);
    }
    
    /**
    * Realiza la operación «agregarPersona».
    * @param p parámetro de entrada.
    */
    public void agregarPersona(Persona p){
        listaPersonas.put(p.getRut(), p);
        rutsPersonas.add(p.getRut());
    }
    
    /**
    * Realiza la operación «eliminarPersona».
    * @param p parámetro de entrada.
    */
    public void eliminarPersona(Persona p){
        listaPersonas.remove(p.getRut());
        rutsPersonas.remove(p.getRut());
    }
    
    /**
    * Realiza la operación «agregarSucursal».
    * @param nombreSucursal parámetro de entrada.
    * @param sucursal parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean agregarSucursal(String nombreSucursal, Sucursal sucursal) {
        if (sucursales.containsKey(nombreSucursal)) return false;
    
        clavesSucursales.add(nombreSucursal.trim());
        sucursales.put(nombreSucursal.trim(), sucursal);
        return true;
    }
    
    /**
    * Realiza la operación «eliminarSucursal».
    * @param nombreSucursal parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean eliminarSucursal(String nombreSucursal){
        if (!clavesSucursales.contains(nombreSucursal)) return false;
        sucursales.remove(nombreSucursal);
        clavesSucursales.remove(nombreSucursal);
        return true;
    }
    
    //Verificar nacimiento
    /**
    * Realiza la operación «nacimiento».
    * @param persona parámetro de entrada.
    * @param nombreSucursal parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean nacimiento(Persona persona, String nombreSucursal) {
        if (validarRut(persona.getRut())) return false;
                                          
        Sucursal suc = sucursales.get(nombreSucursal);
        if (suc == null) return false;
        
        Archivo archivo = new Archivo();
        boolean asignado = archivo.setPersona(persona);

        if (!asignado) return false;
       
        listaPersonas.put(persona.getRut(), persona); //agregar persona a la
        rutsPersonas.add(persona.getRut());          // lista total de personas
        suc.agregarArchivo(persona.getRut(),archivo);
        return true;
    }
    
    /**
    * Realiza la operación «buscarPersona».
    * @param nombreS parámetro de entrada.
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Persona buscarPersona(String nombreS, String rut) {
        if (nombreS == null || rut == null) return null;

        Sucursal suc = sucursales.get(nombreS);
        if (suc == null) return null;

        Archivo personaEnSucursal = suc.getArchivo(rut);
        if (personaEnSucursal == null) return null;
        return personaEnSucursal.getPersona(); // devuelve null si no existe
}
    
    /**
    * Realiza la operación «buscarPersona».
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Persona buscarPersona(String rut) {
        return listaPersonas.get(rut);
    }

    /**
    * Realiza la operación «leerDatos».
    * @param persona parámetro de entrada.
    * @param nombreSucursal parámetro de entrada.
    */
    public void leerDatos(Persona persona, String nombreSucursal) {
        nacimiento(persona, nombreSucursal);
    }
    
    /**
    * Realiza la operación «agregarCertificado».
    * @param certi parámetro de entrada.
    */
    public void agregarCertificado(Certificado certi){
        certificados.add(certi);
    }
}
