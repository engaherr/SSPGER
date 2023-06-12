/*
* Título del programa: Clase POJO de las LGAC
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase modelo para la respuesta del DAO de LGACs que recupera la información de la 
* persistencia del sistema
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class LgacRespuesta {
    private int codigoRespuesta;
    private ArrayList<Lgac> lgacs;

    public LgacRespuesta() {
    }

    public LgacRespuesta(int codigoRespuesta, ArrayList<Lgac> lgacs) {
        this.codigoRespuesta = codigoRespuesta;
        this.lgacs = lgacs;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<Lgac> getLgacs() {
        return lgacs;
    }

    public void setLgacs(ArrayList<Lgac> lgacs) {
        this.lgacs = lgacs;
    }
}
