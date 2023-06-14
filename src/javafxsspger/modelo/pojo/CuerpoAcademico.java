/*
* Título del programa: Clase POJO de Cuerpo Académico
* Autor: Enrique Gamboa Hernández, Jasiel Emir Zavaleta García
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para los Cuerpos Académicos y su información que proviene de la 
* persistencia del sistema
*/
package javafxsspger.modelo.pojo;

public class CuerpoAcademico {
    private int idCuerpoAcademico;
    private String nombre;
    private String clave;
    private int idGradoConsolidacion;
    private String gradoConsolidacion;
    private int idDependencia;
    private String dependencia;
    private int idResponsable;
    private String responsable;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(int idCuerpoAcademico, String nombre, String clave, 
            int idGradoConsolidacion, String gradoConsolidacion, int idDependencia, 
            String dependencia, int idResponsable, String responsable) {
        this.idCuerpoAcademico = idCuerpoAcademico;
        this.nombre = nombre;
        this.clave = clave;
        this.idGradoConsolidacion = idGradoConsolidacion;
        this.gradoConsolidacion = gradoConsolidacion;
        this.idDependencia = idDependencia;
        this.dependencia = dependencia;
        this.idResponsable = idResponsable;
        this.responsable = responsable;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdGradoConsolidacion() {
        return idGradoConsolidacion;
    }

    public void setIdGradoConsolidacion(int idGradoConsolidacion) {
        this.idGradoConsolidacion = idGradoConsolidacion;
    }

    public String getGradoConsolidacion() {
        return gradoConsolidacion;
    }

    public void setGradoConsolidacion(String gradoConsolidacion) {
        this.gradoConsolidacion = gradoConsolidacion;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
