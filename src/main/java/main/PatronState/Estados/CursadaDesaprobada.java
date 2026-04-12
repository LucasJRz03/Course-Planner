
package main.PatronState.Estados;

import main.Clases.Cursada;

/**
 CursadaDesaprobada: "No aprobó la cursada. Debe recursar."
 * @author rodri
 */
public class CursadaDesaprobada extends EstadoCursada{
    
    private static CursadaDesaprobada instance; 
    
    private CursadaDesaprobada(){}
    
     public synchronized static CursadaDesaprobada getInstance(){
        if(instance == null) instance = new CursadaDesaprobada();
        return instance; 
    }
     
     public void debeRecursar(Cursada c){
         if(true){
             System.out.println("Debe recursar la materia.");

         }
     }
    
}
