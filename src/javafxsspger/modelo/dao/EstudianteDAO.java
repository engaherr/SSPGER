/*
* Título del programa: DAO para los estudiantes
* Autor: Omar Dylan Segura Platas, Enrique Gamboa Hernández
* Fecha Creación: 07/06/2023
* Descripción: Clase de acceso a la información para los estudiantes dentro de la base de datos.
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.modelo.pojo.EstudianteRespuesta;
import javafxsspger.utils.Constantes;

public class EstudianteDAO {
    
      public static EstudianteRespuesta obtenerEstudiantes(){
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idEstudiante, email, nombre, apellidoPaterno, apellidoMaterno, matricula\n" +
                "FROM estudiante;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setEmail(resultado.getString("email"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiantes.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantes);
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    } 
      
    public static EstudianteRespuesta obtenerDatosDelEstudiante(String matricula) {
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idEstudiante, email, nombre, apellidoPaterno, apellidoMaterno, matricula "
                        + "FROM estudiante WHERE matricula = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, matricula);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setEmail(resultado.getString("email"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiantes.add(estudiante);
                }
                respuesta.setEstudiantes(estudiantes);
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }

    public static boolean verificarTieneAnteproyecto(int idEstudiante) {
        EstudianteRespuesta respuesta = new EstudianteRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        boolean tieneAnteproyecto = false;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT EXISTS(SELECT 1 FROM estudiante WHERE idEstudiante = ? AND idAnteproyecto IS NOT NULL) AS tiene_anteproyecto";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idEstudiante);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    tieneAnteproyecto = resultado.getBoolean("tiene_anteproyecto");
                }
                conexionBD.close();
            } catch (SQLException ex) {
             respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        } else {
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return tieneAnteproyecto;
    }
}
