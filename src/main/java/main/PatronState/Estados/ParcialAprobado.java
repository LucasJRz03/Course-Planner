package main.PatronState.Estados;

import main.Clases.Cursada;
import main.PatronState.Exception.ExceptionEstadoCursada;

/**
ParcialAprobado: "Aprobó el parcial. Habilita a promoción o a la finalización de cursada."
 * @author rodri
 */
public class ParcialAprobado extends EstadoCursada {
    private static ParcialAprobado instance; 
    
    private ParcialAprobado(){
        
    }
    
    public synchronized static ParcialAprobado getInstance(){
        if(instance == null) instance = new ParcialAprobado();
        return instance; 
    }
    
    public void promocionar(Cursada c) throws ExceptionEstadoCursada {
        boolean esPromocionable = c.getMateria().isPermitePromocion();
        int nota = c.getNotaParcial();
       
        if(esPromocionable && nota >= 7){
            System.out.println("Da para promocionar... cambiando estado.");
            c.setEstado(Promocionada.getInstance());
        } else{
            
            throw new ExceptionEstadoCursada("No cumple el requisito  de promocion.");
        }
    }
    
    public void aprobarCursada(Cursada c){
        if(true){
            System.out.println("Aprueba la cursada... cambiando estado.");
            c.setEstado(CursadaAprobada.getInstance());
        }
    }
    
        
}
