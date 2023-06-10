/*
* Título del programa: Clase POJO de ActividadRespuesta
* Autor: Omar Dylan Segura Platas
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para la respuesta del DAO de Actividad.
*/

package javafxsspger.modelo.pojo;

import java.util.ArrayList;


public class ActividadRespuesta {
    
    private int codigoRespuesta;
    private ArrayList<Actividad> actividades;
    
    public ActividadRespuesta(){
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
    

