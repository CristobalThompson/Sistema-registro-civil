package sistemregistrocivil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */

import java.util.*;

public class RegistroCivil {
    
    private HashMap<String, Sucursal> sucursales = new HashMap<>();
    private HashMap<String, Persona> listaPersonas = new HashMap<>();
    

    private boolean validarRut(String rut) {
        return listaPersonas.containsKey(rut);
    }
    
    public void agregarSucursal(String nombreSucursal, Sucursal sucursal) {
    if (sucursales.containsKey(nombreSucursal)) {
        System.out.println("La sucursal '" + nombreSucursal + "' ya existe.");
    } else {
        sucursales.put(nombreSucursal, sucursal);
        System.out.println("Sucursal '" + nombreSucursal + "' agregada correctamente.");
        }
    }
    
    //Verificar nacimiento
    public boolean nacimiento(Persona persona, String nombreSucursal) {
        if (validarRut(persona.getRut())) {
            System.out.println("El RUT ya está registrado.");
            return false;
        }

        listaPersonas.put(persona.getRut(), persona);

        Sucursal suc = sucursales.get(nombreSucursal);
        if (suc != null) {
            Archivo archivo = new Archivo();
            boolean asignado = archivo.setPersona(persona);

            if (!asignado) {
                System.out.println("Error al asignar la persona al archivo.");
                return false;
            }

            suc.agregarArchivo(nombreSucursal,archivo);
        } else {
            System.out.println("Sucursal no encontrada: " + nombreSucursal);
            return false;
        }

        System.out.println("Nacimiento registrado en sucursal: " + nombreSucursal);
        return true;
    }
    
    public Persona buscarPersona(String nombreS, String rut) {
        if (nombreS == null || rut == null) return null;

        Sucursal suc = sucursales.get(nombreS);
        if (suc == null) return null;

        Archivo personaEnSucursal = suc.getArchivo(rut);
        if (personaEnSucursal == null) return null;
        return personaEnSucursal.getPersona(); // devuelve null si no existe
}
    public Persona buscarPersona(String rut) {
        return listaPersonas.get(rut);
    }
    //Mostrar los documentos del rut solicitado
    public void mostrarDocumento(String rut) {
        Persona persona = buscarPersona(rut);
        if (persona != null) {
            persona.mostrarDatos();
        } else {
            System.out.println("Persona no encontrada.");
        }
    }

    public void leerDatos(Persona persona, String nombreSucursal) {
        nacimiento(persona, nombreSucursal);
    }

}
