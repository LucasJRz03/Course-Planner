package main.PatronBuilder;

import java.util.ArrayList;
import java.util.List;
import main.Clases.Alumno;
import main.Clases.Materia;
import main.PatronStrategy.ICondicionInscripcion;

/**
 *
 * @author rodri
 */
public class PlanDeEstudios {
    //Atributos
    private List<Materia> materiasObligatorias;
    private int cantOptativasRequeridas;
    private List<Materia> materiasOptativas;
    //Permite elegir la estrategia de inscripcion segun sea.
    private ICondicionInscripcion condicionInscripcion;
    
    //En privado para que el builder sea quién lo pueda construir solamente
    private PlanDeEstudios(){}
    
    //Se pasa por parametro el build, quien se encargara de pasarles 
    //los valores a los atributos actuales de PlanDeEstudios
    private PlanDeEstudios(Builder builder){
        this.materiasObligatorias = builder.materiasObligatorias; 
        this.materiasOptativas = builder.materiasOptativas;
        this.cantOptativasRequeridas = builder.cantOptativasRequeridas;
        this.condicionInscripcion = builder.condicionInscripcion;
    }
    
    //Devuelve un lista con las materias de x anio (Con obligatorias y optativas).
    public List<Materia> getMateriasPorAnio(int nroAnio){
        List<Materia> filtradas = new ArrayList<>(); 
        for(Materia m : materiasObligatorias){
            if(m.getAnio() == nroAnio) filtradas.add(m);
        }
        for(Materia m : materiasOptativas){
                if(m.getAnio() == nroAnio) filtradas.add(m);
        }
        return filtradas;           
     }
    
    //Devuelve una lista con las materias por cuatrimestre
    public List<Materia> getMateriasPorCuatrimestre(int nroCuatrimestre) {
          List<Materia> filtradas = new ArrayList<>(); 
        for(Materia m : materiasObligatorias) {
        if(m.getCuatrimestre() == nroCuatrimestre) filtradas.add(m);
         }
        for(Materia m : materiasOptativas) {
            if(m.getCuatrimestre() == nroCuatrimestre) filtradas.add(m);
        }
       return filtradas;            
    }
     
     //Usado en VentanaPrincipal, en el metodo crearPanelEstadoAcademico()
     public boolean verificarGraduacion(Alumno alumno){
         //Todas las materias obligatorias aprobadas
         for(Materia m: materiasObligatorias){
             //En el caso de no tener una aprobada, entra en el if y retorna false
             if(m.isEsObligatorio()){
              if(!alumno.tieneFinalAprobado(m)) {
                 System.out.println("Falta aprobar: "+ m.getNombre());
                 return false; //no merece graduarse. 
              } 
             }
         }
           //Cantidad mini de optativas aprobadas
         int optativasAprobadas=0; 
         for(Materia m : materiasOptativas){
             if(alumno.tieneFinalAprobado(m)) optativasAprobadas++;
         }
         System.out.println("Optativas aprobadas: "+optativasAprobadas+"/"+cantOptativasRequeridas);

         return optativasAprobadas >= cantOptativasRequeridas;
     }
     
     public String obtenerReporteGraduacion(Alumno alumno) {
    //StringBuilder para ir modificando el reporte en el proceso
    StringBuilder reporte = new StringBuilder();
    List<String> obligatoriasFaltantes = new ArrayList<>();
    int optativasAprobadas = 0;

    // 1. Chequeo de Obligatorias
    for (Materia m : materiasObligatorias) {
        if (m.isEsObligatorio()) {
            if (!alumno.tieneFinalAprobado(m)) {
                obligatoriasFaltantes.add(m.getNombre());
            }
        }
    }

    // 2. Conteo de Optativas
    for (Materia o : materiasOptativas) {
        if (alumno.tieneFinalAprobado(o)) {
            optativasAprobadas++;
        }
    }

    // 3. Armado del mensaje
    if (obligatoriasFaltantes.isEmpty() && optativasAprobadas >= cantOptativasRequeridas) {
        return "¡FELICIDADES! El alumno cumple todos los requisitos para graduarse.";
    }
    //append pega el texto al final
    reporte.append("--- REPORTE DE GRADUACIÓN ---\n\n");
    
    if (!obligatoriasFaltantes.isEmpty()) {
        reporte.append("Materias Obligatorias faltantes:\n");
        for (String nombre : obligatoriasFaltantes) {
            reporte.append(" - ").append(nombre).append("\n");
        }
    } else {
        reporte.append("Todas las materias obligatorias aprobadas.\n");
    }

    reporte.append("\nMaterias Optativas:\n");
    reporte.append(" - Aprobadas: ").append(optativasAprobadas).append("\n");
    reporte.append(" - Requeridas: ").append(cantOptativasRequeridas).append("\n");

    if (optativasAprobadas < cantOptativasRequeridas) {
        reporte.append(" ! Faltan ").append(cantOptativasRequeridas - optativasAprobadas).append(" optativa(s).");
    } else {
        reporte.append("Cupo de optativas completado.");
    }
    //A lo ultimo se convierte el reporte en un String para mostrar.
    return reporte.toString(); 
    //Es mas practico que usar un text = texto + " algo mas"  
}
     
     public ICondicionInscripcion getCondicionInscripcion() {
        return condicionInscripcion;
     }

    public List<Materia> getMateriasObligatorias() {
        return materiasObligatorias;
    }

    public List<Materia> getMateriasOptativas() {
        return materiasOptativas;
    }
   

    @Override
    public String toString() {
        return "PlanDeEstudios{" + 
                "\nMateriasObligatorias=" + getMateriasObligatorias() + 
                "\nCantOptativasRequeridas=" + cantOptativasRequeridas + 
                "\nMateriasOptativas=" + getMateriasOptativas() +
                "\nCondicionInscripcion=" + getCondicionInscripcion() + 
                '}';
    }
    
    //Clase estatica e interna de PlanDeEstudios, encargada de crear el Plan
    public static class Builder{
        //Atributos
        private List<Materia> materiasObligatorias = new ArrayList<>();
        private int cantOptativasRequeridas; 
        private List<Materia> materiasOptativas = new ArrayList<>(); 
        private ICondicionInscripcion condicionInscripcion; 
        
        //Metodos
        public Builder agregarMObligatorias(Materia m){
            this.materiasObligatorias.add(m);
            return this; 
        }
        
        public Builder agregarOptativasRequeridas(int n){
            this.cantOptativasRequeridas = n;
            return this;
        }
        
        public Builder agregarMOptativas(Materia m){
            this.materiasOptativas.add(m);
            return this;
        }
        
        public Builder setCondicion(ICondicionInscripcion condicion){
            this.condicionInscripcion = condicion; 
            return this;
        }
        
        //El encargado de crear el plan de estudios
        public PlanDeEstudios build(){
            System.out.println("Creando plan de estudio...");
            return new PlanDeEstudios(this);
        }
        
    }
    
    
}
