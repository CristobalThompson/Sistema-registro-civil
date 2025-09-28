package sistemregistrocivil;

import sistemregistrocivil.model.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class GestorCSV {
    public void cargarCsvSucursales(RegistroCivil rc) throws IOException{
        BufferedReader lectorCSV = new BufferedReader( new InputStreamReader( 
                new FileInputStream("src/datos/sucursales.csv"),"UTF-8"));
        String linea;
        
        linea = lectorCSV.readLine(); //eliminar encabezados
        int id = rc.getTotalClaves() + 1;
        while((linea = lectorCSV.readLine()) != null){
            String[] datosSucursales = linea.split(",");
            
            String nombre = datosSucursales[0];
            String region = datosSucursales[1];
            String ciudad = datosSucursales[2];
            String comuna = datosSucursales[3];
            String direccion = datosSucursales[4];
            
            Ubicacion ubi = new Ubicacion(region, ciudad, comuna, direccion);
            Sucursal suc = new Sucursal(id, nombre, ubi);
            rc.agregarSucursal(nombre, suc);
            ++id;
        }
        lectorCSV.close(); //cerrar la lectura del archivo
        
    }
    
    public void cargarCsvPersonas(RegistroCivil rc) throws IOException{
        BufferedReader lectorCSV = new BufferedReader( new InputStreamReader( 
                new FileInputStream("src/datos/Personas.csv"),"UTF-8"));
        String linea;
        
        linea = lectorCSV.readLine(); //elimina encabezados
        while((linea = lectorCSV.readLine()) != null){
            String[] datosPersonas = linea.split(",");
            
            String rut = datosPersonas[0];
            String nombre = datosPersonas[1];
            int año = Integer.parseInt(datosPersonas[2]);
            int mes = Integer.parseInt(datosPersonas[3]);
            int dia = Integer.parseInt(datosPersonas[4]);
            String nombreSucursal = datosPersonas[5];
            String estadoCivil = datosPersonas[6];
            
            Fecha nacimiento = new Fecha(dia, mes, año);
            Persona persona = new Persona(rut, nombre, nacimiento);
            persona.setEstadoCivil(estadoCivil);
            rc.leerDatos(persona, nombreSucursal);
        }
        lectorCSV.close();
    }
    
    public void cargarCertificados(RegistroCivil rc) throws IOException{
        BufferedReader lectorCSV = new BufferedReader( new InputStreamReader( 
                new FileInputStream("src/datos/certificados.csv"),"UTF-8"));
        String linea;
        while((linea = lectorCSV.readLine()) != null){
            String[] datosCertificados = linea.split(",");
            
            String tipo = datosCertificados[0];
            String nombrePersona = datosCertificados[1];
            String rut = datosCertificados[2];
            
            Certificado certi = null;
            int diaE, mesE, añoE;
            Fecha emi;
            switch(tipo){
                case "1":
                    int diaFalle = Integer.parseInt(datosCertificados[3]);
                    int mesFalle = Integer.parseInt(datosCertificados[4]);
                    int añoFalle = Integer.parseInt(datosCertificados[5]);
                    
                    Fecha falle = new Fecha(diaFalle, mesFalle, añoFalle);
                    
                    diaE = Integer.parseInt(datosCertificados[6]);
                    mesE = Integer.parseInt(datosCertificados[7]);
                    añoE = Integer.parseInt(datosCertificados[8]);
                    
                    emi = new Fecha(diaE, mesE, añoE);
                    
                    certi = new CertificadoDefusion(nombrePersona, rut
                    , falle);
                    certi.setEmision(emi);
                    break;
                case "2" :
                    int diaNace = Integer.parseInt(datosCertificados[3]);
                    int mesNace = Integer.parseInt(datosCertificados[4]);
                    int añoNace = Integer.parseInt(datosCertificados[5]);
                    
                    Fecha nace = new Fecha(diaNace, mesNace, añoNace);
                    
                    diaE = Integer.parseInt(datosCertificados[6]);
                    mesE = Integer.parseInt(datosCertificados[7]);
                    añoE = Integer.parseInt(datosCertificados[8]);
                    
                    emi = new Fecha(diaE, mesE, añoE);
                    
                    certi = new CertificadoNacimiento(nombrePersona, rut
                    , nace);
                    certi.setEmision(emi);
                    break;
                case "3" :
                    String nombreConyuge = datosCertificados[3];
                    String rutConyuge = datosCertificados[4];
                    
                    int diaCasa = Integer.parseInt(datosCertificados[5]);
                    int mesCasa = Integer.parseInt(datosCertificados[6]);
                    int añoCasa = Integer.parseInt(datosCertificados[7]);
                    
                    Fecha casa = new Fecha(diaCasa, mesCasa, añoCasa);
                    
                    diaE = Integer.parseInt(datosCertificados[8]);
                    mesE = Integer.parseInt(datosCertificados[9]);
                    añoE = Integer.parseInt(datosCertificados[10]);
                    
                    emi = new Fecha(diaE, mesE, añoE);
                    
                    certi = new CertificadoMatrimonio(nombrePersona, rut, 
                    nombreConyuge, rutConyuge, casa);
                    break;
            }
            if (certi == null) continue;
            
            rc.agregarCertificado(certi);
        }
        lectorCSV.close();
    }
    
    public void guardarCsvSucursales(RegistroCivil rc) throws IOException{
        File archivo = new File("src/datos/sucursales2.csv");
        FileOutputStream fos = new FileOutputStream(archivo, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);
        
        pw.println("nombre sucursal,region,ciudad,comuna,direcion");
        for (int i = 0; i < rc.getTotalClaves(); ++i) {
            Sucursal suc = rc.getSucursal(i);
            
            if(suc == null) continue;
            
            String linea = suc.getNombre() + "," +
                           suc.getUbicacion().getRegion() + "," +
                           suc.getUbicacion().getCiudad() + "," +
                           suc.getUbicacion().getComuna() + "," +
                           suc.getUbicacion().getDireccion();
            pw.println(linea);
        }
        pw.close();
        bw.close();
        osw.close();
        fos.close();
    }
    
    public void guardarCsvPersonas(RegistroCivil rc) throws IOException{
        File archivo = new File("src/datos/Personas2.csv");
        FileOutputStream fos = new FileOutputStream(archivo, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Rut,Nombre,Año,Mes,Dia,nombre sucursal,estado civil");
        for(int i = 0; i < rc.getTotalClaves(); ++i){
            Sucursal suc = rc.getSucursal(i);
            if (suc == null) continue;
            
            for(int k = 0; k < suc.getTotalArchivos(); ++k){
                Archivo arc = suc.getArchivo(k);
                if (arc == null) continue;
                
                Persona per = arc.getPersona();
                
                String linea = per.getRut() + "," +
                               per.getNombre() + "," +
                               per.getNacimiento().getAño() + "," +
                               per.getNacimiento().getMes() + "," +
                               per.getNacimiento().getDia() + "," +
                               suc.getNombre() + "," +
                               per.getCivil();
                pw.println(linea);
            }
        }
        
        pw.close();
        bw.close();
        osw.close();
        fos.close();
    }
    
    public void guardarCertificados(RegistroCivil rc) throws IOException{
        File archivo = new File("src/datos/certificados2.csv");
        FileOutputStream fos = new FileOutputStream(archivo, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);
        
        for(int i = 0; i < rc.getTotalCertificados(); ++i){
            Certificado certi = rc.getCertificado(i);
            pw.println(certi.generarLinea());
        }
        
        pw.close();
        bw.close();
        osw.close();
        fos.close();
    }
    
    public void generarReporte(RegistroCivil rc) throws IOException{
        File archivo = new File("src/datos/reporte.csv");
        FileOutputStream fos = new FileOutputStream(archivo, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);
        
        pw.println("Total sucursales registradas: " + rc.getTotalClaves());
        pw.println("Total de personas registradas: " + rc.getTotalPersonas());
        pw.println("Total de certificados registrados: " + rc.getTotalCertificados());
        
        pw.close();
        bw.close();
        osw.close();
        fos.close();
    }
}
