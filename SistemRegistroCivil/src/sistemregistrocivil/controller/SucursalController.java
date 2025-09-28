
package sistemregistrocivil.controller;


import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import sistemregistrocivil.GestorCSV;
import sistemregistrocivil.model.*;
import sistemregistrocivil.view.table.*;

public class SucursalController {
    private final JFrame padre;
    private final RegistroCivil rc;
    private final GestorCSV gestor;
    private boolean datosCargadosP;
    private boolean datosCargadosS;
    
    private final JTable tablaSucursales;
    private final JTable tablaPersonas;
    private final SucursalesTableModel modeloSucursales;
    private final PersonasTableModel modeloPersonas;
    
    private final JButton btnSucursales, btnPersonas, btnGuardar, btnCertificados;

    public SucursalController(JFrame padre, 
            RegistroCivil rc, GestorCSV gestor, JTable tablaSucursales, 
            JTable tablaPersonas, SucursalesTableModel modeloSucursales, 
            PersonasTableModel modeloPersonas, JButton btnSucursales, 
            JButton btnPersonas, JButton btnGuardar, JButton btnCertificados) {
        this.padre = padre;
        this.rc = rc;
        this.gestor = gestor;
        this.tablaSucursales = tablaSucursales;
        this.tablaPersonas = tablaPersonas;
        this.modeloSucursales = modeloSucursales;
        this.modeloPersonas = modeloPersonas;
        this.btnSucursales = btnSucursales;
        this.btnPersonas = btnPersonas;
        this.btnCertificados = btnCertificados;
        this.btnGuardar = btnGuardar;
        datosCargadosP = false;
        datosCargadosS = false;
    }
    
    public void conectarVistaControlador(){
        ListSelectionListener sel = e ->{
            if(!e.getValueIsAdjusting()) mostrarPersonasDeSucursalSeleccionada();
        };
        tablaSucursales.getSelectionModel().addListSelectionListener(sel);
        
        btnSucursales.addActionListener(e -> abrirSubmenuSucursales());
        btnPersonas.addActionListener(e -> abrirSubMenuPersonas());
        btnCertificados.addActionListener(e -> abrirSubmenuCertificados());
        btnGuardar.addActionListener(e -> abrirSubMenuGuardar());
    }
    
    private void cargarDatosP() throws IOException{
        if (!datosCargadosS){
            JOptionPane.showMessageDialog(padre, 
                    "Se requiere primero cargar datos de sucursales");
            return;
        }
            
        
        if (datosCargadosP){
            JOptionPane.showMessageDialog(padre, "Los datos ya estaban cargados.");
            return;
        }
        gestor.cargarCsvPersonas(rc);
        datosCargadosP = true;
        JOptionPane.showMessageDialog(padre, "Datos cargados correctamente.");
    }
    
    private void cargarDatosS() throws IOException{
        if (datosCargadosS){
            JOptionPane.showMessageDialog(padre, "Los datos ya estaban cargados.");
            return;
        }
        gestor.cargarCsvSucursales(rc);
        datosCargadosS = true;
        JOptionPane.showMessageDialog(padre, "Datos cargados correctamente.");
    }
    
    public void cargarTablaSucursales(){
        
        modeloSucursales.refrescar();
        tablaSucursales.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaSucursales.getColumnModel().getColumn(0).setMaxWidth(50);
        tablaSucursales.getColumnModel().getColumn(0).setMinWidth(30);

        if (rc.getTotalClaves() == 0) {
            JOptionPane.showMessageDialog(padre, "No hay sucursales cargadas.");
            return;
        }
    }
    
    private void mostrarPersonasDeSucursalSeleccionada(){
        int filaVista = tablaSucursales.getSelectedRow();
        if (filaVista < 0){
            modeloPersonas.setSucursal(null);
            return;
        }
        
        int filaModelo = tablaSucursales.convertRowIndexToModel(filaVista);
        
        Sucursal suc = modeloSucursales.getAt(filaModelo);
        
        modeloPersonas.setSucursal(suc);
        
        if (tablaPersonas.getRowCount() > 0)
            tablaPersonas.scrollRectToVisible(tablaPersonas.getCellRect(0, 0, true));
    }
    
