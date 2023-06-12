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
public class AvanceRespuesta {
    private int codigoRespuesta;
    private ArrayList<Avance> avances;

    public AvanceRespuesta() {
    }

    public AvanceRespuesta(int codigoRespuesta, ArrayList<Avance> avances) {
        this.codigoRespuesta = codigoRespuesta;
        this.avances = avances;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Avance> getAvances() {
        return avances;
    }

    public void setAvances(ArrayList<Avance> avances) {
        this.avances = avances;
    }
  
    
}
