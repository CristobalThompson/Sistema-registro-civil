package sistemregistrocivil.view.popup;

import javax.swing.*;
import java.awt.event.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import sistemregistrocivil.model.*;

public class MenuContextualPersonas extends JPopupMenu{
    JMenuItem eliminarPersona = new JMenuItem("Eliminar Persona");
    JMenuItem verInfo = new JMenuItem("Ver detalles");
    
    public MenuContextualPersonas(
            Supplier<Persona> selectedPersonaSupplier,
            Consumer<Persona> onEliminarPersona,
            Consumer<Persona> onVerDetalles){
        
        add(eliminarPersona);
        addSeparator();
        add(verInfo);
        
        eliminarPersona.addActionListener(e -> {
            Persona p = selectedPersonaSupplier.get();
            if (p != null && onEliminarPersona != null)
                onEliminarPersona.accept(p);
        });
        
        verInfo.addActionListener(e -> {
            Persona p = selectedPersonaSupplier.get();
            if (p != null && onVerDetalles  != null)
                onVerDetalles.accept(p);
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
