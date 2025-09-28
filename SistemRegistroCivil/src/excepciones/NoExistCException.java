/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Excepción si al mostrar un certificado este no existe o no ha sido creado previamente.
 * @author franc
 */
public class NoExistCException extends Exception{
    public NoExistCException(){
        super("El certificado que desea mostrar aun no existe.");
    }
}
