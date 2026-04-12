/*
CondicionInscripcionB: aprobó los finales de las correlativas
*/
package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;

/**
 *
 * @author rodri
 */
public class CondicionInscripcionB implements ICondicionInscripcion {

    @Override
    public boolean puedeInscribirse(Alumno a, Materia m) {
       for (Materia corr : m.getCorrelativas()) {
           //Si el alumno no tiene el final aprobado, aunque sea uno 
            if (!a.tieneFinalAprobado(corr)) return false; //devuelve falso
        }
        return true;
    }
    
}
