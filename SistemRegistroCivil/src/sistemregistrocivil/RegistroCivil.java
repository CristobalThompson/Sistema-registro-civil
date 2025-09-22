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

public class RegistroCivil {
    
    private HashMap<String, Sucursal> sucursales = new HashMap<>();
    private HashMap<String, Persona> listaPersonas = new HashMap<>();
    private ArrayList<String> clavesSucursales = new ArrayList<>();
    private ArrayList<String> rutsPersonas = new ArrayList<>();

    
    public int getTotalClaves(){
        return clavesSucursales.size();
    }
    
    public int getTotalPersonas(){
        return listaPersonas.size();
    }
    
    public Sucursal getSucursal(String key){
        return sucursales.get(key);
    }
    
    //sobreCarga
    public Sucursal getSucursal(int i){
        return sucursales.get(getClave(i));
    }
    
    public Persona getPersona(int i){
        return listaPersonas.get(getRut(i));
    }
    
    //sobreCarga
    public Persona getPersona(String rut){
        return listaPersonas.get(rut);
    }
    
    public HashMap<String, Sucursal> getSucursales(){
        return sucursales;
    }
    
    public String getClave(int i){
        return clavesSucursales.get(i);
    }
    
    public String getRut(int i){
        return rutsPersonas.get(i);
    }
    
    private boolean validarRut(String rut) {
        return listaPersonas.containsKey(rut);
    }
    
    public boolean agregarSucursal(String nombreSucursal, Sucursal sucursal) {
        if (sucursales.containsKey(nombreSucursal)) return false;
    
        clavesSucursales.add(nombreSucursal.trim());
        sucursales.put(nombreSucursal.trim(), sucursal);
        return true;
    }
    
    public boolean eliminarSucursal(String nombreSucursal){
        if (!clavesSucursales.contains(nombreSucursal)) return false;
        sucursales.remove(nombreSucursal);
        clavesSucursales.remove(nombreSucursal);
        return true;
    }
    
    //Verificar nacimiento
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
    
    public Persona buscarPersona(String nombreS, String rut) {
        if (nombreS == null || rut == null) return null;

        Sucursal suc = sucursales.get(nombreS);
        if (suc == null) return null;

        Archivo personaEnSucursal = suc.getArchivo(rut);
        if (personaEnSucursal == null) return null;
        return personaEnSucursal.getPersona(); // devuelve null si no existe
}
    public Persona buscarPersona(String rut) {
        return listaPersonas.get(rut);
    }

    public void leerDatos(Persona persona, String nombreSucursal) {
        nacimiento(persona, nombreSucursal);
    }
}
