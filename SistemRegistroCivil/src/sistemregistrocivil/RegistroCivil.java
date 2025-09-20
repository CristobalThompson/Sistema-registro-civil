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

    
    public int getTotalClaves(){
        return clavesSucursales.size();
    }
    
    public Sucursal getSucursal(String key){
        return sucursales.get(key);
    }
    
    public HashMap<String, Sucursal> getSucursales(){
        return sucursales;
    }
    
    public String getClave(int i){
        return clavesSucursales.get(i);
    }
    
    private boolean validarRut(String rut) {
        return listaPersonas.containsKey(rut);
    }
    
    public boolean agregarSucursal(String nombreSucursal, Sucursal sucursal) {
    if (sucursales.containsKey(nombreSucursal)) return false;
    
    clavesSucursales.add(nombreSucursal);
    sucursales.put(nombreSucursal, sucursal);
    return true;
    }
    
    //Verificar nacimiento
    public boolean nacimiento(Persona persona, String nombreSucursal) {
        if (validarRut(persona.getRut())) return false;
        

        listaPersonas.put(persona.getRut(), persona); //agregar persona a la
        // lista total de personas

                                                     
        Sucursal suc = sucursales.get(nombreSucursal);
        if (suc == null) return false;
        
        
        Archivo archivo = new Archivo();
        boolean asignado = archivo.setPersona(persona);

        if (!asignado) return false;
       
        suc.agregarArchivo(nombreSucursal,archivo);
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
