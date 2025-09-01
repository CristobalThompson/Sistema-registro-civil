package sistemregistrocivil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author crist
 */
public class Ubicacion {
    private String region;
    private String ciudad;
    private String comuna;
    private String direccion;

    // Constructor
    public Ubicacion(String region, String ciudad, String comuna, String direccion) {
        this.region = region;
        this.ciudad = ciudad;
        this.comuna = comuna;
        this.direccion = direccion;
    }

    //Mostrar Datos
    public void Mostrar() {
        System.out.println("Región: " + region);
        System.out.println("Ciudad: " + ciudad);
        System.out.println("Comuna: " + comuna);
        System.out.println("Dirección: " + direccion);
    }

    // Getters
    public String getRegion() {
        return region;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getComuna() {
        return comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    // Setters
    public boolean setRegion(String region) {
        if (region != null && !region.isEmpty()) {
            this.region = region;
            return true;
        }
        return false;
    }

    public boolean setCiudad(String ciudad) {
        if (ciudad != null && !ciudad.isEmpty()) {
            this.ciudad = ciudad;
            return true;
        }
        return false;
    }

    public boolean setComuna(String comuna) {
        if (comuna != null && !comuna.isEmpty()) {
            this.comuna = comuna;
            return true;
        }
        return false;
    }

    public boolean setDireccion(String direccion) {
        if (direccion != null && !direccion.isEmpty()) {
            this.direccion = direccion;
            return true;
        }
        return false;
    }
}