/*
* Título del programa: Clase POJO de las Modalidades de Anteproyecto
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para la respuesta del DAO de las modalidades del anteproyecto que las 
* recupera de la persistencia del sistema
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class ModalidadRespuesta {
    private int codigoRespuesta;
    private ArrayList<Modalidad> modalidades;

    public ModalidadRespuesta() {
    }

    public ModalidadRespuesta(int codigoRespuesta, ArrayList<Modalidad> modalidades) {
        this.codigoRespuesta = codigoRespuesta;
        this.modalidades = modalidades;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Modalidad> getModalidades() {
        return modalidades;
    }

    public void setModalidades(ArrayList<Modalidad> modalidades) {
        this.modalidades = modalidades;
    }
}
