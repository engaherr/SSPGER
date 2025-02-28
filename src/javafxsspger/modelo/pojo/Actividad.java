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
    private int idEstudiante;
    private int idAvance;
    private String descripcion;
    private byte[] archivo;
    private String extensionArchivo;
    private String nombreArchivo;
    private String comentarios;
    private int evaluacion;
    
    public Actividad(){
    }

    public Actividad(int idActividad, String nombre, String fechaInicio, String fechaFin, 
            String fechaCreacion, int idAnteproyecto, String descripcion, byte[] archivo, 
            int idEstudiante, int idAvance, String comentarios, int evaluacion, String nombreArchivo,
            String extensionArchivo) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacion = fechaCreacion;
        this.idAnteproyecto = idAnteproyecto;
        this.idEstudiante = idEstudiante;
        this.idAvance = idAvance;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.extensionArchivo = extensionArchivo;
        this.nombreArchivo = nombreArchivo;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.idEstudiante = idEstudiante;
        this.idAvance = idAvance;
        this.comentarios = comentarios;
        this.evaluacion = evaluacion;
        this.nombreArchivo = nombreArchivo;
    }
    
    
    

    
    public int getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(int evaluacion) {
        this.evaluacion = evaluacion;
    }


    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getExtensionArchivo() {
        return extensionArchivo;
    }

    public void setExtensionArchivo(String extensionArchivo) {
        this.extensionArchivo = extensionArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
