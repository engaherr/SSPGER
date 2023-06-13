/*
* Título: Clase POJO para la respuesta de PeridosEscolares
* Autor: Enrique Gamboa Hernández
* Fecha de Creación: 10/06/2023
* Descripción: Clase modelo para la respuesta del DAO con la BD de los periodos escolares
*/
package javafxsspger.modelo.pojo;

import java.util.ArrayList;

public class PeriodoEscolarRespuesta {
    private int codigoRespuesta;
    private ArrayList<PeriodoEscolar> periodosEscolares;

    public PeriodoEscolarRespuesta() {
    }

    public PeriodoEscolarRespuesta(int codigoRespuesta,
            ArrayList<PeriodoEscolar> periodosEscolares) {
        this.codigoRespuesta = codigoRespuesta;
        this.periodosEscolares = periodosEscolares;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public ArrayList<PeriodoEscolar> getPeriodosEscolares() {
        return periodosEscolares;
    }

    public void setPeriodosEscolares(ArrayList<PeriodoEscolar> periodosEscolares) {
        this.periodosEscolares = periodosEscolares;
    }
}
