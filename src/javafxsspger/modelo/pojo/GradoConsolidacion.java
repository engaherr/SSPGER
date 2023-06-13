package javafxsspger.modelo.pojo;

public class GradoConsolidacion {
    private int idGradoConsolidacion;
    private String nombre;

    public GradoConsolidacion() {
    }

    public GradoConsolidacion(int idGradoConsolidacion, String nombre) {
        this.idGradoConsolidacion = idGradoConsolidacion;
        this.nombre = nombre;
    }

    public int getIdGradoConsolidacion() {
        return idGradoConsolidacion;
    }

    public void setIdGradoConsolidacion(int idGradoConsolidacion) {
        this.idGradoConsolidacion = idGradoConsolidacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
