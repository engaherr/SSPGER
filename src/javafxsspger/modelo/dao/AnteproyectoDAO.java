/*
* Título del programa: DAO para Anteproyectos
* Autor: Enrique Gamboa Hernández
* Fecha Creación: 11/05/2023
* Descripción: Clase de Acceso a la información de la base de datos correspondiente a los 
* anteproyectos los cuales tienen una tabla en la persistencia del sistema
*/
package javafxsspger.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafxsspger.modelo.ConexionBD;
import javafxsspger.modelo.pojo.Anteproyecto;
import javafxsspger.modelo.pojo.AnteproyectoRespuesta;
import javafxsspger.utils.Constantes;

public class AnteproyectoDAO {
    public static AnteproyectoRespuesta obtenerInformacionAnteproyectos(){
        AnteproyectoRespuesta respuesta = new AnteproyectoRespuesta();
        respuesta.setCodigoRespuesta(Constantes.OPERACION_EXITOSA);
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String consulta = "select atp.idAnteproyecto, atp.idModalidad, codirectores,"
                        + "atp.idCuerpoAcademico,proyectoInvestigacion,lineaInvestigacion,"
                        + "duracionAproximada,nombreTrabajo,requisitos,alumnosParticipantes,"
                        + "descripcionProyectoInvestigacion,descripcionTrabajoRecepcional," 
                        + "resultadosEsperados,bibliografiaRecomendada,comentarios,atp.idEstadoATP,"
                        + "idDirector,ca.nombre as nombreCA,´mod´.modalidad,concat(acad.nombre,' ',"
                        + "acad.apellidoPaterno,' ',acad.apellidoMaterno) as 'director', "
                        + "estado.estado, atp.idLgac, lgac.nombre as nombreLgac "
                        + "from anteproyecto atp "
                        + "inner join academico acad on idAcademico = idDirector " 
                        + "inner join modalidad ´mod´ on ´mod´.idModalidad = atp.idModalidad " 
                        + "inner join estadoATP estado on estado.idEstadoATP = atp.idEstadoATP " 
                        + "inner join cuerpoacademico ca on "
                        + "ca.idCuerpoAcademico=atp.idCuerpoAcademico " 
                        + "inner join lgac on atp.idLgac = lgac.idLgac ";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                ArrayList<Anteproyecto> anteproyectosConsulta = new ArrayList();
                while(resultado.next()){
                    Anteproyecto anteproyecto = new Anteproyecto();
                    anteproyecto.setIdAnteproyecto(resultado.getInt("idAnteproyecto"));
                    anteproyecto.setBibliografiaRecomendada(
                            resultado.getString("bibliografiaRecomendada"));
                    anteproyecto.setDescripcionProyectoInvestigacion(
                            resultado.getString("descripcionProyectoInvestigacion"));
                    anteproyecto.setDescripcionTrabajoRecepcional(
                            resultado.getString("descripcionTrabajoRecepcional"));
                    anteproyecto.setEstado(resultado.getString("estado"));
                    anteproyecto.setIdCuerpoAcademico(resultado.getInt("idCuerpoAcademico"));
                    anteproyecto.setIdDirector(resultado.getInt("idDirector"));
                    anteproyecto.setIdEstadoATP(resultado.getInt("idEstadoATP"));
                    anteproyecto.setIdLgac(resultado.getInt("idLgac"));
                    anteproyecto.setIdModalidad(resultado.getInt("idModalidad"));
                    anteproyecto.setLineaInvestigacion(resultado.getString("lineaInvestigacion"));
                    anteproyecto.setMesesDuracionAproximada(resultado.getInt("duracionAproximada"));
                    anteproyecto.setModalidad(resultado.getString("modalidad"));
                    anteproyecto.setNombreCA(resultado.getString("nombreCA"));
                    anteproyecto.setNombreDirector(resultado.getString("director"));
                    anteproyecto.setNombreLgac(resultado.getString("nombreLgac"));
                    anteproyecto.setNombreTrabajo(resultado.getString("nombreTrabajo"));
                    anteproyecto.setNumAlumnosParticipantes(
                            resultado.getInt("alumnosParticipantes"));
                    anteproyecto.setProyectoInvestigacion(
                            resultado.getString("proyectoInvestigacion"));
                    anteproyecto.setComentarios(resultado.getString("comentarios"));
                    anteproyecto.setRequisitos(resultado.getString("requisitos"));
                    anteproyecto.setCodirectores(resultado.getString("codirectores"));
                    anteproyecto.setResultadosEsperados(resultado.getString("resultadosEsperados"));
                    anteproyectosConsulta.add(anteproyecto);
                }
                respuesta.setAnteproyectos(anteproyectosConsulta);
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.setCodigoRespuesta(Constantes.ERROR_CONSULTA);
            }
        }else{
            respuesta.setCodigoRespuesta(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static int guardarAnteproyecto(Anteproyecto anteproyectoNuevo){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "insert into anteproyecto(idModalidad,idCuerpoAcademico,"
                        + "proyectoInvestigacion,lineaInvestigacion,duracionAproximada,"
                        + "nombreTrabajo,"
                        + "requisitos,alumnosParticipantes,descripcionProyectoInvestigacion,"
                        + "descripcionTrabajoRecepcional,resultadosEsperados,"
                        + "bibliografiaRecomendada,"
                        + "idEstadoATP,idDirector,idLgac,codirectores)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                try{
                    prepararSentencia.setInt(1, anteproyectoNuevo.getIdModalidad());
                }catch(NullPointerException npe){
                    prepararSentencia.setNull(1, java.sql.Types.INTEGER);
                }
                try{                
                    prepararSentencia.setInt(2, anteproyectoNuevo.getIdCuerpoAcademico());
                }catch(NullPointerException npe){
                    prepararSentencia.setNull(2, java.sql.Types.INTEGER);
                }
                prepararSentencia.setString(3, anteproyectoNuevo.getProyectoInvestigacion());
                prepararSentencia.setString(4, anteproyectoNuevo.getLineaInvestigacion());
                try{
                    prepararSentencia.setInt(5, anteproyectoNuevo.getMesesDuracionAproximada());
                }catch(NullPointerException npe){
                    prepararSentencia.setNull(5, java.sql.Types.INTEGER);
                }
                prepararSentencia.setString(6, anteproyectoNuevo.getNombreTrabajo());   
                prepararSentencia.setString(7, anteproyectoNuevo.getRequisitos());
                try{
                    prepararSentencia.setInt(8, anteproyectoNuevo.getNumAlumnosParticipantes());
                }catch(NullPointerException npe){
                    prepararSentencia.setNull(8, java.sql.Types.INTEGER);
                }
                prepararSentencia.setString(9, 
                        anteproyectoNuevo.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(10, 
                        anteproyectoNuevo.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(11, anteproyectoNuevo.getResultadosEsperados());
                prepararSentencia.setString(12, anteproyectoNuevo.getBibliografiaRecomendada());
                prepararSentencia.setInt(13, anteproyectoNuevo.getIdEstadoATP());
                prepararSentencia.setInt(14, anteproyectoNuevo.getIdDirector());
                try{
                    prepararSentencia.setInt(15, anteproyectoNuevo.getIdLgac());
                }catch(NullPointerException npe){
                    prepararSentencia.setNull(15, java.sql.Types.INTEGER);
                }
                prepararSentencia.setString(16, anteproyectoNuevo.getCodirectores());
                System.out.println("Fin setters");
                int filasAfectadas = prepararSentencia.executeUpdate();
                System.out.println("executeUpdate()");
                respuesta = (filasAfectadas == 1) ?
                        Constantes.OPERACION_EXITOSA : Constantes.ERROR_CONSULTA;
                System.out.println(filasAfectadas);
                conexionBD.close();
            } catch (SQLException ex) {
                ex.initCause(ex);
                ex.printStackTrace();
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else{
            respuesta = Constantes.ERROR_CONEXION;
        }
        return respuesta;
    }
    
    public static int modificarAnteproyecto(Anteproyecto anteproyectoEdicion){
        int respuesta;
        Connection conexionBD = ConexionBD.abrirConexionBD();
        if(conexionBD != null){
            try {
                String sentencia = "UPDATE anteproyecto SET idModalidad = ?,idCuerpoAcademico = ?,"
                        + "proyectoInvestigacion = ?,lineaInvestigacion = ?,duracionAproximada = ?,"
                        + "nombreTrabajo = ?,requisitos = ?,alumnosParticipantes = ?,"
                        + "descripcionProyectoInvestigacion = ?,descripcionTrabajoRecepcional = ?,"
                        + "resultadosEsperados = ?,bibliografiaRecomendada = ?,comentarios = ?,"
                        + "idEstadoATP = ?,idLgac = ?,codirectores = ? where idAnteproyecto = ?;";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setInt(1, anteproyectoEdicion.getIdModalidad());
                prepararSentencia.setInt(2, anteproyectoEdicion.getIdCuerpoAcademico());
                prepararSentencia.setString(3, anteproyectoEdicion.getProyectoInvestigacion());
                prepararSentencia.setString(4, anteproyectoEdicion.getLineaInvestigacion());
                prepararSentencia.setInt(5, anteproyectoEdicion.getMesesDuracionAproximada());
                prepararSentencia.setString(6, anteproyectoEdicion.getNombreTrabajo());
                prepararSentencia.setString(7, anteproyectoEdicion.getRequisitos());
                prepararSentencia.setInt(8, anteproyectoEdicion.getNumAlumnosParticipantes());
                prepararSentencia.setString(9,
                        anteproyectoEdicion.getDescripcionProyectoInvestigacion());
                prepararSentencia.setString(10, anteproyectoEdicion.getDescripcionTrabajoRecepcional());
                prepararSentencia.setString(11, anteproyectoEdicion.getResultadosEsperados());
                prepararSentencia.setString(12, anteproyectoEdicion.getBibliografiaRecomendada());
                prepararSentencia.setString(13, anteproyectoEdicion.getComentarios());
                prepararSentencia.setInt(14, anteproyectoEdicion.getIdEstadoATP());
                prepararSentencia.setInt(15, anteproyectoEdicion.getIdLgac());
                prepararSentencia.setString(16, anteproyectoEdicion.getCodirectores());
                prepararSentencia.setInt(17, anteproyectoEdicion.getIdAnteproyecto());
                int filasAfectadas = prepararSentencia.executeUpdate();
                respuesta = (filasAfectadas == 1) ? Constantes.OPERACION_EXITOSA : 
                        Constantes.ERROR_CONSULTA;
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta = Constantes.ERROR_CONSULTA;
            }
        }else
            respuesta = Constantes.ERROR_CONEXION;
        return respuesta;
    }
    
    public static String obtenerNombreTrabajoAnteproyecto(int idEstudiante) {
    int respuesta;
    String nombreTrabajo = null;
    Connection conexionBD = ConexionBD.abrirConexionBD();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT a.nombreTrabajo " +
                    "FROM anteproyecto AS a " +
                    "JOIN estudiante AS e ON a.idAnteproyecto = e.idAnteproyecto " +
                    "WHERE e.idEstudiante = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idEstudiante);
            ResultSet resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                nombreTrabajo = resultado.getString("nombreTrabajo");
            }
            conexionBD.close();
        } catch (SQLException ex) {
              respuesta = Constantes.ERROR_CONSULTA;
        }
    } else {
         respuesta = Constantes.ERROR_CONEXION;
    }
    return nombreTrabajo;
}

    
    
}
