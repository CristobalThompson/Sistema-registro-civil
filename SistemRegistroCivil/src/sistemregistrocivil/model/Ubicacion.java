package sistemregistrocivil.model;

/**
 * Representa una ubicación geográfica asociada a una persona, sucursal o entidad
 * del sistema: región, ciudad, comuna y dirección.
 * <strong>Convención:</strong> Un campo nulo o vacío se interpreta como "no informado".
 */
public class Ubicacion {
    private String region;
    private String ciudad;
    private String comuna;
    private String direccion;

    // Constructor
    /**
     * Crea una ubicación con todos sus campos.
     * <p>No aplica validación estricta; si requiere validación incremental,
     * utilice los <em>setters</em>.</p>
     *
     * @param region    Región administrativa (puede ser nula o vacía si no se conoce).
     * @param ciudad    Ciudad (puede ser nula o vacía si no se conoce).
     * @param comuna    Comuna (puede ser nula o vacía si no se conoce).
     * @param direccion Dirección exacta (puede ser nula o vacía si no se conoce).
     */
    public Ubicacion(String region, String ciudad, String comuna, String direccion) {
        this.region = region;
        this.ciudad = ciudad;
        this.comuna = comuna;
        this.direccion = direccion;
    }

    // Getters
    /**
     * Obtiene la región.
     *
     * @return la región actual, o {@code null} si no ha sido informada.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Obtiene la ciudad.
     *
     * @return la ciudad actual, o {@code null} si no ha sido informada.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Obtiene la comuna.
     *
     * @return la comuna actual, o {@code null} si no ha sido informada.
     */
    public String getComuna() {
        return comuna;
    }

    /**
     * Obtiene la dirección exacta.
     *
     * @return la dirección actual, o {@code null} si no ha sido informada.
     */
    public String getDireccion() {
        return direccion;
    }

    // Setters
    /**
     * Asigna la región si el valor es no nulo ni vacío.
     *
     * @param region nueva región a asignar.
     * @return {@code true} si se asignó; {@code false} si el valor era nulo o vacío.
     */
    public boolean setRegion(String region) {
        if (region != null && !region.isEmpty()) {
            this.region = region;
            return true;
        }
        return false;
    }

    /**
     * Asigna la ciudad si el valor es no nulo ni vacío.
     *
     * @param ciudad nueva ciudad a asignar.
     * @return {@code true} si se asignó; {@code false} si el valor era nulo o vacío.
     */
    public boolean setCiudad(String ciudad) {
        if (ciudad != null && !ciudad.isEmpty()) {
            this.ciudad = ciudad;
            return true;
        }
        return false;
    }

    /**
     * Asigna la comuna si el valor es no nulo ni vacío.
     *
     * @param comuna nueva comuna a asignar.
     * @return {@code true} si se asignó; {@code false} si el valor era nulo o vacío.
     */
    public boolean setComuna(String comuna) {
        if (comuna != null && !comuna.isEmpty()) {
            this.comuna = comuna;
            return true;
        }
        return false;
    }

    /**
     * Asigna la dirección si el valor es no nulo ni vacío.
     *
     * @param direccion nueva dirección a asignar.
     * @return {@code true} si se asignó; {@code false} si el valor era nulo o vacío.
     */
    public boolean setDireccion(String direccion) {
        if (direccion != null && !direccion.isEmpty()) {
            this.direccion = direccion;
            return true;
        }
        return false;
    }
}