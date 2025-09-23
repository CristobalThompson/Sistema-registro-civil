package sistemregistrocivil.view.panel;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BarraSuperior extends JPanel {
    public BarraSuperior(JButton btnCargar, JButton btnCertificados, 
            JButton btnPersonas){
        super(new FlowLayout(FlowLayout.LEFT));
        add(btnCargar);
        add(btnCertificados);
        add(btnPersonas);
    }
}
