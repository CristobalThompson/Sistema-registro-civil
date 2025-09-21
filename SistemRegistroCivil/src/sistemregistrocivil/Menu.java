package sistemregistrocivil;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author franc
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;


public class Menu extends JFrame {
    private final RegistroCivil rc;
    private GestorCSV gestor;
    private boolean datosCargados = false;
    private Integer ultimaFilaModelo = null;
    
    private final JButton btnCargar = new JButton("Cargar datos"); //botones
    private final JButton btnSucursales = new JButton("Mostrar Sucursales");
    private final JButton btnCertificados = new JButton("Certificados");
    
    private final JTable tabla = new JTable(); //tabla para mostrar datos
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new Object[]{"ID", "Nombre", "Ciudad"}, 0);
    private final JTable tablaPersonas = new JTable(); //tabla para personas por sucursal
    private final DefaultTableModel modeloPersonas =
            new DefaultTableModel(new Object[]{"Rut", "Nombre"}, 0);
    
    
    public Menu(RegistroCivil rc){
        super("Registro Civil");
        this.rc = rc;
        gestor = new GestorCSV();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE); //indica que cuando se cierre la ventana, se finalize con exito la ejecucion
        setSize(700, 400); //fijar el size de la ventana
        setLocationRelativeTo(null); //centra la ventana en la pantalla
        setLayout(new BorderLayout()); //divide la ventana en 5 zonas: NORTH, SOUTH, CENTER, EAST, WEST.
        
     
        
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT)); //panel para los botones
        barra.add(btnCargar);
        barra.add(btnSucursales);
        barra.add(btnCertificados);
        add(barra, BorderLayout.NORTH); //inserta la barra en la zona north de la ventana
        
        tabla.setModel(modeloTabla);
        tablaPersonas.setModel(modeloPersonas);
        
        JScrollPane spSuc = new JScrollPane(tabla); //para sucursales
        JScrollPane spPer = new JScrollPane(tablaPersonas);//para personas de las sucursales
        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spSuc, spPer);
        split.setResizeWeight(0.65);          // ~65% para sucursales
        split.setOneTouchExpandable(true);    // botoncitos para colapsar
        
        
        add(split, BorderLayout.CENTER); //agrega las tablas al centro y permite usar scroll del mouse
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()){
                mostrarPersonasDeSucursalSeleccionada();
            }
        });
        
        //agregar funcionalidad a los botones cuando se hace click
        btnCargar.addActionListener(e -> {
            try{
                cargarDatos();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(this, "No se pudo cargar las sucursales");
            }}); //"e ->" expresion lambda
        
        btnSucursales.addActionListener(e -> cargarTablaSucursales());
        btnCertificados.addActionListener(e -> abrirSubmenuCertificados());
        
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
        DefaultTableModel m = (DefaultTableModel) tabla.getModel();
        
        m.setRowCount(0);
        m.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Ciudad"});
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.getColumnModel().getColumn(0).setMinWidth(30);
        
        map();
        
    }
    
    private void map(){
        DefaultTableModel m = (DefaultTableModel) tabla.getModel();
        java.util.Map<String, Sucursal> mapa = rc.getSucursales();

        if (mapa == null || mapa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay sucursales cargadas.");
            return;
        }
        for (int i = 0; i < rc.getTotalClaves(); ++i) {
            Sucursal suc = rc.getSucursal(i);
            
            if(suc == null) continue;
            m.addRow(new Object[]{
                suc.getID(),
                suc.getNombre(),
                suc.getUbicacion().getCiudad()
            });
        }
    }
    
    private void mostrarPersonasDeSucursalSeleccionada(){
        int filaVista = tabla.getSelectedRow();
        if (filaVista < 0){
            modeloPersonas.setRowCount(0);
            ultimaFilaModelo = null;
            return;
        }
        
        int filaModelo = tabla.convertRowIndexToModel(filaVista);
        
        if (Objects.equals(ultimaFilaModelo, filaModelo)) return;
        ultimaFilaModelo = filaModelo;
        
        modeloPersonas.setRowCount(0);
        
        Sucursal suc = rc.getSucursal(filaModelo);
        
        for (int i = 0; i < suc.getTotalArchivos(); ++i){
            Archivo arc = suc.getArchivo(i);
            if (arc == null) continue;
            Persona p = arc.getPersona();
            modeloPersonas.addRow(new Object[]{p.getRut(), p.getNombre()});
        }
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
}
