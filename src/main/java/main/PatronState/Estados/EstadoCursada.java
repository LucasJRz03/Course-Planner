package main.PatronState.Estados;

import main.Clases.Cursada;
import main.PatronState.Exception.ExceptionEstadoCursada;

/**
 *
 * @author rodri
 */
public abstract class EstadoCursada {
    
  //Los estados son Singleton, porque son stataless 
  //No guardan datos del alumno, solo tienen la lógica de qué botones se pueden apretar 
  //Por eso, con una sola instancia ta bien.
    
    public void calificarParcial(Cursada c, boolean calificar) throws ExceptionEstadoCursada {
        throw new ExceptionEstadoCursada("No permitido en este estado");  
    }
    
    public void promocionar(Cursada c) throws ExceptionEstadoCursada {
        throw new ExceptionEstadoCursada("No permitido en este estado.");   
    }
    
    public void aprobarCursada(Cursada c) throws ExceptionEstadoCursada{
        throw new ExceptionEstadoCursada("No permitido en este estado.");
    }
    
    public void reprobarCursada(Cursada c) throws ExceptionEstadoCursada{
        throw new ExceptionEstadoCursada("No permitido en este estado.");
    }
    
    public void rendirFinal(Cursada c) throws ExceptionEstadoCursada{
        throw new ExceptionEstadoCursada("No permitido en este estado.");
    }
    
    public void debeRecursar(Cursada c) throws ExceptionEstadoCursada{
        throw new ExceptionEstadoCursada("No permitido en este estado.");
    }
}
