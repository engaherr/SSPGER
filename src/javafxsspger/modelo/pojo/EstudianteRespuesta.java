/*
* Título del programa: Estudiante Respuesta
* Autor: Enrique Gamboa Hernández, Omar Dylan Segura Platas
* Fecha de Creación: 07/06/2023
* Descripción: Clase Respuesta para los estudiantes de la BD 
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class EstudianteRespuesta {
    private int codigoRespuesta;
    private ArrayList<Estudiante> estudiantes;

    public EstudianteRespuesta(int codigoRespuesta, ArrayList<Estudiante> estudiantes) {
        this.codigoRespuesta = codigoRespuesta;
        this.estudiantes = estudiantes;
    }

    public EstudianteRespuesta() {
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
