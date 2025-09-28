package excepciones;


public class RepetidoException extends Exception{
    public RepetidoException(){
        super("El elemento ya se encuentra registrado");
    }
}
