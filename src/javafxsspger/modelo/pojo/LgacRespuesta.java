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
public class LgacRespuesta {
    private ArrayList<Lgac> lgacs;
    private int codigoRespuesta;

    public LgacRespuesta() {
    }

    public LgacRespuesta(ArrayList<Lgac> lgacs, int codigoRespuesta) {
        this.lgacs = lgacs;
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Lgac> getLgacs() {
        return lgacs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setLgacs(ArrayList<Lgac> lgacs) {
        this.lgacs = lgacs;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
    
    
}
