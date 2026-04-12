package main.PatronState.Estados;

/**
Promocionada: "Aprobó la materia por promoción. Se considera Final Aprobado."
 * @author rodri
 */
public class Promocionada extends EstadoCursada {
    private static Promocionada instance;

    private Promocionada() {
    }
    
    public synchronized static Promocionada getInstance(){
        if(instance == null) instance = new Promocionada();
        return instance; 
    }
    
}
