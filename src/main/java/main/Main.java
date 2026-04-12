/*Integrador Programación 2*/

package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import main.Swing.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        //invokeLater hace que Swing corra en su propio hilo de ejecucion
        SwingUtilities.invokeLater(() -> {
            try{
                // Setea el LookAndFeel del sistema para que se vea más moderno
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new VentanaPrincipal().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();//imprime el error  
            } 
        });
    }
}
