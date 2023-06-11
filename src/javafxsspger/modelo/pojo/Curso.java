/*
* Título del programa: POJO del Curso
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase Modelo para los cursos que se vayan a registrar en la BD
*/
package javafxsspger.modelo.pojo;

public class Curso {
    private int idCursoEE;
    private int NRC;
    private int idMateria;
    private int idPeriodoEscolar;
    private int idProfesor;

    public Curso(int idCursoEE, int NRC, int idMateria, int idPeriodoEscolar, int idProfesor) {
        this.idCursoEE = idCursoEE;
        this.NRC = NRC;
        this.idMateria = idMateria;
        this.idPeriodoEscolar = idPeriodoEscolar;
        this.idProfesor = idProfesor;
    }

    public Curso() {
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

    public int getIdPeriodoEscolar() {
        return idPeriodoEscolar;
    }

    public void setIdPeriodoEscolar(int idPeriodoEscolar) {
        this.idPeriodoEscolar = idPeriodoEscolar;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }
}
