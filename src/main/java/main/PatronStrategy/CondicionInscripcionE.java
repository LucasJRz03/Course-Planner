/*
 CondicionInscripcionE: aprobó los finales de las correlativas y 
los finales de todas las materias de 3 cuatrimestres previos.
*/
package main.PatronStrategy;

import main.Clases.Alumno;
import main.Clases.Materia;
import main.PatronBuilder.PlanDeEstudios;

/**
 *
 * @author rodri
 */
public class CondicionInscripcionE implements ICondicionInscripcion {
    
    @Override
    public boolean puedeInscribirse(Alumno a, Materia m) {
        //Finales de correlativas
        for (Materia corr : m.getCorrelativas()) {
            if (!a.tieneFinalAprobado(corr)) return false;
        }
        
     //Lógica de los 3 cuatrimestres previos
    int cuatrimestreLimite = m.getCuatrimestre() - 3;

    // Si el resultado es 0 o menos, no hay materias tan viejas que pedir
    if (cuatrimestreLimite > 0) {
        PlanDeEstudios plan = m.getPlan();
        
        // Buscamos en el plan todas las materias que estén en cuatrimestres
        // iguales o menores al límite calculado.
        for (Materia mat : plan.getMateriasObligatorias()) {
            //si la materia es del pasado (3 o mas cuatrim atras)
            if (mat.getCuatrimestre() <= cuatrimestreLimite) {
                if (!a.tieneFinalAprobado(mat)) {
                    return false; // Le falta un final de hace 3 cuatrimestres
                }
            }
        }
    }
    return true;
    }
}
