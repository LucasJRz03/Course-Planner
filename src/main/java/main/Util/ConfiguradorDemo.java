
package main.Util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import main.Clases.Alumno;
import main.Clases.Carrera;
import main.Clases.Cursada;
import main.Clases.Materia;
import main.PatronBuilder.PlanDeEstudios;
import main.PatronState.Estados.CursadaAprobada;
import main.PatronState.Estados.Promocionada;
import main.PatronStrategy.CondicionInscripcionC;
import main.PatronStrategy.CondicionInscripcionD;

/**
 *
 * @author rodri
 */
public class ConfiguradorDemo {
    
    public static void cargarDatos(DefaultComboBoxModel<Alumno> modeloAlumnos, 
                                   DefaultComboBoxModel<Carrera> modeloCarreras) {
        
        // Limpia los datos
        modeloAlumnos.removeAllElements();
        modeloCarreras.removeAllElements();

        // --- CREACIÓN DE MATERIAS ---
        // (codigo, nombre, anio, cuatrimestre, esObligatorio, correlativas, permitePromocion)
        Materia m1 = new Materia("M1", "Introducción a la Informática", 1, 1, true, new ArrayList<>(), true);
        
        // Cuatrimestre 2
        Materia m2 = new Materia("M2", "Programación I", 1, 2, true, new ArrayList<>(), true);
        
        //Cuatrimestr 4 (Prueba la condicion D: 4 - 3 = 1)
        ArrayList<Materia> corrM4 = new ArrayList<>(); //Array de las correlativas 
        corrM4.add(m2); // Correlativa directa: Prog I
        Materia m4 = new Materia("M4", "Programación III", 2, 4, true, corrM4, true);
        
        
        // --- CONFIGURACIÓN DEL PLAN (BUILDER) ---
        // --- CREACION DEL PLAN DE ESTUDIOS (Condición D) ---
        PlanDeEstudios planD = new PlanDeEstudios.Builder()
            .agregarMObligatorias(m1)
            .agregarMObligatorias(m2)
            .agregarMObligatorias(m4)
            .setCondicion(new CondicionInscripcionD()) 
            .build();

       //setPlan() vincula a la materia con su plan para que
       // el patron strategy pueda acceder a la lista global de la materias 
       //En otras palabras son las CORRELATIVAS
       m1.setPlan(planD); m2.setPlan(planD); m4.setPlan(planD);
       
        // --- CARRERAS ---
        Carrera tudaD = new Carrera("TUDA", planD);
        modeloCarreras.addElement(tudaD);

        // --- ALUMNOS ---
        Alumno lucas = new Alumno(2025, "Lucas");
        lucas.getCarrerasInscriptas().add(tudaD);
        //CASO EXITOSO  Lucas promocionó todo lo viejo y puede anotarse a materias de 2do. 
        // seria la "Programacion III" si la promociona puede graduarse
        lucas.getHistorialCursadas().add(crearCursadaAprobada(m1, true, 9));
        lucas.getHistorialCursadas().add(crearCursadaAprobada(m2, true, 10));
        modeloAlumnos.addElement(lucas);

        //CASO ERRONEO Pedro cumple la correlativa 
        Alumno pedro = new Alumno(2026, "Pedro (Debe Final M1)");
        pedro.getCarrerasInscriptas().add(tudaD);
        // Pedro aprobó la cursada de M1 pero DEBE EL FINAL (Promocionada = false)
        pedro.getHistorialCursadas().add(crearCursadaAprobada(m1, false, 6));
        // Pedro aprobó la cursada de M2 (correlativa directa)
        pedro.getHistorialCursadas().add(crearCursadaAprobada(m2, true, 9));
        modeloAlumnos.addElement(pedro);
        
        System.out.println("--- Demo cargada desde ConfiguradorDemo ---");
    }
    
    private static Cursada crearCursadaAprobada(Materia m, boolean promocionada, int nota) {
        Cursada c = new Cursada(m);
        c.setNotaParcial(nota);
        
        if (promocionada) {
            c.setEstado(Promocionada.getInstance()); // Tiene final
        } else {
            c.setEstado(CursadaAprobada.getInstance()); // Solo cursada, debe final
        }
        return c;
    }
}

