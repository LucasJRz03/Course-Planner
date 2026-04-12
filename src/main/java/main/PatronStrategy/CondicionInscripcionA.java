/*
CondicionInscripcion A: aprobó las cursadas de las correlativas.
*/
package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;

/**
 *
 * @author rodri
 */
public class CondicionInscripcionA implements ICondicionInscripcion {

    @Override
    public boolean puedeInscribirse(Alumno a, Materia m) {
    for (Materia corr : m.getCorrelativas()) {
        //Con solo una que no haya aprobado, retorna false
            if (!a.tieneCursadaAprobada(corr)) return false;
        }
        return true;
    }
}
