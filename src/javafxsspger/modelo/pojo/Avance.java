/*
* Título del programa: Clase POJO de Avances
* Autor: Omar Dylan Segura Platas
* Fecha Creación: 10/06/2023
* Descripción: Clase modelo para Avance y la información que lo integre
*/
package javafxsspger.modelo.pojo;

public class Avance {
    private int idAvance;
    private int idAnteproyecto;
    private String nombre;
    private String nombreTrabajo;
    private int cantidadActividades;
    private int cantidadRegistros;
    private Double porcentaje;

    public Avance() {
    }

    public Avance(int idAvance, int idAnteproyecto, Double porcentaje, String nombre) {
        this.idAvance = idAvance;
        this.idAnteproyecto = idAnteproyecto;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
    }

    public int getIdAvance() {
        return idAvance;
    }

    public void setIdAvance(int idAvance) {
        this.idAvance = idAvance;
    }

    public Avance(int idAnteproyecto, String nombreTrabajo, int cantidadActividades,
            int cantidadRegistros, Double porcentaje) {
        this.idAnteproyecto = idAnteproyecto;
        this.nombreTrabajo = nombreTrabajo;
        this.cantidadActividades = cantidadActividades;
        this.cantidadRegistros = cantidadRegistros;
        this.porcentaje = porcentaje;
    }


    
    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
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
    
    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public int getCantidadActividades() {
        return cantidadActividades;
    }

    public void setCantidadActividades(int cantidadActividades) {
        this.cantidadActividades = cantidadActividades;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }
}
