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
public class DependenciaRespuesta {
    private int codigoRespuesta;
    private ArrayList<Dependencia> dependencias;

    public DependenciaRespuesta() {
    }

    public DependenciaRespuesta(int codigoRespuesta, ArrayList<Dependencia> dependencias) {
        this.codigoRespuesta = codigoRespuesta;
        this.dependencias = dependencias;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Dependencia> getDependencias() {
        return dependencias;
    }

    public void setDependencias(ArrayList<Dependencia> dependencias) {
        this.dependencias = dependencias;
    }
    
    
}
