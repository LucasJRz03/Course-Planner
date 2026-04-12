package main.Clases;

import main.PatronBuilder.PlanDeEstudios;

public class Carrera {
    //Atributos
    private String nombre;
    private PlanDeEstudios planDeEstudios;
    //Constructor con parametros
    public Carrera(String nombre, PlanDeEstudios planDeEstudios) {
        this.nombre = nombre;
        this.planDeEstudios = planDeEstudios;
    }
    // Constructor vacío
    public Carrera() {
    }
    // Métodos getter y setter
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Permite acceder al plan de estudios de la carrera para ver sus materias
    public PlanDeEstudios getPlanDeEstudios() {
        return planDeEstudios;
    }
    // Permitie aniadir un plan de estudio a la carrera
    public void setPlanDeEstudios(PlanDeEstudios planDeEstudios) {
        this.planDeEstudios = planDeEstudios;
    }
    
    //Permite ver el plan de estudios
    public void verPlanDeEstudios(){
        System.out.println(this.planDeEstudios.toString());
    }
  
    @Override
    public String toString() {
        return "Nombre: "+nombre;
    }
    
    
}
