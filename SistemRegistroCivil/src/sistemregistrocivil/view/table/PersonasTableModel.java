
package sistemregistrocivil.view.table;

import javax.swing.table.AbstractTableModel;
import sistemregistrocivil.model.*;


public class PersonasTableModel extends AbstractTableModel {
    private Sucursal suc;
    private final String[] cols = {"Rut","Nombre"};
    
    @Override
    public int getColumnCount(){
        return cols.length;
    }
    
    @Override
    public String getColumnName(int indice){
        return cols[indice];
    }
    
    @Override 
    public boolean isCellEditable(int r, int c){
        return false;
    }
    
    @Override
    public int getRowCount(){
        if (suc == null) return 0;
        
        int contador = 0;
        for(int i = 0; i < suc.getTotalArchivos(); ++i){
            Archivo arc = suc.getArchivo(i);
            
            if (arc != null && arc.getPersona() != null) ++contador;
            
        }
        return contador;
    }
    
    @Override 
    public Object getValueAt(int r, int c){
        Persona p = getPersonaAt(r);
        if (p == null) return "";
        return (c == 0) ? p.getRut() : p.getNombre();
    }
    
    public void setSucursal(Sucursal s) {
        suc = s;
        fireTableDataChanged();
    }
    
    private Persona getPersonaAt(int row){
        if (suc == null) return null;
        int seen = 0;
        
        for(int i = 0; i < suc.getTotalArchivos(); ++i){
            Archivo arc = suc.getArchivo(i);
            if (arc == null) continue;
            Persona p = arc.getPersona();
            
            if (p == null) continue;
            if (seen == row) return p;
            ++seen;
        }
        return null;
    }
    
    public Persona getAt(int filaModelo){
        return getPersonaAt(filaModelo);
    }
}
