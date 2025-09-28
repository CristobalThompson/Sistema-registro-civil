package sistemregistrocivil.model;


public class CertificadoNacimiento extends Certificado{
    private Fecha fechaNac;
    
    public CertificadoNacimiento(String nombre, String rut, Fecha naci) {
        super("Certificado de nacimiento", nombre, rut);
        fechaNac = naci;
    }
    
    public Fecha getNacimiento(){
        return fechaNac;
    }

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
