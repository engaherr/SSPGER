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
