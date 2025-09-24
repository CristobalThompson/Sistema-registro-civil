package sistemregistrocivil.view.popup;

import javax.swing.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import sistemregistrocivil.model.Sucursal;

public class MenuContextualSucursales extends JPopupMenu {
    JMenuItem agregarPersona = new JMenuItem("Agregar persona");
    JMenuItem eliminarSuc   = new JMenuItem("Eliminar sucursal");
    JMenuItem verDetalles   = new JMenuItem("Ver detalles");
    
    public MenuContextualSucursales(
            Supplier<Sucursal> selectedSucursalSupplier,
            Consumer<Sucursal> onAgregarPersona,
            Consumer<Sucursal> onEliminarSucursal,
            Consumer<Sucursal> onVerDetalles){
        
        add(agregarPersona);
        add(eliminarSuc);
        addSeparator();
        add(verDetalles);
        
        agregarPersona.addActionListener(e ->{
            Sucursal suc = selectedSucursalSupplier.get();
            if (suc != null && onAgregarPersona != null)
                onAgregarPersona.accept(suc);
        });
        
        eliminarSuc.addActionListener(e ->{
            Sucursal suc = selectedSucursalSupplier.get();
            if (suc != null && onEliminarSucursal != null)
                onEliminarSucursal.accept(suc);
        });
        
        verDetalles.addActionListener(e -> {
            Sucursal suc = selectedSucursalSupplier.get();
            if (suc != null && onVerDetalles  != null)
                onVerDetalles.accept(suc);
        });
    }
    
    public static void instalarEnTabla(JTable tabla, JPopupMenu popup){
        tabla.addMouseListener(new MouseAdapter() {
            private void maybeShow(MouseEvent e){
               if (!e.isPopupTrigger()) return;
               int filaVista = tabla.rowAtPoint(e.getPoint());
               if (filaVista < 0) return;
               tabla.setRowSelectionInterval(filaVista, filaVista);
               popup.show(e.getComponent(), e.getX(), e.getY());
           }
           
            @Override
            public void mousePressed(MouseEvent e){
               maybeShow(e);
            }
           
            @Override
            public void mouseReleased(MouseEvent e){
               maybeShow(e);
            }
        });
    }
}
