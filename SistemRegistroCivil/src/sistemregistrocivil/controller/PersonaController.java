package sistemregistrocivil.controller;

import java.awt.GridLayout;
import javax.swing.*;
import sistemregistrocivil.model.*;
import sistemregistrocivil.view.table.PersonasTableModel;
import java.util.function.Supplier;


public class PersonaController {
    private final JFrame padre;
    private final RegistroCivil rc;
    private final JTable tablaPersonas;
    private final PersonasTableModel modeloPersonas;
    private final Supplier<Sucursal> sucursalActual;
    

    public PersonaController(JFrame padre, RegistroCivil rc, 
            JTable tablaPersonas, PersonasTableModel modeloPersonas, 
            Supplier<Sucursal> sucursalActual) {
        this.padre = padre;
        this.rc = rc;
        this.tablaPersonas = tablaPersonas;
        this.modeloPersonas = modeloPersonas;
        this.sucursalActual = sucursalActual;
        
    }


    public void agregarPersona(Sucursal suc){
        if (suc == null) return;
        
        JTextField campoRut = new JTextField(12);
        JTextField campoNombre = new JTextField(20);
        JTextField campoFecha = new JTextField(10);
        
        JComboBox<String> campoCivil = new JComboBox<>(new String[]{
        "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"
        });
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        
        panel.add(new JLabel("RUT:"));          
        panel.add(campoRut);
        
        panel.add(new JLabel("Nombre:"));       
        panel.add(campoNombre);
        
        panel.add(new JLabel("Nacimiento (dd/mm/aaaa):"));   
        panel.add(campoFecha);
        
        panel.add(new JLabel("Estado civil:")); 
        panel.add(campoCivil);
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Agregar Persona a " + suc.getNombre(), 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String rut = campoRut.getText().trim();
        String nombre = campoNombre.getText().trim();
        
        if (rut.isEmpty() || nombre.isEmpty()){
            JOptionPane.showMessageDialog(padre, "RUT y Nombre son obligatorios.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Fecha fn = null;
        String fechaText = campoFecha.getText().trim();
        if (!fechaText.isEmpty()){
            String[] p = fechaText.split("[/.-]");
            if (p.length == 3){
                try{
                    int dia = Integer.parseInt(p[0]);
                    int mes = Integer.parseInt(p[1]);
                    int año = Integer.parseInt(p[2]);
                    fn = new Fecha(dia, mes, año);
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(padre, 
                            "Fecha inválida (usa (dd/mm/aaaa", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            else{
                JOptionPane.showMessageDialog(padre, "Fecha inválida (usa dd/mm/aaaa).",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        String civil = (String) campoCivil.getSelectedItem();
        
        Persona per = new Persona(rut, nombre, fn);
        per.setEstadoCivil(civil);
        Archivo nuevoArchivo = new Archivo();
        nuevoArchivo.setPersona(per);
        
        boolean ok = rc.validarRut(rut);
        if (ok){
            JOptionPane.showMessageDialog(padre, 
                    "Ya existe una persona con ese RUT en el registro.",
                "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        rc.agregarPersona(per);
        suc.agregarArchivo(rut, nuevoArchivo);
        
        modeloPersonas.setSucursal(suc);
        if (tablaPersonas.getRowCount() > 0) {
        tablaPersonas.scrollRectToVisible(tablaPersonas.getCellRect(0, 0, true));
        }
        
        JOptionPane.showMessageDialog(padre, "Persona agregada a " + suc.getNombre() + ".");
        
    }
    
    public void eliminarPersona(Persona p){
        if (p == null) return;
        
        Sucursal suc = sucursalActual.get();//aa
        if (suc == null) return;
        
        boolean ok = suc.eliminarArchivo(p.getRut());
        if (!ok){
            JOptionPane.showMessageDialog(padre, 
                    "No se pudo eliminar la persona seleccionada (rut no encontrado)",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        rc.eliminarPersona(p);
        modeloPersonas.setSucursal(suc);
        if (tablaPersonas.getRowCount() > 0) {
        tablaPersonas.scrollRectToVisible(tablaPersonas.getCellRect(0, 0, true));
        }
    }
    
    
    public void verDetalles(Persona p){
        if (p == null) return;

        String nac;
        if (p.getNacimiento() == null) {
            nac = "—";
        } else {
            nac = p.getNacimiento().getDia() + "/" +
                p.getNacimiento().getMes() + "/" +
                p.getNacimiento().getAño();
        }

        String nombre = (p.getNombre() == null) ? "—" : p.getNombre();
        String rut    = (p.getRut()    == null) ? "—" : p.getRut();
        String civil  = (p.getCivil()  == null) ? "—" : p.getCivil();

        JOptionPane.showMessageDialog(padre,
            "Nombre: " + nombre + "\n" +
            "RUT: "    + rut    + "\n" +
            "Nacimiento: " + nac + "\n" +
                "Estado civil: " + civil,
        "Persona", JOptionPane.INFORMATION_MESSAGE
        );
    }
}
