/*
* Título del programa: DAO de AnteproyectoEstudiante
* Autor: Jasiel Emir Zavaleta García y Omar Dylan Segura Platas
* Fecha de Creación: 11/06/2023
* Descripción: DAO para la comunicación entre la base de datos y el sistema para recuperar y 
* modificar la tabla de AnteproyectoEstudiante
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.AnteproyectoEstudiante;
import javafxsspger.utils.Constantes;

public class AnteproyectoEstudianteDAO {
    public static AnteproyectoEstudiante obtenerAnteproyectoEstudiante(int idEstudiante){
        AnteproyectoEstudiante respuesta = new AnteproyectoEstudiante();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String sentencia = "select idAnteproyecto,estado "
                        + "where idEstudiante = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareCall(sentencia);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                
                respuesta.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                respuesta.setEstado(resultado.
                        getString("estado"));
                respuesta.setIdEstudiante(idEstudiante);
            }catch(SQLException ex ){
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }            
        }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;        
    }
}
