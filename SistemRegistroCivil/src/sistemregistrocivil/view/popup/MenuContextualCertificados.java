package sistemregistrocivil.view.popup;

import javax.swing.*;
import java.awt.event.*;
import sistemregistrocivil.model.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuContextualCertificados extends JPopupMenu {

    JMenuItem verDetalles = new JMenuItem("Ver detalles");

    public MenuContextualCertificados(
            Supplier<Certificado> certificadoSeleccionadoSupplier,
            Consumer<Certificado> onVerDetalles) {
        
        add(verDetalles);

        verDetalles.addActionListener(e -> {
            Certificado certificado = certificadoSeleccionadoSupplier.get();
            if (certificado != null && onVerDetalles != null) {
                onVerDetalles.accept(certificado);
            }
        });
    }

    public static void instalarEnTabla(JTable tabla, JPopupMenu popup) {
        tabla.addMouseListener(new MouseAdapter() {
            private void maybeShow(MouseEvent e) {
                if (!e.isPopupTrigger()) return;
                int filaVista = tabla.rowAtPoint(e.getPoint());
                if (filaVista < 0) return;
                tabla.setRowSelectionInterval(filaVista, filaVista);
                popup.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                maybeShow(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShow(e);
            }
        });
    }
}

