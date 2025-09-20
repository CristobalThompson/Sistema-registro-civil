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
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private final RegistroCivil rc;
    private boolean datosCargados = false;
    
    private final JButton btnCargar = new JButton("Cargar datos");
    private final JButton btnSucursales = new JButton("Mostrar Sucursales");
    private final JButton btnCertificados = new JButton("Certificados");
    private final JTable tabla = new JTable();
    private final DefaultTableModel modeloTabla =
            new DefaultTableModel(new Object[]{"ID", "Nombre", "Ciudad"}, 0);
    
    public Menu(RegistroCivil rc){
        super("Registro Civil");
        this.rc = rc;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barra.add(btnCargar);
        barra.add(btnSucursales);
        barra.add(btnCertificados);
        add(barra, BorderLayout.NORTH);
        
        tabla.setModel(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        
        btnCargar.addActionListener(e -> cargarDatos());
        btnSucursales.addActionListener(e -> cargarTablaSucursales());
        btnCertificados.addActionListener(e -> abrirSubmenuCertificados());
        
        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
    }
    
    public void mostrar(){
        setVisible(true);
    }
    
    private void cargarDatos(){
        if (datosCargados){
            JOptionPane.showMessageDialog(this, "Los datos ya estaban cargados.");
            return;
        }
        SistemRegistroCivil.leerDatos(rc);
        datosCargados = true;
        JOptionPane.showMessageDialog(this, "Datos cargados correctamente.");
    }
    
    private void cargarTablaSucursales(){
        DefaultTableModel m = (DefaultTableModel) tabla.getModel();
        
        m.setRowCount(0);
        m.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Ciudad"});
        
        map();
        
    }
    
    private void map(){
        DefaultTableModel m = (DefaultTableModel) tabla.getModel();
        java.util.Map<String, Sucursal> mapa = rc.getSucursales(); // <- tu método real

        if (mapa == null || mapa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay sucursales cargadas.");
            return;
        }
        for (Sucursal s : mapa.values()) {
            m.addRow(new Object[]{
                s.getID(),
                s.getNombre(),
                s.getUbicacion().getCiudad()
            });
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
