
package sistemregistrocivil;

import java.io.IOException;
/**
 *
 * @author crist
 */
public class SistemRegistroCivil {

    public static void main(String[] args) throws IOException {
        javax.swing.SwingUtilities.invokeLater(() -> {
            RegistroCivil rc = new RegistroCivil();
            Menu consoleichon = new Menu(rc);
            consoleichon.mostrar();
        });
    }
}

