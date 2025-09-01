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

public class Sucursal {
    private int id;
    private String nombre;
    private Ubicacion ubicacion;
    private HashMap<String,Archivo> archivos;
    
    //Constructores
    
    public void Sucursal(int iD, String name,Ubicacion location){
        this.id = iD;
        this.nombre = name;
        this.ubicacion = location;
        archivos = new HashMap<String,Archivo>();
    }
    
    //Métodos de la clase
    
    public boolean agregarArchivo(String key,Archivo valor){
        return this.archivos.putIfAbsent(key,valor) == null;
        //Retorna true si se insertó
    }
    
    public boolean eliminarArchivo(String key, Archivo value){
        return this.archivos.remove(key,value);
    }
    
    public void mostrarSucursal(){
        System.out.println("- - - - - - - - - - - - - -");
        System.out.println("Sucursal: "+this.nombre);
        System.out.println("- - - - - - - - - - - - - -");
        System.out.println("- iD:" + this.id);
        System.out.println("- Ubicación:");
        System.out.println(""); //pendiente añadir los datos de ubición con gets.
        System.out.println("");
        System.out.println("");
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
    
    public Archivo getArchivo(String rut){
        if (rut == null) return null;
        return this.archivos.get(rut);
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }
    
    public void mostrarPersona(){
        //pendiente hasta que esté lista la clase Archivo
    }
}