package main.Clases;

import java.util.List;
import main.PatronBuilder.PlanDeEstudios;

public class Materia {
    //Atributos 
    private String codigo;
    private String nombre;
    private int anio; 
    private int cuatrimestre; 
    private boolean esObligatorio;
    private List<Materia> correlativas; 
    private boolean permitePromocion; 
    private PlanDeEstudios plan; 

    
    //Constructor  vacio
    public Materia() {
    }
    //Constructor con parametros
    public Materia(String codigo, String nombre, int anio, int cuatrimestre, 
                boolean esObligatorio, List<Materia> correlativas, boolean permitePromocion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.esObligatorio = esObligatorio;
        this.correlativas = correlativas;
        this.permitePromocion = permitePromocion; 
    }
    
    //Metodos getter y setter
    public PlanDeEstudios getPlan() {
        return plan;
    }

    //Vincula la materia con su Plan De Estudios
    public void setPlan(PlanDeEstudios plan) {
        this.plan = plan;
    }
    
    public boolean isPermitePromocion(){
        return permitePromocion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnio() {
        return anio;
    }

    public boolean isEsObligatorio() {
        return esObligatorio;
    }
    //Permite que las estrategias C,D y E calculen el limite de correlativas previas
    public int getCuatrimestre() {
        return this.cuatrimestre;
    }
  
    public List<Materia> getCorrelativas() {
        return correlativas;
    }
     
    public String getCodigoCorrelativas(){
        String cadena = " ";
        for(Materia m : correlativas){
            cadena += m.codigo + ", "; 
        }
        return cadena;
    }

    //Sobreescribe equals y hashCode para que Java identifique a la materia por codigo unico
    //Esto era por un error de contains() 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Si es la misma instancia
        if (obj == null || getClass() != obj.getClass()) return false;
        Materia other = (Materia) obj;
        // Compara por el código de la materia (que debería ser único)
        return codigo != null && codigo.equals(other.codigo);
    }
    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "Cod:" + codigo + ", Nombre:" + nombre + ", Año:" + anio +", Cuatrim:"+cuatrimestre+ ", Es Obligatorio: " + esObligatorio + ", Promocion:" + permitePromocion + ", Correlativas:" + getCodigoCorrelativas();
    }
    
    
}
