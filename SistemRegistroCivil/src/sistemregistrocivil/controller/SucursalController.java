
package sistemregistrocivil.controller;


import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import sistemregistrocivil.GestorCSV;
import sistemregistrocivil.model.*;
import sistemregistrocivil.view.table.*;

public class SucursalController {
    private final JFrame padre;
    private final RegistroCivil rc;
    private final GestorCSV gestor;
    private boolean datosCargados;
    
    private final JTable tablaSucursales;
    private final JTable tablaPersonas;
    private final SucursalesTableModel modeloSucursales;
    private final PersonasTableModel modeloPersonas;
    
    private final JButton btnSucursales, btnCertificados, btnPersonas;

    public SucursalController(JFrame padre, 
            RegistroCivil rc, GestorCSV gestor, JTable tablaSucursales, 
            JTable tablaPersonas, SucursalesTableModel modeloSucursales, 
            PersonasTableModel modeloPersonas, JButton btnSucursales, 
            JButton btnCertificados, JButton btnPersonas) {
        this.padre = padre;
        this.rc = rc;
        this.gestor = gestor;
        this.tablaSucursales = tablaSucursales;
        this.tablaPersonas = tablaPersonas;
        this.modeloSucursales = modeloSucursales;
        this.modeloPersonas = modeloPersonas;
        this.btnSucursales = btnSucursales;
        this.btnCertificados = btnCertificados;
        this.btnPersonas = btnPersonas;
        datosCargados = false;
    }
    
    public void conectarVistaControlador(){
        ListSelectionListener sel = e ->{
            if(!e.getValueIsAdjusting()) mostrarPersonasDeSucursalSeleccionada();
        };
        tablaSucursales.getSelectionModel().addListSelectionListener(sel);
        
        btnSucursales.addActionListener(e -> abrirSubmenuSucursales());
        btnCertificados.addActionListener(e -> abrirSubmenuCertificados());
        btnPersonas.addActionListener(e -> abrirSubMenuPersonas());
        
        
    }
    
    private void cargarDatos() throws IOException{
        if (datosCargados){
            JOptionPane.showMessageDialog(padre, "Los datos ya estaban cargados.");
            return;
        }
        gestor.cargarCsvSucursales(rc);
        gestor.cargarCsvPersonas(rc);
        datosCargados = true;
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
                cargarDatos();
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
        
    }
    
    private void listarCertificadosUI(){
        
    }
    
    private void abrirSubMenuPersonas(){
        //PENDIENTE
    }
    
    public boolean eliminarSucursal(String nombreSucursal){
        boolean ok = rc.eliminarSucursal(nombreSucursal);
        
        modeloSucursales.refrescar();
        modeloPersonas.setSucursal(null);
        
        return ok;
    }
    
    public void verDetallerSucursal(Sucursal suc){
        JOptionPane.showMessageDialog(padre,
            "Nombre: " + suc.getNombre() + "\n" +
            "Ciudad: " + suc.getUbicacion().getCiudad() + "\n" +
            "Comuna: " + suc.getUbicacion().getComuna() + "\n" +
            "Dirección: " + suc.getUbicacion().getDireccion(),
            "Sucursal", JOptionPane.INFORMATION_MESSAGE);
    }
}
