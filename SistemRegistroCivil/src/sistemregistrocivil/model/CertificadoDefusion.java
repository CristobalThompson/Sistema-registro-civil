package sistemregistrocivil.model;


public class CertificadoDefusion extends Certificado{
    private final Fecha fallecimiento;
    
    public CertificadoDefusion(String nombre, String rut, Fecha falle) {
        super("Certificado de defusión", nombre, rut);
        fallecimiento = falle;
    }

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
    
}
