/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion cuando ingresan una fecha en formato válido pero que no existe.
 * Por ejemplo 30/02/2000
 * @author franc
 */
public class FechaInexistenteException extends Exception{
    public FechaInexistenteException(){
        super("Fecha inexistente!");
    }
}
