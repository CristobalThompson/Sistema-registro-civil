/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Función destinada al dato de las personas en temas de matrimonio.
 * Sirve a la hora de divorciar estando soltero, casar estando casado o viudar
 * estando soltero, etc.
 * @author franc
 */
public class SinSentidoException extends Exception{
    public SinSentidoException(){
        super("Su acción no tiene sentido con los datos actuales.");
    }
}
