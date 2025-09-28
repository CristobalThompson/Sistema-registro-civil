package sistemregistrocivil.view.table;

import javax.swing.table.AbstractTableModel;
import sistemregistrocivil.model.*;

public class CertificadosTableModel extends AbstractTableModel {

    private final String[] cols = {"Certificado", "RUT", "Nombre"};
    private final RegistroCivil rc;

    public CertificadosTableModel(RegistroCivil rc) {
        this.rc = rc;
    }

    @Override
    public int getRowCount() {
        return rc.getTotalCertificados();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int col) {
        return cols[col];
    }

    @Override
    public Object getValueAt(int fila, int c) {
        Certificado certificado = rc.getCertificado(fila);

        switch(c) {
            case 0: 
                return certificado.getNombreCertificado();
            case 1: 
                return certificado.getRut();
            case 2: 
                return certificado.getNombrePersona();
            default:
                return "";
        }
    }
    
    public Certificado getAt(int filaModelo){
        return rc.getCertificado(filaModelo);
    }

    public void refrescar() {
        fireTableDataChanged();
    }
}
