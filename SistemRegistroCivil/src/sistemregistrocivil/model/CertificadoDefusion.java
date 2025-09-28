package sistemregistrocivil.model;

/**
 * Certificado de defunción dentro del sistema.
 * Incluye datos y operaciones asociadas al documento.
 */
public class CertificadoDefusion extends Certificado{
    private final Fecha fallecimiento;
    
    /**
     * Crea una instancia de CertificadoDefusion.
     * @param nombre parámetro de entrada.
     * @param rut parámetro de entrada.
     * @param falle parámetro de entrada.
     */
    public CertificadoDefusion(String nombre, String rut, Fecha falle) {
        super("Certificado de defusión", nombre, rut);
        fallecimiento = falle;
    }
    
    /**
     * Emite o genera el certificado correspondiente.
     * @return valor resultante de la operación.
     */
    @Override
    public String generarLinea() {
        String nombreCerti = getNombreCertificado();
        String nombre = getNombrePersona();
        String rut = getRut();
                
        int diaFalle = fallecimiento.getDia();
        int mesFalle = fallecimiento.getMes();
        int añoFalle = fallecimiento.getAño();
        
        Fecha Femision = getEmision();
        
        int diaE = Femision.getDia();
        int mesE = Femision.getMes();
        int añoE = Femision.getAño();
        
        return  "1" + "," +
                nombre + "," +
                rut + "," +
                diaFalle + "," +
                mesFalle + "," +
                añoFalle + "," +
                diaE + "," +
                mesE + "," +
                añoE;
    }
    
    /**
     * Obtiene defucion.
     * @return valor resultante de la operación.
     */
    public Fecha getDefucion(){
        return fallecimiento;
    }
}
