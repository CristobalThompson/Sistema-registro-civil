/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * Función destinada al dato de casillas vacias
 * @author franc
 */
public class SinSentidoException extends Exception{
    public SinSentidoException(){
        super("La casilla se encuentra vacia");
    }
}
