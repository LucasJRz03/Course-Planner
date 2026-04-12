package main.Clases;

import java.util.ArrayList;
import java.util.List;
import main.PatronBuilder.PlanDeEstudios;
import main.PatronState.Estados.CursadaAprobada;
import main.PatronState.Estados.CursadaDesaprobada;
import main.PatronState.Estados.Promocionada;

public class Alumno {
    //Atributos
     private int legajo;
     private String nombre;
     private List<Carrera> carrerasInscriptas;
     private List<Cursada> historialCursadas; 
     //Constructor
    public Alumno(int legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrerasInscriptas = new ArrayList<Carrera>();
        this.historialCursadas = new ArrayList<Cursada>();
    }
    //Segundo constructor, crea un  alumno con una lista de carrera y cursadas previas.
    public Alumno(int legajo, String nombre, List<Carrera> carrerasInscriptas, List<Cursada> historialCursadas) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.carrerasInscriptas = carrerasInscriptas;
        this.historialCursadas = historialCursadas;
    }

    public List<Cursada> getHistorialCursadas() {
        return historialCursadas;
    }

    public List<Carrera> getCarrerasInscriptas() {
        return carrerasInscriptas;
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }
     
    // Verifica si tiene la cursada (requisito para Condiciones A, C, D) de Patron Strategy
    //Ingresa una materia
    public boolean tieneCursadaAprobada(Materia m){
        //Se busca esa materia en el historial de cursada del alumno 
        for(Cursada c: historialCursadas){
            //Si se encuentra la materia sucede que
            if(c.getMateria().equals(m)){
                //busca si una esta bien (aprobada, promocionada), sucede que retorna true
                if(c.getEstado() instanceof CursadaAprobada || c.getEstado() instanceof Promocionada){
                    return true; 
                }
              
            }           
        }
        //En el caso de que no se encuentre la materia retorna false
        return false; 
    }
    
    // Verifica si tiene el final (requisito para Condiciones B, C, D, E) de Patron Strategy
    public boolean tieneFinalAprobado(Materia m){
        for(Cursada c: historialCursadas){
            //Buscamos la cursada de esa materia
            if(c.getMateria().equals(m)){
            // Si encuentra una sola cursada promocionada, ya está, tiene el final.
                if (c.getEstado() instanceof Promocionada) {
                    return true; 
            }
            }
        }
        //En caso de no haber ni una devuelve false
        return false; 
    }
    
    public boolean aproboTodoElCuatrimestre(int nroCuatrimestre, PlanDeEstudios plan) {
    // Accede al plan desde la carrera
    List<Materia> materiasDelCuatrimestre = plan.getMateriasPorCuatrimestre(nroCuatrimestre);
    if(materiasDelCuatrimestre.isEmpty()) return true; 
    
    for (Materia m : materiasDelCuatrimestre) {
        // 2. Verificamos si el alumno tiene el final aprobado (Promocionada)
        if (!this.tieneFinalAprobado(m)) {
            return false; // Con una que falte, ya no aprobó "todo"
        }
    }
      return  true;
    }
    
    
    //Verifica que el alumno tenga permitido anotarse de nuevo a X materia
    // si es que nunca la curso o si tiene la cursada desaprobada
    public boolean puedeInscribirsePorEstado(Materia m) {
        for(Cursada c: historialCursadas) {
            if(c.getMateria().equals(m)){
                // Si hay una cursada que no sea desaprobada significa que
                // o la aprobo, o la promociono o la esta cursando. 
                if(!(c.getEstado() instanceof CursadaDesaprobada)){
                    return false;
                }
               
            }
        }
        return true;  //Nunca se anoto, o todas las cursadas eran desaprobadas y asi puede inscribirse
    }
    
    
    @Override
    public String toString() {
        return "legajo: " + legajo + ",  nombre: " + nombre;
    }
     
    
}
