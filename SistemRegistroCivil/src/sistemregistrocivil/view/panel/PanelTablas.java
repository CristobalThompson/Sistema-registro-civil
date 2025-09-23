/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemregistrocivil.view.panel;

import java.awt.BorderLayout;
import javax.swing.*;

public class PanelTablas extends JPanel{
    private final JSplitPane split;
    
    public PanelTablas(JTable tablaSucursales, JTable tablaPersonas){
        super(new BorderLayout());
        JScrollPane spSucursal = new JScrollPane(tablaSucursales);
        JScrollPane spPersonas = new JScrollPane(tablaPersonas);
        
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spSucursal, spPersonas);
        split.setResizeWeight(0.65);
        split.setOneTouchExpandable(true);
        
        add(split, BorderLayout.CENTER);
    }
    
    public JSplitPane getSplitPane(){
        return split;
    }
}
