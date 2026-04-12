/*
CondicionInscripcionC: aprobó las cursadas de las correlativas y los finales de todas las materias de 5 cuatrimestres previos
al que se quiere anotar
*/
package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;
import main.PatronBuilder.PlanDeEstudios;

/**
 *
 * @author rodri
 */
public class CondicionInscripcionC implements ICondicionInscripcion {

    @Override
    public boolean puedeInscribirse(Alumno alumno, Materia materiaDestino) {
        //Chequeo de correlativas 
        for (Materia corr : materiaDestino.getCorrelativas()) {
            if (!alumno.tieneCursadaAprobada(corr)) return false;
        }

        //Lógica de los 5 cuatrimestres previos
        int cuatrimestreLimite = materiaDestino.getCuatrimestre() - 5;

        // Si el resultado es 0 o menos, no hay materias tan viejas que pedir
        if (cuatrimestreLimite > 0) {
            PlanDeEstudios plan = materiaDestino.getPlan();
        
        // Busca en el plan todas las materias que estén en cuatrimestres
        // iguales o menores al límite calculado.
            for (Materia m : plan.getMateriasObligatorias()) {
                if (m.getCuatrimestre() <= cuatrimestreLimite) {
                    if (!alumno.tieneFinalAprobado(m)) {
                        return false; // Le falta un final de hace 5 cuatrimestres
                    }
                }
            }
        }
    return true;
    }
    
}
