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
public class ActividadRespuesta {
    int codigoRespuesta;
    ArrayList<Actividad> actividades;

    public ActividadRespuesta() {
    }

    public ActividadRespuesta(int codigoRespuesta, ArrayList<Actividad> actividades) {
        this.codigoRespuesta = codigoRespuesta;
        this.actividades = actividades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }
    
    
}
