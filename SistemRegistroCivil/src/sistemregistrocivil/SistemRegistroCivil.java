
package sistemregistrocivil;

import java.io.IOException;
/**
 *
 * @author crist
 */
public class SistemRegistroCivil {

    public static void main(String[] args) throws IOException {
        javax.swing.SwingUtilities.invokeLater(() -> {
            RegistroCivil rc = new RegistroCivil();
            Menu consoleichon = new Menu(rc);
            consoleichon.mostrar();
        });
    }
 
    public static void leerDatos(RegistroCivil rc){
        // 1) Sucursales
        String[] sucursales = {
            "Santiago Centro", "Providencia",
            "Valparaíso", "Viña del Mar", "Concepción" };
        
        int id = 1;
        for (String nombre : sucursales) {
            Ubicacion ubi = new Ubicacion(nombre, nombre, nombre, nombre);
            Sucursal suc = new Sucursal(id, nombre, ubi);
            rc.agregarSucursal(nombre, suc);
            ++id;
        }

        // 2) Personas:{rut, nombre,            año,   mes,día, sucursal}
        Object[][] datos = {
            {"12.345.678-9", "Ana Rojas",        1998,  3, 12, "Santiago Centro"},
            {"11.111.111-1", "Benjamín Pérez",   1999,  7,  4, "Valparaíso"},
            {"13.456.789-0", "Camila Soto",      2001, 10, 23, "Viña del Mar"},
            {"14.789.123-4", "Diego Hernández",  1995,  1, 14, "Providencia"},
            {"15.234.567-8", "Emilia Vargas",    2000,  5, 30, "Concepción"},
            {"16.345.678-5", "Francisco Muñoz",  1997, 12,  2, "Santiago Centro"},
            {"17.456.789-3", "Gabriela Silva",   1994,  2, 19, "Valparaíso"},
            {"18.567.890-2", "Hugo Castillo",    2002,  6,  7, "Viña del Mar"},
            {"19.678.901-1", "Isidora Fuentes",  1993,  9,  9, "Providencia"},
            {"20.789.012-0", "Joaquín Reyes",    1996,  4, 25, "Concepción"},
            {"21.888.645-0", "Cristobal Thompson",2005, 07, 19, "Santiago Centro"},
            {"22.901.234-8", "Luis Torres",      2001,  8, 18, "Valparaíso"},
            {"23.012.345-7", "Martina Navarro",  1992,  3,  5, "Viña del Mar"},
            {"24.123.456-6", "Nicolás Araya",    1999,  7, 28, "Providencia"},
            {"25.234.567-5", "Olivia Contreras", 1997,  1, 17, "Concepción"}
        };

        for (Object[] d : datos) {
            String rut     = (String) d[0];
            String nombre  = (String) d[1];
            int    anio    = (int)    d[2];
            int    mes     = (int)    d[3];
            int    dia     = (int)    d[4];
            String sucNom  = (String) d[5];
            Fecha fn = new Fecha();
            fn.setDia(dia);
            fn.setMes(mes);
            fn.setAño(anio);

            Persona p = new Persona();
            p.setNombre(nombre);
            p.setRut(rut);
            p.setFechaN(fn);
            p.setEstado(true);
            p.setEstadoCivil("Soltero/a");
            rc.nacimiento(p, sucNom); // registra persona y la asocia a la sucursal si existe
        }
    }
}

