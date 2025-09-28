/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Exception debido a que el certificado no se puede generar, puede ser debido a
 * querer generar un certidicado de matrimonio estando casado o
 * generar uno de defución estando la persona viva.
 * @author franc
 */
public class NoGenerableCException extends Exception{
    public NoGenerableCException(){
        super("No se pudo generar el certificado");
    }
}
