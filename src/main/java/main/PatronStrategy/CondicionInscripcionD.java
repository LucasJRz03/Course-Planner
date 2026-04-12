/*
CondicionInscripcionD: aprobó las cursadas de las correlativas y los
  finales de todas las materias de 3 cuatrimestres previos al que se quiere anotar.
 */
package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;
import main.PatronBuilder.PlanDeEstudios;

/**
 *
 * @author rodri
 */
public class CondicionInscripcionD implements ICondicionInscripcion {

    @Override   
    public boolean puedeInscribirse(Alumno alumno, Materia materiaDestino) {
        //Chequeo de correlativas
        for (Materia corr : materiaDestino.getCorrelativas()) {
            //si falta una cursada no puede anotarse
            if (!alumno.tieneCursadaAprobada(corr)) return false; 
        }   

    //Lógica de los 3 cuatrimestres previos, por ejempl si se anota a una materia del 4to
    // debe tener todo el 1er cuatrimestre con el final aprobado
    int cuatrimestreLimite = materiaDestino.getCuatrimestre() - 3;

    // Solo se aplica el filtro si el alumno avanzo lo suficiente en la carrera 
    if (cuatrimestreLimite > 0) {
        //devuelve el plan mediante el metodo de la Materia
        PlanDeEstudios plan = materiaDestino.getPlan();
        
        // Verifica el historial contra las materias obligatorias del plan
        for (Materia m : plan.getMateriasObligatorias()) {
            //Si la materia es del limite calculado o mas vieja sucede que
            if (m.getCuatrimestre() <= cuatrimestreLimite) {
                // se le exige que tenga el Final aprobado, que seria el estado Promocionado
                if (!alumno.tieneFinalAprobado(m)) {
                    return false; // Le falta un final de hace 3 cuatrimestres
                }
            }
        }
    }
    return true; //En caso de que cumpla con todos retorn ok
}
}
