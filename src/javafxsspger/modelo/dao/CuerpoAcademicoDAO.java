/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.CuerpoAcademico;
import javafxsspger.modelo.pojo.CuerpoAcademicoRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author kikga
 */
public class CuerpoAcademicoDAO {
    public static CuerpoAcademicoRespuesta obtenerCuerposAcademicos(){
        CuerpoAcademicoRespuesta respuesta = new CuerpoAcademicoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        ArrayList<CuerpoAcademico> cuerposAcademicos = new ArrayList<>();
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try{
                String consulta = "select ca.idCuerpoAcademico,ca.nombre,ca.clave,d.nombre as "
                        + "'dependencia',ca.idGradoConsolidacion,ca.idDependencia,ca.idResponsable,"
                        + "gc.grado, concat(a.nombre,' ',a.apellidoPaterno,' ',a.apellidoMaterno) "
                        + "as 'responsable' "
                        + "from cuerpoacademico ca "
                        + "inner join academico a on a.idAcademico = ca.idResponsable "
                        + "inner join gradoconsolidacion gc on gc.idGradoConsolidacion = "
                        + "ca.idGradoConsolidacion "
                        + "inner join dependencia d on d.idDependencia = ca.idDependencia;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while(resultado.next()){
                    CuerpoAcademico cuerpoAcademico = new CuerpoAcademico();
                    cuerpoAcademico.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    cuerpoAcademico.setClave(resultado.getString("clave"));
                    cuerpoAcademico.setDependencia(resultado.getString("dependencia"));
                    cuerpoAcademico.setGradoConsolidacion("grado");
                    cuerpoAcademico.setIdDependencia(resultado.getInt("idDependencia"));
                    cuerpoAcademico.setIdGradoConsolidacion(
                            resultado.getInt("idGradoConsolidacion"));
                    cuerpoAcademico.setIdResponsable(resultado.getInt("idResponsable"));
                    cuerpoAcademico.setNombre(resultado.getString("nombre"));
                    cuerpoAcademico.setResponsable(resultado.getString("responsable"));
                    cuerposAcademicos.add(cuerpoAcademico);
                }
                respuesta.setCuerposAcademicos(cuerposAcademicos);
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    } 
}
