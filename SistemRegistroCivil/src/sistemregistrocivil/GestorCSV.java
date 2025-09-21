package sistemregistrocivil;

import java.io.*;

public class GestorCSV {
    public void cargarCsvSucursales(RegistroCivil rc) throws IOException{
        BufferedReader lectorCSV = new BufferedReader( new InputStreamReader( 
                new FileInputStream("src/datos/sucursales.csv"),"UTF-8"));
        String linea;
        
        linea = lectorCSV.readLine(); //eliminar encabezados
        int id = 1;
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
}
