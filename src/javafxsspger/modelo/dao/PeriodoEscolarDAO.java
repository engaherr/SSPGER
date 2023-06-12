/*
* Título del programa: DAO para periodos escolares
* Autor: Enrique Gamboa Hernández 
* Fecha de Creación: 10/06/2023
* Descripción: Clase DAO para establecer comunicación con la persistencia del sistema y recuperar
* los periodos escolares de la base de datos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.PeriodoEscolar;
import javafxsspger.modelo.pojo.PeriodoEscolarRespuesta;
import javafxsspger.utils.Constantes;

public class PeriodoEscolarDAO {
    public static PeriodoEscolarRespuesta obtenerPeriodosEscolares(){
        PeriodoEscolarRespuesta respuesta = new PeriodoEscolarRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<PeriodoEscolar> periodosEscolares = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select * from periodoescolar";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    PeriodoEscolar periodoEscolar = new PeriodoEscolar();
                    periodoEscolar.setFechaFin(resultado.getString("fechaFin"));
                    periodoEscolar.setFechaInicio(resultado.getString("fechaInicio"));
                    periodoEscolar.setIdPeriodoEscolar(resultado.getInt("idPeriodoEscolar"));
                    periodoEscolar.setNombre(resultado.getString("nombre"));
                    periodosEscolares.add(periodoEscolar);
                }
                conexionBD.close();
                respuesta.setPeriodosEscolares(periodosEscolares);
            }catch(SQLException e){
                e.printStackTrace();
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
