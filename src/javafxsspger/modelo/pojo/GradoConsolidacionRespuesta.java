package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class GradoConsolidacionRespuesta {
    private int codigoRespuesta;
    private ArrayList<GradoConsolidacion> grados;

    public GradoConsolidacionRespuesta() {
    }

    public GradoConsolidacionRespuesta(int codigoRespuesta, ArrayList<GradoConsolidacion> grados) {
        this.codigoRespuesta = codigoRespuesta;
        this.grados = grados;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<GradoConsolidacion> getGrados() {
        return grados;
    }

    public void setGrados(ArrayList<GradoConsolidacion> grados) {
        this.grados = grados;
    }
    
}
