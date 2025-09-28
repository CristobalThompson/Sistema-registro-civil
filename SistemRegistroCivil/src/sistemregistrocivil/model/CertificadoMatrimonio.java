package sistemregistrocivil.model;


public class CertificadoMatrimonio extends Certificado{
    private String Nombreconyuge;
    private String RutConyuge;
    
    private Fecha fechaCasamiento;
    
    
    public CertificadoMatrimonio(String nombre, String rut, String Nconyuge, String RutConyuge,
            Fecha casamiento){
        super("Certificado de matrimonio", nombre, rut);
        Nombreconyuge = Nconyuge;
        this.RutConyuge = RutConyuge;
        fechaCasamiento = casamiento;
    }

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

    public String getNombreconyuge() {
        return Nombreconyuge;
    }

    public String getRutConyuge() {
        return RutConyuge;
    }

    public Fecha getFechaCasamiento() {
        return fechaCasamiento;
    }
    
    
}
