/*
* Título del programa: Clase POJO de las LGAC
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para la respuesta del DAO de LGACs que recupera la información de la 
* persistencia del sistema
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

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
