/*
* Título del programa: DAO de Dependencia
* Autor: Jasiel Emir Zavaleta García
* Fecha de Creación: 11/06/2023
* Descripción: DAo para establecer la comunicación entre la BD y el sistema para la gestion de las
* dependencias de los cuerpos académicos
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Dependencia;
import javafxsspger.modelo.pojo.DependenciaRespuesta;
import javafxsspger.utils.Constantes;

public class DependenciaDAO {
    public static DependenciaRespuesta recuperarDependencias(){
        DependenciaRespuesta  respuesta= new DependenciaRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Dependencia> dependencias = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "select idDependencia,nombre  from dependencia";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Dependencia dependencia = new Dependencia();
                    dependencia.setIdDependencia(resultado.getInt("idDependencia"));
                    dependencia.setNombre(resultado.getString("nombre"));
                    dependencias.add(dependencia);
                }
                conexionBD.close();
                respuesta.setDependencias(dependencias);
            }catch(SQLException ex){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
          respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
