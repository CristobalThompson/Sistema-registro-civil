package sistemregistrocivil.view.table;

import javax.swing.table.AbstractTableModel;
import sistemregistrocivil.model.RegistroCivil;
import sistemregistrocivil.model.Sucursal;


public class SucursalesTableModel extends AbstractTableModel{
    private final RegistroCivil rc;
    private final String[] cols = {"ID", "Nombre", "Ciudad"};
    
    public SucursalesTableModel(RegistroCivil rc){
        this.rc = rc;
    }
    
    @Override 
    public int getRowCount() { 
        return rc.getTotalClaves(); 
    }
    
    @Override 
    public int getColumnCount() { 
        return cols.length; 
    }
    
    @Override 
    public String getColumnName(int c) { 
        return cols[c]; 
    }
    
    @Override 
    public Class<?> getColumnClass(int c) { 
        return c==0? Integer.class:String.class; 
    }
    
    @Override 
    public boolean isCellEditable(int r,int c){
        return false; 
    }
    
    @Override
    public Object getValueAt(int fila, int c){
        Sucursal suc = rc.getSucursal(fila);
        
        if (suc == null) return "";
        switch(c){
            case 0:
                return suc.getID();
            case 1:
                return suc.getNombre();
            case 2:
                return suc.getUbicacion().getCiudad();
            default :
                return "";
        }
    }
    
    
    public Sucursal getAt(int filaModelo){
        return rc.getSucursal(filaModelo);
    }
    
    public void refrescar(){
        fireTableDataChanged();
    }
}
