/*
* Título: Clase Respuesta POJO para Materia
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Fecha Modelo para la respuesta del DAO con la BD de materias
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class MateriaRespuesta {
    private int codigoRespuesta;
    private ArrayList<Materia> materias;

    public MateriaRespuesta() {
    }

    public MateriaRespuesta(int codigoRespuesta, ArrayList<Materia> materias) {
        this.codigoRespuesta = codigoRespuesta;
        this.materias = materias;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }
}
