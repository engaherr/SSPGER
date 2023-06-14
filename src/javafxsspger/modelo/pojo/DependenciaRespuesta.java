/*
* Título del programa: Clase POJO de DependenciaRespuesta
* Autor: Jasiel Emir Zavaleta García
* Fecha Creación: 9/06/2023
* Descripción: Clase modelo para la respuesta del DAO de Dependencias
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

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
