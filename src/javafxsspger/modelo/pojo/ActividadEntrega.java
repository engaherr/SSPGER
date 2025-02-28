/*
* Título del programa: Clase POJO de Actividad Entregra
* Autor: Omar Dylan Segura Platas
* Fecha Creación: 11/06/2023
* Descripción: Clase modelo para las actividades y la información que lo integre
*/

package javafxsspger.modelo.pojo;


public class ActividadEntrega {
    
   
    private int idActividad;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String fechaCreacion;
    private int idAnteproyecto;
    private String descripcion;
    private byte[] archivo;
    private int idEstudiante;
    private int idAvance;
    private String comentarios;
    private int evaluacion;
    private String nombreArchivo;

    
    
    public ActividadEntrega(){
    }

    public ActividadEntrega(int idActividad, String nombre, String fechaInicio, String fechaFin, 
            String fechaCreacion, int idAnteproyecto, String descripcion, byte[] archivo, 
            int idEstudiante, int idAvance, String comentarios, int evaluacion, 
            String nombreArchivo) {
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
        this.comentarios = comentarios;
        this.evaluacion = evaluacion;
        this.nombreArchivo = nombreArchivo;
    }



    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    

