package main.PatronState.Estados;

import main.Clases.Cursada;

/**
 CursadaAprobada: "La cursada ha finalizado con éxito. Habilita a rendir el Examen Final."
 * @author rodri
 */
public class CursadaAprobada extends EstadoCursada {
    //Atributo privado y estatico para el singleton
    private static CursadaAprobada instance; 
    //Constructor privado 
    private CursadaAprobada(){}
    
    //Con synchronized ordenamos los 'thread' que intenten pedir instancia
    //Esto daria una unica instancia compartida por todas Cursadas
    public synchronized static CursadaAprobada getInstance(){
        if(instance == null) instance = new CursadaAprobada();
        return instance;
    } 
    
    
    public void rendirFinal(Cursada c){
        if(true){
            System.out.println("Se habilita rendir final...");
           
        }
    }
    
}
