/*
* Título del programa: Clase POJO de los Cuerpos Académicos
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para la respuesta del DAO de Cuerpo Académico que recupera información
* de la persistencia del sistema
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class CuerpoAcademicoRespuesta {
    private int codigoRespuesta;
    private ArrayList<CuerpoAcademico> cuerposAcademicos;

    public CuerpoAcademicoRespuesta() {
    }

    public CuerpoAcademicoRespuesta(int codigoRespuesta, ArrayList<CuerpoAcademico> cuerposAcademicos) {
        this.codigoRespuesta = codigoRespuesta;
        this.cuerposAcademicos = cuerposAcademicos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<CuerpoAcademico> getCuerposAcademicos() {
        return cuerposAcademicos;
    }

    public void setCuerposAcademicos(ArrayList<CuerpoAcademico> cuerposAcademicos) {
        this.cuerposAcademicos = cuerposAcademicos;
    }
}
