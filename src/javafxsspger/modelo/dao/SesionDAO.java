/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;

/**
 *
 * @author kikga
 */
public class SesionDAO {
    public static Academico verificarAcademicoSesion(String usuario, String password){
        Academico academicoVerificado = new Academico();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            try {
                String consulta = "select * from academico where noPersonal = ? and password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                academicoVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    academicoVerificado.setIdAcademico(resultado.getInt("idAcademico"));
                    academicoVerificado.setNombre(resultado.getString("nombre"));
                    academicoVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    academicoVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    academicoVerificado.setPassword(resultado.getString("password"));
                    academicoVerificado.setEmail(resultado.getString("email"));
                    academicoVerificado.setEsAdmin(resultado.getBoolean("esAdmin"));
                    academicoVerificado.setFoto(resultado.getBytes("foto"));
                    academicoVerificado.setNoPersonal(resultado.getInt("noPersonal"));
                }
                conexion.close();
            } catch (SQLException ex) {
                academicoVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            academicoVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return academicoVerificado;
    }
    
    public static Estudiante verificarEstudianteSesion(String usuario, String password){
        Estudiante estudianteVerificado = new Estudiante();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            try {
                String consulta = "select * from estudiante where matricula = ? and password = ?";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
                estudianteVerificado.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
                if(resultado.next()){
                    estudianteVerificado.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudianteVerificado.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudianteVerificado.setEmail(resultado.getString("email"));
                    estudianteVerificado.setFoto(resultado.getBytes("foto"));
                    estudianteVerificado.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudianteVerificado.setMatricula(resultado.getString("matricula"));
                    estudianteVerificado.setNombre(resultado.getString("nombre"));
                    estudianteVerificado.setPassword(resultado.getString("password"));
                }
                conexion.close();
            } catch (SQLException ex) {
                estudianteVerificado.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            estudianteVerificado.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return estudianteVerificado;
    }
}
