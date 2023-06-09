/*
* Título del programa: Clase POJO de Anteproyectos
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 09/05/2023
* Descripción: Clase modelo para la respuesta del DAO de los registros de Anteproyecto provenientes
* de la persistencia del sistema.
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

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
