/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

/**
 *
 * @author jasie
 */
public class AcademicoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Academico> academicos;

    public AcademicoRespuesta() {
    }

    public AcademicoRespuesta(int codigoRespuesta, ArrayList<Academico> academicos) {
        this.codigoRespuesta = codigoRespuesta;
        this.academicos = academicos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Academico> getAcademicos() {
        return academicos;
    }

    public void setAcademicos(ArrayList<Academico> academicos) {
        this.academicos = academicos;
    }
    
    
}
