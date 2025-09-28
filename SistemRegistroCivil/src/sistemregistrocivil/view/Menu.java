package sistemregistrocivil.view;

import excepciones.InvalidFechaException;
import excepciones.RepetidoException;
import excepciones.SinSentidoException;
import sistemregistrocivil.model.*;

import javax.swing.*;
import java.awt.*;
import sistemregistrocivil.GestorCSV;

import sistemregistrocivil.view.panel.*;
import sistemregistrocivil.view.table.*;
import sistemregistrocivil.controller.*;
import sistemregistrocivil.view.popup.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Menu extends JFrame {
    private final RegistroCivil rc;
    private GestorCSV gestor;
    
    private SucursalController sucController;
    private PersonaController perController;
    private MenuContextualSucursales popupSucursales;
    private MenuContextualPersonas popupPersonas;
    
    private final JButton btnCargar; //botones
    private final JButton btnPersonas;
    private final JButton btnCertificados;
    private final JButton btnGuardar;
    
    private final JTable tabla = new JTable(); //tabla para mostrar datos
    private final JTable tablaPersonas = new JTable(); //tabla para personas por sucursal
    private final JTable tablaCertificados = new JTable();
    
    private final SucursalesTableModel modeloTabla;
    private final PersonasTableModel modeloPersonas;
    private final CertificadosTableModel modeloCertificados;
    
    
    public Menu(RegistroCivil rc){
        super("Registro Civil");
        this.rc = rc;
        gestor = new GestorCSV();
        
        modeloTabla = new SucursalesTableModel(rc);
        modeloPersonas = new PersonasTableModel();
        modeloCertificados = new CertificadosTableModel(rc);
        
        btnCargar = new JButton("Administrar Sucursales");
        btnPersonas = new JButton("Administrar Personas");
        btnCertificados = new JButton("Administrar certificados");
        btnGuardar = new JButton("Guardar datos");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE); //indica que cuando se cierre la ventana, se finalize con exito la ejecucion
        setSize(700, 400); //fijar el size de la ventana
        setLocationRelativeTo(null); //centra la ventana en la pantalla
        setLayout(new BorderLayout()); //divide la ventana en 5 zonas: NORTH, SOUTH, CENTER, EAST, WEST.
        
        add(new BarraSuperior(btnCargar, btnPersonas, btnCertificados, btnGuardar), BorderLayout.NORTH);
        
        tabla.setModel(modeloTabla);
        tablaPersonas.setModel(modeloPersonas);
        tablaCertificados.setModel(modeloCertificados);
     
        add(new PanelTablas(tabla, tablaPersonas), BorderLayout.CENTER); //agrega las tablas al centro y permite usar scroll del mouse
        
        sucController = new SucursalController(this, rc, gestor,
                        tabla, tablaPersonas, modeloTabla, modeloPersonas, modeloCertificados,
                        btnCargar, btnPersonas, btnGuardar, btnCertificados);
        
        sucController.conectarVistaControlador();
        
        Supplier<Sucursal> sucursalSeleccionada = () -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) return null;
            int fm = tabla.convertRowIndexToModel(fila);
            return modeloTabla.getAt(fm);
        };
        
        perController = new PersonaController(this, rc, tablaPersonas, 
                modeloPersonas, sucursalSeleccionada);
        
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        popupSucursales = new MenuContextualSucursales(
            () ->{
                int filaVista = tabla.getSelectedRow();
                if (filaVista < 0) return null;
                int filaModelo = tabla.convertRowIndexToView(filaVista);
                return modeloTabla.getAt(filaModelo);
            },
            s -> {
                JOptionPane.showMessageDialog(this, "Agregar persona en: " + s.getNombre());
            try {
                perController.agregarPersona(s);
            } catch (SinSentidoException ex) {
                JOptionPane.showMessageDialog(this, "RUT y Nombre son obligatorios.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (InvalidFechaException ex) {
                JOptionPane.showMessageDialog(this, 
                            "Fecha inválida (usa (dd/mm/aaaa", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
            } catch (RepetidoException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Ya existe una persona con ese RUT en el registro.",
                "Duplicado", JOptionPane.WARNING_MESSAGE);
            }
            },
            s -> {
                int r = JOptionPane.showConfirmDialog(this, 
                        "¿Eliminar " + s.getNombre() + "?", "Confirmar", 
                        JOptionPane.OK_CANCEL_OPTION);
                
                if (r == JOptionPane.OK_OPTION){
                    boolean ok = sucController.eliminarSucursal(s.getNombre());
                    if (!ok){
                        JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar (no encontrada).",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            },
            s -> sucController.verDetallesSucursal(s)
        );
        
        
        
        popupPersonas = new MenuContextualPersonas(
            () -> {
                int fila = tablaPersonas.getSelectedRow();
                if (fila < 0) return null;
                int filaModelo = tablaPersonas.convertRowIndexToModel(fila);
                return modeloPersonas.getAt(filaModelo);
            },
            p -> {
                int confirmacion = JOptionPane.showConfirmDialog(this, 
                        "¿Desea eliminar a " + p.getNombre() + "?", "Confirmar",
                        JOptionPane.OK_CANCEL_OPTION);
                if (confirmacion == JOptionPane.OK_OPTION){
                    perController.eliminarPersona(p);
                }
            },
            p -> perController.verDetalles(p)
        
        );
        
        
        MenuContextualSucursales.instalarEnTabla(tabla, popupSucursales);
        MenuContextualPersonas.instalarEnTabla(tablaPersonas, popupPersonas);

        UIManager.put("OptionPane.okButtonText", "Ok"); //cambiar los botones a español
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
    }
    
    public void mostrar(){
        setVisible(true);
    }
}