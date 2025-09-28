package sistemregistrocivil.model;

import java.util.*;

/**
* Representa una sucursal dentro del sistema (Registro Civil u organización asociada).
* Contiene sus datos y operaciones básicas.
*/
public class Sucursal {
    private int id;
    private String nombre;
    private Ubicacion ubicacion;
    private HashMap<String,Archivo> archivos;
    private ArrayList<String> keys;
    //Constructores
    
    /**
    * Crea una instancia de Sucursal.
    * @param iD parámetro de entrada.
    * @param name parámetro de entrada.
    * @param location parámetro de entrada.
    */
    public Sucursal(int iD, String name,Ubicacion location){
        this.id = iD;
        this.nombre = name;
        this.ubicacion = location;
        archivos = new HashMap<String,Archivo>();
        keys = new ArrayList<String>();
    }
    
    //Métodos de la clase
    
    /**
    * Realiza la operación «agregarArchivo».
    * @param rut parámetro de entrada.
    * @param valor parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean agregarArchivo(String rut,Archivo valor){
        
        if(archivos.containsKey(rut)) return false;
        
        archivos.put(rut, valor);
        keys.add(rut);
        return true;
        //Retorna true si se insertó
    }
    
    /**
    * Realiza la operación «eliminarArchivo».
    * @param rut parámetro de entrada.
    * @param value parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean eliminarArchivo(String rut, Archivo value){
        return this.archivos.remove(rut, value);
    }
    
    /**
    * Realiza la operación «eliminarArchivo».
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean eliminarArchivo(String rut){
        if (!archivos.containsKey(rut)) return false;
        archivos.remove(rut);
        return true;
    }
    
    //Métodos sets
    
    /**
    * Asigna id.
    * @param nuevo parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean setID(int nuevo){
        this.id = nuevo;
        return true;
    }
    
    /**
    * Asigna nombre.
    * @param nuevo parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean setNombre(String nuevo){
        this.nombre = nuevo;
        return true;
    }
    
    /**
    * Asigna ubicacion.
    * @param nuevo parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public boolean setUbicacion(Ubicacion nuevo){
        this.ubicacion = nuevo;
        return true;
    }
    
    //Métodos gets
    
    /**
    * Obtiene id.
    * @return valor resultante de la operación.
    */
    public int getID(){
        return this.id;
    }
    
    /**
    * Obtiene key.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public String getKey(int i){
        return keys.get(i);
    }
    
    /**
    * Obtiene total archivos.
    * @return valor resultante de la operación.
    */
    public int getTotalArchivos(){
        return archivos.size();
    }
    
    /**
    * Obtiene archivo.
    * @param rut parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Archivo getArchivo(String rut){
        if (rut == null) return null;
        return archivos.get(rut);
    }
    
    //sobreCarga
    /**
    * Obtiene archivo.
    * @param i parámetro de entrada.
    * @return valor resultante de la operación.
    */
    public Archivo getArchivo(int i){
        return archivos.get(getKey(i));
    }
    
    /**
    * Obtiene nombre.
    * @return valor resultante de la operación.
    */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
    * Obtiene ubicacion.
    * @return valor resultante de la operación.
    */
    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }
    
}