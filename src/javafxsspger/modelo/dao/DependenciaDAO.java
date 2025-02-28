/*
* Título del programa: DAO para las Dependencias de Educación Superior (DES)
* Autor: Jasiel Emir Zavaleta García
* Fecha: 09/06/2023
* Descripción: Se encarga de la correcta conexión y obtención de datos de la base de datos.
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
                    dependencia.setIdDependencia(resultado.getInt
                        ("idDependencia"));
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
