package sistemregistrocivil.view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author franc
 */
import sistemregistrocivil.model.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import sistemregistrocivil.GestorCSV;

import sistemregistrocivil.view.panel.*;
import sistemregistrocivil.view.table.*;


public class Menu extends JFrame {
    private final RegistroCivil rc;
    private GestorCSV gestor;
    private boolean datosCargados;
    private Integer ultimaFilaModelo;
    private int filaModeloParaPopup;
    
    private final JButton btnCargar = new JButton("Administrar Sucursales"); //botones
    private final JButton btnCertificados = new JButton("Certificados");
    private final JButton btnPersonas = new JButton("Administrar Personas");
    
    private final JTable tabla = new JTable(); //tabla para mostrar datos
    private final JTable tablaPersonas = new JTable(); //tabla para personas por sucursal
    
    private final SucursalesTableModel modeloTabla;
    private final PersonasTableModel modeloPersonas = new PersonasTableModel();
    
    
    public Menu(RegistroCivil rc){
        super("Registro Civil");
        this.rc = rc;
        gestor = new GestorCSV();
        datosCargados = false;
        ultimaFilaModelo = null;
        filaModeloParaPopup = -1;
        modeloTabla = new SucursalesTableModel(rc);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE); //indica que cuando se cierre la ventana, se finalize con exito la ejecucion
        setSize(700, 400); //fijar el size de la ventana
        setLocationRelativeTo(null); //centra la ventana en la pantalla
        setLayout(new BorderLayout()); //divide la ventana en 5 zonas: NORTH, SOUTH, CENTER, EAST, WEST.
        
        add(new BarraSuperior(btnCargar, btnCertificados, btnPersonas), BorderLayout.NORTH);
        
        tabla.setModel(modeloTabla);
        tablaPersonas.setModel(modeloPersonas);
     
