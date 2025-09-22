package sistemregistrocivil;

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
            
            Fecha nacimiento = new Fecha(dia, mes, año);
            Persona persona = new Persona(rut, nombre, nacimiento);
            rc.leerDatos(persona, nombreSucursal);
        }
        lectorCSV.close();
    }
    
    public void guardarCsvSucursales(RegistroCivil rc) throws IOException{
        File archivo = new File("src/datos/sucursales2.csv");
        FileOutputStream fos = new FileOutputStream(archivo, false);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        PrintWriter pw = new PrintWriter(bw);
        
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
                               suc.getNombre();
                pw.println(linea);
            }
        }
        
        pw.close();
        bw.close();
        osw.close();
        fos.close();
    }
}
