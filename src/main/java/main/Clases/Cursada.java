
package main.Clases;

import main.PatronState.Estados.EstadoCursada;
import main.PatronState.Estados.Inscripto;
import main.PatronState.Exception.ExceptionEstadoCursada;

public class Cursada {
    //atributos
    private EstadoCursada estado; 
    private int notaParcial; 
    private Materia materia; 
    //Constructor
    public Cursada(Materia materia){
        this.materia = materia; 
        this.estado = Inscripto.getInstance();
    }
    //getter y setter
    public Materia getMateria(){
        return materia; 
    }
    
    public int getNotaParcial() { return notaParcial; }
    public void setNotaParcial(int notaParcial){
        this.notaParcial = notaParcial;
    }
    //Del patron state, recibe el siguiente estado sobrescribiendo el actual
    public void setEstado(EstadoCursada nuevoEstado){
        this.estado = nuevoEstado; 
    }
    
    //devuelve el estado actual de la cursada.
    public EstadoCursada getEstado(){
        return this.estado; 
    }
    
    //- - Metodos que delegan al ESTADO - -\\
    
    public void calificarParcial(boolean calificar) throws ExceptionEstadoCursada{
     this.estado.calificarParcial(this, calificar);
    }
    
    public void aprobarCursada() throws ExceptionEstadoCursada{
        this.estado.aprobarCursada(this);
    }
    
    public void promocionar() throws ExceptionEstadoCursada{
        this.estado.promocionar(this);
    }
    
    public void reprobarCursada() throws ExceptionEstadoCursada{
        this.estado.reprobarCursada(this);
    }
    
    public void rendirFinal() throws ExceptionEstadoCursada{
        this.estado.rendirFinal(this);
    }
    
    public void debeRecuperar() throws ExceptionEstadoCursada{
        this.estado.debeRecursar(this);
    }
    
    @Override 
    public String toString() {
        return "Materia:"+materia+", Estado:"+estado+"Nota:"+notaParcial; 
    }
}
