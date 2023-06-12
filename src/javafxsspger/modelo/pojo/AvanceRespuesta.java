/*
* Título del programa: Clase POJO de AvanceRespuesta
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 09/05/2023
* Descripción: Clase modelo para la respuesta del DAO de los registros de Avances provenientes
* de la persistencia del sistema.
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

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

