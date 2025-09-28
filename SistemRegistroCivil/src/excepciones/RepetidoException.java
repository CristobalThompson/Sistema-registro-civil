package excepciones;

/**
 * Función destinada al dato repetido en el sistema
 * @author franc
 */
public class RepetidoException extends Exception{
    public RepetidoException(){
        super("El elemento ya se encuentra registrado");
    }
}
