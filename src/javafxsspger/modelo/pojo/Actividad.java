/*
* Título del programa: Clase POJO de Actividad
* Autor: Omar Dylan Segura Platas
* Fecha Creación: 05/06/2023
* Descripción: Clase modelo para las actividades y la información que lo integre
*/


package javafxsspger.modelo.pojo;

import java.io.File;


public class Actividad {
    private int idActividad;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String fechaCreacion;
    private int idAnteproyecto;
    private String descripcion;
    private File archivo;
    private int idEstudiante;
    private int idAvance;
    
    
    public Actividad(){
    }

    public Actividad(int idActividad, String nombre, String fechaInicio, String fechaFin, String fechaCreacion, int idAnteproyecto, String descripcion, File archivo, int idEstudiante, int idAvance) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacion = fechaCreacion;
        this.idAnteproyecto = idAnteproyecto;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.idEstudiante = idEstudiante;
        this.idAvance = idAvance;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdAvance() {
        return idAvance;
    }

    public void setIdAvance(int idAvance) {
        this.idAvance = idAvance;
    }
    
    
    
}
