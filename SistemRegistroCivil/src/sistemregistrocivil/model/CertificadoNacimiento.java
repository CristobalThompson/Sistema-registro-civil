package sistemregistrocivil.model;

/**
 * Certificado de nacimiento dentro del sistema.
 * Expone datos y operaciones asociadas al certificado.
 */
public class CertificadoNacimiento extends Certificado{
    private Fecha fechaNac;
    
    /**
     * Crea una instancia de CertificadoNacimiento.
     * @param nombre parámetro de entrada.
     * @param rut parámetro de entrada.
     * @param naci parámetro de entrada.
     */
    public CertificadoNacimiento(String nombre, String rut, Fecha naci) {
        super("Certificado de nacimiento", nombre, rut);
        fechaNac = naci;
    }
    
    /**
     * Obtiene nacimiento.
     * @return valor resultante de la operación.
     */
    public Fecha getNacimiento(){
        return fechaNac;
    }

    /**
     * Realiza la operación «generarLinea».
     * @return valor resultante de la operación.
     */
    @Override
    public String generarLinea() {
        String nombre = getNombrePersona();
        String rut = getRut();
                
        int diaNace = fechaNac.getDia();
        int mesNace = fechaNac.getMes();
        int añoNace = fechaNac.getAño();
        
        Fecha Femision = getEmision();
        
        int diaE = Femision.getDia();
        int mesE = Femision.getMes();
        int añoE = Femision.getAño();
        
        return  "2" + "," +
                nombre + "," +
                rut + "," +
                diaNace + "," +
                mesNace + "," +
                añoNace + "," +
                diaE + "," +
                mesE + "," +
                añoE;
    }
    
    
}
