/*
* Título del programa: DAO para el inicio de sesión
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 05/06/2023
* Descripción: Clase de acceso a la información para el inicio de sesión con credenciales
* que recupera de la base de datos el rol autoritario de Administrador que tiene una columna
* en la persistencia del sistema en Académico y verifica los roles de lógica del negocio
* Director de Anteproyecto y Responsable del Cuerpo Académico y los asigna a una clase Singleton
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.Estudiante;
import javafxsspger.utils.Constantes;

public class SesionDAO {
    public static Academico verificarAcademicoSesion(String usuario, String password){
        Academico academicoVerificado = new Academico();
        Connection conexion = ConexionBD.abrirConexionBD();
        if(conexion != null){
            try {
                String consulta = "select * from academico where noPersonal = ? and password = ?;";
                PreparedStatement prepararSentencia = conexion.prepareStatement(consulta);
                prepararSentencia.setString(1, usuario);
                prepararSentencia.setString(2, password);
                ResultSet resultado = prepararSentencia.executeQuery();
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
                    academicoVerificado.setCodigoRespuesta(
                    verificarRolAcademico(academicoVerificado, conexion));
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

    private static int verificarRolAcademico(Academico academicoVerificado, Connection conexionBD){
        int respuesta = Constantes.OPERACION_EXITOSA;
        try {
            String consulta = "select count(*) as numAnteproyectos "
                    + "from anteproyecto where idDirector = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, academicoVerificado.getIdAcademico());
            ResultSet resultado = prepararSentencia.executeQuery();
            if(resultado.next()){
                int numAnteproyectos = resultado.getInt("numAnteproyectos");
                
                if(numAnteproyectos > 0)
                    academicoVerificado.setEsDirector(true);
            }
            String consulta1 = "select idCuerpoAcademico "
                    + "from cuerpoacademico where idResponsable = ?";
            PreparedStatement prepararSentencia1 = conexionBD.prepareStatement(consulta1);
            prepararSentencia1.setInt(1, academicoVerificado.getIdAcademico());
            ResultSet resultado1 = prepararSentencia1.executeQuery();
            if(resultado1.next()){
                int idCAResponsable = resultado1.getInt("idCuerpoAcademico");
                academicoVerificado.setIdCAResponsable(idCAResponsable);
                academicoVerificado.setEsResponsableCA(true);
            }
        } catch (SQLException ex) {
            respuesta = Constantes.ERROR_CONSULTA;
        }
        return respuesta;
    }

}
