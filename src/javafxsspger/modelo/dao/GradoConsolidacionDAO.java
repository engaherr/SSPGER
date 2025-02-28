/*
* Título del programa: DAO para los grados de consolidación de un cuerpo académico
* Autor: Jasiel Emir Zavaleta García, Dylan Omar Segura Platas
* Fecha Creación: 10/06/2023
* Descripción: Clase de Acceso a la información de la base de datos correspondiente a las 
* actividades los cuales tienen una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.GradoConsolidacion;
import javafxsspger.modelo.pojo.GradoConsolidacionRespuesta;
import javafxsspger.utils.Constantes;

public class GradoConsolidacionDAO {
    public static GradoConsolidacionRespuesta recuperarGradosConsolidacion(){
        GradoConsolidacionRespuesta  respuesta= new GradoConsolidacionRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<GradoConsolidacion> gradosConsolidacion = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "select idGradoConsolidacion,nombre  from gradoconsolidacion";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    GradoConsolidacion grado = new GradoConsolidacion();
                    grado.setIdGradoConsolidacion(resultado.getInt
                            ("idGradoConsolidacion"));
                    grado.setNombre(resultado.getString("nombre"));
                    gradosConsolidacion.add(grado);
                }
                conexionBD.close();
                respuesta.setGrados(gradosConsolidacion);
            }catch(SQLException ex){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
          respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }    
}
