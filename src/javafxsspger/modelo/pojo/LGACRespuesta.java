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
public class LGACRespuesta {
    private int codigoRespuesta;
    private ArrayList<LGAC> lgacs;

    public LGACRespuesta() {
    }

    public LGACRespuesta(int codigoRespuesta, ArrayList<LGAC> lgacs) {
        this.codigoRespuesta = codigoRespuesta;
        this.lgacs = lgacs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<LGAC> getLgacs() {
        return lgacs;
    }

    public void setLgacs(ArrayList<LGAC> lgacs) {
        this.lgacs = lgacs;
    }
}
