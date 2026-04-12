package main.PatronState.Estados;

import main.Clases.Cursada;

/**
 ParcialDesaprobado: "Desaprobó el parcial."

 * @author rodri
 */
public class ParcialDesaprobado extends EstadoCursada {
    private static ParcialDesaprobado instance;
    
    private ParcialDesaprobado() {
    }
    
    public synchronized static ParcialDesaprobado getInstance(){
        if(instance == null) instance = new ParcialDesaprobado();
        return instance; 
    }
    
    public void reprobarCursada(Cursada c){
        if(true){
            System.out.println("Cursada desaprobada... cambiando estado.");
            c.setEstado(CursadaDesaprobada.getInstance());
        }
    }
    
}
