package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;

/**
 *
 * @author rodri
 */
public interface ICondicionInscripcion {

    public boolean puedeInscribirse(Alumno a, Materia m);
    
}
