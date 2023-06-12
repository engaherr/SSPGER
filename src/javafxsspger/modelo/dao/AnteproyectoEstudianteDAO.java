/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.AnteproyectoEstudiante;
import javafxsspger.utils.Constantes;

/**
 *
 * @author jasie
 */
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
