/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepcion cuando ingresas una fecha inválida, ya sea utilizar Strings donde van 
 * números, usar simbolos raros, etc.
 * @author franc
 */
public class InvalidFechaException extends Exception{
    public InvalidFechaException(){
        super("Ingresaste la fecha en un formato inválido.");
    }
}