        add(new PanelTablas(tabla, tablaPersonas), BorderLayout.CENTER); //agrega las tablas al centro y permite usar scroll del mouse
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                mostrarPersonasDeSucursalSeleccionada();
            }
        });
        
        instalarMenu();
        
        //agregar funcionalidad a los botones cuando se hace click
        
        btnCargar.addActionListener(e -> abrirSubmenuSucursales());
        btnCertificados.addActionListener(e -> abrirSubmenuCertificados());
        btnPersonas.addActionListener(e -> abrirSubMenuPersonas());
        
        UIManager.put("OptionPane.okButtonText", "OK"); //cambiar los botones a español
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
    }
    
    public void mostrar(){
        setVisible(true);
    }
    
    private void cargarDatos() throws IOException{
        if (datosCargados){
            JOptionPane.showMessageDialog(this, "Los datos ya estaban cargados.");
            return;
        }
        gestor.cargarCsvSucursales(rc);
        gestor.cargarCsvPersonas(rc);
        datosCargados = true;
        JOptionPane.showMessageDialog(this, "Datos cargados correctamente.");
    }
    
    private void cargarTablaSucursales(){
        //DefaultTableModel m = (DefaultTableModel) tabla.getModel();
        
        modeloTabla.refrescar();
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.getColumnModel().getColumn(0).setMinWidth(30);

        if (rc.getTotalClaves() == 0) {
            JOptionPane.showMessageDialog(this, "No hay sucursales cargadas.");
            return;
        }
        /*for (int i = 0; i < rc.getTotalClaves(); ++i) {
            Sucursal suc = rc.getSucursal(i);
            
            if(suc == null) continue;
            m.addRow(new Object[]{
                suc.getID(),
                suc.getNombre(),
                suc.getUbicacion().getCiudad(),
                suc.getUbicacion().getDireccion()
            });
        }*/
    }
    
    private void mostrarPersonasDeSucursalSeleccionada(){
        int filaVista = tabla.getSelectedRow();
        if (filaVista < 0){
            modeloPersonas.setSucursal(null);
            return;
        }
        
        int filaModelo = tabla.convertRowIndexToModel(filaVista);
        
        if (Objects.equals(ultimaFilaModelo, filaModelo)) return;
        ultimaFilaModelo = filaModelo; //que no haga nada si clickea la misma casilla
        
        Sucursal suc = modeloTabla.getAt(filaModelo);
        
        modeloPersonas.setSucursal(suc);
        
    }
    
    private void abrirSubmenuSucursales(){
        JDialog jdl = new JDialog(this, "Administrar sucursales", true);
        jdl.setLayout(new GridLayout(0,1,8,8));
        jdl.setSize(320, 200);
        jdl.setLocationRelativeTo(this);
        
        JButton btnCsv = new JButton("Cargar sucursales via CSV ");
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
                JOptionPane.showMessageDialog(this, "No se pudo cargar las sucursales");
            }});
        
        btnAgregar.addActionListener(ev ->{
            agregarSucursalSwing();
            modeloPersonas.setSucursal(null);
        });
        
        btnEliminar.addActionListener(ev ->{
            if (rc.getTotalClaves() == 0){
                JOptionPane.showMessageDialog(this, "No hay sucursales para eliminar", 
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
        
        int confirmacion = JOptionPane.showConfirmDialog(this, panel, 
                "Agregar sucursal", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String nombre = campoNombre.getText().trim();
        
        //esto puede ser un Exception, para cambiar esto dsp
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", 
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
            JOptionPane.showMessageDialog(this, "Sucursal agregada correctamente.");
        }
        else{
            JOptionPane.showMessageDialog(this, "Ya existía una sucursal con ese nombre.",
                                      "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        cargarTablaSucursales();
        
    }
    
    private void eliminarSucursalSwing(){
        JTextField campoNombre = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("Nombre de la sucursal a eliminar:"));
        panel.add(campoNombre);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, panel, 
                "Eliminar sucursal", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        
        if (confirmacion != JOptionPane.OK_OPTION) return;
        
        String nombre = campoNombre.getText().trim();
        
        //esto puede ser un Exception, para cambiar esto dsp
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if (rc.eliminarSucursal(nombre))
            JOptionPane.showMessageDialog(this, "Sucursal " + nombre + " eliminado");
        
        else JOptionPane.showMessageDialog(this, 
                "No se pudo eliminar (no encontrada).",
                "Aviso", JOptionPane.WARNING_MESSAGE);
       
    }
    
    private void abrirSubMenuPersonas(){
        //PENDIENTE
    }
    
    private void abrirSubmenuCertificados(){
        String[] ops = {"Emitir", "Listar", "Cerrar"};
        String op = (String) JOptionPane.showInputDialog(
            this, "Elige", "Certificados",
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
        JTextField rut = new JTextField();
        JTextField tipo = new JTextField();
        
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("RUT:"));
        panel.add(rut);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipo);
        
        int ok = JOptionPane.showConfirmDialog(this, panel, "Emitir Certificado",
                                               JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            // rc.emitirCertificado(rut.getText(), tipo.getText());
            JOptionPane.showMessageDialog(this, "Certificado emitido (demo).");
        }
    }
    
    private void listarCertificadosUI(){
        
        DefaultTableModel m = new DefaultTableModel(new Object[]{"Folio","RUT","Tipo","Fecha"}, 0);
        
        
        m.addRow(new Object[]{123, "11.111.111-1", "Nacimiento", "2025-09-19"});
        JTable t = new JTable(m);
        JOptionPane.showMessageDialog(this, new JScrollPane(t), "Certificados", JOptionPane.PLAIN_MESSAGE);
    }
    
    private JPopupMenu crearMenuContextualSucursales(){
        JPopupMenu menu = new JPopupMenu();
        
        JMenuItem agregarPersona = new JMenuItem("Agregar persona");
        JMenuItem eliminarSuc   = new JMenuItem("Eliminar sucursal");
        JMenuItem verDetalles   = new JMenuItem("Ver detalles");
        
        agregarPersona.addActionListener(ev ->{
            if (filaModeloParaPopup < 0) return;
            Sucursal suc = rc.getSucursal(filaModeloParaPopup);
            if (suc == null) return;
            //agregarPersonaDialog(suc);
        });
        
        eliminarSuc.addActionListener(ev ->{
            if (filaModeloParaPopup < 0) return;
            Sucursal suc = rc.getSucursal(filaModeloParaPopup);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                    "¿Eliminar \"" + suc.getNombre() + "\"?", "Confirmar",
                    JOptionPane.OK_CANCEL_OPTION);
            
            if (confirmacion == JOptionPane.OK_OPTION){
                if (rc.eliminarSucursal(suc.getNombre())){
                    cargarTablaSucursales();
                    modeloPersonas.setSucursal(null);
                }
                else{
                    JOptionPane.showMessageDialog(this, 
                            "No se pudo eliminar (no encontrada).", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        verDetalles.addActionListener(ev -> {
            if (filaModeloParaPopup < 0) return;
            Sucursal suc = rc.getSucursal(filaModeloParaPopup);
            if (suc == null) return;
            JOptionPane.showMessageDialog(this,
            "Nombre: " + suc.getNombre() + "\n" +
            "Ciudad: " + suc.getUbicacion().getCiudad() + "\n" +
            "Comuna: " + suc.getUbicacion().getComuna() + "\n" +
            "Dirección: " + suc.getUbicacion().getDireccion(),
            "Sucursal", JOptionPane.INFORMATION_MESSAGE);
        });
        
        menu.add(agregarPersona);
        menu.add(eliminarSuc);
        menu.addSeparator();
        menu.add(verDetalles);
        
        return menu;
    }
    
    private void instalarMenu(){
        JPopupMenu menu = crearMenuContextualSucursales();
        
        tabla.addMouseListener(new MouseAdapter(){
            private void maybeShowPopup(MouseEvent e){
                if (!e.isPopupTrigger()) return; //solo el click derecho
                int filaVista = tabla.rowAtPoint(e.getPoint());
                if (filaVista < 0) return;
                
                tabla.setRowSelectionInterval(filaVista, filaVista);
                filaModeloParaPopup = tabla.convertRowIndexToModel(filaVista);
                
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
            
            @Override
            public void mousePressed(MouseEvent e){
                maybeShowPopup(e);
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
                maybeShowPopup(e);
            }
        });
    }
}
