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
public class AnteproyectoRespuesta {
    private int codigoRespuesta;
    private ArrayList<Anteproyecto> anteproyectos;

    public AnteproyectoRespuesta() {
    }

    public AnteproyectoRespuesta(int codigoRespuesta, ArrayList<Anteproyecto> anteproyectos) {
        this.codigoRespuesta = codigoRespuesta;
        this.anteproyectos = anteproyectos;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Anteproyecto> getAnteproyectos() {
        return anteproyectos;
    }

    public void setAnteproyectos(ArrayList<Anteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }
    
}
