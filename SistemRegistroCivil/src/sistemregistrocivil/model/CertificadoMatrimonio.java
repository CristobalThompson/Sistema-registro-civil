package sistemregistrocivil.model;


/**
 * Certificado de matrimonio dentro del sistema.
 * Incluye datos y operaciones asociadas al documento.
 */
public class CertificadoMatrimonio extends Certificado{
    private String Nombreconyuge;
    private String RutConyuge;
    
    private Fecha fechaCasamiento;
    
    /**
     * Crea una instancia de CertificadoMatrimonio.
     * @param nombre parámetro de entrada.
     * @param rut parámetro de entrada.
     * @param Nconyuge parámetro de entrada.
     * @param RutConyuge parámetro de entrada.
     * @param casamiento parámetro de entrada.
     */
    public CertificadoMatrimonio(String nombre, String rut, String Nconyuge, String RutConyuge,
            Fecha casamiento){
        super("Certificado de matrimonio", nombre, rut);
        Nombreconyuge = Nconyuge;
        this.RutConyuge = RutConyuge;
        fechaCasamiento = casamiento;
    }

    /**
     * Emite o genera el certificado correspondiente.
     * @return valor resultante de la operación.
     */
    @Override
    public String generarLinea() {
        String nombre = getNombrePersona();
        String rut = getRut();
                
        int diaCasa = fechaCasamiento.getDia();
        int mesCasa = fechaCasamiento.getMes();
        int añoCasa = fechaCasamiento.getAño();
        
        Fecha Femision = getEmision();
        
        int diaE = Femision.getDia();
        int mesE = Femision.getMes();
        int añoE = Femision.getAño();
        
        return  "3" + "," +
                nombre + "," +
                rut + "," +
                Nombreconyuge + "," +
                RutConyuge + "," +
                diaCasa + "," +
                mesCasa + "," +
                añoCasa + "," +
                diaE + "," +
                mesE + "," +
                añoE;
    }
    
    /**
     * Obtiene nombreconyuge.
     * @return valor resultante de la operación.
     */
    public String getNombreconyuge() {
        return Nombreconyuge;
    }

    /**
     * Obtiene rut conyuge.
     * @return valor resultante de la operación.
     */
    public String getRutConyuge() {
        return RutConyuge;
    }

    /**
     * Obtiene fecha casamiento.
     * @return valor resultante de la operación.
     */
    public Fecha getFechaCasamiento() {
        return fechaCasamiento;
    }  
}
