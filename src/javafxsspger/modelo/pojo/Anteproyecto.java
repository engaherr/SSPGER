/*
* Título del programa: Clase POJO de Anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 11/05/2023
* Descripción: Clase modelo para la información de los Anteproyectos que proviene de la persistencia
* del sistema
*/
package javafxsspger.modelo.pojo;

public class Anteproyecto {
    private int idAnteproyecto;
    private int idModalidad;
    private int idCuerpoAcademico;
    private String proyectoInvestigacion;
    private String lineaInvestigacion;
    private int mesesDuracionAproximada;
    private String nombreTrabajo;
    private String gradoDirector;
    private String requisitos;
    private int numAlumnosParticipantes;
    private String descripcionProyectoInvestigacion;
    private String descripcionTrabajoRecepcional;
    private String resultadosEsperados;
    private String bibliografiaRecomendada;
    private String comentarios;
    private int idEstadoATP;
    private int idDirector;
    private String nombreCA;
    private String modalidad;
    private String nombreDirector;
    private String codirectores;
    private String estado;
    private int idLgac;
    private String nombreLgac;
    private int responsablesActivos;

    public Anteproyecto() {
    }

    public Anteproyecto(int idAnteproyecto, int idModalidad, int idCuerpoAcademico, String proyectoInvestigacion, String lineaInvestigacion, int mesesDuracionAproximada, String nombreTrabajo, String gradoDirector, String requisitos, int numAlumnosParticipantes, String descripcionProyectoInvestigacion, String descripcionTrabajoRecepcional, String resultadosEsperados, String bibliografiaRecomendada, String comentarios, int idEstadoATP, int idDirector, String nombreCA, String modalidad, String nombreDirector, String codirectores, String estado, int idLgac, String nombreLgac, int responsablesActivos) {
        this.idAnteproyecto = idAnteproyecto;
        this.idModalidad = idModalidad;
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.proyectoInvestigacion = proyectoInvestigacion;
        this.lineaInvestigacion = lineaInvestigacion;
        this.mesesDuracionAproximada = mesesDuracionAproximada;
        this.nombreTrabajo = nombreTrabajo;
        this.gradoDirector = gradoDirector;
        this.requisitos = requisitos;
        this.numAlumnosParticipantes = numAlumnosParticipantes;
        this.descripcionProyectoInvestigacion = descripcionProyectoInvestigacion;
        this.descripcionTrabajoRecepcional = descripcionTrabajoRecepcional;
        this.resultadosEsperados = resultadosEsperados;
        this.bibliografiaRecomendada = bibliografiaRecomendada;
        this.comentarios = comentarios;
        this.idEstadoATP = idEstadoATP;
        this.idDirector = idDirector;
        this.nombreCA = nombreCA;
        this.modalidad = modalidad;
        this.nombreDirector = nombreDirector;
        this.codirectores = codirectores;
        this.estado = estado;
        this.idLgac = idLgac;
        this.nombreLgac = nombreLgac;
        this.responsablesActivos = responsablesActivos;
    }

    public int getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(int idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public int getIdModalidad() {
        return idModalidad;
    }

    public String getGradoDirector() {
        return gradoDirector;
    }

    public void setGradoDirector(String gradoDirector) {
        this.gradoDirector = gradoDirector;
    }

    public void setIdModalidad(int idModalidad) {
        this.idModalidad = idModalidad;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getProyectoInvestigacion() {
        return proyectoInvestigacion;
    }

    public void setProyectoInvestigacion(String proyectoInvestigacion) {
        this.proyectoInvestigacion = proyectoInvestigacion;
    }

    public String getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(String lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    public int getMesesDuracionAproximada() {
        return mesesDuracionAproximada;
    }

    public void setMesesDuracionAproximada(int mesesDuracionAproximada) {
        this.mesesDuracionAproximada = mesesDuracionAproximada;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public int getNumAlumnosParticipantes() {
        return numAlumnosParticipantes;
    }

    public void setNumAlumnosParticipantes(int numAlumnosParticipantes) {
        this.numAlumnosParticipantes = numAlumnosParticipantes;
    }

    public String getDescripcionProyectoInvestigacion() {
        return descripcionProyectoInvestigacion;
    }

    public void setDescripcionProyectoInvestigacion(String descripcionProyectoInvestigacion) {
        this.descripcionProyectoInvestigacion = descripcionProyectoInvestigacion;
    }

    public String getDescripcionTrabajoRecepcional() {
        return descripcionTrabajoRecepcional;
    }

    public void setDescripcionTrabajoRecepcional(String descripcionTrabajoRecepcional) {
        this.descripcionTrabajoRecepcional = descripcionTrabajoRecepcional;
    }

    public String getResultadosEsperados() {
        return resultadosEsperados;
    }

    public void setResultadosEsperados(String resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
    }

    public String getBibliografiaRecomendada() {
        return bibliografiaRecomendada;
    }

    public void setBibliografiaRecomendada(String bibliografiaRecomendada) {
        this.bibliografiaRecomendada = bibliografiaRecomendada;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdEstadoATP() {
        return idEstadoATP;
    }

    public void setIdEstadoATP(int idEstadoATP) {
        this.idEstadoATP = idEstadoATP;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getNombreCA() {
        return nombreCA;
    }

    public void setNombreCA(String nombreCA) {
        this.nombreCA = nombreCA;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombreDirector() {
        return nombreDirector;
    }

    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }

    public String getCodirectores() {
        return codirectores;
    }

    public void setCodirectores(String codirectores) {
        this.codirectores = codirectores;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdLgac() {
        return idLgac;
    }

    public void setIdLgac(int idLgac) {
        this.idLgac = idLgac;
    }

    public String getNombreLgac() {
        return nombreLgac;
    }

    public void setNombreLgac(String nombreLgac) {
        this.nombreLgac = nombreLgac;
    }

    public int getResponsablesActivos() {
        return responsablesActivos;
    }

    public void setResponsablesActivos(int responsablesActivos) {
        this.responsablesActivos = responsablesActivos;
    }
    
    
}
