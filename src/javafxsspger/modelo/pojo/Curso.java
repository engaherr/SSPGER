/*
* Título del programa: Clase POJO de Curso
* Autor: Omar Dylan Segura Platas
* Fecha Creación: 10/06/2023
* Descripción: Clase modelo para Curso y la información que lo integre
*/


package javafxsspger.modelo.pojo;


public class Curso {
    private int idCursoEE;
    private int NRC;
    private int idMateria;
    private int idPeridoEscolar;
    private int  idProfesor;
    private String nombreMateria;
    
    public Curso(){
    }

    public Curso(int idCursoEE, int NRC, int idMateria, int idPeridoEscolar, int idProfesor, String nombreMateria) {
        this.idCursoEE = idCursoEE;
        this.NRC = NRC;
        this.idMateria = idMateria;
        this.idPeridoEscolar = idPeridoEscolar;
        this.idProfesor = idProfesor;
        this.nombreMateria = nombreMateria;
    }

    
    
    
    public int getIdCursoEE() {
        return idCursoEE;
    }

    public void setIdCursoEE(int idCursoEE) {
        this.idCursoEE = idCursoEE;
    }

    public int getNRC() {
        return NRC;
    }

    public void setNRC(int NRC) {
        this.NRC = NRC;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdPeridoEscolar() {
        return idPeridoEscolar;
    }

    public void setIdPeridoEscolar(int idPeridoEscolar) {
        this.idPeridoEscolar = idPeridoEscolar;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }
    
    
    
}
