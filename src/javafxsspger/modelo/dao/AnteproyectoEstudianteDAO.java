/*
* Título del programa: DAO para la relación entre estudiante y anteproyecto
* Autor: Jasiel Emir Zavaleta García
* Fecha Creación: 10/06/2023
* Descripción: Clase de Acceso a la información de la base de datos correspondiente a la relación 
* entre el estudiante y un anteproyecto,los cuales tienen una tabla en la persistencia del sistema
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
