/*
* Título de programa: DAO de Academico
* Autor: Jasiel Emir Zavaleta García
* Fecha de Creación 11/06/2023
* Descripción: DAO para la comunicación entre la BD y los academicos registrados ahí
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Academico;
import javafxsspger.modelo.pojo.AcademicoRespuesta;
import javafxsspger.utils.Constantes;

public class AcademicoDAO {
    public static AcademicoRespuesta recuperarAcademicosSinCA(){
       AcademicoRespuesta respuesta = new AcademicoRespuesta();
       respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
       ArrayList<Academico> academicos = new ArrayList<>();       
       Connection conexionBD = ConexionBD.abrirConexionBD();
       if(conexionBD != null){
           try{
                String sentencia = "select idAcademico,nombre,apellidoPaterno,noPersonal,\n" +
                         "from academico\n" +
                         "where idCuerpoAcademico = 'null'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    Academico academico = new Academico();
                    academico.setIdAcademico(resultado.getInt("idAcademico"));
                    academico.setNombre(resultado.getString("nombre"));
                    academico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    academico.setNoPersonal(resultado.getInt("noPersonal"));
                    academicos.add(academico);
                }
                respuesta.setAcademicos(academicos);
                conexionBD.close();
           }catch(SQLException ex){
               respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
           }
       }else{
           respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
       }
       return respuesta;
    }
}