    private void abrirSubmenuSucursales(){
        JDialog jdl = new JDialog(padre, "Administrar sucursales", true);
        jdl.setLayout(new GridLayout(0,1,8,8));
        jdl.setSize(320, 200);
        jdl.setLocationRelativeTo(padre);
        
        JButton btnCsv = new JButton("Cargar sucursales vía CSV ");
        JButton btnAgregar = new JButton("Agregar nueva sucursal");
        JButton btnEliminar = new JButton("Eliminar sucursal");
        JButton btnCerrar = new JButton("Cerrar");
        
        jdl.add(btnCsv);
        jdl.add(btnAgregar);
        jdl.add(btnEliminar);
        jdl.add(btnCerrar);
        
        btnCsv.addActionListener(ev ->{
            try{
                cargarDatosS();
                cargarTablaSucursales();
                modeloPersonas.setSucursal(null);
                jdl.dispose();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(padre, "No se pudo cargar las sucursales");
            }
        });
        
        btnAgregar.addActionListener(ev ->{
            agregarSucursalSwing();
            modeloPersonas.setSucursal(null);
        });
        
        btnEliminar.addActionListener(ev ->{
            if (rc.getTotalClaves() == 0){
                JOptionPane.showMessageDialog(padre, "No hay sucursales para eliminar", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            else{
                eliminarSucursalSwing();
                cargarTablaSucursales();
                modeloPersonas.setSucursal(null); 
            }
        });
        
        btnCerrar.addActionListener(ev -> jdl.dispose()); //cerrar jdl
        
        jdl.setVisible(true);
    }
   
    private void agregarSucursalSwing(){
        JTextField campoNombre = new JTextField(20);
        JTextField campoRegion = new JTextField(20);
        JTextField campoCiudad = new JTextField(20);
        JTextField campoComuna = new JTextField(20);
        JTextField campoDireccion = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        
        panel.add(new JLabel("Región:"));
        panel.add(campoRegion);
        
        panel.add(new JLabel("Ciudad:"));
        panel.add(campoCiudad);
        
        panel.add(new JLabel("Comuna:"));
        panel.add(campoComuna);
        
        panel.add(new JLabel("Dirección:"));
        panel.add(campoDireccion);
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Agregar sucursal", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String nombre = campoNombre.getText().trim();
        
        //esto puede ser un Exception, para cambiar esto dsp
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(padre, "El nombre es obligatorio", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        int id = rc.getTotalClaves() + 1;
        
        Ubicacion ubi = new Ubicacion(campoRegion.getText().trim(),
                                      campoCiudad.getText().trim(),
                                      campoComuna.getText().trim(),
                                      campoDireccion.getText().trim());
        Sucursal suc = new Sucursal(id, nombre, ubi);
        
        if (rc.agregarSucursal(nombre, suc)){
            JOptionPane.showMessageDialog(padre, "Sucursal agregada correctamente.");
        }
        else{
            JOptionPane.showMessageDialog(padre, "Ya existía una sucursal con ese nombre.",
                                      "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        cargarTablaSucursales();   
    }
    
    private void eliminarSucursalSwing(){
        JTextField campoNombre = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nombre de la sucursal a eliminar:"));
        panel.add(campoNombre);
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Eliminar sucursal", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String nombre = campoNombre.getText().trim();
        
        //esto puede ser un Exception, para cambiar esto dsp
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(padre, "El nombre es obligatorio", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if (rc.eliminarSucursal(nombre))
            JOptionPane.showMessageDialog(padre, "Sucursal " + nombre + " eliminado");
        
        else JOptionPane.showMessageDialog(padre, 
                "No se pudo eliminar (no encontrada).",
                "Aviso", JOptionPane.WARNING_MESSAGE);
    }
    
    private void abrirSubmenuCertificados(){
        String[] ops = {"Emitir", "Listar", "Cerrar"};
        String op = (String) JOptionPane.showInputDialog(
            padre, "Elige", "Certificados",
            JOptionPane.QUESTION_MESSAGE, null, ops, ops[0]);
        if (op == null || op.equals("Cerrar")) return;
        
        switch(op){
            case "Emitir" :
                emitirCertificadoUI();
                break;
            case "Listar" :
                listarCertificadosUI();
                break;
        }
    }
    
    private void emitirCertificadoUI(){
        JTextField campoRut = new JTextField(20);
        
        JComboBox<String> opciones = new JComboBox<>(new String[]{
        "Nacimiento", "Matrimonio", "Defusión"});
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("rut de la persona:"));
        panel.add(campoRut);
        
        panel.add(new JLabel("Tipo de certificado:"));
        panel.add(opciones);
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Emitir certificado", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String rut = campoRut.getText().trim();
        
        if (rut.isEmpty()){
            JOptionPane.showMessageDialog(padre, "RUT es obligatorio.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!rc.validarRut(rut)){
            JOptionPane.showMessageDialog(padre, "RUT no registrado",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Archivo arc = null;
        
        for (int i = 0; i < rc.getTotalClaves(); ++i){
            Sucursal suc = rc.getSucursal(i);
            if (suc == null) continue;
            arc = suc.getArchivo(rut);
            if (arc != null) break;
        }
        
        String tipo = (String) opciones.getSelectedItem();
        
        switch(tipo){
            case "Nacimiento" :
                Certificado cerNaci = arc.generarNacimiento();
                rc.agregarCertificado(cerNaci);
                break;
            case "Defusión" :
                Certificado cerFalle = arc.generarDefusion();
                if (cerFalle == null){
                    JOptionPane.showMessageDialog(padre, 
                            "La persona aun no fallece",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                rc.agregarCertificado(cerFalle);
                break;
            case "Matrimonio" :
                Certificado cerMatri = arc.generarMatrimonio();
                if (cerMatri == null){
                    JOptionPane.showMessageDialog(padre, 
                            "La persona aun no se casa",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                rc.agregarCertificado(cerMatri);
                break;
            default :
                JOptionPane.showMessageDialog(padre, 
                            "Ocurrio un error",
                    "Aviso", JOptionPane.WARNING_MESSAGE);   
        }
    }
    
    private void listarCertificadosUI(){
        
    }
    
    private void abrirSubMenuPersonas(){
        JDialog jdl = new JDialog(padre, "Administrar Personas", true);
        jdl.setLayout(new GridLayout(0,1,8,8));
        jdl.setSize(390, 300);
        jdl.setLocationRelativeTo(padre);
        
        JButton btnCsv = new JButton("Cargar personas vía CSV (cargar sucursales primero)");
        JButton btnAgregar = new JButton("Agregar nueva persona");
        JButton btnModificar = new JButton("Modificar persona existente");
        JButton btnEliminar = new JButton("Eliminar persona");
        JButton btnCerrar = new JButton("Cerrar");
        
        jdl.add(btnCsv);
        jdl.add(btnAgregar);
        jdl.add(btnModificar);
        jdl.add(btnEliminar);
        jdl.add(btnCerrar);
        
        btnCsv.addActionListener(ev ->{
            try{
                cargarDatosP();
                cargarTablaSucursales();
                modeloPersonas.setSucursal(null);
                jdl.dispose();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(padre, "No se pudo cargar las personas");
            }
        });
        
        btnAgregar.addActionListener(ev ->{
            if (rc.getTotalClaves() == 0){
                JOptionPane.showMessageDialog(padre, "No hay sucursales para agregar a alguien", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            else{
                agregarPersonaSwing();
                modeloPersonas.setSucursal(null);
            }
        });
        
        btnModificar.addActionListener(ev ->{
            if (rc.getTotalPersonas() == 0){
                JOptionPane.showMessageDialog(padre, "No hay personas para modificar", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            else{
                modificarPersonaSwing();
                cargarTablaSucursales();
                modeloPersonas.setSucursal(null);
            }
        });
        
        btnEliminar.addActionListener(ev ->{
            if (rc.getTotalPersonas() == 0){
                JOptionPane.showMessageDialog(padre, "No hay personas para eliminar", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            else{
                eliminarPersonaSwing();
                cargarTablaSucursales();
                modeloPersonas.setSucursal(null);
            }
        });
        
        btnCerrar.addActionListener(ev -> jdl.dispose()); //cerrar jdl
        
        jdl.setVisible(true);
    }
    
    private void agregarPersonaSwing(){
        JTextField campoRut = new JTextField(12);
        JTextField campoNombre = new JTextField(20);
        JTextField campoFecha = new JTextField(10);
        
        JComboBox<String> campoCivil = new JComboBox<>(new String[]{
        "Soltero/a", "Casado/a", "Divorciado/a", "Viudo/a"
        });
        
        ArrayList<String> opciones = rc.getClavesSucursales();
        DefaultComboBoxModel<String> model = 
                new DefaultComboBoxModel<>(opciones.toArray(new String[0]));
        
        JComboBox<String> combo = new JComboBox<>(model);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        
        panel.add(new JLabel("RUT:"));          
        panel.add(campoRut);
        
        panel.add(new JLabel("Nombre:"));       
        panel.add(campoNombre);
        
        panel.add(new JLabel("Nacimiento (dd/mm/aaaa):"));   
        panel.add(campoFecha);
        
        panel.add(new JLabel("Estado civil:")); 
        panel.add(campoCivil);
        
        panel.add(new JLabel("Seleccione una sucursal"));
        panel.add(combo);
        
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Agregar Persona", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String nombreSuc = (String) combo.getSelectedItem();
        Sucursal suc = rc.getSucursal(nombreSuc);
        
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
    
    private void modificarPersonaSwing(){
        //casarse o declarar fallecido
    }
    
    private void eliminarPersonaSwing(){
        JTextField campoRut = new JTextField(12);
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("RUT a buscar:"));          
        panel.add(campoRut);
        
        int confirmacion = JOptionPane.showConfirmDialog(padre, panel, 
                "Eliminar Persona", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String rut = campoRut.getText().trim();
        
        if (rut.isEmpty()){
            JOptionPane.showMessageDialog(padre, "RUT es obligatorio",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        } 
        
        if (!rc.validarRut(rut)){
            JOptionPane.showMessageDialog(padre, "El rut ingresado no esta en el sistema",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for(int i = 0; i < rc.getTotalClaves(); ++i){
            Sucursal suc = rc.getSucursal(i);
            Archivo arc = suc.getArchivo(rut);
            if (arc != null) {
                Persona p = arc.getPersona();
                rc.eliminarPersona(p);
                suc.eliminarArchivo(rut, arc);
                
                JOptionPane.showMessageDialog(padre, "Persona " + p.getNombre() 
                        + " eliminada");
                return;
            }
        }
    }
    
    public boolean eliminarSucursal(String nombreSucursal){
        boolean ok = rc.eliminarSucursal(nombreSucursal);
        
        modeloSucursales.refrescar();
        modeloPersonas.setSucursal(null);
        
        return ok;
    }
    
    public void verDetallesSucursal(Sucursal suc){
        JOptionPane.showMessageDialog(padre,
            "Nombre: " + suc.getNombre() + "\n" +
            "Ciudad: " + suc.getUbicacion().getCiudad() + "\n" +
            "Comuna: " + suc.getUbicacion().getComuna() + "\n" +
            "Dirección: " + suc.getUbicacion().getDireccion(),
            "Sucursal", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirSubMenuGuardar(){
        JDialog jdl = new JDialog(padre, "Guardar datos", true);
        jdl.setLayout(new GridLayout(0,1,8,8));
        jdl.setSize(390, 200);
        jdl.setLocationRelativeTo(padre);
        
        JButton guarSucCsv = new JButton("Guardar datos de sucursales en CSV");
        JButton guarPerCsv = new JButton("Guardar datos de personas en CSV");
        JButton guarCerCsv = new JButton("Guardar datos de certificados en CSV");
        
        jdl.add(guarSucCsv);
        jdl.add(guarPerCsv);
        jdl.add(guarCerCsv);
        
        guarSucCsv.addActionListener(e -> {
            try{
                gestor.guardarCsvSucursales(rc);
                JOptionPane.showMessageDialog(padre, 
                        "datos de sucursales guardados con exito");
                
            }catch(IOException ex){
                JOptionPane.showMessageDialog(padre, 
                        "No se pudo guardar los datos de las sucursales", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        guarPerCsv.addActionListener(e ->{
            try{
                gestor.guardarCsvPersonas(rc);
                JOptionPane.showMessageDialog(padre, 
                        "datos de personas guardados con exito");
            }catch(IOException ex){
                JOptionPane.showMessageDialog(padre, 
                        "No se pudo guardar los datos de las personas",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        guarCerCsv.addActionListener(e ->{
            try{
                gestor.guardarCertificados(rc);
                JOptionPane.showMessageDialog(padre, 
                        "datos de certificados guardados con exito");
            }catch(IOException ex){
                JOptionPane.showMessageDialog(padre, 
                        "No se pudo guardar los datos de los certificados",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        jdl.setVisible(true);
    }
}
