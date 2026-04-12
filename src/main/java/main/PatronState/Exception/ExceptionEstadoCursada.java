package main.PatronState.Exception;

/**
 *
 * @author rodri
 */

//Excepcion personalizada para los estados de EstadoCursada.
//Notifica transiciones de estado invalidas o acciones no permitidas
//segun el estado de la Cursada (importante en el Pattern State) 
public class ExceptionEstadoCursada extends Exception {
   
    public ExceptionEstadoCursada() { }
    
    public ExceptionEstadoCursada(String msg){
        super(msg);
    } 
}
