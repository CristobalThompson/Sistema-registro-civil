package sistemregistrocivil.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */

import java.util.*;

public class Sucursal {
    private int id;
    private String nombre;
    private Ubicacion ubicacion;
    private HashMap<String,Archivo> archivos;
    private ArrayList<String> keys;
    //Constructores
    
    public Sucursal(int iD, String name,Ubicacion location){
        this.id = iD;
        this.nombre = name;
        this.ubicacion = location;
        archivos = new HashMap<String,Archivo>();
        keys = new ArrayList<String>();
    }
    
    //Métodos de la clase
    
    public boolean agregarArchivo(String rut,Archivo valor){
        
        if(archivos.containsKey(rut)) return false;
        
        archivos.put(rut, valor);
        keys.add(rut);
        return true;
        //Retorna true si se insertó
    }
    
    public boolean eliminarArchivo(String rut, Archivo value){
        return this.archivos.remove(rut, value);
    }
    
    public boolean eliminarArchivo(String rut){
        if (!archivos.containsKey(rut)) return false;
        archivos.remove(rut);
        return true;
    }
    
    //Métodos sets
    
    public boolean setID(int nuevo){
        this.id = nuevo;
        return true;
    }
    
    public boolean setNombre(String nuevo){
        this.nombre = nuevo;
        return true;
    }
    
    public boolean setUbicacion(Ubicacion nuevo){
        this.ubicacion = nuevo;
        return true;
    }
    
    //Métodos gets
    
    public int getID(){
        return this.id;
    }
    
    public String getKey(int i){
        return keys.get(i);
    }
    
    public int getTotalArchivos(){
        return archivos.size();
    }
    
    public Archivo getArchivo(String rut){
        if (rut == null) return null;
        return archivos.get(rut);
    }
    
    //sobreCarga
    public Archivo getArchivo(int i){
        return archivos.get(getKey(i));
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }
    
}