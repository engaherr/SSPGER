/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author kikga
 */
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
