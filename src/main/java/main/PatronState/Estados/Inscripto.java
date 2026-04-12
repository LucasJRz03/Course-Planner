package main.PatronState.Estados;

import main.Clases.Cursada;

/**
 Inscripto: "Inicio de la cursada."
 * @author rodri
 */
public class Inscripto extends EstadoCursada {
    private static Inscripto instance;

    private Inscripto() {
    }
    
    //Con synchronized ordenamos los 'thread' que intenten pedir instancia
    //Esto daria una unica instancia compartida por todas las Cursadas
    public synchronized static Inscripto getInstance(){
        if(instance == null) instance = new Inscripto(); 
        return instance; 
    }  
    
    @Override
    public void calificarParcial(Cursada c, boolean aprobado){
        if(aprobado) {
            System.out.println("Parcial aprobado... cambiando estado.");
          c.setEstado(ParcialAprobado.getInstance());
        } else {
            System.out.println("Parcial desaprobado... cambiando estado.");
            c.setEstado(ParcialDesaprobado.getInstance());
        }
        
        
    }
    
}
