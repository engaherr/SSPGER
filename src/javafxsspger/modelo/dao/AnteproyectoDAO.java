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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;

/**
 *
 * @author kikga
 */
public class AnteproyectoDAO {
    /*public static void obtenerAnteproyectosDisponibles(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select atp.idAnteproyecto, atp.idModalidad, "
                        + "atp.idCuerpoAcademico,proyectoInvestigacion,lineaInvestigacion,"
                        + "duracionAproximada,nombreTrabajo,requisitos,alumnosParticipantes,"
                        + "descripcionProyectoInvestigacion,descripcionTrabajoRecepcional," 
                        + "resultadosEsperados,bibliografiaRecomendada,comentarios,atp.idEstadoATP,"
                        + "idDirector,ca.nombre,´mod´.modalidad,concat(acad.nombre+\" \"+"
                        + "acad.apellidoPaterno+\" \"+acad.apellidoMaterno) as \"Director\", "
                        + "estado.estado, atp.idLgac, lgac.nombre from anteproyecto atp"
                        + "inner join academico acad on idAcademico = idDirector" 
                        + "inner join modalidad ´mod´ on ´mod´.idModalidad = atp.idModalidad" 
                        + "inner join estadoATP estado on estado.idEstadoATP = atp.idEstadoATP" 
                        + "inner join cuerpoacademico ca on ca.idCuerpoAcademico=atp.idCuerpoAcademico" 
                        + "inner join lgac on atp.idLgac = lgac.idLgac"
                        + "where ´mod´.modalidad = \"Disponible\";";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto
                }
            } catch (Exception e) {
            }
        }
    }*///TODO
    public static int guardarAnteproyecto(Anteproyecto anteproyectoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "insert into anteproyecto(idModalidad,idCuerpoAcademico,"
                        + "proyectoInvestigacion,lineaInvestigacion,duracionAproximada,nombreTrabajo,"
                        + "requisitos,alumnosParticipantes,descripcionProyectoInvestigacion,"
                        + "descripcionTrabajoRecepcional,resultadosEsperados,bibliografiaRecomendada"
                        + "idEstadoATP,idDirector,idLgac values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoNuevo.getIdModalidad());
                prepararSentencia.setInt(2, anteproyectoNuevo.getIdCuerpoAcademico());
                prepararSentencia.setString(3, anteproyectoNuevo.getProyectoInvestigacion());
                prepararSentencia.setString(4, anteproyectoNuevo.getLineaInvestigacion());
                prepararSentencia.setInt(5, anteproyectoNuevo.getMesesDuracionAproximada());
                prepararSentencia.setString(6, anteproyectoNuevo.getNombreTrabajo());
                prepararSentencia.setString(7, anteproyectoNuevo.getRequisitos());
                prepararSentencia.setInt(8, anteproyectoNuevo.getNumAlumnosParticipantes());
                prepararSentencia.setString(9, 
                        anteproyectoNuevo.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(10, 
                        anteproyectoNuevo.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(11, anteproyectoNuevo.getResultadosEsperados());
                prepararSentencia.setString(12, anteproyectoNuevo.getBibliografiaRecomendada());
                prepararSentencia.setInt(13, anteproyectoNuevo.getIdEstadoATP());
                prepararSentencia.setInt(14, anteproyectoNuevo.getIdDirector());
                prepararSentencia.setInt(15, anteproyectoNuevo.getIdLgac());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ?
                        Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
}
